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

class HoursDaysValidatorTests {
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
