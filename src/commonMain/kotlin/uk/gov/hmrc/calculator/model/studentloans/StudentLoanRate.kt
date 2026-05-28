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
        StudentLoanPlan.PLAN_ONE to StudentLoanRepayment(22015.0, 0.09,1834.58,1693.46,423.36),
        StudentLoanPlan.PLAN_TWO to StudentLoanRepayment(27295.0, 0.09,2274.58,2099.61,524.90),
        StudentLoanPlan.PLAN_FOUR to StudentLoanRepayment(27660.0, 0.09,2305.00,2127.69,531.92),
        StudentLoanPlan.POST_GRADUATE_PLAN to StudentLoanRepayment(21000.0, 0.06,1750.00,1615.38,403.84),
    )

    private fun studentLoanRepaymentRate2023JanuaryRevised() = mapOf(
        StudentLoanPlan.PLAN_ONE to StudentLoanRepayment(22015.0, 0.09,1834.58,1693.46,423.36),
        StudentLoanPlan.PLAN_TWO to StudentLoanRepayment(27295.0, 0.09,2274.58,2099.61,524.90),
        StudentLoanPlan.PLAN_FOUR to StudentLoanRepayment(27660.0, 0.09,2305.00,2127.69,531.92),
        StudentLoanPlan.POST_GRADUATE_PLAN to StudentLoanRepayment(21000.0, 0.06,1750.00,1615.38,403.84),
    )

    private fun studentLoanRepaymentRate2024() = mapOf(
        StudentLoanPlan.PLAN_ONE to StudentLoanRepayment(24990.0, 0.09,2082.5,1922.30,480.57),
        StudentLoanPlan.PLAN_TWO to StudentLoanRepayment(27295.0, 0.09,2274.58,2099.61,524.90),
        StudentLoanPlan.PLAN_FOUR to StudentLoanRepayment(31395.0, 0.09,2616.25,2415.00,603.75),
        StudentLoanPlan.POST_GRADUATE_PLAN to StudentLoanRepayment(21000.0, 0.06,1750.00,1615.38,403.84),
    )

    private fun studentLoanRepaymentRate2025() = mapOf(
        StudentLoanPlan.PLAN_ONE to StudentLoanRepayment(26065.0, 0.09,2172.08,2005.00,501.25),
        StudentLoanPlan.PLAN_TWO to StudentLoanRepayment(28470.0, 0.09,2372.5,2190.00,547.5),
        StudentLoanPlan.PLAN_FOUR to StudentLoanRepayment(32745.0, 0.09,2728.75,2518.84,629.71),
        StudentLoanPlan.PLAN_FIVE to StudentLoanRepayment(25000.0, 0.09,2083.33,1923.07,480.76),
        StudentLoanPlan.POST_GRADUATE_PLAN to StudentLoanRepayment(21000.0, 0.06,1750.00,1615.38,403.84),
    )

    //Student Loan rates updated for all payPeriods
    private fun studentLoanRepaymentRate2026() = mapOf(
        StudentLoanPlan.PLAN_ONE to StudentLoanRepayment(26900.0, 0.09,2241.66,2069.23,517.30),
        StudentLoanPlan.PLAN_TWO to StudentLoanRepayment(29385.0, 0.09,2448.75,2260.38,565.09),
        StudentLoanPlan.PLAN_FOUR to StudentLoanRepayment(33795.0, 0.09,2816.25,2599.61,649.90),
        StudentLoanPlan.PLAN_FIVE to StudentLoanRepayment(25000.0, 0.09,2083.33,1923.07,480.76),
        StudentLoanPlan.POST_GRADUATE_PLAN to StudentLoanRepayment(21000.0, 0.06,1750.00,1615.38,403.84),
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
        TaxYear.TWENTY_TWENTY_SIX -> studentLoanRepaymentRate2026()
    }

    internal data class StudentLoanRepayment(
        val yearlyThreshold: Double,
        val recoveryRatePercentage: Double,
        val monthlyThreshold: Double = 0.0,
        val fourWeeklyThreshold: Double = 0.0,
        val weeklyThreshold: Double = 0.0
    )

    internal enum class StudentLoanPlan(val value: String) {
        PLAN_ONE("planOne"),
        PLAN_TWO("planTwo"),
        PLAN_FOUR("planFour"),
        PLAN_FIVE("planFive"),
        POST_GRADUATE_PLAN("postGraduatePlan"),
    }
}
