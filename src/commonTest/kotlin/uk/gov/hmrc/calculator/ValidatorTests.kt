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
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import uk.gov.hmrc.calculator.model.ValidationError
import uk.gov.hmrc.calculator.utils.validation.HoursDaysValidator
import uk.gov.hmrc.calculator.utils.validation.TaxCodeValidator
import uk.gov.hmrc.calculator.utils.validation.WageValidator

class ValidatorTests {
    @Test
    fun `Validate valid tax code`() {
        assertTrue(TaxCodeValidator.isValidTaxCode("1250L").isValid)
    }

    @Test
    fun `Validate invalid tax code WrongTaxCodeNumber`() {
        assertFalse(TaxCodeValidator.isValidTaxCode("HELLO").isValid)
        assertEquals(ValidationError.WrongTaxCodeNumber, TaxCodeValidator.isValidTaxCode("HELLO").errorType)
    }

    @Test
    fun `Validate invalid tax code Other`() {
        assertFalse(TaxCodeValidator.isValidTaxCode("110").isValid)
        assertEquals(ValidationError.Other, TaxCodeValidator.isValidTaxCode("110").errorType)
    }

    @Test
    fun `Validate invalid tax code Prefix`() {
        assertFalse(TaxCodeValidator.isValidTaxCode("OO9999").isValid)
        assertEquals(ValidationError.WrongTaxCodePrefix, TaxCodeValidator.isValidTaxCode("OO9999").errorType)
    }
    @Test
    fun `Validate invalid tax code Suffix`() {
        assertFalse(TaxCodeValidator.isValidTaxCode("9999R").isValid)
        assertEquals(ValidationError.WrongTaxCodeSuffix, TaxCodeValidator.isValidTaxCode("9999R").errorType)
    }

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

    @Test
    fun `Validate hours with no decimal places`() {
        assertTrue(HoursDaysValidator.isValidHoursPerWeek(10.toDouble()))
    }

    @Test
    fun `Validate hours with one decimal place`() {
        assertTrue(HoursDaysValidator.isValidHoursPerWeek(10.2))
    }

    @Test
    fun `Validate hours with two decimal places`() {
        assertTrue(HoursDaysValidator.isValidHoursPerWeek(10.45))
    }

    @Test
    fun `Validate hours with three decimal places`() {
        assertFalse(HoursDaysValidator.isValidHoursPerWeek(10.123))
    }

    @Test
    fun `Validate hours below zero`() {
        assertFalse(HoursDaysValidator.isAboveMinimumHoursPerWeek(-1.0))
    }

    @Test
    fun `Validate hours above zero`() {
        assertTrue(HoursDaysValidator.isAboveMinimumHoursPerWeek(26.0))
    }

    @Test
    fun `Validate hours below max`() {
        assertTrue(HoursDaysValidator.isBelowMaximumHoursPerWeek(168.0))
    }
    @Test
    fun `Validate hours above max`() {
        assertFalse(HoursDaysValidator.isBelowMaximumHoursPerWeek(168.1))
    }

    @Test
    fun validate_days_below_zero() {
        assertFalse(HoursDaysValidator.isAboveMinimumDaysPerWeek(-1.0))
    }

    @Test
    fun `Validate days above zero`() {
        assertTrue(HoursDaysValidator.isAboveMinimumDaysPerWeek(5.0))
    }

    @Test
    fun `Validate days below max`() {
        assertTrue(HoursDaysValidator.isBelowMaximumDaysPerWeek(6.0))
    }

    @Test
    fun `Validate days above max`() {
        assertFalse(HoursDaysValidator.isBelowMaximumDaysPerWeek(8.0))
    }
}
