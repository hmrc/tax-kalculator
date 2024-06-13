/*
 * Copyright 2024 HM Revenue & Customs
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
package uk.gov.hmrc.calculator.utils.clarification

// Description added for readability clarifying what each Clarification enum represent, no real usage in code yet.
enum class Clarification(val priority: Int, private val description: String) {
    HAVE_STATE_PENSION(1, "Have state pension."),
    HAVE_NO_STATE_PENSION(2, "Have no state pension."),
    INCOME_OVER_100K_WITH_TAPERING(3, "Income over £100K with default 1257L tax code."),
    NO_TAX_CODE_SUPPLIED(4, "Did not provide a tax code."),
    SCOTTISH_INCOME_APPLIED(5, "Applied Scottish Income Tax rates."),
    SCOTTISH_CODE_BUT_OTHER_RATE(6, "Applied Scottish Income Tax rates because you used a Scottish tax code."),
    NON_SCOTTISH_CODE_BUT_SCOTTISH_RATE(7, "Applied Scottish Income Tax rates as you said you pay Scottish Income Tax."),
    K_CODE(8, "Tax code started with K."),
    PENSION_EXCEED_ANNUAL_ALLOWANCE(9, "Pension contribution amount is higher than annual allowance."),
    PENSION_BELOW_ANNUAL_ALLOWANCE(10, "Pension contribution amount is below than annual allowance."),
    INCOME_BELOW_STUDENT_AND_POSTGRAD_LOAN(11, "Income is below the level needed to start repaying student/postgrad loan."),
    INCOME_BELOW_STUDENT_LOAN(12, "Income is below the level needed to start repaying student loan."),
    INCOME_BELOW_POSTGRAD_LOAN(13, "Income is below the level needed to start repaying postgrad loan."),
    INCOME_BELOW_STUDENT_BUT_ABOVE_POSTGRAD_LOAN(
        14,
        "Income is below the level needed to start repaying student loan, but high enough to repay postgrad loan"
    ),
    INCOME_OVER_100K(15, "Income over £100k"),
    PENSION_EXCEED_INCOME(16, "Pension contribution amount is higher than income."),
}
