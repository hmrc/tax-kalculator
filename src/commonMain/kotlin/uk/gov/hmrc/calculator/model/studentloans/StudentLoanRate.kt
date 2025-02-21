/*
 * Copyright 2023 HM Revenue & Customs
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
package uk.gov.hmrc.calculator.model.studentloans

import uk.gov.hmrc.calculator.model.TaxYear
import kotlin.jvm.JvmSynthetic

internal class StudentLoanRate(taxYear: TaxYear) {

    private fun studentLoanRepaymentRate2020() = mapOf(
        StudentLoanPlan.PLAN_ONE to StudentLoanRepayment(19390.0, 0.09),
        StudentLoanPlan.PLAN_TWO to StudentLoanRepayment(26575.0, 0.09),
        StudentLoanPlan.POST_GRADUATE_PLAN to StudentLoanRepayment(21000.0, 0.06),
    )

    private fun studentLoanRepaymentRate2021() = mapOf(
        StudentLoanPlan.PLAN_ONE to StudentLoanRepayment(19895.0, 0.09),
        StudentLoanPlan.PLAN_TWO to StudentLoanRepayment(27295.0, 0.09),
        StudentLoanPlan.PLAN_FOUR to StudentLoanRepayment(25000.0, 0.09),
        StudentLoanPlan.POST_GRADUATE_PLAN to StudentLoanRepayment(21000.0, 0.06),
    )

    private fun studentLoanRepaymentRate2022() = mapOf(
        StudentLoanPlan.PLAN_ONE to StudentLoanRepayment(20195.0, 0.09),
        StudentLoanPlan.PLAN_TWO to StudentLoanRepayment(27295.0, 0.09),
        StudentLoanPlan.PLAN_FOUR to StudentLoanRepayment(25375.0, 0.09),
        StudentLoanPlan.POST_GRADUATE_PLAN to StudentLoanRepayment(21000.0, 0.06),
    )

    private fun studentLoanRepaymentRate2023() = mapOf(
        StudentLoanPlan.PLAN_ONE to StudentLoanRepayment(22015.0, 0.09),
        StudentLoanPlan.PLAN_TWO to StudentLoanRepayment(27295.0, 0.09),
        StudentLoanPlan.PLAN_FOUR to StudentLoanRepayment(27660.0, 0.09),
        StudentLoanPlan.POST_GRADUATE_PLAN to StudentLoanRepayment(21000.0, 0.06),
    )

    private fun studentLoanRepaymentRate2023JanuaryRevised() = mapOf(
        StudentLoanPlan.PLAN_ONE to StudentLoanRepayment(22015.0, 0.09),
        StudentLoanPlan.PLAN_TWO to StudentLoanRepayment(27295.0, 0.09),
        StudentLoanPlan.PLAN_FOUR to StudentLoanRepayment(27660.0, 0.09),
        StudentLoanPlan.POST_GRADUATE_PLAN to StudentLoanRepayment(21000.0, 0.06),
    )

    private fun studentLoanRepaymentRate2024() = mapOf(
        StudentLoanPlan.PLAN_ONE to StudentLoanRepayment(24990.0, 0.09),
        StudentLoanPlan.PLAN_TWO to StudentLoanRepayment(27295.0, 0.09),
        StudentLoanPlan.PLAN_FOUR to StudentLoanRepayment(31395.0, 0.09),
        StudentLoanPlan.POST_GRADUATE_PLAN to StudentLoanRepayment(21000.0, 0.06),
    )

    private fun studentLoanRepaymentRate2025() = mapOf(
        StudentLoanPlan.PLAN_ONE to StudentLoanRepayment(26065.0, 0.09),
        StudentLoanPlan.PLAN_TWO to StudentLoanRepayment(28470.0, 0.09),
        StudentLoanPlan.PLAN_FOUR to StudentLoanRepayment(32745.0, 0.09),
        StudentLoanPlan.POST_GRADUATE_PLAN to StudentLoanRepayment(21000.0, 0.06),
    )

    @JvmSynthetic
    internal val rate: Map<StudentLoanPlan, StudentLoanRepayment> = when (taxYear) {
        TaxYear.TWENTY_TWENTY -> studentLoanRepaymentRate2020()
        TaxYear.TWENTY_TWENTY_ONE -> studentLoanRepaymentRate2021()
        TaxYear.TWENTY_TWENTY_TWO,
        TaxYear.TWENTY_TWENTY_TWO_JULY_REVISED,
        TaxYear.TWENTY_TWENTY_TWO_NOVEMBER_REVISED -> studentLoanRepaymentRate2022()
        TaxYear.TWENTY_TWENTY_THREE -> studentLoanRepaymentRate2023()
        TaxYear.TWENTY_TWENTY_THREE_JANUARY_REVISED -> studentLoanRepaymentRate2023JanuaryRevised()
        TaxYear.TWENTY_TWENTY_FOUR -> studentLoanRepaymentRate2024()
        TaxYear.TWENTY_TWENTY_FIVE -> studentLoanRepaymentRate2025()
    }

    internal data class StudentLoanRepayment(
        val yearlyThreshold: Double,
        val recoveryRatePercentage: Double,
    )

    internal enum class StudentLoanPlan(val value: String) {
        PLAN_ONE("planOne"),
        PLAN_TWO("planTwo"),
        PLAN_FOUR("planFour"),
        POST_GRADUATE_PLAN("postGraduatePlan"),
    }
}
