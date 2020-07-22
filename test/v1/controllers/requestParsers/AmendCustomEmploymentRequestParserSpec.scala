/*
 * Copyright 2020 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package v1.controllers.requestParsers

import play.api.libs.json.{JsValue, Json}
import play.api.mvc.AnyContentAsJson
import support.UnitSpec
import uk.gov.hmrc.domain.Nino
import v1.mocks.validators.MockAmendCustomEmploymentValidator
import v1.models.errors._
import v1.models.request.amendCustomEmployment._

class AmendCustomEmploymentRequestParserSpec extends UnitSpec {

  private val nino: String = "AA123456B"
  private val taxYear: String = "2017-18"
  private val employmentId = "4557ecb5-fd32-48cc-81f5-e6acd1099f3c"

  private val validRequestJson: JsValue = Json.parse(
    """
      |{
      |  "employerRef": "123/AZ12334",
      |  "employerName": "AMD infotech Ltd",
      |  "startDate": "2019-01-01",
      |  "cessationDate": "2020-06-01",
      |  "payrollId": "124214112412"
      |}
    """.stripMargin
  )

  private val validRawBody = AnyContentAsJson(validRequestJson)

  private val amendCustomEmploymentRawData = AmendCustomEmploymentRawData(
    nino = nino,
    taxYear = taxYear,
    employmentId = employmentId,
    body = validRawBody
  )

  private val amendCustomEmploymentBodyModel = AmendCustomEmploymentRequestBody(
    employerRef = Some("123/AZ12334"),
    employerName = "AMD infotech Ltd",
    startDate = "2019-01-01",
    cessationDate = Some("2020-06-01"),
    payrollId = Some("124214112412")
  )

  private val amendCustomEmploymentRequest = AmendCustomEmploymentRequest(
    nino = Nino(nino),
    taxYear = taxYear,
    employmentId = employmentId,
    body = amendCustomEmploymentBodyModel
  )

  trait Test extends MockAmendCustomEmploymentValidator {
    lazy val parser: AmendCustomEmploymentRequestParser = new AmendCustomEmploymentRequestParser(
      validator = mockAmendCustomEmploymentValidator
    )
  }

  "parse" should {
    "return a request object" when {
      "valid request data is supplied" in new Test {
        MockAmendCustomEmploymentValidator.validate(amendCustomEmploymentRawData).returns(Nil)
        parser.parseRequest(amendCustomEmploymentRawData) shouldBe Right(amendCustomEmploymentRequest)
      }
    }

    "return an ErrorWrapper" when {
      "a single validation error occurs" in new Test {
        MockAmendCustomEmploymentValidator.validate(amendCustomEmploymentRawData.copy(nino = "notANino"))
          .returns(List(NinoFormatError))

        parser.parseRequest(amendCustomEmploymentRawData.copy(nino = "notANino")) shouldBe
          Left(ErrorWrapper(None, NinoFormatError, None))
      }

      "multiple path parameter validation errors occur" in new Test {
        MockAmendCustomEmploymentValidator.validate(amendCustomEmploymentRawData.copy(nino = "notANino", taxYear = "notATaxYear"))
          .returns(List(NinoFormatError, TaxYearFormatError))

        parser.parseRequest(amendCustomEmploymentRawData.copy(nino = "notANino", taxYear = "notATaxYear")) shouldBe
          Left(ErrorWrapper(None, BadRequestError, Some(Seq(NinoFormatError, TaxYearFormatError))))
      }

      "multiple field value validation errors occur" in new Test {

        private val invalidValueRequestJson: JsValue = Json.parse(
          s"""
             |{
             |  "employerRef": "notValid",
             |  "employerName": "${"a" * 75}",
             |  "startDate": "notValid",
             |  "cessationDate": "notValid",
             |  "payrollId": "${"b" * 75}"
             |}
            """.stripMargin
        )

        private val invalidValueRawBody = AnyContentAsJson(invalidValueRequestJson)

        private val errors = List(
          EmployerRefFormatError,
          EmployerNameFormatError,
          StartDateFormatError,
          CessationDateFormatError,
          PayrollIdFormatError
        )

        MockAmendCustomEmploymentValidator.validate(amendCustomEmploymentRawData.copy(body = invalidValueRawBody))
          .returns(errors)

        parser.parseRequest(amendCustomEmploymentRawData.copy(body = invalidValueRawBody)) shouldBe
          Left(ErrorWrapper(None, BadRequestError, Some(errors)))
      }
    }
  }
}