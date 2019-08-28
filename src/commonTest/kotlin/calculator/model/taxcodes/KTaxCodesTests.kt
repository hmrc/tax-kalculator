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
package calculator.model.taxcodes

import calculator.Calculator
import calculator.model.PayPeriod
import kotlin.test.Test
import kotlin.test.assertEquals

class KTaxCodesTests {

    @Test
    fun `CK100 Wales 100K`() {
        val calculator = Calculator("CK100", 100000.0, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(32905.4, calculator.yearly.taxToPay)
        assertEquals(12608.784000000001, calculator.yearly.employersNI)
        assertEquals(5964.16, calculator.yearly.employeesNI)
    }

    @Test
    fun `K100 England 100K`() {
        val calculator = Calculator("K100", 100000.0, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(32905.4, calculator.yearly.taxToPay)
        assertEquals(12608.784000000001, calculator.yearly.employersNI)
        assertEquals(5964.16, calculator.yearly.employeesNI)
    }

    @Test
    fun `SK100 Scotland 100K`() {
        val calculator = Calculator("SK100", 100000.0, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(35084.74, calculator.yearly.taxToPay)
        assertEquals(12608.784000000001, calculator.yearly.employersNI)
        assertEquals(5964.16, calculator.yearly.employeesNI)
    }
}