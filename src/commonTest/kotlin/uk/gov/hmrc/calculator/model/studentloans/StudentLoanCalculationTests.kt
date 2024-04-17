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

import uk.gov.hmrc.calculator.model.StudentLoanAmountBreakdown
import uk.gov.hmrc.calculator.model.TaxYear
import kotlin.test.Test
import kotlin.test.assertEquals

class StudentLoanCalculationTests {

    @Test
    fun `GIVEN no student loan WHEN init THEN listOfBreakdownResult return default breakdown`() {
        val wage = 30000.00
        val listOfUndergraduatePlan = listOfUndergraduatePlan(planOne = false, planTwo = false, planFour = false)
        val hasPostgraduatePlan = false

        val result = StudentLoanCalculation(TaxYear.TWENTY_TWENTY_FOUR, wage, listOfUndergraduatePlan, hasPostgraduatePlan).listOfBreakdownResult

        assertEquals(expectedBreakdown(0.0, 0.0, 0.0, 0.0), result)
    }

    @Test
    fun `GIVEN no student loan WHEN calculateTotalLoanDeduction THEN return zero`() {
        val wage = 30000.00
        val listOfUndergraduatePlan = listOfUndergraduatePlan(planOne = false, planTwo = false, planFour = false)
        val hasPostgraduatePlan = false

        val result = StudentLoanCalculation(TaxYear.TWENTY_TWENTY_FOUR, wage, listOfUndergraduatePlan, hasPostgraduatePlan).calculateTotalLoanDeduction()

        assertEquals(0.0, result)
    }

    @Test
    fun `GIVEN one undergraduate plan AND wage under threshold WHEN init THEN return default breakdown`() {
        val wage = 10000.00
        val listOfUndergraduatePlan = listOfUndergraduatePlan(planOne = true, planTwo = false, planFour = false)
        val hasPostgraduatePlan = false

        val result = StudentLoanCalculation(TaxYear.TWENTY_TWENTY_FOUR, wage, listOfUndergraduatePlan, hasPostgraduatePlan).listOfBreakdownResult

        assertEquals(expectedBreakdown(0.0, 0.0, 0.0, 0.0), result)
    }

    @Test
    fun `GIVEN one undergraduate plan AND wage under threshold WHEN calculateTotalLoanDeduction THEN return zero`() {
        val wage = 10000.00
        val listOfUndergraduatePlan = listOfUndergraduatePlan(planOne = true, planTwo = false, planFour = false)
        val hasPostgraduatePlan = false

        val result = StudentLoanCalculation(TaxYear.TWENTY_TWENTY_FOUR, wage, listOfUndergraduatePlan, hasPostgraduatePlan).calculateTotalLoanDeduction()

        assertEquals(0.0, result)
    }

    @Test
    fun `GIVEN one undergraduate plan AND wage above threshold WHEN init THEN return breakdown with loan`() {
        val wage = 33000.00
        val listOfUndergraduatePlan = listOfUndergraduatePlan(planOne = true, planTwo = false, planFour = false)
        val hasPostgraduatePlan = false

        val result = StudentLoanCalculation(TaxYear.TWENTY_TWENTY_FOUR, wage, listOfUndergraduatePlan, hasPostgraduatePlan).listOfBreakdownResult

        assertEquals(expectedBreakdown(720.9, 0.0, 0.0, 0.0), result)
    }

    @Test
    fun `GIVEN one undergraduate plan AND wage above threshold WHEN calculateTotalLoanDeduction THEN loan amount`() {
        val wage = 33000.00
        val listOfUndergraduatePlan = listOfUndergraduatePlan(planOne = true, planTwo = false, planFour = false)
        val hasPostgraduatePlan = false

        val result = StudentLoanCalculation(TaxYear.TWENTY_TWENTY_FOUR, wage, listOfUndergraduatePlan, hasPostgraduatePlan).calculateTotalLoanDeduction()

        assertEquals(720.9, result)
    }

