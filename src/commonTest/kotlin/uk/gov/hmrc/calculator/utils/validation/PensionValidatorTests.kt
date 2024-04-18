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
package uk.gov.hmrc.calculator.utils.validation

import uk.gov.hmrc.calculator.model.TaxYear
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PensionValidatorTests {

    @Test
    fun `GIVEN pension is lower then wage WHEN isValidYearlyPension THEN return true`() {
        val wage = 20000.0
        val pension = 2000.0

        assertTrue(PensionValidator.isValidYearlyPension(wage, pension))
    }

    @Test
    fun `GIVEN pension is greater then wages WHEN isValidYearlyPension THEN return false`() {
        val wage = 20000.0
        val pension = 20001.0

        assertFalse(PensionValidator.isValidYearlyPension(wage, pension))
    }

    @Test
    fun `GIVEN pension is same as wage WHEN isValidYearlyPension THEN return true`() {
        val wage = 20000.0
        val pension = 20000.0

        assertTrue(PensionValidator.isValidYearlyPension(wage, pension))
    }

    @Test
    fun `GIVEN pension is lower then annualAllowance THEN return false`() {
        val pension = 50000.0
        val taxYear = TaxYear.TWENTY_TWENTY_FOUR

        assertFalse(PensionValidator.isAboveAnnualAllowance(pension, taxYear))
    }

    @Test
    fun `GIVEN pension is greater then annualAllowance THEN return true`() {
        val pension = 61000.0
        val taxYear = TaxYear.TWENTY_TWENTY_FOUR

        assertTrue(PensionValidator.isAboveAnnualAllowance(pension, taxYear))
    }
}
