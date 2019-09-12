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
import calculator.model.PayPeriod.YEARLY
import kotlin.test.Test
import kotlin.test.assertEquals

class MarriageMCodesTests {

    @Test
    fun `Wales M Code 20k`() {
        val calculator = Calculator("C1450M", 20000.0, payPeriod = YEARLY, taxYear = 2019).run()
        assertEquals(1098.20, calculator.yearly.taxToPay)
        assertEquals(1568.784, calculator.yearly.employersNI)
        assertEquals(1364.1599999999999, calculator.yearly.employeesNI)
    }

    @Test
    fun `Scotland M Code 20k`() {
        val calculator = Calculator("S1450M", 20000.0, payPeriod = YEARLY, taxYear = 2019).run()
        assertEquals(1077.8000000000002, calculator.yearly.taxToPay)
        assertEquals(1568.784, calculator.yearly.employersNI)
        assertEquals(1364.1599999999999, calculator.yearly.employeesNI)
    }

    @Test
    fun `England M Code 20k`() {
        val calculator = Calculator("1450M", 20000.0, payPeriod = YEARLY, taxYear = 2019).run()
        assertEquals(1098.20, calculator.yearly.taxToPay)
        assertEquals(1568.784, calculator.yearly.employersNI)
        assertEquals(1364.1599999999999, calculator.yearly.employeesNI)
    }
}
