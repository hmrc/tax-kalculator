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
package uk.gov.hmrc.calculator.model

import kotlin.test.Test
import kotlin.test.assertEquals
import uk.gov.hmrc.calculator.Calculator
import uk.gov.hmrc.calculator.model.Country.ENGLAND
import uk.gov.hmrc.calculator.model.Country.SCOTLAND
import uk.gov.hmrc.calculator.model.PayPeriod.MONTHLY
import uk.gov.hmrc.calculator.model.PayPeriod.YEARLY

class CalculatorResponseTests {

    @Test
    fun `Check Summation Of Total Deductions`() {
        val response = CalculatorResponsePayPeriod(
            payPeriod = YEARLY,
            taxToPayForPayPeriod = 100.0,
            employeesNI = 200.0,
            employersNI = 300.0,
            wages = 1000.0,
            taxBreakdownForPayPeriod = listOf(
                BandBreakdown(0.0, 0.0),
                BandBreakdown(0.2, 2000.0)
            ),
            taxFree = 12509.0
        )

        assertEquals(100.0, response.taxToPay)
        assertEquals(200.0, response.employeesNI)
        assertEquals(300.0, response.employersNI)
        assertEquals(300.0, response.totalDeductions)
        assertEquals(700.0, response.takeHome)
        assertEquals(
            listOf(
                BandBreakdown(0.0, 0.0),
                BandBreakdown(0.2, 2000.0)
            ), response.taxBreakdown
        )
        assertEquals(12509.0, response.taxFree)
    }

