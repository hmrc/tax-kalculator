/*
 * Copyright 2019 HM Revenue & Customs
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
package uk.gov.hmrc.calculator

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ValidatorTests {
    @Test
    fun `Validate valid tax code`() {
        assertTrue(Validator.isValidTaxCode("1250L"))
    }

    @Test
    fun `Validate invalid tax code`() {
        assertFalse(Validator.isValidTaxCode("HELLO"))
    }

    @Test
    fun `Validate wages with no decimal places`() {
        assertTrue(Validator.isValidWages(10.toDouble()))
    }

    @Test
    fun `Validate wages with one decimal place`() {
        assertTrue(Validator.isValidWages(10.2))
    }

    @Test
    fun `Validate wages with two decimal places`() {
        assertTrue(Validator.isValidWages(10.45))
    }

    @Test
    fun `Validate wages with three decimal places`() {
        assertFalse(Validator.isValidWages(10.123))
    }

    @Test
    fun `Validate wages below zero`() {
        assertFalse(Validator.isAboveMinimumWages(-1.0))
    }

    @Test
    fun `Validate wages above zero`() {
        assertTrue(Validator.isAboveMinimumWages(12000.0))
    }

    @Test
    fun `Validate wages below max`() {
        assertTrue(Validator.isBelowMaximumWages(9999999.0))
    }

    @Test
    fun `Validate wages above max`() {
        assertFalse(Validator.isBelowMaximumWages(10000000.0))
    }

    @Test
    fun `Validate hours with no decimal places`() {
        assertTrue(Validator.isValidHoursPerWeek(10.toDouble()))
    }

    @Test
    fun `Validate hours with one decimal place`() {
        assertTrue(Validator.isValidHoursPerWeek(10.2))
    }

    @Test
    fun `Validate hours with two decimal places`() {
        assertTrue(Validator.isValidHoursPerWeek(10.45))
    }

    @Test
    fun `Validate hours with three decimal places`() {
        assertFalse(Validator.isValidHoursPerWeek(10.123))
    }

    @Test
    fun `Validate hours below zero`() {
        assertFalse(Validator.isAboveMinimumHoursPerWeek(-1.0))
    }

    @Test
    fun `Validate hours above zero`() {
        assertTrue(Validator.isAboveMinimumHoursPerWeek(26.0))
    }

    @Test
    fun `Validate hours below max`() {
        assertTrue(Validator.isBelowMaximumHoursPerWeek(168.0))
    }

    @Test
    fun `Validate hours above max`() {
        assertFalse(Validator.isBelowMaximumHoursPerWeek(168.1))
    }
}
