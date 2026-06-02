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

import uk.gov.hmrc.calculator.Calculator
import uk.gov.hmrc.calculator.model.PayPeriod
import uk.gov.hmrc.calculator.model.StudentLoanAmountBreakdown
import uk.gov.hmrc.calculator.model.TaxYear
import uk.gov.hmrc.calculator.utils.clarification.Clarification
import kotlin.jvm.JvmSynthetic

internal class StudentLoanCalculation(
    taxYear: TaxYear,
    yearlyWage: Double,
    studentLoanPlans: Calculator.StudentLoanPlans?,
) {

    private val listOfUndergraduateResult = mutableListOf<StudentLoanPlanAmount>()
    private val listOfPostgraduateResult = mutableListOf<StudentLoanPlanAmount>()

    var studentLoanClarification: Clarification? = null

    val defaultAmountMap = mapOf(
        PayPeriod.WEEKLY to 0.0,
        PayPeriod.FOUR_WEEKLY to 0.0,
        PayPeriod.MONTHLY to 0.0,
        PayPeriod.YEARLY to 0.0,
    )
    val listOfBreakdownResult = mutableListOf(
        StudentLoanAmountBreakdown(StudentLoanRate.StudentLoanPlan.PLAN_ONE.value, defaultAmountMap),
        StudentLoanAmountBreakdown(StudentLoanRate.StudentLoanPlan.PLAN_TWO.value, defaultAmountMap),
        StudentLoanAmountBreakdown(StudentLoanRate.StudentLoanPlan.PLAN_FOUR.value, defaultAmountMap),
        StudentLoanAmountBreakdown(StudentLoanRate.StudentLoanPlan.POST_GRADUATE_PLAN.value, defaultAmountMap),
        StudentLoanAmountBreakdown(StudentLoanRate.StudentLoanPlan.PLAN_FIVE.value, defaultAmountMap),
    )

    init {
        val studentLoanRate = StudentLoanRate(taxYear).rate

        val listOfUndergraduatePlan = if (studentLoanPlans != null) {
            mapOf(
                StudentLoanRate.StudentLoanPlan.PLAN_ONE to studentLoanPlans.hasPlanOne,
                StudentLoanRate.StudentLoanPlan.PLAN_TWO to studentLoanPlans.hasPlanTwo,
                StudentLoanRate.StudentLoanPlan.PLAN_FOUR to studentLoanPlans.hasPlanFour,
                StudentLoanRate.StudentLoanPlan.PLAN_FIVE to studentLoanPlans.hasPlanFive
            )
        } else {
            mapOf(
                StudentLoanRate.StudentLoanPlan.PLAN_ONE to false,
                StudentLoanRate.StudentLoanPlan.PLAN_TWO to false,
                StudentLoanRate.StudentLoanPlan.PLAN_FOUR to false,
                StudentLoanRate.StudentLoanPlan.PLAN_FIVE to false
            )
        }

        val hasPostgraduatePlan = studentLoanPlans?.hasPostgraduatePlan ?: false

        calculateUndergraduateWithPlan(
            yearlyWage,
            listOfUndergraduatePlan,
            studentLoanRate
        )

        calculatePostgraduateWithPlan(
            yearlyWage,
            hasPostgraduatePlan,
            studentLoanRate
        )

        updateStudentLoanClarification(listOfUndergraduatePlan, hasPostgraduatePlan)
    }

    private fun calculateStudentLoan(
        yearlyWage: Double,
        studentLoanRepayment: StudentLoanRate.StudentLoanRepayment,
    ): Map<PayPeriod, Double> {

        fun calculateLoan(
            wage: Double,
            threshold: Double,
            minimumLoanAmount: Double,
        ): Double {
            return if (wage > threshold) {
                val amountToCalculateLoan = wage - threshold
                val loanAmount =
                    amountToCalculateLoan * studentLoanRepayment.recoveryRatePercentage

                if (loanAmount >= minimumLoanAmount) {
                    loanAmount
                } else {
                    0.0
                }
            } else {
                0.0
            }
        }

        val weeklyLoan = calculateLoan(
            wage = yearlyWage / 52,
            threshold = studentLoanRepayment.weeklyThreshold,
            MINIMUM_STUDENT_LOAN_AMOUNT
        )

        val fourWeeklyLoan = calculateLoan(
            wage = yearlyWage / 13,
            threshold = studentLoanRepayment.fourWeeklyThreshold,
            MINIMUM_STUDENT_LOAN_AMOUNT
        )

        val monthlyLoan = calculateLoan(
            wage = yearlyWage / 12,
            threshold = studentLoanRepayment.monthlyThreshold,
            MINIMUM_STUDENT_LOAN_AMOUNT
        )

        val yearlyLoan = calculateLoan(
            wage = yearlyWage,
            threshold = studentLoanRepayment.yearlyThreshold,
            MINIMUM_YEARLY_STUDENT_LOAN_AMOUNT
        )

        return mapOf(
            PayPeriod.WEEKLY to weeklyLoan,
            PayPeriod.FOUR_WEEKLY to fourWeeklyLoan,
            PayPeriod.MONTHLY to monthlyLoan,
            PayPeriod.YEARLY to yearlyLoan,
        )
    }

    private fun calculateUndergraduateWithPlan(
        yearlyWage: Double,
        listOfUndergraduatePlan: Map<StudentLoanRate.StudentLoanPlan, Boolean>,
        studentLoanRate: Map<StudentLoanRate.StudentLoanPlan, StudentLoanRate.StudentLoanRepayment>,
    ) {
        listOfUndergraduatePlan.forEach { (plan, hasStudentLoan) ->

            // If student loan plan is not enabled, then skipping it
            if (!hasStudentLoan) return@forEach

            if (studentLoanRate.containsKey(plan)) {
                val rate = studentLoanRate[plan]!!
                val loanAmount = calculateStudentLoan(yearlyWage, rate)

                // This will calculate and add all "true" student loan amount to the list.
                listOfUndergraduateResult.add(
                    StudentLoanPlanAmount(
                        plan,
                        loanAmount,
                        rate.yearlyThreshold,
                        rate.monthlyThreshold,
                        rate.fourWeeklyThreshold,
                        rate.weeklyThreshold,
                        hasStudentLoan
                    )
                )
                addLowestUndergraduateLoanToBreakdown()
            }
        }
    }

    private fun calculatePostgraduateWithPlan(
        yearlyWage: Double,
        hasPostgraduatePlan: Boolean,
        studentLoanRate: Map<StudentLoanRate.StudentLoanPlan, StudentLoanRate.StudentLoanRepayment>,
    ) {
        val rate = studentLoanRate[StudentLoanRate.StudentLoanPlan.POST_GRADUATE_PLAN]!!
        val loanAmount = calculateStudentLoan(yearlyWage, rate)

        listOfPostgraduateResult.add(
            StudentLoanPlanAmount(
                StudentLoanRate.StudentLoanPlan.POST_GRADUATE_PLAN,
                loanAmount,
                rate.yearlyThreshold,
                rate.monthlyThreshold,
                rate.fourWeeklyThreshold,
                rate.weeklyThreshold,
                hasPostgraduatePlan,
            )
        )

        addPostgraduateLoanToBreakdown()
    }

    // This filter the listOfUndergraduateResult and only pass the lowest
    // threshold loan that has a plan to the listOfBreakdownResult.
    private fun addLowestUndergraduateLoanToBreakdown() {
        val filteredLoan = listOfUndergraduateResult.filter { it.hasPlan }

        val lowestThresholdLoan = filteredLoan.minByOrNull { it.planYearlyThreshold }

        lowestThresholdLoan?.let { loan ->
            listOfBreakdownResult.find { it.plan == loan.plan.value }?.let {
                it.amount = loan.amount
            }
        }
    }

    private fun addPostgraduateLoanToBreakdown() {
        val filteredLoan = listOfPostgraduateResult.filter { it.hasPlan }
        if (filteredLoan.isNotEmpty()) {
            listOfBreakdownResult.find { it.plan == filteredLoan[0].plan.value }?.let {
                it.amount = filteredLoan[0].amount
            }
        }
    }

    private fun updateStudentLoanClarification(
        listOfUndergraduatePlan: Map<StudentLoanRate.StudentLoanPlan, Boolean>,
        hasPostgraduatePlan: Boolean,
    ) {
        val hasStudentLoanPlan = listOf(
            listOfUndergraduatePlan[StudentLoanRate.StudentLoanPlan.PLAN_ONE],
            listOfUndergraduatePlan[StudentLoanRate.StudentLoanPlan.PLAN_TWO],
            listOfUndergraduatePlan[StudentLoanRate.StudentLoanPlan.PLAN_FOUR],
            listOfUndergraduatePlan[StudentLoanRate.StudentLoanPlan.PLAN_FIVE],
        ).any { it!! }

        val clarification = when {
            hasStudentLoanPlan && hasPostgraduatePlan -> {
                clarificationForBothLoans()
            }
            hasStudentLoanPlan && getStudentLoanDeduction()[PayPeriod.YEARLY] == 0.0 -> {
                Clarification.INCOME_BELOW_STUDENT_LOAN
            }
            hasPostgraduatePlan && getPostgraduateLoanDeduction()[PayPeriod.YEARLY] == 0.0 -> {
                Clarification.INCOME_BELOW_POSTGRAD_LOAN
            }
            else -> null
        }

        studentLoanClarification = clarification
    }

    private fun clarificationForBothLoans(): Clarification? {
        return if (getStudentLoanDeduction()[PayPeriod.YEARLY] == 0.0) {
            if ((getPostgraduateLoanDeduction()[PayPeriod.YEARLY] ?: 0.0) > 0.0) {
                Clarification.INCOME_BELOW_STUDENT_BUT_ABOVE_POSTGRAD_LOAN
            } else Clarification.INCOME_BELOW_STUDENT_AND_POSTGRAD_LOAN
        } else null
    }

    @JvmSynthetic
    internal fun getStudentLoanDeduction(): Map<PayPeriod, Double> {
        val listOfStudentLoan = listOfBreakdownResult.filter {
            it.plan != StudentLoanRate.StudentLoanPlan.POST_GRADUATE_PLAN.value
        }
        val studentLoanWithAmount = listOfStudentLoan.filter {
            it.amount.get(PayPeriod.YEARLY) != 0.0
        }

        return if (studentLoanWithAmount.isNotEmpty()) studentLoanWithAmount.first().amount else defaultAmountMap
    }

    @JvmSynthetic
    internal fun getPostgraduateLoanDeduction(): Map<PayPeriod, Double> {
        return listOfBreakdownResult.first {
            it.plan == StudentLoanRate.StudentLoanPlan.POST_GRADUATE_PLAN.value
        }.amount
    }

    internal data class StudentLoanPlanAmount(
        val plan: StudentLoanRate.StudentLoanPlan,
        val amount: Map<PayPeriod, Double>,
        val planYearlyThreshold: Double,
        val planMonthlyThreshold: Double,
        val planFourWeeklyThreshold: Double,
        val planWeeklyThreshold: Double,
        val hasPlan: Boolean,
    )

    companion object {
        private const val MINIMUM_YEARLY_STUDENT_LOAN_AMOUNT = 12.0
        private const val MINIMUM_STUDENT_LOAN_AMOUNT = 1.0
    }
}
