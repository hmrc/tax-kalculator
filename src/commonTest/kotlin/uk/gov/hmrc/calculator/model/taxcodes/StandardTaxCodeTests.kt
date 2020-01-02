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
package uk.gov.hmrc.calculator.model.taxcodes

import kotlin.test.Test
import kotlin.test.assertEquals
import uk.gov.hmrc.calculator.Calculator
import uk.gov.hmrc.calculator.model.CalculatorResponse
import uk.gov.hmrc.calculator.model.PayPeriod.FOUR_WEEKLY
import uk.gov.hmrc.calculator.model.PayPeriod.HOURLY
import uk.gov.hmrc.calculator.model.PayPeriod.MONTHLY
import uk.gov.hmrc.calculator.model.PayPeriod.WEEKLY
import uk.gov.hmrc.calculator.model.PayPeriod.YEARLY

class StandardTaxCodeTestsYearly {

    //    1250L @ 100K
    @Test
    fun `1250L WalesYEARLY 100k`() {
        val calculator: CalculatorResponse =
            Calculator(
                "C1250L",
                100000.0,
                payPeriod = YEARLY,
                taxYear = 2019
            ).run()
        assertEquals(27498.2, calculator.yearly.taxToPay)
        assertEquals(12608.784000000001, calculator.yearly.employersNI)
        assertEquals(5964.16, calculator.yearly.employeesNI)
    }

    @Test
    fun `1250L England YEARLY 100k`() {
        val calculator = Calculator(
            "1250L",
            100000.0,
            payPeriod = YEARLY,
            taxYear = 2019
        ).run()
        assertEquals(27498.2, calculator.yearly.taxToPay)
        assertEquals(12608.784000000001, calculator.yearly.employersNI)
        assertEquals(5964.16, calculator.yearly.employeesNI)
    }

    @Test
    fun `1185L England YEARLY 100k`() {
        val calculator = Calculator(
            "1185L",
            100000.0,
            payPeriod = YEARLY,
            taxYear = 2019
        ).run()
        assertEquals(27758.2, calculator.yearly.taxToPay)
        assertEquals(12608.784000000001, calculator.yearly.employersNI)
        assertEquals(5964.16, calculator.yearly.employeesNI)
    }

    @Test
    fun `1250L Scotland YEARLY 100k`() {
        val calculator = Calculator(
            "S1250L",
            100000.0,
            payPeriod = YEARLY,
            taxYear = 2019
        ).run()
        assertEquals(29542.359999999997, calculator.yearly.taxToPay)
        assertEquals(12608.784000000001, calculator.yearly.employersNI)
        assertEquals(5964.16, calculator.yearly.employeesNI)
    }

    //    1250L @ 20K
    @Test
    fun `1250L England YEARLY 20k`() {
        val calculator = Calculator(
            "1250L",
            20000.0,
            payPeriod = YEARLY,
            taxYear = 2019
        ).run()
        assertEquals(1498.2, calculator.yearly.taxToPay)
        assertEquals(1568.784, calculator.yearly.employersNI)
        assertEquals(1364.1599999999999, calculator.yearly.employeesNI)
    }

    @Test
    fun `1250L Wales YEARLY 20k`() {
        val calculator = Calculator(
            "C1250L",
            20000.0,
            payPeriod = YEARLY,
            taxYear = 2019
        ).run()
        assertEquals(1498.2, calculator.yearly.taxToPay)
        assertEquals(1568.784, calculator.yearly.employersNI)
        assertEquals(1364.1599999999999, calculator.yearly.employeesNI)
    }

    @Test
    fun `1250L Scotland YEARLY 20k`() {
        val calculator = Calculator(
            "S1250L",
            20000.0,
            payPeriod = YEARLY,
            taxYear = 2019
        ).run()
        assertEquals(1477.8000000000002, calculator.yearly.taxToPay)
        assertEquals(1568.784, calculator.yearly.employersNI)
        assertEquals(1364.1599999999999, calculator.yearly.employeesNI)
    }
}

class StandardTaxCodeTestsOtherTimePeriods {
    @Test
    fun `1250L England MONTHLY 2k`() {
        val calculator = Calculator(
            "1250L",
            2000.0,
            payPeriod = MONTHLY,
            taxYear = 2019
        ).run()
        assertEquals(1844.1599999999999, calculator.yearly.employeesNI)
        assertEquals(2120.784, calculator.yearly.employersNI)
        assertEquals(2298.2000000000003, calculator.yearly.taxToPay)
    }

    @Test
    fun `1250L England FOURWEEKLY 2k`() {
        val calculator = Calculator(
            "1250L",
            2000.0,
            payPeriod = FOUR_WEEKLY,
            taxYear = 2019
        ).run()
        assertEquals(2084.16, calculator.yearly.employeesNI)
        assertEquals(2396.784, calculator.yearly.employersNI)
        assertEquals(2698.2000000000003, calculator.yearly.taxToPay)
    }

    @Test
    fun `1250L England WEEKLY 500 pounds`() {
        val calculator = Calculator(
            "1250L",
            500.0,
            payPeriod = WEEKLY,
            taxYear = 2019
        ).run()
        assertEquals(2084.16, calculator.yearly.employeesNI)
        assertEquals(2396.784, calculator.yearly.employersNI)
        assertEquals(2698.2000000000003, calculator.yearly.taxToPay)
    }

    @Test
    fun `1250L England HOURLY 20 pounds`() {
        val calculator =
            Calculator(
                "1250L",
                20.0,
                payPeriod = HOURLY,
                howManyAWeek = 37.5,
                taxYear = 2019
            ).run()
        assertEquals(3644.16, calculator.yearly.employeesNI)
        assertEquals(4190.784000000001, calculator.yearly.employersNI)
        assertEquals(5298.200000000001, calculator.yearly.taxToPay)
    }
}
