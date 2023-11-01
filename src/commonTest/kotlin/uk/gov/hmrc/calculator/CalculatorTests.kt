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

import co.touchlab.kermit.Logger
import uk.gov.hmrc.calculator.exception.InvalidHoursException
import uk.gov.hmrc.calculator.exception.InvalidPensionException
import uk.gov.hmrc.calculator.exception.InvalidTaxYearException
import uk.gov.hmrc.calculator.exception.InvalidWagesException
import uk.gov.hmrc.calculator.model.Country
import uk.gov.hmrc.calculator.model.PayPeriod
import uk.gov.hmrc.calculator.model.TaxYear
import uk.gov.hmrc.calculator.model.pension.AnnualPensionMethod
import uk.gov.hmrc.calculator.model.taxcodes.TaxCode
import uk.gov.hmrc.calculator.utils.prettyPrintDataClass
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

internal class CalculatorTests {

    @Test
    fun `GIVEN TWENTY_TWENTY_ONE WHEN 40000 wage THEN calculates response`() {
        val result = Calculator(
            taxCode = "1257L",
            wages = 40000.0,
            payPeriod = PayPeriod.YEARLY,
            taxYear = TaxYear.TWENTY_TWENTY_ONE
        ).run()
        Logger.i(result.prettyPrintDataClass())

        assertEquals(Country.ENGLAND, result.country)
        assertFalse(result.isKCode)

        val weekly = result.weekly
        assertEquals(PayPeriod.WEEKLY, weekly.payPeriod)
        assertEquals(70.23, weekly.employeesNI)
        assertEquals(82.69, weekly.employersNI)
        assertEquals(769.23, weekly.wages)
        assertEquals(241.73, weekly.taxFree)
        assertEquals(105.47, weekly.taxToPay)
        assertEquals(593.53, weekly.takeHome)
        assertEquals(0.0, weekly.pensionContribution)
        assertEquals(0.0, weekly.taperingAmountDeduction)
        assertNull(weekly.studentLoanBreakdown)
        assertEquals(0.0, weekly.finalStudentLoanAmount)

        val fourWeekly = result.fourWeekly
        assertEquals(PayPeriod.FOUR_WEEKLY, fourWeekly.payPeriod)
        assertEquals(280.91, fourWeekly.employeesNI)
        assertEquals(330.78, fourWeekly.employersNI)
        assertEquals(3076.92, fourWeekly.wages)
        assertEquals(966.92, fourWeekly.taxFree)
        assertEquals(421.86, fourWeekly.taxToPay)
        assertEquals(2374.15, fourWeekly.takeHome)
        assertEquals(0.0, fourWeekly.pensionContribution)
        assertEquals(0.0, fourWeekly.taperingAmountDeduction)
        assertNull(fourWeekly.studentLoanBreakdown)
        assertEquals(0.0, fourWeekly.finalStudentLoanAmount)

        val monthly = result.monthly
        assertEquals(PayPeriod.MONTHLY, monthly.payPeriod)
        assertEquals(304.32, monthly.employeesNI)
        assertEquals(358.34, monthly.employersNI)
        assertEquals(3333.33, monthly.wages)
        assertEquals(1047.5, monthly.taxFree)
        assertEquals(457.02, monthly.taxToPay)
        assertEquals(2571.99, monthly.takeHome)
        assertEquals(0.0, monthly.pensionContribution)
        assertEquals(0.0, monthly.taperingAmountDeduction)
        assertNull(monthly.studentLoanBreakdown)
        assertEquals(0.0, monthly.finalStudentLoanAmount)

        val yearly = result.yearly
        assertEquals(PayPeriod.YEARLY, yearly.payPeriod)
        assertEquals(3651.84, yearly.employeesNI)
        assertEquals(4300.08, yearly.employersNI)
        assertEquals(40000.00, yearly.wages)
        assertEquals(12570.0, yearly.taxFree)
        assertEquals(5484.20, yearly.taxToPay)
        assertEquals(30863.96, yearly.takeHome)
        assertEquals(0.0, yearly.pensionContribution)
        assertEquals(0.0, yearly.taperingAmountDeduction)
        assertNull(yearly.studentLoanBreakdown)
        assertEquals(0.0, yearly.finalStudentLoanAmount)
    }

    @Test
    fun `GIVEN TWENTY_TWENTY_TWO WHEN 40000 wage THEN calculates response`() {
        val result = Calculator(
            taxCode = "1257L",
            wages = 40000.0,
            payPeriod = PayPeriod.YEARLY,
            taxYear = TaxYear.TWENTY_TWENTY_TWO
        ).run()
        Logger.i(result.prettyPrintDataClass())

        assertEquals(Country.ENGLAND, result.country)
        assertFalse(result.isKCode)

        val weekly = result.weekly
        assertEquals(PayPeriod.WEEKLY, weekly.payPeriod)
        assertEquals(76.75, weekly.employeesNI)
        assertEquals(89.43, weekly.employersNI)
        assertEquals(769.23, weekly.wages)
        assertEquals(241.73, weekly.taxFree)
        assertEquals(105.47, weekly.taxToPay)
        assertEquals(587.01, weekly.takeHome)
        assertTrue(weekly.taxBreakdown!!.isNotEmpty())
        assertEquals(0.0, weekly.pensionContribution)
        assertEquals(0.0, weekly.taperingAmountDeduction)
        assertNull(weekly.studentLoanBreakdown)
        assertEquals(0.0, weekly.finalStudentLoanAmount)

        val fourWeekly = result.fourWeekly
        assertEquals(PayPeriod.FOUR_WEEKLY, fourWeekly.payPeriod)
        assertEquals(306.99, fourWeekly.employeesNI)
        assertEquals(357.73, fourWeekly.employersNI)
        assertEquals(3076.92, fourWeekly.wages)
        assertEquals(966.92, fourWeekly.taxFree)
        assertEquals(421.86, fourWeekly.taxToPay)
        assertEquals(2348.07, fourWeekly.takeHome)
        assertTrue(fourWeekly.taxBreakdown!!.isNotEmpty())
        assertEquals(0.0, fourWeekly.pensionContribution)
        assertEquals(0.0, fourWeekly.taperingAmountDeduction)
        assertNull(fourWeekly.studentLoanBreakdown)
        assertEquals(0.0, fourWeekly.finalStudentLoanAmount)

        val monthly = result.monthly
        assertEquals(PayPeriod.MONTHLY, monthly.payPeriod)
        assertEquals(332.58, monthly.employeesNI)
        assertEquals(387.54, monthly.employersNI)
        assertEquals(3333.33, monthly.wages)
        assertEquals(1047.5, monthly.taxFree)
        assertEquals(457.02, monthly.taxToPay)
        assertEquals(2543.73, monthly.takeHome)
        assertTrue(monthly.taxBreakdown!!.isNotEmpty())
        assertEquals(0.0, monthly.pensionContribution)
        assertEquals(0.0, monthly.taperingAmountDeduction)
        assertNull(monthly.studentLoanBreakdown)
        assertEquals(0.0, monthly.finalStudentLoanAmount)

        val yearly = result.yearly
        assertEquals(PayPeriod.YEARLY, yearly.payPeriod)
        assertEquals(3990.90, yearly.employeesNI)
        assertEquals(4650.45, yearly.employersNI)
        assertEquals(40000.00, yearly.wages)
        assertEquals(12570.00, yearly.taxFree)
        assertEquals(5484.20, yearly.taxToPay)
        assertEquals(30524.90, yearly.takeHome)
        assertTrue(yearly.taxBreakdown!!.isNotEmpty())
        assertEquals(0.0, yearly.pensionContribution)
        assertEquals(0.0, yearly.taperingAmountDeduction)
        assertNull(yearly.studentLoanBreakdown)
        assertEquals(0.0, yearly.finalStudentLoanAmount)
    }

