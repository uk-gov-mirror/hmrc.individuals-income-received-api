/*
 * Copyright 2021 HM Revenue & Customs
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

package v1.models.response.retrieveFinancialDetails

import play.api.libs.json.{JsError, JsObject, JsValue, Json}
import support.UnitSpec

class DeductionsSpec extends UnitSpec {
  val json: JsValue = Json.parse(
    """
      |{
      |  "studentLoans": {
      |    "uglDeductionAmount": 200.33,
      |    "pglDeductionAmount": 300.33
      |  }
      |}
    """.stripMargin
  )

  val studentLoansModel: StudentLoans = StudentLoans(
    uglDeductionAmount = Some(200.33),
    pglDeductionAmount = Some(300.33)
  )

  val model: Deductions = Deductions(
    studentLoans = Some(studentLoansModel)
  )

  "Deductions" when {
    "read from valid JSON" should {
      "produce the expected 'Deductions' object" in{
        json.as[Deductions] shouldBe model
      }
    }

    "read from JSON with an empty or missing 'StudentLoans' field" should {
      "produce an empty 'Deductions' object" in {
        val invalidJson: JsValue = Json.parse(
          """
            |{
            |  "studentLoans": {
            |  }
            |}
          """.stripMargin
        )

        invalidJson.as[Deductions] shouldBe Deductions.empty
        JsObject.empty.as[Deductions] shouldBe Deductions.empty
      }
    }

    "read from invalid JSON" should {
      "produce a JsError" in {
        val invalidJson: JsValue = Json.parse(
          """
            |{
            |  "studentLoans": {
            |    "uglDeductionAmount": false
            |  }
            |}
          """.stripMargin
        )

        invalidJson.validate[Deductions] shouldBe a[JsError]
      }
    }

    "written to JSON" should {
      "produce the expected JsObject" in {
        Json.toJson(model) shouldBe json
      }
    }
  }
}
