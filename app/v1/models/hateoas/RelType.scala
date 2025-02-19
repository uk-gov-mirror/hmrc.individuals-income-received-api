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

package v1.models.hateoas

object RelType {
  val AMEND_SAVINGS_INCOME = "create-and-amend-savings-income"
  val DELETE_SAVINGS_INCOME = "delete-savings-income"

  val AMEND_INSURANCE_POLICIES_INCOME = "create-and-amend-insurance-policies-income"
  val DELETE_INSURANCE_POLICIES_INCOME = "delete-insurance-policies-income"

  val AMEND_FOREIGN_INCOME = "create-and-amend-foreign-income"
  val DELETE_FOREIGN_INCOME = "delete-foreign-income"

  val AMEND_PENSIONS_INCOME = "create-and-amend-pensions-income"
  val DELETE_PENSIONS_INCOME = "delete-pensions-income"

  val AMEND_OTHER_INCOME = "create-and-amend-other-income"
  val DELETE_OTHER_INCOME = "delete-other-income"

  val AMEND_OTHER_EMPLOYMENT_INCOME = "create-and-amend-employments-other-income"
  val DELETE_OTHER_EMPLOYMENT_INCOME = "delete-employments-other-income"

  val AMEND_DIVIDENDS_INCOME = "create-and-amend-dividends-income"
  val DELETE_DIVIDENDS_INCOME = "delete-dividends-income"

  val LIST_EMPLOYMENTS = "list-employments"
  val RETRIEVE_EMPLOYMENT = "retrieve-employment"
  val IGNORE_EMPLOYMENT = "ignore-employment"
  val UNIGNORE_EMPLOYMENT = "unignore-employment"

  val ADD_CUSTOM_EMPLOYMENT = "add-custom-employment"
  val AMEND_CUSTOM_EMPLOYMENT = "amend-custom-employment"
  val DELETE_CUSTOM_EMPLOYMENT = "delete-custom-employment"

  val AMEND_EMPLOYMENT_FINANCIAL_DETAILS = "create-and-amend-employment-financial-details"
  val DELETE_EMPLOYMENT_FINANCIAL_DETAILS = "delete-employment-financial-details"

  val SELF = "self"
}