    @Test
    fun `GIVEN TWENTY_TWENTY_TWO_JULY_REVISED WHEN 40000 wage THEN calculates response`() {
        val result = Calculator(
            taxCode = "1257L",
            wages = 40000.0,
            payPeriod = PayPeriod.YEARLY,
            taxYear = TaxYear.TWENTY_TWENTY_TWO_JULY_REVISED
        ).run()
        Logger.i(result.prettyPrintDataClass())

        assertEquals(Country.ENGLAND, result.country)
        assertFalse(result.isKCode)

        val weekly = result.weekly
        assertEquals(PayPeriod.WEEKLY, weekly.payPeriod)
        assertEquals(69.89, weekly.employeesNI)
        assertEquals(89.43, weekly.employersNI)
        assertEquals(769.23, weekly.wages)
        assertEquals(241.73, weekly.taxFree)
        assertEquals(105.47, weekly.taxToPay)
        assertEquals(593.87, weekly.takeHome)
        assertTrue(weekly.taxBreakdown!!.isNotEmpty())
        assertEquals(0.0, weekly.pensionContribution)
        assertEquals(0.0, weekly.taperingAmountDeduction)
        assertNull(weekly.studentLoanBreakdown)
        assertEquals(0.0, weekly.finalStudentLoanAmount)

        val fourWeekly = result.fourWeekly
        assertEquals(PayPeriod.FOUR_WEEKLY, fourWeekly.payPeriod)
        assertEquals(279.58, fourWeekly.employeesNI)
        assertEquals(357.73, fourWeekly.employersNI)
        assertEquals(3076.92, fourWeekly.wages)
        assertEquals(966.92, fourWeekly.taxFree)
        assertEquals(421.86, fourWeekly.taxToPay)
        assertEquals(2375.48, fourWeekly.takeHome)
        assertTrue(fourWeekly.taxBreakdown!!.isNotEmpty())
        assertEquals(0.0, fourWeekly.pensionContribution)
        assertEquals(0.0, fourWeekly.taperingAmountDeduction)
        assertNull(fourWeekly.studentLoanBreakdown)
        assertEquals(0.0, fourWeekly.finalStudentLoanAmount)

        val monthly = result.monthly
        assertEquals(PayPeriod.MONTHLY, monthly.payPeriod)
        assertEquals(302.87, monthly.employeesNI)
        assertEquals(387.54, monthly.employersNI)
        assertEquals(3333.33, monthly.wages)
        assertEquals(1047.5, monthly.taxFree)
        assertEquals(457.02, monthly.taxToPay)
        assertEquals(2573.44, monthly.takeHome)
        assertTrue(monthly.taxBreakdown!!.isNotEmpty())
        assertEquals(0.0, monthly.pensionContribution)
        assertEquals(0.0, monthly.taperingAmountDeduction)
        assertNull(monthly.studentLoanBreakdown)
        assertEquals(0.0, monthly.finalStudentLoanAmount)

        val yearly = result.yearly
        assertEquals(PayPeriod.YEARLY, yearly.payPeriod)
        assertEquals(3634.48, yearly.employeesNI)
        assertEquals(4650.45, yearly.employersNI)
        assertEquals(40000.00, yearly.wages)
        assertEquals(12570.00, yearly.taxFree)
        assertEquals(5484.20, yearly.taxToPay)
        assertEquals(30881.33, yearly.takeHome)
        assertTrue(yearly.taxBreakdown!!.isNotEmpty())
        assertEquals(0.0, yearly.pensionContribution)
        assertEquals(0.0, yearly.taperingAmountDeduction)
        assertNull(yearly.studentLoanBreakdown)
        assertEquals(0.0, yearly.finalStudentLoanAmount)
    }

    @Test
    fun `GIVEN TWENTY_TWENTY_TWO_JULY_REVISED WHEN 60000 wage THEN calculates response`() {
        val result = Calculator(
            taxCode = "1257L",
            wages = 60000.0,
            payPeriod = PayPeriod.YEARLY,
            taxYear = TaxYear.TWENTY_TWENTY_TWO_JULY_REVISED
        ).run()
        Logger.i(result.prettyPrintDataClass())

        assertEquals(Country.ENGLAND, result.country)
        assertFalse(result.isKCode)

        val weekly = result.weekly
        assertEquals(PayPeriod.WEEKLY, weekly.payPeriod)
        assertEquals(102.14, weekly.employeesNI)
        assertEquals(147.32, weekly.employersNI)
        assertEquals(1153.85, weekly.wages)
        assertEquals(241.73, weekly.taxFree)
        assertEquals(219.78, weekly.taxToPay)
        assertEquals(831.93, weekly.takeHome)
        assertTrue(weekly.taxBreakdown!!.isNotEmpty())
        assertEquals(0.0, weekly.pensionContribution)
        assertEquals(0.0, weekly.taperingAmountDeduction)
        assertNull(weekly.studentLoanBreakdown)
        assertEquals(0.0, weekly.finalStudentLoanAmount)

        val fourWeekly = result.fourWeekly
        assertEquals(PayPeriod.FOUR_WEEKLY, fourWeekly.payPeriod)
        assertEquals(408.58, fourWeekly.employeesNI)
        assertEquals(589.27, fourWeekly.employersNI)
        assertEquals(4615.38, fourWeekly.wages)
        assertEquals(966.92, fourWeekly.taxFree)
        assertEquals(879.11, fourWeekly.taxToPay)
        assertEquals(3327.7, fourWeekly.takeHome)
        assertTrue(fourWeekly.taxBreakdown!!.isNotEmpty())
        assertEquals(0.0, fourWeekly.pensionContribution)
        assertEquals(0.0, fourWeekly.taperingAmountDeduction)
        assertNull(fourWeekly.studentLoanBreakdown)
        assertEquals(0.0, fourWeekly.finalStudentLoanAmount)

        val monthly = result.monthly
        assertEquals(PayPeriod.MONTHLY, monthly.payPeriod)
        assertEquals(442.62, monthly.employeesNI)
        assertEquals(638.37, monthly.employersNI)
        assertEquals(5000.00, monthly.wages)
        assertEquals(1047.5, monthly.taxFree)
        assertEquals(952.37, monthly.taxToPay)
        assertEquals(3605.01, monthly.takeHome)
        assertTrue(monthly.taxBreakdown!!.isNotEmpty())
        assertEquals(0.0, monthly.pensionContribution)
        assertEquals(0.0, monthly.taperingAmountDeduction)
        assertNull(monthly.studentLoanBreakdown)
        assertEquals(0.0, monthly.finalStudentLoanAmount)

        val yearly = result.yearly
        assertEquals(PayPeriod.YEARLY, yearly.payPeriod)
        assertEquals(5311.48, yearly.employeesNI)
        assertEquals(7660.45, yearly.employersNI)
        assertEquals(60000.00, yearly.wages)
        assertEquals(12570.00, yearly.taxFree)
        assertEquals(11428.40, yearly.taxToPay)
        assertEquals(43260.12, yearly.takeHome)
        assertTrue(yearly.taxBreakdown!!.isNotEmpty())
        assertEquals(0.0, yearly.pensionContribution)
        assertEquals(0.0, yearly.taperingAmountDeduction)
        assertNull(yearly.studentLoanBreakdown)
        assertEquals(0.0, yearly.finalStudentLoanAmount)
    }