    @Test
    fun `GIVEN multiple undergraduate plan AND wage under threshold WHEN init THEN return default breakdown`() {
        val wage = 10000.00
        val listOfUndergraduatePlan = listOfUndergraduatePlan(planOne = true, planTwo = true, planFour = false)
        val hasPostgraduatePlan = false

        val result = StudentLoanCalculation(TaxYear.TWENTY_TWENTY_FOUR, wage, listOfUndergraduatePlan, hasPostgraduatePlan).listOfBreakdownResult

        assertEquals(expectedBreakdown(0.0, 0.0, 0.0, 0.0), result)
    }

    @Test
    fun `GIVEN multiple undergraduate plan AND wage under threshold WHEN calculateTotalLoanDeduction THEN return zero`() {
        val wage = 10000.00
        val listOfUndergraduatePlan = listOfUndergraduatePlan(planOne = true, planTwo = true, planFour = false)
        val hasPostgraduatePlan = false

        val result = StudentLoanCalculation(TaxYear.TWENTY_TWENTY_FOUR, wage, listOfUndergraduatePlan, hasPostgraduatePlan).calculateTotalLoanDeduction()

        assertEquals(0.0, result)
    }

    @Test
    fun `GIVEN multiple undergraduate plan AND wage only above one threshold WHEN init THEN return breakdown with loan`() {
        val wage = 25200.00
        val listOfUndergraduatePlan = listOfUndergraduatePlan(planOne = true, planTwo = true, planFour = false)
        val hasPostgraduatePlan = false

        val result = StudentLoanCalculation(TaxYear.TWENTY_TWENTY_FOUR, wage, listOfUndergraduatePlan, hasPostgraduatePlan).listOfBreakdownResult

        assertEquals(expectedBreakdown(18.9, 0.0, 0.0, 0.0), result)
    }

    @Test
    fun `GIVEN multiple undergraduate plan AND wage only above one threshold WHEN calculateTotalLoanDeduction THEN loan amount`() {
        val wage = 25200.00
        val listOfUndergraduatePlan = listOfUndergraduatePlan(planOne = true, planTwo = true, planFour = false)
        val hasPostgraduatePlan = false

        val result = StudentLoanCalculation(TaxYear.TWENTY_TWENTY_FOUR, wage, listOfUndergraduatePlan, hasPostgraduatePlan).calculateTotalLoanDeduction()

        assertEquals(18.9, result)
    }

    @Test
    fun `GIVEN multiple undergraduate plan AND wage above both threshold WHEN init THEN return breakdown with the lowest threshold loan`() {
        val wage = 33000.00
        val listOfUndergraduatePlan = listOfUndergraduatePlan(planOne = true, planTwo = true, planFour = false)
        val hasPostgraduatePlan = false

        val result = StudentLoanCalculation(TaxYear.TWENTY_TWENTY_FOUR, wage, listOfUndergraduatePlan, hasPostgraduatePlan).listOfBreakdownResult

        assertEquals(expectedBreakdown(720.9, 0.0, 0.0, 0.0), result)
    }

    @Test
    fun `GIVEN multiple undergraduate plan AND wage above both threshold WHEN calculateTotalLoanDeduction THEN loan amount`() {
        val wage = 33000.00
        val listOfUndergraduatePlan = listOfUndergraduatePlan(planOne = true, planTwo = true, planFour = false)
        val hasPostgraduatePlan = false

        val result = StudentLoanCalculation(TaxYear.TWENTY_TWENTY_FOUR, wage, listOfUndergraduatePlan, hasPostgraduatePlan).calculateTotalLoanDeduction()

        assertEquals(720.9, result)
    }

    @Test
    fun `GIVEN under AND post graduate plan AND wage only above post graduate threshold WHEN init THEN return breakdown with post graduate loan`() {
        val wage = 24000.00
        val listOfUndergraduatePlan = listOfUndergraduatePlan(planOne = false, planTwo = true, planFour = false)
        val hasPostgraduatePlan = true

        val result = StudentLoanCalculation(TaxYear.TWENTY_TWENTY_FOUR, wage, listOfUndergraduatePlan, hasPostgraduatePlan).listOfBreakdownResult

        assertEquals(expectedBreakdown(0.0, 0.0, 0.0, 180.0), result)
    }

