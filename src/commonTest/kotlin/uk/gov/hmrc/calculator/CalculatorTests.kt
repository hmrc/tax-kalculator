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

import uk.gov.hmrc.calculator.model.PayPeriod.HOURLY
import uk.gov.hmrc.calculator.exception.InvalidHoursException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CalculatorTests {

    @Test
    fun `Error When Hours=0 And PayPeriod Is HOURLY`() {
        assertFailsWith<InvalidHoursException> {
            Calculator("1250L", 20.0, payPeriod = HOURLY, hoursPerWeek = 0.0, taxYear = 2019)
        }
    }
    @Test
    fun `Error When Hours=null And PayPeriod Is HOURLY`() {
        assertFailsWith<InvalidHoursException> {
            Calculator("1250L", 20.0, payPeriod = HOURLY, taxYear = 2019)
        }
    }

    @Test
    fun `Get Default Tax code for year`() {
        assertEquals(Calculator.getDefaultTaxCode(), "1250L")
    }
}