    @Test
    fun `GIVEN TWENTY_TWENTY_TWO_NOVEMBER_REVISED WHEN 40000 wage THEN calculates response`() {
        val result = Calculator(
            taxCode = "1257L",
            wages = 40000.0,
            payPeriod = PayPeriod.YEARLY,
            taxYear = TaxYear.TWENTY_TWENTY_TWO_NOVEMBER_REVISED
        ).run()
        Logger.i(result.prettyPrintDataClass())

        assertEquals(Country.ENGLAND, result.country)
        assertFalse(result.isKCode)

        val weekly = result.weekly
        assertEquals(PayPeriod.WEEKLY, weekly.payPeriod)
        assertEquals(63.3, weekly.employeesNI)
        assertEquals(82.00, weekly.employersNI)
        assertEquals(769.23, weekly.wages)
        assertEquals(241.73, weekly.taxFree)
        assertEquals(105.47, weekly.taxToPay)
        assertEquals(600.46, weekly.takeHome)
        assertTrue(weekly.taxBreakdown!!.isNotEmpty())
        assertEquals(0.0, weekly.pensionContribution)
        assertEquals(0.0, weekly.taperingAmountDeduction)
        assertNull(weekly.studentLoanBreakdown)
        assertEquals(0.0, weekly.finalStudentLoanAmount)

        val fourWeekly = result.fourWeekly
        assertEquals(PayPeriod.FOUR_WEEKLY, fourWeekly.payPeriod)
        assertEquals(253.2, fourWeekly.employeesNI)
        assertEquals(328.02, fourWeekly.employersNI)
        assertEquals(3076.92, fourWeekly.wages)
        assertEquals(966.92, fourWeekly.taxFree)
        assertEquals(421.86, fourWeekly.taxToPay)
        assertEquals(2401.86, fourWeekly.takeHome)
        assertTrue(fourWeekly.taxBreakdown!!.isNotEmpty())
        assertEquals(0.0, fourWeekly.pensionContribution)
        assertEquals(0.0, fourWeekly.taperingAmountDeduction)
        assertNull(fourWeekly.studentLoanBreakdown)
        assertEquals(0.0, fourWeekly.finalStudentLoanAmount)

        val monthly = result.monthly
        assertEquals(PayPeriod.MONTHLY, monthly.payPeriod)
        assertEquals(274.3, monthly.employeesNI)
        assertEquals(355.35, monthly.employersNI)
        assertEquals(3333.33, monthly.wages)
        assertEquals(1047.5, monthly.taxFree)
        assertEquals(457.02, monthly.taxToPay)
        assertEquals(2602.01, monthly.takeHome)
        assertTrue(monthly.taxBreakdown!!.isNotEmpty())
        assertEquals(0.0, monthly.pensionContribution)
        assertEquals(0.0, monthly.taperingAmountDeduction)
        assertNull(monthly.studentLoanBreakdown)
        assertEquals(0.0, monthly.finalStudentLoanAmount)

        val yearly = result.yearly
        assertEquals(PayPeriod.YEARLY, yearly.payPeriod)
        assertEquals(3291.6, yearly.employeesNI)
        assertEquals(4264.20, yearly.employersNI)
        assertEquals(40000.00, yearly.wages)
        assertEquals(12570.00, yearly.taxFree)
        assertEquals(5484.20, yearly.taxToPay)
        assertEquals(31224.2, yearly.takeHome)
        assertTrue(yearly.taxBreakdown!!.isNotEmpty())
        assertEquals(0.0, yearly.pensionContribution)
        assertEquals(0.0, yearly.taperingAmountDeduction)
        assertNull(yearly.studentLoanBreakdown)
        assertEquals(0.0, yearly.finalStudentLoanAmount)
    }

    @Test
    fun `GIVEN TWENTY_TWENTY_TWO_NOVEMBER_REVISED WHEN 60000 wage THEN calculates response`() {
        val result = Calculator(
            taxCode = "1257L",
            wages = 60000.0,
            payPeriod = PayPeriod.YEARLY,
            taxYear = TaxYear.TWENTY_TWENTY_TWO_NOVEMBER_REVISED
        ).run()
        Logger.i(result.prettyPrintDataClass())

        assertEquals(Country.ENGLAND, result.country)
        assertFalse(result.isKCode)

        val weekly = result.weekly
        assertEquals(PayPeriod.WEEKLY, weekly.payPeriod)
        assertEquals(90.74, weekly.employeesNI)
        assertEquals(135.08, weekly.employersNI)
        assertEquals(1153.85, weekly.wages)
        assertEquals(241.73, weekly.taxFree)
        assertEquals(219.78, weekly.taxToPay)
        assertEquals(843.33, weekly.takeHome)
        assertTrue(weekly.taxBreakdown!!.isNotEmpty())
        assertEquals(0.0, weekly.pensionContribution)
        assertEquals(0.0, weekly.taperingAmountDeduction)
        assertNull(weekly.studentLoanBreakdown)
        assertEquals(0.0, weekly.finalStudentLoanAmount)

        val fourWeekly = result.fourWeekly
        assertEquals(PayPeriod.FOUR_WEEKLY, fourWeekly.payPeriod)
        assertEquals(362.97, fourWeekly.employeesNI)
        assertEquals(540.32, fourWeekly.employersNI)
        assertEquals(4615.38, fourWeekly.wages)
        assertEquals(966.92, fourWeekly.taxFree)
        assertEquals(879.11, fourWeekly.taxToPay)
        assertEquals(3373.3, fourWeekly.takeHome)
        assertTrue(fourWeekly.taxBreakdown!!.isNotEmpty())
        assertEquals(0.0, fourWeekly.pensionContribution)
        assertEquals(0.0, fourWeekly.taperingAmountDeduction)
        assertNull(fourWeekly.studentLoanBreakdown)
        assertEquals(0.0, fourWeekly.finalStudentLoanAmount)

        val monthly = result.monthly
        assertEquals(PayPeriod.MONTHLY, monthly.payPeriod)
        assertEquals(393.22, monthly.employeesNI)
        assertEquals(585.35, monthly.employersNI)
        assertEquals(5000.00, monthly.wages)
        assertEquals(1047.5, monthly.taxFree)
        assertEquals(952.37, monthly.taxToPay)
        assertEquals(3654.41, monthly.takeHome)
        assertTrue(monthly.taxBreakdown!!.isNotEmpty())
        assertEquals(0.0, monthly.pensionContribution)
        assertEquals(0.0, monthly.taperingAmountDeduction)
        assertNull(monthly.studentLoanBreakdown)
        assertEquals(0.0, monthly.finalStudentLoanAmount)

        val yearly = result.yearly
        assertEquals(PayPeriod.YEARLY, yearly.payPeriod)
        assertEquals(4718.6, yearly.employeesNI)
        assertEquals(7024.2, yearly.employersNI)
        assertEquals(60000.00, yearly.wages)
        assertEquals(12570.00, yearly.taxFree)
        assertEquals(11428.40, yearly.taxToPay)
        assertEquals(43853.0, yearly.takeHome)
        assertTrue(yearly.taxBreakdown!!.isNotEmpty())
        assertEquals(0.0, yearly.pensionContribution)
        assertEquals(0.0, yearly.taperingAmountDeduction)
        assertNull(yearly.studentLoanBreakdown)
        assertEquals(0.0, yearly.finalStudentLoanAmount)
    }

