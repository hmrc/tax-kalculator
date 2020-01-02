/*
 * Copyright 2020 HM Revenue & Customs
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

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class WageValidatorTests {
    @Test
    fun `Validate wages with no decimal places`() {
        assertTrue(WageValidator.isValidWages(10.toDouble()))
    }

    @Test
    fun `Validate wages with one decimal place`() {
        assertTrue(WageValidator.isValidWages(10.2))
    }

    @Test
    fun `Validate wages with two decimal places`() {
        assertTrue(WageValidator.isValidWages(10.45))
    }

    @Test
    fun `Validate wages with three decimal places`() {
        assertFalse(WageValidator.isValidWages(10.123))
    }

    @Test
    fun `Validate wages below zero`() {
        assertFalse(WageValidator.isAboveMinimumWages(-1.0))
    }

    @Test
    fun `Validate wages above zero`() {
        assertTrue(WageValidator.isAboveMinimumWages(12000.0))
    }

    @Test
    fun `Validate wages below max`() {
        assertTrue(WageValidator.isBelowMaximumWages(9999999.0))
    }

    @Test
    fun `Validate wages above max`() {
        assertFalse(WageValidator.isBelowMaximumWages(10000000.0))
    }
}
