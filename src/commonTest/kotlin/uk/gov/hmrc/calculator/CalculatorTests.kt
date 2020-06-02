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
package uk.gov.hmrc.calculator

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import uk.gov.hmrc.calculator.exception.InvalidHoursException
import uk.gov.hmrc.calculator.exception.InvalidWagesException
import uk.gov.hmrc.calculator.model.PayPeriod

internal class CalculatorTests {

    @Test
    fun `GIVEN hours is zero and pay period hour WHEN calculate THEN exception`() {
        assertFailsWith<InvalidHoursException> {
            Calculator(taxCode = "1250L", wages = 20.0, payPeriod = PayPeriod.HOURLY, howManyAWeek = 0.0).run()
        }
    }

    @Test
    fun `GIVEN hours is null and pay period hour WHEN calculate THEN exception`() {
        assertFailsWith<InvalidHoursException> {
            Calculator(taxCode = "1250L", wages = 20.0, payPeriod = PayPeriod.HOURLY).run()
        }
    }

    @Test
    fun `GIVEN wages is below zero WHEN calculate THEN exception`() {
        assertFailsWith<InvalidWagesException> {
            Calculator(taxCode = "1250L", wages = -190.0, payPeriod = PayPeriod.WEEKLY).run()
        }
    }

    @Test
    fun `GIVEN wages is zero WHEN calculate THEN exception`() {
        assertFailsWith<InvalidWagesException> {
            Calculator(taxCode = "1250L", wages = 0.0, payPeriod = PayPeriod.YEARLY).run()
        }
    }

    @Test
    fun `GIVEN wages too high WHEN calculate THEN exception`() {
        assertFailsWith<InvalidWagesException> {
            Calculator(taxCode = "1250L", wages = 10000000.0, payPeriod = PayPeriod.YEARLY).run()
        }
    }

    @Test
    fun `WHEN get default tax code THEN return 1250L`() {
        assertEquals("1250L", Calculator.getDefaultTaxCode())
    }
}
