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
    val listOfBreakdownResult = mutableListOf(
        StudentLoanAmountBreakdown(StudentLoanRate.StudentLoanPlan.PLAN_ONE.value, 0.0),
        StudentLoanAmountBreakdown(StudentLoanRate.StudentLoanPlan.PLAN_TWO.value, 0.0),
        StudentLoanAmountBreakdown(StudentLoanRate.StudentLoanPlan.PLAN_FOUR.value, 0.0),
        StudentLoanAmountBreakdown(StudentLoanRate.StudentLoanPlan.POST_GRADUATE_PLAN.value, 0.0),
    )

    init {
        val studentLoanRate = StudentLoanRate(taxYear).rate

        val listOfUndergraduatePlan = if (studentLoanPlans != null) {
            mapOf(
                StudentLoanRate.StudentLoanPlan.PLAN_ONE to studentLoanPlans.hasPlanOne,
                StudentLoanRate.StudentLoanPlan.PLAN_TWO to studentLoanPlans.hasPlanTwo,
                StudentLoanRate.StudentLoanPlan.PLAN_FOUR to studentLoanPlans.hasPlanFour
            )
        } else {
            mapOf(
                StudentLoanRate.StudentLoanPlan.PLAN_ONE to false,
                StudentLoanRate.StudentLoanPlan.PLAN_TWO to false,
                StudentLoanRate.StudentLoanPlan.PLAN_FOUR to false
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
    ): Double {
        return if (yearlyWage > studentLoanRepayment.yearlyThreshold) {
            val amountToCalculateLoan = yearlyWage - studentLoanRepayment.yearlyThreshold
            amountToCalculateLoan * studentLoanRepayment.recoveryRatePercentage
        } else 0.0
    }

    private fun calculateUndergraduateWithPlan(
        yearlyWage: Double,
        listOfUndergraduatePlan: Map<StudentLoanRate.StudentLoanPlan, Boolean>,
        studentLoanRate: Map<StudentLoanRate.StudentLoanPlan, StudentLoanRate.StudentLoanRepayment>,
    ) {
        listOfUndergraduatePlan.forEach { (plan, hasStudentLoan) ->
            val rate = studentLoanRate[plan]!!
            val loanAmount = calculateStudentLoan(yearlyWage, rate)

            // This will calculate and add all "true" student loan amount to the list.
            listOfUndergraduateResult.add(StudentLoanPlanAmount(plan, loanAmount, rate.yearlyThreshold, hasStudentLoan))
            addLowestUndergraduateLoanToBreakdown()
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
                hasPostgraduatePlan,
            )
        )

        addPostgraduateLoanToBreakdown()
    }

    // This filter the listOfUndergraduateResult and only pass the lowest
    // threshold loan that has a plan to the listOfBreakdownResult.
    private fun addLowestUndergraduateLoanToBreakdown() {
        val filteredLoan = listOfUndergraduateResult.filter { it.hasPlan }

        val lowestThresholdLoan = filteredLoan.minByOrNull { it.planThreshold }

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
        ).any { it!! }

        val clarification = when {
            hasStudentLoanPlan && hasPostgraduatePlan -> {
                clarificationForBothLoans()
            }
            hasStudentLoanPlan && calculateTotalStudentLoanDeduction() == 0.0 -> {
                Clarification.INCOME_BELOW_STUDENT_LOAN
            }
            hasPostgraduatePlan && calculateTotalPostgraduateLoanDeduction() == 0.0 -> {
                Clarification.INCOME_BELOW_POSTGRAD_LOAN
            }
            else -> null
        }

        studentLoanClarification = clarification
    }

    private fun clarificationForBothLoans(): Clarification? {
        return if (calculateTotalStudentLoanDeduction() == 0.0) {
            if (calculateTotalPostgraduateLoanDeduction() > 0.0) {
                Clarification.INCOME_BELOW_STUDENT_BUT_ABOVE_POSTGRAD_LOAN
            } else Clarification.INCOME_BELOW_STUDENT_AND_POSTGRAD_LOAN
        } else null
    }

    @JvmSynthetic
    internal fun calculateTotalStudentLoanDeduction(): Double {
        val listOfStudentLoan = listOfBreakdownResult.filter {
            it.plan != StudentLoanRate.StudentLoanPlan.POST_GRADUATE_PLAN.value
        }
        return listOfStudentLoan.sumOf { it.amount }
    }

    @JvmSynthetic
    internal fun calculateTotalPostgraduateLoanDeduction(): Double {
        return listOfBreakdownResult.first {
            it.plan == StudentLoanRate.StudentLoanPlan.POST_GRADUATE_PLAN.value
        }.amount
    }

    internal data class StudentLoanPlanAmount(
        val plan: StudentLoanRate.StudentLoanPlan,
        val amount: Double,
        val planThreshold: Double,
        val hasPlan: Boolean,
    )
}