    @Test
    fun `GIVEN under AND post graduate plan AND wage only above post graduate threshold WHEN calculateTotalLoanDeduction THEN loan amount`() {
        val wage = 24000.00
        val listOfUndergraduatePlan = listOfUndergraduatePlan(planOne = false, planTwo = true, planFour = false)
        val hasPostgraduatePlan = true

        val result = StudentLoanCalculation(TaxYear.TWENTY_TWENTY_FOUR, wage, listOfUndergraduatePlan, hasPostgraduatePlan).calculateTotalLoanDeduction()

        assertEquals(180.0, result)
    }

    @Test
    fun `GIVEN under AND post graduate plan AND wage above both threshold WHEN init THEN result breakdown with both loan`() {
        val wage = 28800.00
        val listOfUndergraduatePlan = listOfUndergraduatePlan(planOne = false, planTwo = true, planFour = false)
        val hasPostgraduatePlan = true

        val result = StudentLoanCalculation(TaxYear.TWENTY_TWENTY_FOUR, wage, listOfUndergraduatePlan, hasPostgraduatePlan).listOfBreakdownResult

        assertEquals(expectedBreakdown(0.0, 135.45, 0.0, 468.0), result)
    }

    @Test
    fun `GIVEN under AND post graduate plan AND wage above both threshold WHEN calculateTotalLoanDeduction THEN sum of both loan amount`() {
        val wage = 28800.00
        val listOfUndergraduatePlan = listOfUndergraduatePlan(planOne = false, planTwo = true, planFour = false)
        val hasPostgraduatePlan = true

        val result = StudentLoanCalculation(TaxYear.TWENTY_TWENTY_FOUR, wage, listOfUndergraduatePlan, hasPostgraduatePlan).calculateTotalLoanDeduction()

        assertEquals(603.45, result)
    }

    @Test
    fun `GIVEN all plans AND wage above all threshold WHEN calculateTotalLoanDeduction WHEN init THEN result breakdown with lowest threshold and post grad loan`() {
        val wage = 28800.00
        val listOfUndergraduatePlan = listOfUndergraduatePlan(planOne = true, planTwo = true, planFour = true)
        val hasPostgraduatePlan = true

        val result = StudentLoanCalculation(TaxYear.TWENTY_TWENTY_FOUR, wage, listOfUndergraduatePlan, hasPostgraduatePlan).listOfBreakdownResult

        assertEquals(expectedBreakdown(342.9, 0.0, 0.0, 468.0), result)
    }

    @Test
    fun `GIVEN all plans AND wage above all threshold WHEN calculateTotalLoanDeduction THEN sum of lowest threshold and post grad loan amount`() {
        val wage = 28800.00
        val listOfUndergraduatePlan = listOfUndergraduatePlan(planOne = true, planTwo = true, planFour = true)
        val hasPostgraduatePlan = true

        val result = StudentLoanCalculation(TaxYear.TWENTY_TWENTY_FOUR, wage, listOfUndergraduatePlan, hasPostgraduatePlan).calculateTotalLoanDeduction()

        assertEquals(810.9, result)
    }

    companion object {
        private fun listOfUndergraduatePlan(
            planOne: Boolean,
            planTwo: Boolean,
            planFour: Boolean,
        ) = mapOf(
            StudentLoanRate.StudentLoanPlan.PLAN_ONE to planOne,
            StudentLoanRate.StudentLoanPlan.PLAN_TWO to planTwo,
            StudentLoanRate.StudentLoanPlan.PLAN_FOUR to planFour
        )

        private fun expectedBreakdown(
            planOne: Double,
            planTwo: Double,
            planFour: Double,
            postGradPlan: Double,
        ) = mutableListOf(
            StudentLoanAmountBreakdown(StudentLoanRate.StudentLoanPlan.PLAN_ONE.value, planOne),
            StudentLoanAmountBreakdown(StudentLoanRate.StudentLoanPlan.PLAN_TWO.value, planTwo),
            StudentLoanAmountBreakdown(StudentLoanRate.StudentLoanPlan.PLAN_FOUR.value, planFour),
            StudentLoanAmountBreakdown(StudentLoanRate.StudentLoanPlan.POST_GRADUATE_PLAN.value, postGradPlan),
        )
    }
}