    @Test
    fun `GIVEN TWENTY_TWENTY_THREE WHEN 48000 wage AND 10 percent pension contribution THEN calculates response`() {
        val result = Calculator(
            taxCode = "1257L",
            wages = 48000.0,
            payPeriod = PayPeriod.YEARLY,
            taxYear = TaxYear.TWENTY_TWENTY_THREE,
            pensionMethod = AnnualPensionMethod.PERCENTAGE,
            pensionPercentage = 10.0
        ).run()
        Logger.i(result.prettyPrintDataClass())

        assertEquals(Country.ENGLAND, result.country)
        assertFalse(result.isKCode)

        val weekly = result.weekly
        assertEquals(PayPeriod.WEEKLY, weekly.payPeriod)
        assertEquals(70.68, weekly.employeesNI)
        assertEquals(90.50, weekly.employersNI)
        assertEquals(923.08, weekly.wages)
        assertEquals(241.73, weekly.taxFree)
        assertEquals(117.77, weekly.taxToPay)
        assertEquals(642.32, weekly.takeHome)
        assertEquals(92.31, weekly.pensionContribution)
        assertEquals(830.77, weekly.wageAfterPensionDeduction)
        assertEquals(0.0, weekly.taperingAmountDeduction)
        assertNull(weekly.studentLoanBreakdown)
        assertEquals(0.0, weekly.finalStudentLoanAmount)

        val fourWeekly = result.fourWeekly
        assertEquals(PayPeriod.FOUR_WEEKLY, fourWeekly.payPeriod)
        assertEquals(282.74, fourWeekly.employeesNI)
        assertEquals(361.98, fourWeekly.employersNI)
        assertEquals(3692.31, fourWeekly.wages)
        assertEquals(966.92, fourWeekly.taxFree)
        assertEquals(471.09, fourWeekly.taxToPay)
        assertEquals(2569.25, fourWeekly.takeHome)
        assertEquals(369.23, fourWeekly.pensionContribution)
        assertEquals(3323.08, fourWeekly.wageAfterPensionDeduction)
        assertEquals(0.0, fourWeekly.taperingAmountDeduction)
        assertNull(fourWeekly.studentLoanBreakdown)
        assertEquals(0.0, fourWeekly.finalStudentLoanAmount)

        val monthly = result.monthly
        assertEquals(PayPeriod.MONTHLY, monthly.payPeriod)
        assertEquals(306.3, monthly.employeesNI)
        assertEquals(392.15, monthly.employersNI)
        assertEquals(4000.0, monthly.wages)
        assertEquals(1047.5, monthly.taxFree)
        assertEquals(510.35, monthly.taxToPay)
        assertEquals(2783.35, monthly.takeHome)
        assertEquals(400.0, monthly.pensionContribution)
        assertEquals(3600.0, monthly.wageAfterPensionDeduction)
        assertEquals(0.0, monthly.taperingAmountDeduction)
        assertNull(monthly.studentLoanBreakdown)
        assertEquals(0.0, monthly.finalStudentLoanAmount)

        val yearly = result.yearly
        assertEquals(PayPeriod.YEARLY, yearly.payPeriod)
        assertEquals(3675.6, yearly.employeesNI)
        assertEquals(4705.8, yearly.employersNI)
        assertEquals(48000.00, yearly.wages)
        assertEquals(12570.0, yearly.taxFree)
        assertEquals(6124.20, yearly.taxToPay)
        assertEquals(33400.2, yearly.takeHome)
        assertEquals(4800.0, yearly.pensionContribution)
        assertEquals(43200.0, yearly.wageAfterPensionDeduction)
        assertEquals(0.0, yearly.taperingAmountDeduction)
        assertNull(yearly.studentLoanBreakdown)
        assertEquals(0.0, yearly.finalStudentLoanAmount)
    }

    @Test
    fun `GIVEN TWENTY_TWENTY_THREE WHEN 48000 AND 4800 yearly pension contribution THEN calculates response`() {
        val result = Calculator(
            taxCode = "1257L",
            wages = 48000.0,
            payPeriod = PayPeriod.YEARLY,
            taxYear = TaxYear.TWENTY_TWENTY_THREE,
            pensionMethod = AnnualPensionMethod.AMOUNT_IN_POUNDS,
            pensionYearlyAmount = 4800.0
        ).run()
        Logger.i(result.prettyPrintDataClass())

        assertEquals(Country.ENGLAND, result.country)
        assertFalse(result.isKCode)

        val weekly = result.weekly
        assertEquals(PayPeriod.WEEKLY, weekly.payPeriod)
        assertEquals(70.68, weekly.employeesNI)
        assertEquals(90.50, weekly.employersNI)
        assertEquals(923.08, weekly.wages)
        assertEquals(241.73, weekly.taxFree)
        assertEquals(117.77, weekly.taxToPay)
        assertEquals(642.32, weekly.takeHome)
        assertEquals(92.31, weekly.pensionContribution)
        assertEquals(830.77, weekly.wageAfterPensionDeduction)
        assertEquals(0.0, weekly.taperingAmountDeduction)
        assertNull(weekly.studentLoanBreakdown)
        assertEquals(0.0, weekly.finalStudentLoanAmount)

        val fourWeekly = result.fourWeekly
        assertEquals(PayPeriod.FOUR_WEEKLY, fourWeekly.payPeriod)
        assertEquals(282.74, fourWeekly.employeesNI)
        assertEquals(361.98, fourWeekly.employersNI)
        assertEquals(3692.31, fourWeekly.wages)
        assertEquals(966.92, fourWeekly.taxFree)
        assertEquals(471.09, fourWeekly.taxToPay)
        assertEquals(2569.25, fourWeekly.takeHome)
        assertEquals(369.23, fourWeekly.pensionContribution)
        assertEquals(3323.08, fourWeekly.wageAfterPensionDeduction)
        assertEquals(0.0, fourWeekly.taperingAmountDeduction)
        assertNull(fourWeekly.studentLoanBreakdown)
        assertEquals(0.0, fourWeekly.finalStudentLoanAmount)

        val monthly = result.monthly
        assertEquals(PayPeriod.MONTHLY, monthly.payPeriod)
        assertEquals(306.3, monthly.employeesNI)
        assertEquals(392.15, monthly.employersNI)
        assertEquals(4000.0, monthly.wages)
        assertEquals(1047.5, monthly.taxFree)
        assertEquals(510.35, monthly.taxToPay)
        assertEquals(2783.35, monthly.takeHome)
        assertEquals(400.0, monthly.pensionContribution)
        assertEquals(3600.0, monthly.wageAfterPensionDeduction)
        assertEquals(0.0, monthly.taperingAmountDeduction)
        assertNull(monthly.studentLoanBreakdown)
        assertEquals(0.0, monthly.finalStudentLoanAmount)

        val yearly = result.yearly
        assertEquals(PayPeriod.YEARLY, yearly.payPeriod)
        assertEquals(3675.6, yearly.employeesNI)
        assertEquals(4705.8, yearly.employersNI)
        assertEquals(48000.00, yearly.wages)
        assertEquals(12570.0, yearly.taxFree)
        assertEquals(6124.20, yearly.taxToPay)
        assertEquals(33400.2, yearly.takeHome)
        assertEquals(4800.0, yearly.pensionContribution)
        assertEquals(43200.0, yearly.wageAfterPensionDeduction)
        assertEquals(0.0, yearly.taperingAmountDeduction)
        assertNull(yearly.studentLoanBreakdown)
        assertEquals(0.0, yearly.finalStudentLoanAmount)
    }

