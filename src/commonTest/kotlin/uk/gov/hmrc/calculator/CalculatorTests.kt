/*
 * Copyright 2022 HM Revenue & Customs
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
import uk.gov.hmrc.calculator.exception.InvalidHoursException
import uk.gov.hmrc.calculator.exception.InvalidTaxYearException
import uk.gov.hmrc.calculator.exception.InvalidWagesException
import uk.gov.hmrc.calculator.model.Country
import uk.gov.hmrc.calculator.model.PayPeriod
import uk.gov.hmrc.calculator.model.TaxYear
import uk.gov.hmrc.calculator.model.taxcodes.TaxCode

internal class CalculatorTests {

    @Test
    fun `GIVEN 2021 WHEN all supplied data valid THEN calculates response`() {
        val result = Calculator(
            taxCode = "1257L",
            wages = 40000.0,
            payPeriod = PayPeriod.YEARLY,
            taxYear = 2021
        ).run()

        assertEquals(Country.ENGLAND, result.country)
        assertFalse(result.isKCode)

        val weekly = result.weekly
        assertEquals(PayPeriod.WEEKLY, weekly.payPeriod)
        assertEquals(70.23, weekly.employeesNI)
        assertEquals(82.69, weekly.employersNI)
        assertEquals(769.23, weekly.wages)
        assertEquals(241.9, weekly.taxFree)
        assertEquals(105.47, weekly.taxToPay)
        assertEquals(593.53, weekly.takeHome)

        val fourWeekly = result.fourWeekly
        assertEquals(PayPeriod.FOUR_WEEKLY, fourWeekly.payPeriod)
        assertEquals(280.91, fourWeekly.employeesNI)
        assertEquals(330.78, fourWeekly.employersNI)
        assertEquals(3076.92, fourWeekly.wages)
        assertEquals(967.62, fourWeekly.taxFree)
        assertEquals(421.86, fourWeekly.taxToPay)
        assertEquals(2374.15, fourWeekly.takeHome)

        val monthly = result.monthly
        assertEquals(PayPeriod.MONTHLY, monthly.payPeriod)
        assertEquals(304.32, monthly.employeesNI)
        assertEquals(358.34, monthly.employersNI)
        assertEquals(3333.33, monthly.wages)
        assertEquals(1048.25, monthly.taxFree)
        assertEquals(457.02, monthly.taxToPay)
        assertEquals(2571.99, monthly.takeHome)

        val yearly = result.yearly
        assertEquals(PayPeriod.YEARLY, yearly.payPeriod)
        assertEquals(3651.84, yearly.employeesNI)
        assertEquals(4300.08, yearly.employersNI)
        assertEquals(40000.00, yearly.wages)
        assertEquals(12579.0, yearly.taxFree)
        assertEquals(5484.20, yearly.taxToPay)
        assertEquals(30863.96, yearly.takeHome)
    }

    @Test
    fun `GIVEN 2022 WHEN all supplied data valid THEN calculates response`() {
        val result = Calculator(
            taxCode = "1257L",
            wages = 40000.0,
            payPeriod = PayPeriod.YEARLY,
            taxYear = 2022
        ).run()

        assertEquals(Country.ENGLAND, result.country)
        assertFalse(result.isKCode)

        val weekly = result.weekly
        assertEquals(PayPeriod.WEEKLY, weekly.payPeriod)
        assertEquals(76.75, weekly.employeesNI)
        assertEquals(89.43, weekly.employersNI)
        assertEquals(769.23, weekly.wages)
        assertEquals(241.9, weekly.taxFree)
        assertEquals(105.47, weekly.taxToPay)
        assertEquals(587.01, weekly.takeHome)

        val fourWeekly = result.fourWeekly
        assertEquals(PayPeriod.FOUR_WEEKLY, fourWeekly.payPeriod)
        assertEquals(306.99, fourWeekly.employeesNI)
        assertEquals(357.73, fourWeekly.employersNI)
        assertEquals(3076.92, fourWeekly.wages)
        assertEquals(967.62, fourWeekly.taxFree)
        assertEquals(421.86, fourWeekly.taxToPay)
        assertEquals(2348.07, fourWeekly.takeHome)

        val monthly = result.monthly
        assertEquals(PayPeriod.MONTHLY, monthly.payPeriod)
        assertEquals(332.58, monthly.employeesNI)
        assertEquals(387.54, monthly.employersNI)
        assertEquals(3333.33, monthly.wages)
        assertEquals(1048.25, monthly.taxFree)
        assertEquals(457.02, monthly.taxToPay)
        assertEquals(2543.73, monthly.takeHome)

        val yearly = result.yearly
        assertEquals(PayPeriod.YEARLY, yearly.payPeriod)
        assertEquals(3990.90, yearly.employeesNI)
        assertEquals(4650.45, yearly.employersNI)
        assertEquals(40000.00, yearly.wages)
        assertEquals(12579.00, yearly.taxFree)
        assertEquals(5484.20, yearly.taxToPay)
        assertEquals(30524.90, yearly.takeHome)
    }

    @Test
    fun `GIVEN hours is zero and pay period hour WHEN calculate THEN exception`() {
        assertFailsWith<InvalidHoursException> {
            Calculator(
                taxCode = "1250L",
                wages = 20.0,
                payPeriod = PayPeriod.HOURLY,
                howManyAWeek = 0.0
            ).run()
        }
    }

    @Test
    fun `GIVEN hours is null and pay period hour WHEN calculate THEN exception`() {
        assertFailsWith<InvalidHoursException> {
            Calculator(
                taxCode = "1250L",
                wages = 20.0,
                payPeriod = PayPeriod.HOURLY
            ).run()
        }
    }

    @Test
    fun `GIVEN wages is below zero WHEN calculate THEN exception`() {
        assertFailsWith<InvalidWagesException> {
            Calculator(
                taxCode = "1250L",
                wages = -190.0,
                payPeriod = PayPeriod.WEEKLY
            ).run()
        }
    }

    @Test
    fun `GIVEN wages is zero WHEN calculate THEN exception`() {
        assertFailsWith<InvalidWagesException> {
            Calculator(
                taxCode = "1250L",
                wages = 0.0,
                payPeriod = PayPeriod.YEARLY
            ).run()
        }
    }

    @Test
    fun `GIVEN wages too high WHEN calculate THEN exception`() {
        assertFailsWith<InvalidWagesException> {
            Calculator(
                taxCode = "1257L",
                wages = 10000000.0,
                payPeriod = PayPeriod.YEARLY
            ).run()
        }
    }

    @Test
    fun `GIVEN year invalid WHEN calculate THEN exception`() {
        assertFailsWith<InvalidTaxYearException> {
            Calculator(
                taxCode = "1257L",
                wages = 40000.0,
                payPeriod = PayPeriod.YEARLY,
                taxYear = 2040
            ).run()
        }
    }

    @Test
    fun `WHEN get default tax code THEN return 1257L`() {
        assertEquals(
            "1257L",
            TaxCode.getDefaultTaxCode()
        )
    }

    @Test
    fun `WHEN 2020 get default tax code THEN return 1250L`() {
        assertEquals(
            "1250L",
            TaxCode.getDefaultTaxCode(TaxYear.TWENTY_TWENTY)
        )
    }

    @Test
    fun `WHEN 2021 get default tax code THEN return 1257L`() {
        assertEquals(
            "1257L",
            TaxCode.getDefaultTaxCode(TaxYear.TWENTY_TWENTY_ONE)
        )
    }

    @Test
    fun `WHEN 2022 get default tax code THEN return 1257L`() {
        assertEquals(
            "1257L",
            TaxCode.getDefaultTaxCode(TaxYear.TWENTY_TWENTY_TWO)
        )
    }
}