    @Test
    fun `Check Full Calculator Response Small Amount`() {
        val taxCode = "1250L"
        val wages = 2000.00
        val response = Calculator(taxCode, wages, payPeriod = MONTHLY).run()

        assertEquals(ENGLAND, response.country)
        assertEquals(false, response.isKCode)
        assertEquals("1250L", response.taxCode)
        // Year

        assertEquals(YEARLY, response.yearly.payPeriod)
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
                BandBreakdown(
                    percentage = 0.2,
                    amount = 2298.2000000000003
                )
            ), response.yearly.taxBreakdown
        )
        assertEquals(response.yearly.taxBreakdown?.get(0)?.bandDescription, "Income taxed at 20%")

        assertEquals(191.51666666666668, response.monthly.taxToPay)
        assertEquals(153.67999999999998, response.monthly.employeesNI)
        assertEquals(176.732, response.monthly.employersNI)
        assertEquals(345.19666666666666, response.monthly.totalDeductions)
        assertEquals(1654.8033333333333, response.monthly.takeHome)
        assertEquals(1042.4166666666667, response.monthly.taxFree)
        assertEquals(2000.0, response.monthly.wages)
        assertEquals(
            listOf(
                BandBreakdown(
                    percentage = 0.2,
                    amount = 191.51666666666668
                )
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
                BandBreakdown(
                    percentage = 0.2,
                    amount = 176.7846153846154
                )
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
                BandBreakdown(
                    percentage = 0.2,
                    amount = 44.19615384615385
                )
            ), response.weekly.taxBreakdown
        )
    }

    @Test
    fun `Check Full Calculator Response Large Amount`() {
        val taxCode = "250T"
        val wages = 10000.00
        val response = Calculator(taxCode, wages, payPeriod = MONTHLY).run()

        assertEquals(ENGLAND, response.country)
        assertEquals(false, response.isKCode)
        assertEquals("250T", response.taxCode)
        // Year

        assertEquals(YEARLY, response.yearly.payPeriod)
        assertEquals(39498.20, response.yearly.taxToPay)
        assertEquals(6364.16, response.yearly.employeesNI)
        assertEquals(15368.784, response.yearly.employersNI)
        assertEquals(45862.36, response.yearly.totalDeductions)
        assertEquals(74137.64, response.yearly.takeHome)
        assertEquals(2509.0, response.yearly.taxFree)
        assertEquals(120000.0, response.yearly.wages)
        assertEquals(null, response.yearly.kCodeAdjustment)
        assertEquals(
            listOf(
                BandBreakdown(
                    percentage = 0.2,
                    amount = 7498.200000000001
                ),
                BandBreakdown(percentage = 0.4, amount = 32000.00)
            ), response.yearly.taxBreakdown
        )
        assertEquals(response.monthly.taxBreakdown?.get(0)?.bandDescription, "Income taxed at 20%")
        assertEquals(response.monthly.taxBreakdown?.get(1)?.bandDescription, "Income taxed at 40%")

        assertEquals(3291.5166666666664, response.monthly.taxToPay)
        assertEquals(530.3466666666667, response.monthly.employeesNI)
        assertEquals(1280.732, response.monthly.employersNI)
        assertEquals(3821.8633333333332, response.monthly.totalDeductions)
        assertEquals(6178.136666666667, response.monthly.takeHome)
        assertEquals(209.08333333333334, response.monthly.taxFree)
        assertEquals(10000.0, response.monthly.wages)
        assertEquals(
            listOf(
                BandBreakdown(percentage = 0.2, amount = 624.85),
                BandBreakdown(
                    percentage = 0.4,
                    amount = 2666.6666666666665
                )
            ), response.monthly.taxBreakdown
        )

        assertEquals(3038.3230769230768, response.fourWeekly.taxToPay)
        assertEquals(489.5507692307692, response.fourWeekly.employeesNI)
        assertEquals(1182.2141538461537, response.fourWeekly.employersNI)
        assertEquals(3527.873846153846, response.fourWeekly.totalDeductions)
        assertEquals(5702.8953846153845, response.fourWeekly.takeHome)
        assertEquals(193.0, response.fourWeekly.taxFree)
        assertEquals(9230.76923076923, response.fourWeekly.wages)
        assertEquals(
            listOf(
                BandBreakdown(
                    percentage = 0.2,
                    amount = 576.7846153846154
                ),
                BandBreakdown(
                    percentage = 0.4,
                    amount = 2461.5384615384614
                )
            ), response.fourWeekly.taxBreakdown
        )

        assertEquals(759.5807692307692, response.weekly.taxToPay)
        assertEquals(122.3876923076923, response.weekly.employeesNI)
        assertEquals(295.55353846153844, response.weekly.employersNI)
        assertEquals(881.9684615384615, response.weekly.totalDeductions)
        assertEquals(1425.7238461538461, response.weekly.takeHome)
        assertEquals(48.25, response.weekly.taxFree)
        assertEquals(2307.6923076923076, response.weekly.wages)
        assertEquals(
            listOf(
                BandBreakdown(
                    percentage = 0.2,
                    amount = 144.19615384615385
                ),
                BandBreakdown(
                    percentage = 0.4,
                    amount = 615.3846153846154
                )
            ), response.weekly.taxBreakdown
        )
    }

    @Test
    fun `Check Full Calculator Response Scotland Large Amount`() {
        val taxCode = "S250T"
        val wages = 10000.00
        val response = Calculator(taxCode, wages, payPeriod = MONTHLY).run()

        assertEquals(SCOTLAND, response.country)
        assertEquals(false, response.isKCode)
        assertEquals("S250T", response.taxCode)
        // Year

        assertEquals(YEARLY, response.yearly.payPeriod)
        assertEquals(41842.36, response.yearly.taxToPay)
        assertEquals(6364.16, response.yearly.employeesNI)
        assertEquals(15368.784, response.yearly.employersNI)
        assertEquals(48206.520000000004, response.yearly.totalDeductions)
        assertEquals(71793.48, response.yearly.takeHome)
        assertEquals(2509.0, response.yearly.taxFree)
        assertEquals(120000.0, response.yearly.wages)
        assertEquals(null, response.yearly.kCodeAdjustment)
        assertEquals(
            listOf(
                BandBreakdown(percentage = 0.19, amount = 387.60),
                BandBreakdown(percentage = 0.2, amount = 2079.0),
                BandBreakdown(percentage = 0.21, amount = 3882.06),
                BandBreakdown(percentage = 0.41, amount = 35493.70)
            ), response.yearly.taxBreakdown
        )
        assertEquals(response.monthly.taxBreakdown?.get(0)?.bandDescription, "Income taxed at 19%")
        assertEquals(response.monthly.taxBreakdown?.get(1)?.bandDescription, "Income taxed at 20%")
        assertEquals(response.monthly.taxBreakdown?.get(2)?.bandDescription, "Income taxed at 21%")
        assertEquals(response.monthly.taxBreakdown?.get(3)?.bandDescription, "Income taxed at 41%")

        assertEquals(3486.8633333333332, response.monthly.taxToPay)
        assertEquals(530.3466666666667, response.monthly.employeesNI)
        assertEquals(1280.732, response.monthly.employersNI)
        assertEquals(4017.21, response.monthly.totalDeductions)
        assertEquals(5982.79, response.monthly.takeHome)
        assertEquals(209.08333333333334, response.monthly.taxFree)
        assertEquals(10000.0, response.monthly.wages)
        assertEquals(
            listOf(
                BandBreakdown(
                    percentage = 0.19,
                    amount = 32.300000000000004
                ),
                BandBreakdown(percentage = 0.2, amount = 173.25),
                BandBreakdown(percentage = 0.21, amount = 323.505),
                BandBreakdown(
                    percentage = 0.41,
                    amount = 2957.808333333333
                )
            ), response.monthly.taxBreakdown
        )

        assertEquals(3218.643076923077, response.fourWeekly.taxToPay)
        assertEquals(489.5507692307692, response.fourWeekly.employeesNI)
        assertEquals(1182.2141538461537, response.fourWeekly.employersNI)
        assertEquals(3708.193846153846, response.fourWeekly.totalDeductions)
        assertEquals(5522.575384615384, response.fourWeekly.takeHome)
        assertEquals(193.0, response.fourWeekly.taxFree)
        assertEquals(9230.76923076923, response.fourWeekly.wages)
        assertEquals(
            listOf(
                BandBreakdown(
                    percentage = 0.19,
                    amount = 29.815384615384616
                ),
                BandBreakdown(
                    percentage = 0.2,
                    amount = 159.92307692307693
                ),
                BandBreakdown(percentage = 0.21, amount = 298.62),
                BandBreakdown(
                    percentage = 0.41,
                    amount = 2730.2846153846153
                )
            ), response.fourWeekly.taxBreakdown
        )

        assertEquals(804.6607692307692, response.weekly.taxToPay)
        assertEquals(122.3876923076923, response.weekly.employeesNI)
        assertEquals(295.55353846153844, response.weekly.employersNI)
        assertEquals(927.0484615384615, response.weekly.totalDeductions)
        assertEquals(1380.643846153846, response.weekly.takeHome)
        assertEquals(48.25, response.weekly.taxFree)
        assertEquals(2307.6923076923076, response.weekly.wages)
        assertEquals(
            listOf(
                BandBreakdown(
                    percentage = 0.19,
                    amount = 7.453846153846154
                ),
                BandBreakdown(
                    percentage = 0.2,
                    amount = 39.98076923076923
                ),
                BandBreakdown(percentage = 0.21, amount = 74.655),
                BandBreakdown(
                    percentage = 0.41,
                    amount = 682.5711538461538
                )
            ), response.weekly.taxBreakdown
        )
    }

    @Test
    fun `Test code SD2`() {
        val taxCode = "SD2"
        val wages = 130000.00
        val response = Calculator(taxCode, wages, payPeriod = YEARLY).run()

        assertEquals(66364.16, response.yearly.totalDeductions)
        assertEquals(
            listOf(
                BandBreakdown(
                    percentage = 0.46,
                    amount = 59800.00
                )
            ), response.yearly.taxBreakdown
        )
    }

    @Test
    fun `Test code NT`() {
        val taxCode = "NT"
        val wages = 130000.00
        val response = Calculator(taxCode, wages, payPeriod = YEARLY).run()
        assertEquals(false, response.yearly.maxTaxAmountExceeded)
        assertEquals(6564.16, response.yearly.totalDeductions)
        assertEquals(
            listOf(
                BandBreakdown(
                    percentage = 0.00,
                    amount = 0.00
                )
            ), response.yearly.taxBreakdown
        )
    }

    @Test
    fun `Check K9999 (Ensure HMRC does not tax you more than 50%)`() {
        val taxCode = "K9999"
        val wages = 400.00
        val response = Calculator(taxCode, wages, payPeriod = PayPeriod.WEEKLY).run()

        assertEquals(ENGLAND, response.country)
        assertEquals(true, response.isKCode)
        assertEquals("K9999", response.taxCode)

        // Year
        assertEquals(YEARLY, response.yearly.payPeriod)
        assertEquals(10400.0, response.yearly.taxToPay)
        assertEquals(1460.1599999999999, response.yearly.employeesNI)
        assertEquals(true, response.yearly.maxTaxAmountExceeded)
        assertEquals(11860.16, response.yearly.totalDeductions)
        assertEquals(8939.84, response.yearly.takeHome)
        assertEquals(0.0, response.yearly.taxFree)
        assertEquals(20800.0, response.yearly.wages)
        assertEquals(99999.0, response.yearly.kCodeAdjustment)
        assertEquals(null, response.yearly.taxBreakdown)
    }

    @Test
    fun `Check S1250L when £500 a day for 5 days`() {
        val taxCode = "S1250L"
        val wages = 500.0
        val days = 5.0
        val response = Calculator(taxCode, wages, payPeriod = PayPeriod.DAILY, howManyAWeek = days).run()

        assertEquals(SCOTLAND, response.country)
        assertEquals(false, response.isKCode)
        assertEquals("S1250L", response.taxCode)

        // Year
        assertEquals(YEARLY, response.yearly.payPeriod)

        assertEquals(41842.36, response.yearly.taxToPay)
        assertEquals(6564.16, response.yearly.employeesNI)
        assertEquals(false, response.yearly.maxTaxAmountExceeded)
        assertEquals(48406.520000000004, response.yearly.totalDeductions)
        assertEquals(81593.48, response.yearly.takeHome)
        assertEquals(12509.0, response.yearly.taxFree)
        assertEquals(130000.0, response.yearly.wages)
        assertEquals(null, response.yearly.kCodeAdjustment)

        assertEquals(listOf(BandBreakdown(percentage = 0.19, amount = 387.6), BandBreakdown(percentage = 0.2, amount = 2079.0), BandBreakdown(percentage = 0.21, amount = 3882.06), BandBreakdown(percentage = 0.41, amount = 35493.7)), response.yearly.taxBreakdown)
        assertEquals(listOf(BandBreakdown(percentage = 0.19, amount = 32.300000000000004), BandBreakdown(percentage = 0.2, amount = 173.25), BandBreakdown(percentage = 0.21, amount = 323.505), BandBreakdown(percentage = 0.41, amount = 2957.808333333333)), response.monthly.taxBreakdown)
        assertEquals(listOf(BandBreakdown(percentage = 0.19, amount = 29.815384615384616), BandBreakdown(percentage = 0.2, amount = 159.92307692307693), BandBreakdown(percentage = 0.21, amount = 298.62), BandBreakdown(percentage = 0.41, amount = 2730.2846153846153)), response.fourWeekly.taxBreakdown)
    }
}
