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
    fun `GIVEN valid pension WHEN isValidYearlyPension THEN return true`() {
        val wage = 20000.0
        val pension = 2000.0
        val taxYear = TaxYear.TWENTY_TWENTY_THREE

        assertTrue(PensionValidator.isValidYearlyPension(wage, pension, taxYear))
    }

    @Test
    fun `GIVEN pension is greater then wages WHEN isValidYearlyPension THEN return false`() {
        val wage = 20000.0
        val pension = 20001.0
        val taxYear = TaxYear.TWENTY_TWENTY_THREE

        assertFalse(PensionValidator.isValidYearlyPension(wage, pension, taxYear))
    }

    @Test
    fun `GIVEN pension is greater then standardLifetimeAllowance THEN return false`() {
        val wage = 1073500.0
        val pension = 1073500.0
        val taxYear = TaxYear.TWENTY_TWENTY_THREE

        assertFalse(PensionValidator.isValidYearlyPension(wage, pension, taxYear))
    }
}