    @Test
    fun `GIVEN TWENTY_TWENTY_THREE WHEN 100000 wage AND 60000 yearly pension contribution THEN calculates response`() {
        val result = Calculator(
            taxCode = "1257L",
            wages = 100000.0,
            payPeriod = PayPeriod.YEARLY,
            taxYear = TaxYear.TWENTY_TWENTY_THREE,
            pensionMethod = AnnualPensionMethod.AMOUNT_IN_POUNDS,
            pensionYearlyAmount = 60000.0
        ).run()
        Logger.i(result.prettyPrintDataClass())

        assertEquals(Country.ENGLAND, result.country)
        assertFalse(result.isKCode)

        val weekly = result.weekly
        assertEquals(PayPeriod.WEEKLY, weekly.payPeriod)
        assertEquals(63.3, weekly.employeesNI)
        assertEquals(82.0, weekly.employersNI)
        assertEquals(1923.08, weekly.wages)
        assertEquals(241.73, weekly.taxFree)
        assertEquals(105.47, weekly.taxToPay)
        assertEquals(600.46, weekly.takeHome)
        assertEquals(1153.85, weekly.pensionContribution)
        assertEquals(769.23, weekly.wageAfterPensionDeduction)
        assertEquals(0.0, weekly.taperingAmountDeduction)
        assertNull(weekly.studentLoanBreakdown)
        assertEquals(0.0, weekly.finalStudentLoanAmount)

        val fourWeekly = result.fourWeekly
        assertEquals(PayPeriod.FOUR_WEEKLY, fourWeekly.payPeriod)
        assertEquals(253.2, fourWeekly.employeesNI)
        assertEquals(328.02, fourWeekly.employersNI)
        assertEquals(7692.31, fourWeekly.wages)
        assertEquals(966.92, fourWeekly.taxFree)
        assertEquals(421.86, fourWeekly.taxToPay)
        assertEquals(2401.87, fourWeekly.takeHome)
        assertEquals(4615.38, fourWeekly.pensionContribution)
        assertEquals(3076.92, fourWeekly.wageAfterPensionDeduction)
        assertEquals(0.0, fourWeekly.taperingAmountDeduction)
        assertNull(fourWeekly.studentLoanBreakdown)
        assertEquals(0.0, fourWeekly.finalStudentLoanAmount)

        val monthly = result.monthly
        assertEquals(PayPeriod.MONTHLY, monthly.payPeriod)
        assertEquals(274.3, monthly.employeesNI)
        assertEquals(355.35, monthly.employersNI)
        assertEquals(8333.33, monthly.wages)
        assertEquals(1047.50, monthly.taxFree)
        assertEquals(457.02, monthly.taxToPay)
        assertEquals(2602.01, monthly.takeHome)
        assertEquals(5000.00, monthly.pensionContribution)
        assertEquals(3333.33, monthly.wageAfterPensionDeduction)
        assertEquals(0.0, monthly.taperingAmountDeduction)
        assertNull(monthly.studentLoanBreakdown)
        assertEquals(0.0, monthly.finalStudentLoanAmount)

        val yearly = result.yearly
        assertEquals(PayPeriod.YEARLY, yearly.payPeriod)
        assertEquals(3291.6, yearly.employeesNI)
        assertEquals(4264.2, yearly.employersNI)
        assertEquals(100000.00, yearly.wages)
        assertEquals(12570.0, yearly.taxFree)
        assertEquals(5484.20, yearly.taxToPay)
        assertEquals(31224.2, yearly.takeHome)
        assertEquals(60000.0, yearly.pensionContribution)
        assertEquals(40000.0, yearly.wageAfterPensionDeduction)
        assertEquals(0.0, yearly.taperingAmountDeduction)
        assertNull(yearly.studentLoanBreakdown)
        assertEquals(0.0, yearly.finalStudentLoanAmount)
    }

    @Test
    fun `GIVEN TWENTY_TWENTY_THREE WHEN 100000 wage AND 60 percent pension contribution THEN calculates response`() {
        val result = Calculator(
            taxCode = "1257L",
            wages = 100000.0,
            payPeriod = PayPeriod.YEARLY,
            taxYear = TaxYear.TWENTY_TWENTY_THREE,
            pensionMethod = AnnualPensionMethod.PERCENTAGE,
            pensionPercentage = 60.0
        ).run()
        Logger.i(result.prettyPrintDataClass())

        assertEquals(Country.ENGLAND, result.country)
        assertFalse(result.isKCode)

        val weekly = result.weekly
        assertEquals(PayPeriod.WEEKLY, weekly.payPeriod)
        assertEquals(63.3, weekly.employeesNI)
        assertEquals(82.0, weekly.employersNI)
        assertEquals(1923.08, weekly.wages)
        assertEquals(241.73, weekly.taxFree)
        assertEquals(105.47, weekly.taxToPay)
        assertEquals(600.46, weekly.takeHome)
        assertEquals(1153.85, weekly.pensionContribution)
        assertEquals(769.23, weekly.wageAfterPensionDeduction)
        assertEquals(0.0, weekly.taperingAmountDeduction)
        assertNull(weekly.studentLoanBreakdown)
        assertEquals(0.0, weekly.finalStudentLoanAmount)

        val fourWeekly = result.fourWeekly
        assertEquals(PayPeriod.FOUR_WEEKLY, fourWeekly.payPeriod)
        assertEquals(253.2, fourWeekly.employeesNI)
        assertEquals(328.02, fourWeekly.employersNI)
        assertEquals(7692.31, fourWeekly.wages)
        assertEquals(966.92, fourWeekly.taxFree)
        assertEquals(421.86, fourWeekly.taxToPay)
        assertEquals(2401.87, fourWeekly.takeHome)
        assertEquals(4615.38, fourWeekly.pensionContribution)
        assertEquals(3076.92, fourWeekly.wageAfterPensionDeduction)
        assertEquals(0.0, fourWeekly.taperingAmountDeduction)
        assertNull(fourWeekly.studentLoanBreakdown)
        assertEquals(0.0, fourWeekly.finalStudentLoanAmount)

        val monthly = result.monthly
        assertEquals(PayPeriod.MONTHLY, monthly.payPeriod)
        assertEquals(274.3, monthly.employeesNI)
        assertEquals(355.35, monthly.employersNI)
        assertEquals(8333.33, monthly.wages)
        assertEquals(1047.50, monthly.taxFree)
        assertEquals(457.02, monthly.taxToPay)
        assertEquals(2602.01, monthly.takeHome)
        assertEquals(5000.00, monthly.pensionContribution)
        assertEquals(3333.33, monthly.wageAfterPensionDeduction)
        assertEquals(0.0, monthly.taperingAmountDeduction)
        assertNull(monthly.studentLoanBreakdown)
        assertEquals(0.0, monthly.finalStudentLoanAmount)

        val yearly = result.yearly
        assertEquals(PayPeriod.YEARLY, yearly.payPeriod)
        assertEquals(3291.6, yearly.employeesNI)
        assertEquals(4264.2, yearly.employersNI)
        assertEquals(100000.00, yearly.wages)
        assertEquals(12570.0, yearly.taxFree)
        assertEquals(5484.20, yearly.taxToPay)
        assertEquals(31224.2, yearly.takeHome)
        assertEquals(60000.0, yearly.pensionContribution)
        assertEquals(40000.0, yearly.wageAfterPensionDeduction)
        assertEquals(0.0, yearly.taperingAmountDeduction)
        assertNull(yearly.studentLoanBreakdown)
        assertEquals(0.0, yearly.finalStudentLoanAmount)
    }

