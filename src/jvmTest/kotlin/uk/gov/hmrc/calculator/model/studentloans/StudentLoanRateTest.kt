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
package uk.gov.hmrc.calculator.model.studentloans

import uk.gov.hmrc.calculator.model.TaxYear
import kotlin.test.Test
import kotlin.test.assertEquals

class StudentLoanRateTest {

    @Test
    fun `GIVEN year is 2020 WHEN get rate THEN return populated objects`() {
        val result = StudentLoanRate(TaxYear.TWENTY_TWENTY).rate

        assertEquals(19390.0, result[StudentLoanRate.StudentLoanPlan.PLAN_ONE]!!.yearlyThreshold)
        assertEquals(0.09, result[StudentLoanRate.StudentLoanPlan.PLAN_ONE]!!.recoveryRatePercentage)
        assertEquals(26575.0, result[StudentLoanRate.StudentLoanPlan.PLAN_TWO]!!.yearlyThreshold)
        assertEquals(0.09, result[StudentLoanRate.StudentLoanPlan.PLAN_TWO]!!.recoveryRatePercentage)
        assertEquals(21000.0, result[StudentLoanRate.StudentLoanPlan.POST_GRADUATE_PLAN]!!.yearlyThreshold)
        assertEquals(0.06, result[StudentLoanRate.StudentLoanPlan.POST_GRADUATE_PLAN]!!.recoveryRatePercentage)
    }

    @Test
    fun `GIVEN year is 2021 WHEN get rate THEN return populated objects`() {
        val result = StudentLoanRate(TaxYear.TWENTY_TWENTY_ONE).rate

        assertEquals(19895.0, result[StudentLoanRate.StudentLoanPlan.PLAN_ONE]!!.yearlyThreshold)
        assertEquals(0.09, result[StudentLoanRate.StudentLoanPlan.PLAN_ONE]!!.recoveryRatePercentage)
        assertEquals(27295.0, result[StudentLoanRate.StudentLoanPlan.PLAN_TWO]!!.yearlyThreshold)
        assertEquals(0.09, result[StudentLoanRate.StudentLoanPlan.PLAN_TWO]!!.recoveryRatePercentage)
        assertEquals(25000.0, result[StudentLoanRate.StudentLoanPlan.PLAN_FOUR]!!.yearlyThreshold)
        assertEquals(0.09, result[StudentLoanRate.StudentLoanPlan.PLAN_FOUR]!!.recoveryRatePercentage)
        assertEquals(21000.0, result[StudentLoanRate.StudentLoanPlan.POST_GRADUATE_PLAN]!!.yearlyThreshold)
        assertEquals(0.06, result[StudentLoanRate.StudentLoanPlan.POST_GRADUATE_PLAN]!!.recoveryRatePercentage)
    }

    @Test
    fun `GIVEN year is 2022 WHEN get rate THEN return populated objects`() {
        val result = StudentLoanRate(TaxYear.TWENTY_TWENTY_TWO).rate

        assertEquals(20195.0, result[StudentLoanRate.StudentLoanPlan.PLAN_ONE]!!.yearlyThreshold)
        assertEquals(0.09, result[StudentLoanRate.StudentLoanPlan.PLAN_ONE]!!.recoveryRatePercentage)
        assertEquals(27295.0, result[StudentLoanRate.StudentLoanPlan.PLAN_TWO]!!.yearlyThreshold)
        assertEquals(0.09, result[StudentLoanRate.StudentLoanPlan.PLAN_TWO]!!.recoveryRatePercentage)
        assertEquals(25375.0, result[StudentLoanRate.StudentLoanPlan.PLAN_FOUR]!!.yearlyThreshold)
        assertEquals(0.09, result[StudentLoanRate.StudentLoanPlan.PLAN_FOUR]!!.recoveryRatePercentage)
        assertEquals(21000.0, result[StudentLoanRate.StudentLoanPlan.POST_GRADUATE_PLAN]!!.yearlyThreshold)
        assertEquals(0.06, result[StudentLoanRate.StudentLoanPlan.POST_GRADUATE_PLAN]!!.recoveryRatePercentage)
    }

