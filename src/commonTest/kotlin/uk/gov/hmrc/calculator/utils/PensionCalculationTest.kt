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
package uk.gov.hmrc.calculator.utils

import uk.gov.hmrc.calculator.model.pension.AnnualPensionMethod
import uk.gov.hmrc.calculator.model.pension.calculateYearlyPension
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class PensionCalculationTest {

    @Test
    fun `GIVEN pensionMethod null WHEN calculateYearlyPension THEN return null`() {
        val result = calculateYearlyPension(
            1000.0,
            pensionMethod = null,
            pensionYearlyAmount = 10.0,
            pensionPercentage = null,
        )

        assertNull(result)
    }

    @Test
    fun `GIVEN pensionMethod PERCENTAGE AND pensionPercentage null WHEN calculateYearlyPension THEN return null`() {
        val result = calculateYearlyPension(
            1000.0,
            pensionMethod = AnnualPensionMethod.PERCENTAGE,
            pensionYearlyAmount = 10.0,
            pensionPercentage = null,
        )

        assertNull(result)
    }

    @Test
    fun `GIVEN pensionMethod AMOUNT_IN_POUND AND pensionYearlyAmount null WHEN calculateYearlyPension THEN return null`() {
        val result = calculateYearlyPension(
            1000.0,
            pensionMethod = AnnualPensionMethod.AMOUNT_IN_POUND,
            pensionYearlyAmount = null,
            pensionPercentage = 10.0,
        )

        assertNull(result)
    }

    @Test
    fun `GIVEN pensionMethod PERCENTAGE AND pensionPercentage 10 percent WHEN calculateYearlyPension THEN return pension amount`() {
        val result = calculateYearlyPension(
            1000.0,
            pensionMethod = AnnualPensionMethod.PERCENTAGE,
            pensionYearlyAmount = null,
            pensionPercentage = 10.0,
        )

        assertEquals(100.0, result)
    }

    @Test
    fun `GIVEN pensionMethod AMOUNT_IN_POUND AND pensionYearlyAmount is 100 WHEN calculateYearlyPension THEN return pension amount`() {
        val result = calculateYearlyPension(
            1000.0,
            pensionMethod = AnnualPensionMethod.AMOUNT_IN_POUND,
            pensionYearlyAmount = 100.0,
            pensionPercentage = null,
        )

        assertEquals(100.0, result)
    }
}