    @Test
    fun `GIVEN TWENTY_TWENTY_THREE WHEN 100000 wage AND 65000 yearly pension contribution THEN calculates response`() {
        val result = Calculator(
            taxCode = "1257L",
            wages = 100000.0,
            payPeriod = PayPeriod.YEARLY,
            taxYear = TaxYear.TWENTY_TWENTY_THREE,
            pensionMethod = AnnualPensionMethod.AMOUNT_IN_POUNDS,
            pensionYearlyAmount = 65000.0
        ).run()
        Logger.i(result.prettyPrintDataClass())

        assertEquals(Country.ENGLAND, result.country)
        assertFalse(result.isKCode)

        val weekly = result.weekly
        assertEquals(PayPeriod.WEEKLY, weekly.payPeriod)
        assertEquals(63.3, weekly.employeesNI)
        assertEquals(82.00, weekly.employersNI)
        assertEquals(1923.08, weekly.wages)
        assertEquals(241.73, weekly.taxFree)
        assertEquals(105.47, weekly.taxToPay)
        assertEquals(504.31, weekly.takeHome)
        assertEquals(1250.00, weekly.pensionContribution)
        assertEquals(769.23, weekly.wageAfterPensionDeduction)
        assertEquals(0.0, weekly.taperingAmountDeduction)
        assertNull(weekly.studentLoanBreakdown)
        assertEquals(0.0, weekly.finalStudentLoanAmount)

        val fourWeekly = result.fourWeekly
        assertEquals(PayPeriod.FOUR_WEEKLY, fourWeekly.payPeriod)
        assertEquals(253.2, fourWeekly.employeesNI)
        assertEquals(328.02, fourWeekly.employersNI)
        assertEquals(7692.31, fourWeekly.wages)
        assertEquals(966.92, fourWeekly.taxFree)
        assertEquals(421.86, fourWeekly.taxToPay)
        assertEquals(2017.25, fourWeekly.takeHome)
        assertEquals(5000.00, fourWeekly.pensionContribution)
        assertEquals(3076.92, fourWeekly.wageAfterPensionDeduction)
        assertEquals(0.0, fourWeekly.taperingAmountDeduction)
        assertNull(fourWeekly.studentLoanBreakdown)
        assertEquals(0.0, fourWeekly.finalStudentLoanAmount)

        val monthly = result.monthly
        assertEquals(PayPeriod.MONTHLY, monthly.payPeriod)
        assertEquals(274.3, monthly.employeesNI)
        assertEquals(355.35, monthly.employersNI)
        assertEquals(8333.33, monthly.wages)
        assertEquals(1047.50, monthly.taxFree)
        assertEquals(457.02, monthly.taxToPay)
        assertEquals(2185.34, monthly.takeHome)
        assertEquals(5416.67, monthly.pensionContribution)
        assertEquals(3333.33, monthly.wageAfterPensionDeduction)
        assertEquals(0.0, monthly.taperingAmountDeduction)
        assertNull(monthly.studentLoanBreakdown)
        assertEquals(0.0, monthly.finalStudentLoanAmount)

        val yearly = result.yearly
        assertEquals(PayPeriod.YEARLY, yearly.payPeriod)
        assertEquals(3291.6, yearly.employeesNI)
        assertEquals(4264.2, yearly.employersNI)
        assertEquals(100000.00, yearly.wages)
        assertEquals(12570.0, yearly.taxFree)
        assertEquals(5484.20, yearly.taxToPay)
        assertEquals(26224.2, yearly.takeHome)
        assertEquals(65000.0, yearly.pensionContribution)
        assertEquals(40000.0, yearly.wageAfterPensionDeduction)
        assertEquals(0.0, yearly.taperingAmountDeduction)
        assertNull(yearly.studentLoanBreakdown)
        assertEquals(0.0, yearly.finalStudentLoanAmount)
    }

    @Test
    fun `GIVEN TWENTY_TWENTY_THREE WHEN 100000 wage AND 65 percent pension contribution THEN calculates response`() {
        val result = Calculator(
            taxCode = "1257L",
            wages = 100000.0,
            payPeriod = PayPeriod.YEARLY,
            taxYear = TaxYear.TWENTY_TWENTY_THREE,
            pensionMethod = AnnualPensionMethod.PERCENTAGE,
            pensionPercentage = 65.0
        ).run()
        Logger.i(result.prettyPrintDataClass())

        assertEquals(Country.ENGLAND, result.country)
        assertFalse(result.isKCode)

        val weekly = result.weekly
        assertEquals(PayPeriod.WEEKLY, weekly.payPeriod)
        assertEquals(63.3, weekly.employeesNI)
        assertEquals(82.00, weekly.employersNI)
        assertEquals(1923.08, weekly.wages)
        assertEquals(241.73, weekly.taxFree)
        assertEquals(105.47, weekly.taxToPay)
        assertEquals(504.31, weekly.takeHome)
        assertEquals(1250.00, weekly.pensionContribution)
        assertEquals(769.23, weekly.wageAfterPensionDeduction)
        assertEquals(0.0, weekly.taperingAmountDeduction)
        assertNull(weekly.studentLoanBreakdown)
        assertEquals(0.0, weekly.finalStudentLoanAmount)

        val fourWeekly = result.fourWeekly
        assertEquals(PayPeriod.FOUR_WEEKLY, fourWeekly.payPeriod)
        assertEquals(253.2, fourWeekly.employeesNI)
        assertEquals(328.02, fourWeekly.employersNI)
        assertEquals(7692.31, fourWeekly.wages)
        assertEquals(966.92, fourWeekly.taxFree)
        assertEquals(421.86, fourWeekly.taxToPay)
        assertEquals(2017.25, fourWeekly.takeHome)
        assertEquals(5000.00, fourWeekly.pensionContribution)
        assertEquals(3076.92, fourWeekly.wageAfterPensionDeduction)
        assertEquals(0.0, fourWeekly.taperingAmountDeduction)
        assertNull(fourWeekly.studentLoanBreakdown)
        assertEquals(0.0, fourWeekly.finalStudentLoanAmount)

        val monthly = result.monthly
        assertEquals(PayPeriod.MONTHLY, monthly.payPeriod)
        assertEquals(274.3, monthly.employeesNI)
        assertEquals(355.35, monthly.employersNI)
        assertEquals(8333.33, monthly.wages)
        assertEquals(1047.50, monthly.taxFree)
        assertEquals(457.02, monthly.taxToPay)
        assertEquals(2185.34, monthly.takeHome)
        assertEquals(5416.67, monthly.pensionContribution)
        assertEquals(3333.33, monthly.wageAfterPensionDeduction)
        assertEquals(0.0, monthly.taperingAmountDeduction)
        assertNull(monthly.studentLoanBreakdown)
        assertEquals(0.0, monthly.finalStudentLoanAmount)

        val yearly = result.yearly
        assertEquals(PayPeriod.YEARLY, yearly.payPeriod)
        assertEquals(3291.6, yearly.employeesNI)
        assertEquals(4264.2, yearly.employersNI)
        assertEquals(100000.00, yearly.wages)
        assertEquals(12570.0, yearly.taxFree)
        assertEquals(5484.20, yearly.taxToPay)
        assertEquals(26224.2, yearly.takeHome)
        assertEquals(65000.0, yearly.pensionContribution)
        assertEquals(40000.0, yearly.wageAfterPensionDeduction)
        assertEquals(0.0, yearly.taperingAmountDeduction)
        assertNull(yearly.studentLoanBreakdown)
        assertEquals(0.0, yearly.finalStudentLoanAmount)
    }

