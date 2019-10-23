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
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import uk.gov.hmrc.calculator.exception.InvalidHoursException
import uk.gov.hmrc.calculator.exception.InvalidWagesException
import uk.gov.hmrc.calculator.model.PayPeriod.HOURLY
import uk.gov.hmrc.calculator.model.PayPeriod.YEARLY

class CalculatorTests {

    @Test
    fun `Error When Hours=0 And PayPeriod Is HOURLY`() {
        assertFailsWith<InvalidHoursException> {
            Calculator("1250L", 20.0, payPeriod = HOURLY, hoursPerWeek = 0.0, taxYear = 2019).run()
        }
    }

    @Test
    fun `Error When Hours=null And PayPeriod Is HOURLY`() {
        assertFailsWith<InvalidHoursException> {
            Calculator("1250L", 20.0, payPeriod = HOURLY, taxYear = 2019).run()
        }
    }

    @Test
    fun `Error when wages zero`() {
        assertFailsWith<InvalidWagesException> {
            Calculator("1250L", 0.0, payPeriod = YEARLY, taxYear = 2019).run()
        }
    }

    @Test
    fun `Get Default Tax code for year`() {
        assertEquals(Calculator.getDefaultTaxCode(), "1250L")
    }

    @Test
    fun `Validate valid tax code`() {
        assertTrue(Calculator.isValidTaxCode("1250L"))
    }

    @Test
    fun `Validate invalid tax code`() {
        assertFalse(Calculator.isValidTaxCode("HELLO"))
    }

    @Test
    fun `Validate wages below zero`() {
        assertFalse(Calculator.isAboveMinimumWages(-1.0))
    }

    @Test
    fun `Validate wages above zero`() {
        assertTrue(Calculator.isAboveMinimumWages(12000.0))
    }

    @Test
    fun `Validate wages below max`() {
        assertTrue(Calculator.isBelowMaximumWages(9999999.0))
    }

    @Test
    fun `Validate wages above max`() {
        assertFalse(Calculator.isBelowMaximumWages(10000000.0))
    }

    @Test
    fun `Validate hours below zero`() {
        assertFalse(Calculator.isAboveMinimumHoursPerWeek(-1.0))
    }

    @Test
    fun `Validate hours above zero`() {
        assertTrue(Calculator.isAboveMinimumHoursPerWeek(26.0))
    }

    @Test
    fun `Validate hours below max`() {
        assertTrue(Calculator.isBelowMaximumHoursPerWeek(168.0))
    }

    @Test
    fun `Validate hours above max`() {
        assertFalse(Calculator.isBelowMaximumHoursPerWeek(168.1))
    }
}
