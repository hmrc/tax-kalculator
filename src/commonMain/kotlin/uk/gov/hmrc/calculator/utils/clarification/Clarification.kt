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
enum class Clarification(private val description: String) {
    NO_TAX_CODE_SUPPLIED("Did not provide a tax code."),
    HAVE_STATE_PENSION("Have state pension."),
    HAVE_NO_STATE_PENSION("Have no state pension."),
    SCOTTISH_INCOME_APPLIED("Applied Scottish Income Tax rates."),
    SCOTTISH_CODE_BUT_OTHER_RATE("Applied Scottish Income Tax rates because you used a Scottish tax code."),
    NON_SCOTTISH_CODE_BUT_SCOTTISH_RATE("Applied Scottish Income Tax rates as you said you pay Scottish Income Tax."),
    INCOME_BELOW_STUDENT_AND_POSTGRAD_LOAN("Income is below the level needed to start repaying student/postgrad loan."),
    INCOME_BELOW_STUDENT_LOAN("Income is below the level needed to start repaying student loan."),
    INCOME_BELOW_POSTGRAD_LOAN("Income is below the level needed to start repaying postgrad loan."),
    INCOME_BELOW_STUDENT_BUT_ABOVE_POSTGRAD_LOAN(
        "Income is below the level needed to start repaying student loan, but high enough to repay postgrad loan"
    ),
    PENSION_EXCEED_INCOME("Pension contribution amount is higher than income."),
    PENSION_EXCEED_ANNUAL_ALLOWANCE("Pension contribution amount is higher than annual allowance."),
    PENSION_BELOW_ANNUAL_ALLOWANCE("Pension contribution amount is below than annual allowance."),
    INCOME_OVER_100K("Income over £100k"),
    INCOME_OVER_100K_WITH_TAPERING("Income over £100K with default 1257L tax code."),
    K_CODE("Tax code started with K.")
}
