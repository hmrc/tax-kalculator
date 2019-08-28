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
package calculator.model

import calculator.Calculator
import kotlin.test.Test
import kotlin.test.assertEquals

class CalculatorResponseTests {

    @Test
    fun `Check Summation Of Total Deductions`() {
        val response = CalculatorResponsePayPeriod(
            payPeriod = PayPeriod.YEARLY,
            taxToPay = 100.0,
            employeesNI = 200.0,
            employersNI = 300.0,
            wages = 1000.0,
            taxBreakdown = listOf(BandBreakdown(0.0, 0.0), BandBreakdown(0.2, 2000.0)),
            taxFree = 12509.0
        )

        assertEquals(100.0, response.taxToPay)
        assertEquals(200.0, response.employeesNI)
        assertEquals(300.0, response.employersNI)
        assertEquals(300.0, response.totalDeductions)
        assertEquals(700.0, response.takeHome)
        assertEquals(listOf(BandBreakdown(0.0, 0.0), BandBreakdown(0.2, 2000.0)), response.taxBreakdown)
        assertEquals(12509.0, response.taxFree)
    }

    @Test
    fun `Check Full Calculator Response`() {
        val taxCode = "1250L"
        val wages = 2000.00
        val response = Calculator(taxCode, wages, payPeriod = PayPeriod.MONTHLY).run()

        assertEquals(Country.ENGLAND, response.country)
        assertEquals(false, response.isKCode)
        assertEquals("1250L", response.taxCode)
        // Year

        assertEquals(PayPeriod.YEARLY, response.yearly.payPeriod)
        assertEquals(2298.2000000000003, response.yearly.taxToPay)
        assertEquals(1844.1599999999999, response.yearly.employeesNI)
        assertEquals(2120.784, response.yearly.employersNI)
        assertEquals(4142.360000000001, response.yearly.totalDeductions)
        assertEquals(19857.64, response.yearly.takeHome)
        assertEquals(12509.0, response.yearly.taxFree)
        assertEquals(24000.0, response.yearly.wages)
        assertEquals(null, response.yearly.kCodeAdjustment)
        assertEquals(
            listOf(
                BandBreakdown(percentage = 0.2, amount = 2298.2000000000003)
            ), response.yearly.taxBreakdown
        )
        assertEquals(response.yearly.taxBreakdown[0].bandDescription, "Income taxed at 20%")

        assertEquals(191.51666666666668, response.monthly.taxToPay)
        assertEquals(153.67999999999998, response.monthly.employeesNI)
        assertEquals(176.732, response.monthly.employersNI)
        assertEquals(345.19666666666666, response.monthly.totalDeductions)
        assertEquals(1654.8033333333333, response.monthly.takeHome)
        assertEquals(1042.4166666666667, response.monthly.taxFree)
        assertEquals(2000.0, response.monthly.wages)
        assertEquals(
            listOf(
                BandBreakdown(percentage = 0.2, amount = 191.51666666666668)
            ), response.monthly.taxBreakdown
        )

        assertEquals(176.7846153846154, response.fourWeekly.taxToPay)
        assertEquals(141.85846153846154, response.fourWeekly.employeesNI)
        assertEquals(163.13723076923077, response.fourWeekly.employersNI)
        assertEquals(318.64307692307693, response.fourWeekly.totalDeductions)
        assertEquals(1527.5107692307693, response.fourWeekly.takeHome)
        assertEquals(962.2307692307693, response.fourWeekly.taxFree)
        assertEquals(
            listOf(
                BandBreakdown(percentage = 0.2, amount = 176.7846153846154)
            ), response.fourWeekly.taxBreakdown
        )

        assertEquals(44.19615384615385, response.weekly.taxToPay)
        assertEquals(35.464615384615385, response.weekly.employeesNI)
        assertEquals(40.78430769230769, response.weekly.employersNI)
        assertEquals(79.66076923076923, response.weekly.totalDeductions)
        assertEquals(381.8776923076923, response.weekly.takeHome)
        assertEquals(240.55769230769232, response.weekly.taxFree)
        assertEquals(
            listOf(
                BandBreakdown(percentage = 0.2, amount = 44.19615384615385)
            ), response.weekly.taxBreakdown
        )
    }
}