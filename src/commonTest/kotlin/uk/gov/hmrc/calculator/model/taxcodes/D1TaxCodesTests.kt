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
package uk.gov.hmrc.calculator.model.taxcodes

import uk.gov.hmrc.calculator.Calculator
import uk.gov.hmrc.calculator.model.PayPeriod.YEARLY
import kotlin.test.Test
import kotlin.test.assertEquals

class D1TaxCodesTests {

    @Test
    fun `D1 Wales 100K`() {
        val calculator = Calculator(
            "CD1",
            100000.0,
            payPeriod = YEARLY,
            taxYear = 2019
        ).run()
        assertEquals(45000.00, calculator.yearly.taxToPay)
        assertEquals(12608.784000000001, calculator.yearly.employersNI)
        assertEquals(5964.16, calculator.yearly.employeesNI)
    }

    @Test
    fun `D1 England 100K`() {
        val calculator = Calculator(
            "D1",
            100000.0,
            payPeriod = YEARLY,
            taxYear = 2019
        ).run()
        assertEquals(45000.00, calculator.yearly.taxToPay)
        assertEquals(12608.784000000001, calculator.yearly.employersNI)
        assertEquals(5964.16, calculator.yearly.employeesNI)
    }

    @Test
    fun `D1 Scotland 100K`() {
        val calculator = Calculator(
            "SD1",
            100000.0,
            payPeriod = YEARLY,
            taxYear = 2019
        ).run()
        assertEquals(41000.00, calculator.yearly.taxToPay)
        assertEquals(12608.784000000001, calculator.yearly.employersNI)
        assertEquals(5964.16, calculator.yearly.employeesNI)
    }
}