    @Test
    fun `GIVEN TWENTY_TWENTY_THREE WHEN 24579 wage AND 12000 yearly pension contribution THEN calculates response`() {
        val result = Calculator(
            taxCode = "1257L",
            wages = 24579.0,
            payPeriod = PayPeriod.YEARLY,
            taxYear = TaxYear.TWENTY_TWENTY_THREE,
            pensionMethod = AnnualPensionMethod.AMOUNT_IN_POUNDS,
            pensionYearlyAmount = 12000.0
        ).run()
        Logger.i(result.prettyPrintDataClass())

        assertEquals(Country.ENGLAND, result.country)
        assertFalse(result.isKCode)

        val weekly = result.weekly
        assertEquals(PayPeriod.WEEKLY, weekly.payPeriod)
        assertEquals(0.02, weekly.employeesNI)
        assertEquals(9.23, weekly.employersNI)
        assertEquals(472.67, weekly.wages)
        assertEquals(241.73, weekly.taxFree)
        assertEquals(0.0, weekly.taxToPay)
        assertEquals(241.88, weekly.takeHome)
        assertEquals(230.77, weekly.pensionContribution)
        assertEquals(241.90, weekly.wageAfterPensionDeduction)
        assertEquals(0.0, weekly.taperingAmountDeduction)
        assertNull(weekly.studentLoanBreakdown)
        assertEquals(0.0, weekly.finalStudentLoanAmount)

        val fourWeekly = result.fourWeekly
        assertEquals(PayPeriod.FOUR_WEEKLY, fourWeekly.payPeriod)
        assertEquals(0.08, fourWeekly.employeesNI)
        assertEquals(36.93, fourWeekly.employersNI)
        assertEquals(1890.69, fourWeekly.wages)
        assertEquals(966.92, fourWeekly.taxFree)
        assertEquals(0.0, fourWeekly.taxToPay)
        assertEquals(967.53, fourWeekly.takeHome)
        assertEquals(923.08, fourWeekly.pensionContribution)
        assertEquals(967.62, fourWeekly.wageAfterPensionDeduction)
        assertEquals(0.0, fourWeekly.taperingAmountDeduction)
        assertNull(fourWeekly.studentLoanBreakdown)
        assertEquals(0.0, fourWeekly.finalStudentLoanAmount)

        val monthly = result.monthly
        assertEquals(PayPeriod.MONTHLY, monthly.payPeriod)
        assertEquals(0.09, monthly.employeesNI)
        assertEquals(40.01, monthly.employersNI)
        assertEquals(2048.25, monthly.wages)
        assertEquals(1047.50, monthly.taxFree)
        assertEquals(0.0, monthly.taxToPay)
        assertEquals(1048.16, monthly.takeHome)
        assertEquals(1000.0, monthly.pensionContribution)
        assertEquals(1048.25, monthly.wageAfterPensionDeduction)
        assertEquals(0.0, monthly.taperingAmountDeduction)
        assertNull(monthly.studentLoanBreakdown)
        assertEquals(0.0, monthly.finalStudentLoanAmount)

        val yearly = result.yearly
        assertEquals(PayPeriod.YEARLY, yearly.payPeriod)
        assertEquals(1.08, yearly.employeesNI)
        assertEquals(480.10, yearly.employersNI)
        assertEquals(24579.0, yearly.wages)
        assertEquals(12570.0, yearly.taxFree)
        assertEquals(0.0, yearly.taxToPay)
        assertEquals(12577.92, yearly.takeHome)
        assertEquals(12000.0, yearly.pensionContribution)
        assertEquals(12579.0, yearly.wageAfterPensionDeduction)
        assertEquals(0.0, yearly.taperingAmountDeduction)
        assertNull(yearly.studentLoanBreakdown)
        assertEquals(0.0, yearly.finalStudentLoanAmount)
    }

    @Test
    fun `GIVEN TWENTY_TWENTY_THREE WHEN 125140 wage to apply tapering AND userSuppliedTaxCode false THEN calculates response`() {
        val result = Calculator(
            taxCode = "1257L",
            userSuppliedTaxCode = false,
            wages = 125140.0,
            payPeriod = PayPeriod.YEARLY,
            taxYear = TaxYear.TWENTY_TWENTY_THREE,
        ).run()
        Logger.i(result.prettyPrintDataClass())

        assertEquals(Country.ENGLAND, result.country)
        assertFalse(result.isKCode)

        val weekly = result.weekly
        assertEquals(PayPeriod.WEEKLY, weekly.payPeriod)
        assertEquals(115.80, weekly.employeesNI)
        assertEquals(307.95, weekly.employersNI)
        assertEquals(2406.54, weekly.wages)
        assertEquals(0.0, weekly.taxFree)
        assertEquals(817.55, weekly.taxToPay)
        assertEquals(1473.19, weekly.takeHome)
        assertEquals(0.0, weekly.pensionContribution)
        assertEquals(2406.54, weekly.wageAfterPensionDeduction)
        assertEquals(241.73, weekly.taperingAmountDeduction)
        assertNull(weekly.studentLoanBreakdown)
        assertEquals(0.0, weekly.finalStudentLoanAmount)

        val fourWeekly = result.fourWeekly
        assertEquals(PayPeriod.FOUR_WEEKLY, fourWeekly.payPeriod)
        assertEquals(463.18, fourWeekly.employeesNI)
        assertEquals(1231.81, fourWeekly.employersNI)
        assertEquals(9626.15, fourWeekly.wages)
        assertEquals(0.0, fourWeekly.taxFree)
        assertEquals(3270.18, fourWeekly.taxToPay)
        assertEquals(5892.79, fourWeekly.takeHome)
        assertEquals(0.0, fourWeekly.pensionContribution)
        assertEquals(9626.15, fourWeekly.wageAfterPensionDeduction)
        assertEquals(966.92, fourWeekly.taperingAmountDeduction)
        assertNull(fourWeekly.studentLoanBreakdown)
        assertEquals(0.0, fourWeekly.finalStudentLoanAmount)

        val monthly = result.monthly
        assertEquals(PayPeriod.MONTHLY, monthly.payPeriod)
        assertEquals(501.78, monthly.employeesNI)
        assertEquals(1334.46, monthly.employersNI)
        assertEquals(10428.33, monthly.wages)
        assertEquals(0.0, monthly.taxFree)
        assertEquals(3542.70, monthly.taxToPay)
        assertEquals(6383.85, monthly.takeHome)
        assertEquals(0.0, monthly.pensionContribution)
        assertEquals(10428.33, monthly.wageAfterPensionDeduction)
        assertEquals(1047.5, monthly.taperingAmountDeduction)
        assertNull(monthly.studentLoanBreakdown)
        assertEquals(0.0, monthly.finalStudentLoanAmount)

        val yearly = result.yearly
        assertEquals(PayPeriod.YEARLY, yearly.payPeriod)
        assertEquals(6021.4, yearly.employeesNI)
        assertEquals(16013.52, yearly.employersNI)
        assertEquals(125140.0, yearly.wages)
        assertEquals(0.0, yearly.taxFree)
        assertEquals(42512.4, yearly.taxToPay)
        assertEquals(76606.2, yearly.takeHome)
        assertEquals(0.0, yearly.pensionContribution)
        assertEquals(125140.0, yearly.wageAfterPensionDeduction)
        assertEquals(12570.0, yearly.taperingAmountDeduction)
        assertNull(yearly.studentLoanBreakdown)
        assertEquals(0.0, yearly.finalStudentLoanAmount)
    }