    @Test
    fun `GIVEN year is 2023 WHEN get rate THEN return populated objects`() {
        val result = StudentLoanRate(TaxYear.TWENTY_TWENTY_THREE).rate

        assertEquals(22015.0, result[StudentLoanRate.StudentLoanPlan.PLAN_ONE]!!.yearlyThreshold)
        assertEquals(0.09, result[StudentLoanRate.StudentLoanPlan.PLAN_ONE]!!.recoveryRatePercentage)
        assertEquals(27295.0, result[StudentLoanRate.StudentLoanPlan.PLAN_TWO]!!.yearlyThreshold)
        assertEquals(0.09, result[StudentLoanRate.StudentLoanPlan.PLAN_TWO]!!.recoveryRatePercentage)
        assertEquals(27660.0, result[StudentLoanRate.StudentLoanPlan.PLAN_FOUR]!!.yearlyThreshold)
        assertEquals(0.09, result[StudentLoanRate.StudentLoanPlan.PLAN_FOUR]!!.recoveryRatePercentage)
        assertEquals(21000.0, result[StudentLoanRate.StudentLoanPlan.POST_GRADUATE_PLAN]!!.yearlyThreshold)
        assertEquals(0.06, result[StudentLoanRate.StudentLoanPlan.POST_GRADUATE_PLAN]!!.recoveryRatePercentage)
    }

    @Test
    fun `GIVEN year is 2023 revised WHEN get rate THEN return populated objects`() {
        val result = StudentLoanRate(TaxYear.TWENTY_TWENTY_THREE_JANUARY_REVISED).rate

        assertEquals(22015.0, result[StudentLoanRate.StudentLoanPlan.PLAN_ONE]!!.yearlyThreshold)
        assertEquals(0.09, result[StudentLoanRate.StudentLoanPlan.PLAN_ONE]!!.recoveryRatePercentage)
        assertEquals(27295.0, result[StudentLoanRate.StudentLoanPlan.PLAN_TWO]!!.yearlyThreshold)
        assertEquals(0.09, result[StudentLoanRate.StudentLoanPlan.PLAN_TWO]!!.recoveryRatePercentage)
        assertEquals(27660.0, result[StudentLoanRate.StudentLoanPlan.PLAN_FOUR]!!.yearlyThreshold)
        assertEquals(0.09, result[StudentLoanRate.StudentLoanPlan.PLAN_FOUR]!!.recoveryRatePercentage)
        assertEquals(21000.0, result[StudentLoanRate.StudentLoanPlan.POST_GRADUATE_PLAN]!!.yearlyThreshold)
        assertEquals(0.06, result[StudentLoanRate.StudentLoanPlan.POST_GRADUATE_PLAN]!!.recoveryRatePercentage)
    }

    @Test
    fun `GIVEN year is 2024 WHEN get rate THEN return populated objects`() {
        val result = StudentLoanRate(TaxYear.TWENTY_TWENTY_FOUR).rate

        assertEquals(24990.0, result[StudentLoanRate.StudentLoanPlan.PLAN_ONE]!!.yearlyThreshold)
        assertEquals(0.09, result[StudentLoanRate.StudentLoanPlan.PLAN_ONE]!!.recoveryRatePercentage)
        assertEquals(27295.0, result[StudentLoanRate.StudentLoanPlan.PLAN_TWO]!!.yearlyThreshold)
        assertEquals(0.09, result[StudentLoanRate.StudentLoanPlan.PLAN_TWO]!!.recoveryRatePercentage)
        assertEquals(31395.0, result[StudentLoanRate.StudentLoanPlan.PLAN_FOUR]!!.yearlyThreshold)
        assertEquals(0.09, result[StudentLoanRate.StudentLoanPlan.PLAN_FOUR]!!.recoveryRatePercentage)
        assertEquals(21000.0, result[StudentLoanRate.StudentLoanPlan.POST_GRADUATE_PLAN]!!.yearlyThreshold)
        assertEquals(0.06, result[StudentLoanRate.StudentLoanPlan.POST_GRADUATE_PLAN]!!.recoveryRatePercentage)
    }
}
