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

import kotlin.test.Test
import kotlin.test.assertEquals
import uk.gov.hmrc.calculator.Calculator
import uk.gov.hmrc.calculator.model.PayPeriod.YEARLY

class MarriageNCodesTests {

    @Test
    fun `Wales N Code 20k`() {
        val calculator = Calculator(
            "C1050N",
            10000.0,
            payPeriod = YEARLY,
            taxYear = 2019
        ).run()
        assertEquals(0.0, calculator.yearly.taxToPay)
        assertEquals(188.78400000000002, calculator.yearly.employersNI)
        assertEquals(164.16, calculator.yearly.employeesNI)
    }

    @Test
    fun `Scotland N Code 20k`() {
        val calculator = Calculator(
            "S1050N",
            10000.0,
            payPeriod = YEARLY,
            taxYear = 2019
        ).run()
        assertEquals(0.0, calculator.yearly.taxToPay)
        assertEquals(188.78400000000002, calculator.yearly.employersNI)
        assertEquals(164.16, calculator.yearly.employeesNI)
    }

    @Test
    fun `England N Code 20k`() {
        val calculator = Calculator(
            "1050N",
            10000.0,
            payPeriod = YEARLY,
            taxYear = 2019
        ).run()
        assertEquals(0.0, calculator.yearly.taxToPay)
        assertEquals(188.78400000000002, calculator.yearly.employersNI)
        assertEquals(164.16, calculator.yearly.employeesNI)
    }
}