    @Test
    fun `GIVEN TWENTY_TWENTY_THREE WHEN 125140 wage to apply tapering AND userSuppliedTaxCode true THEN calculates response without tapering`() {
        val result = Calculator(
            taxCode = "1257L",
            userSuppliedTaxCode = true,
            wages = 125140.0,
            payPeriod = PayPeriod.YEARLY,
            taxYear = TaxYear.TWENTY_TWENTY_THREE,
        ).run()
        Logger.i(result.prettyPrintDataClass())

        assertEquals(Country.ENGLAND, result.country)
        assertFalse(result.isKCode)

        val weekly = result.weekly
        assertEquals(PayPeriod.WEEKLY, weekly.payPeriod)
        assertEquals(115.80, weekly.employeesNI)
        assertEquals(307.95, weekly.employersNI)
        assertEquals(2406.54, weekly.wages)
        assertEquals(241.73, weekly.taxFree)
        assertEquals(720.85, weekly.taxToPay)
        assertEquals(1569.89, weekly.takeHome)
        assertEquals(0.0, weekly.pensionContribution)
        assertEquals(2406.54, weekly.wageAfterPensionDeduction)
        assertEquals(0.0, weekly.taperingAmountDeduction)
        assertNull(weekly.studentLoanBreakdown)
        assertEquals(0.0, weekly.finalStudentLoanAmount)

        val fourWeekly = result.fourWeekly
        assertEquals(PayPeriod.FOUR_WEEKLY, fourWeekly.payPeriod)
        assertEquals(463.18, fourWeekly.employeesNI)
        assertEquals(1231.81, fourWeekly.employersNI)
        assertEquals(9626.15, fourWeekly.wages)
        assertEquals(966.92, fourWeekly.taxFree)
        assertEquals(2883.42, fourWeekly.taxToPay)
        assertEquals(6279.55, fourWeekly.takeHome)
        assertEquals(0.0, fourWeekly.pensionContribution)
        assertEquals(9626.15, fourWeekly.wageAfterPensionDeduction)
        assertEquals(0.0, fourWeekly.taperingAmountDeduction)
        assertNull(fourWeekly.studentLoanBreakdown)
        assertEquals(0.0, fourWeekly.finalStudentLoanAmount)

        val monthly = result.monthly
        assertEquals(PayPeriod.MONTHLY, monthly.payPeriod)
        assertEquals(501.78, monthly.employeesNI)
        assertEquals(1334.46, monthly.employersNI)
        assertEquals(10428.33, monthly.wages)
        assertEquals(1047.5, monthly.taxFree)
        assertEquals(3123.70, monthly.taxToPay)
        assertEquals(6802.85, monthly.takeHome)
        assertEquals(0.0, monthly.pensionContribution)
        assertEquals(10428.33, monthly.wageAfterPensionDeduction)
        assertEquals(0.0, monthly.taperingAmountDeduction)
        assertNull(monthly.studentLoanBreakdown)
        assertEquals(0.0, monthly.finalStudentLoanAmount)

        val yearly = result.yearly
        assertEquals(PayPeriod.YEARLY, yearly.payPeriod)
        assertEquals(6021.4, yearly.employeesNI)
        assertEquals(16013.52, yearly.employersNI)
        assertEquals(125140.0, yearly.wages)
        assertEquals(12570.0, yearly.taxFree)
        assertEquals(37484.4, yearly.taxToPay)
        assertEquals(81634.2, yearly.takeHome)
        assertEquals(0.0, yearly.pensionContribution)
        assertEquals(125140.0, yearly.wageAfterPensionDeduction)
        assertEquals(0.0, yearly.taperingAmountDeduction)
        assertNull(yearly.studentLoanBreakdown)
        assertEquals(0.0, yearly.finalStudentLoanAmount)
    }

    @Test
    fun `GIVEN TWENTY_TWENTY_THREE AND student loan plan 2 AND post graduate plan WHEN 28800 wage THEN calculates response`() {
        val result = Calculator(
            taxCode = "1257L",
            wages = 28800.0,
            payPeriod = PayPeriod.YEARLY,
            taxYear = TaxYear.TWENTY_TWENTY_THREE,
            hasStudentLoanPlanTwo = true,
            hasStudentLoanPostgraduatePlan = true,
        ).run()
        Logger.i(result.prettyPrintDataClass())

        assertEquals(Country.ENGLAND, result.country)
        assertFalse(result.isKCode)

        val weekly = result.weekly
        assertEquals(PayPeriod.WEEKLY, weekly.payPeriod)
        assertEquals(37.45, weekly.employeesNI)
        assertEquals(52.28, weekly.employersNI)
        assertEquals(553.85, weekly.wages)
        assertEquals(241.73, weekly.taxFree)
        assertEquals(62.39, weekly.taxToPay)
        assertEquals(443.01, weekly.takeHome)
        assertEquals(0.0, weekly.pensionContribution)
        assertEquals(553.85, weekly.wageAfterPensionDeduction)
        assertEquals(0.0, weekly.taperingAmountDeduction)
        assertEquals(2.0, weekly.studentLoanBreakdown!![1].amount) // plan 2
        assertEquals(9.0, weekly.studentLoanBreakdown!![3].amount) // postgraduate plan
        assertEquals(11.0, weekly.finalStudentLoanAmount)

        val fourWeekly = result.fourWeekly
        assertEquals(PayPeriod.FOUR_WEEKLY, fourWeekly.payPeriod)
        assertEquals(149.82, fourWeekly.employeesNI)
        assertEquals(209.12, fourWeekly.employersNI)
        assertEquals(2215.38, fourWeekly.wages)
        assertEquals(966.92, fourWeekly.taxFree)
        assertEquals(249.55, fourWeekly.taxToPay)
        assertEquals(1770.01, fourWeekly.takeHome)
        assertEquals(0.0, fourWeekly.pensionContribution)
        assertEquals(2215.38, fourWeekly.wageAfterPensionDeduction)
        assertEquals(0.0, fourWeekly.taperingAmountDeduction)
        assertEquals(10.0, fourWeekly.studentLoanBreakdown!![1].amount) // plan 2
        assertEquals(36.0, fourWeekly.studentLoanBreakdown!![3].amount) // postgraduate plan
        assertEquals(46.0, fourWeekly.finalStudentLoanAmount)

        val monthly = result.monthly
        assertEquals(PayPeriod.MONTHLY, monthly.payPeriod)
        assertEquals(162.30, monthly.employeesNI)
        assertEquals(226.55, monthly.employersNI)
        assertEquals(2400.0, monthly.wages)
        assertEquals(1047.5, monthly.taxFree)
        assertEquals(270.35, monthly.taxToPay)
        assertEquals(1917.35, monthly.takeHome)
        assertEquals(0.0, monthly.pensionContribution)
        assertEquals(2400.0, monthly.wageAfterPensionDeduction)
        assertEquals(0.0, monthly.taperingAmountDeduction)
        assertEquals(11.0, monthly.studentLoanBreakdown!![1].amount) // plan 2
        assertEquals(39.0, monthly.studentLoanBreakdown!![3].amount) // postgraduate plan
        assertEquals(50.0, monthly.finalStudentLoanAmount)

        val yearly = result.yearly
        assertEquals(PayPeriod.YEARLY, yearly.payPeriod)
        assertEquals(1947.6, yearly.employeesNI)
        assertEquals(2718.60, yearly.employersNI)
        assertEquals(28800.0, yearly.wages)
        assertEquals(12570.0, yearly.taxFree)
        assertEquals(3244.20, yearly.taxToPay)
        assertEquals(23005.2, yearly.takeHome)
        assertEquals(0.0, yearly.pensionContribution)
        assertEquals(28800.0, yearly.wageAfterPensionDeduction)
        assertEquals(0.0, yearly.taperingAmountDeduction)
        assertEquals(135.0, yearly.studentLoanBreakdown!![1].amount) // plan 2
        assertEquals(468.0, yearly.studentLoanBreakdown!![3].amount) // postgraduate plan
        assertEquals(603.0, yearly.finalStudentLoanAmount)
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
                taxYear = TaxYear.fromInt(2040)
            ).run()
        }
    }

    @Test
    fun `GIVEN pension is greater then wage WHEN calculate THEN exception`() {
        assertFailsWith<InvalidPensionException> {
            Calculator(
                taxCode = "1257L",
                wages = 40000.0,
                payPeriod = PayPeriod.YEARLY,
                taxYear = TaxYear.fromInt(2023),
                pensionMethod = AnnualPensionMethod.AMOUNT_IN_POUNDS,
                pensionYearlyAmount = 45000.0
            ).run()
        }
    }

    @Test
    fun `GIVEN pension is greater then 100 percent WHEN calculate THEN exception`() {
        assertFailsWith<InvalidPensionException> {
            Calculator(
                taxCode = "1257L",
                wages = 40000.0,
                payPeriod = PayPeriod.YEARLY,
                taxYear = TaxYear.fromInt(2023),
                pensionMethod = AnnualPensionMethod.PERCENTAGE,
                pensionPercentage = 120.0
            ).run()
        }
    }

    @Test
    fun `GIVEN pension is greater then standardLifetimeAllowance WHEN calculate THEN exception`() {
        assertFailsWith<InvalidPensionException> {
            Calculator(
                taxCode = "1257L",
                wages = 1073500.0,
                payPeriod = PayPeriod.YEARLY,
                taxYear = TaxYear.fromInt(2023),
                pensionMethod = AnnualPensionMethod.AMOUNT_IN_POUNDS,
                pensionYearlyAmount = 1073500.0
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
