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
import uk.gov.hmrc.calculator.model.pension.PensionMethod
import uk.gov.hmrc.calculator.model.taxcodes.TaxCode
import uk.gov.hmrc.calculator.utils.clarification.Clarification
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
        assertEquals(0.0, weekly.finalPostgraduateLoanAmount)
        assertEquals(0.0, weekly.otherAmount)

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
        assertEquals(0.0, fourWeekly.finalPostgraduateLoanAmount)
        assertEquals(0.0, fourWeekly.otherAmount)

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
        assertEquals(0.0, monthly.finalPostgraduateLoanAmount)
        assertEquals(0.0, monthly.otherAmount)

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
        assertEquals(0.0, yearly.finalPostgraduateLoanAmount)
        assertEquals(0.0, yearly.otherAmount)
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
        assertEquals(0.0, weekly.finalPostgraduateLoanAmount)
        assertEquals(0.0, weekly.otherAmount)

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
        assertEquals(0.0, fourWeekly.finalPostgraduateLoanAmount)
        assertEquals(0.0, fourWeekly.otherAmount)

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
        assertEquals(0.0, monthly.finalPostgraduateLoanAmount)
        assertEquals(0.0, monthly.otherAmount)

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
        assertEquals(0.0, yearly.finalPostgraduateLoanAmount)
        assertEquals(0.0, yearly.otherAmount)
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
        assertEquals(0.0, weekly.finalPostgraduateLoanAmount)
        assertEquals(0.0, weekly.otherAmount)

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
        assertEquals(0.0, fourWeekly.finalPostgraduateLoanAmount)
        assertEquals(0.0, fourWeekly.otherAmount)

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
        assertEquals(0.0, monthly.finalPostgraduateLoanAmount)
        assertEquals(0.0, monthly.otherAmount)

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
        assertEquals(0.0, yearly.finalPostgraduateLoanAmount)
        assertEquals(0.0, yearly.otherAmount)
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
        assertEquals(0.0, weekly.finalPostgraduateLoanAmount)
        assertEquals(0.0, weekly.otherAmount)

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
        assertEquals(0.0, fourWeekly.finalPostgraduateLoanAmount)
        assertEquals(0.0, fourWeekly.otherAmount)

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
        assertEquals(0.0, monthly.finalPostgraduateLoanAmount)
        assertEquals(0.0, monthly.otherAmount)

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
        assertEquals(0.0, yearly.finalPostgraduateLoanAmount)
        assertEquals(0.0, yearly.otherAmount)
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
        assertEquals(0.0, weekly.finalPostgraduateLoanAmount)
        assertEquals(0.0, weekly.otherAmount)

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
        assertEquals(0.0, fourWeekly.finalPostgraduateLoanAmount)
        assertEquals(0.0, fourWeekly.otherAmount)

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
        assertEquals(0.0, monthly.finalPostgraduateLoanAmount)
        assertEquals(0.0, monthly.otherAmount)

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
        assertEquals(0.0, yearly.finalPostgraduateLoanAmount)
        assertEquals(0.0, yearly.otherAmount)
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
        assertEquals(0.0, weekly.finalPostgraduateLoanAmount)
        assertEquals(0.0, weekly.otherAmount)

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
        assertEquals(0.0, fourWeekly.finalPostgraduateLoanAmount)
        assertEquals(0.0, fourWeekly.otherAmount)

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
        assertEquals(0.0, monthly.finalPostgraduateLoanAmount)
        assertEquals(0.0, monthly.otherAmount)

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
        assertEquals(0.0, yearly.finalPostgraduateLoanAmount)
        assertEquals(0.0, yearly.otherAmount)
    }

    @Test
    fun `GIVEN TWENTY_TWENTY_THREE WHEN 48000 wage AND 10 percent pension contribution THEN calculates response`() {
        val result = Calculator(
            taxCode = "1257L",
            wages = 48000.0,
            payPeriod = PayPeriod.YEARLY,
            taxYear = TaxYear.TWENTY_TWENTY_THREE,
            pensionContribution = Calculator.PensionContribution(PensionMethod.PERCENTAGE, 10.0)
        ).run()
        Logger.i(result.prettyPrintDataClass())

        assertEquals(Country.ENGLAND, result.country)
        assertFalse(result.isKCode)

        val weekly = result.weekly
        assertEquals(PayPeriod.WEEKLY, weekly.payPeriod)
        assertEquals(81.76, weekly.employeesNI)
        assertEquals(103.23, weekly.employersNI)
        assertEquals(923.08, weekly.wages)
        assertEquals(241.73, weekly.taxFree)
        assertEquals(117.77, weekly.taxToPay)
        assertEquals(631.24, weekly.takeHome)
        assertEquals(92.31, weekly.pensionContribution)
        assertEquals(830.77, weekly.wageAfterPensionDeduction)
        assertEquals(0.0, weekly.taperingAmountDeduction)
        assertNull(weekly.studentLoanBreakdown)
        assertEquals(0.0, weekly.finalStudentLoanAmount)
        assertEquals(0.0, weekly.finalPostgraduateLoanAmount)
        assertEquals(92.31, weekly.otherAmount)

        val fourWeekly = result.fourWeekly
        assertEquals(PayPeriod.FOUR_WEEKLY, fourWeekly.payPeriod)
        assertEquals(327.05, fourWeekly.employeesNI)
        assertEquals(412.94, fourWeekly.employersNI)
        assertEquals(3692.31, fourWeekly.wages)
        assertEquals(966.92, fourWeekly.taxFree)
        assertEquals(471.09, fourWeekly.taxToPay)
        assertEquals(2524.94, fourWeekly.takeHome)
        assertEquals(369.23, fourWeekly.pensionContribution)
        assertEquals(3323.08, fourWeekly.wageAfterPensionDeduction)
        assertEquals(0.0, fourWeekly.taperingAmountDeduction)
        assertNull(fourWeekly.studentLoanBreakdown)
        assertEquals(0.0, fourWeekly.finalStudentLoanAmount)
        assertEquals(0.0, fourWeekly.finalPostgraduateLoanAmount)
        assertEquals(369.23, fourWeekly.otherAmount)

        val monthly = result.monthly
        assertEquals(PayPeriod.MONTHLY, monthly.payPeriod)
        assertEquals(354.30, monthly.employeesNI)
        assertEquals(447.35, monthly.employersNI)
        assertEquals(4000.0, monthly.wages)
        assertEquals(1047.5, monthly.taxFree)
        assertEquals(510.35, monthly.taxToPay)
        assertEquals(2735.35, monthly.takeHome)
        assertEquals(400.0, monthly.pensionContribution)
        assertEquals(3600.0, monthly.wageAfterPensionDeduction)
        assertEquals(0.0, monthly.taperingAmountDeduction)
        assertNull(monthly.studentLoanBreakdown)
        assertEquals(0.0, monthly.finalStudentLoanAmount)
        assertEquals(0.0, monthly.finalPostgraduateLoanAmount)
        assertEquals(400.0, monthly.otherAmount)

        val yearly = result.yearly
        assertEquals(PayPeriod.YEARLY, yearly.payPeriod)
        assertEquals(4251.60, yearly.employeesNI)
        assertEquals(5368.20, yearly.employersNI)
        assertEquals(48000.00, yearly.wages)
        assertEquals(12570.0, yearly.taxFree)
        assertEquals(6124.20, yearly.taxToPay)
        assertEquals(32824.2, yearly.takeHome)
        assertEquals(4800.0, yearly.pensionContribution)
        assertEquals(43200.0, yearly.wageAfterPensionDeduction)
        assertEquals(0.0, yearly.taperingAmountDeduction)
        assertNull(yearly.studentLoanBreakdown)
        assertEquals(0.0, yearly.finalStudentLoanAmount)
        assertEquals(0.0, yearly.finalPostgraduateLoanAmount)
        assertEquals(4800.0, yearly.otherAmount)
    }

    @Test
    fun `GIVEN TWENTY_TWENTY_THREE WHEN 48000 AND 400 monthly pension contribution THEN calculates response`() {
        val result = Calculator(
            taxCode = "1257L",
            wages = 48000.0,
            payPeriod = PayPeriod.YEARLY,
            taxYear = TaxYear.TWENTY_TWENTY_THREE,
            pensionContribution = Calculator.PensionContribution(PensionMethod.MONTHLY_AMOUNT_IN_POUNDS, 400.0)
        ).run()
        Logger.i(result.prettyPrintDataClass())

        assertEquals(Country.ENGLAND, result.country)
        assertFalse(result.isKCode)

        val weekly = result.weekly
        assertEquals(PayPeriod.WEEKLY, weekly.payPeriod)
        assertEquals(81.76, weekly.employeesNI)
        assertEquals(103.23, weekly.employersNI)
        assertEquals(923.08, weekly.wages)
        assertEquals(241.73, weekly.taxFree)
        assertEquals(117.77, weekly.taxToPay)
        assertEquals(631.24, weekly.takeHome)
        assertEquals(92.31, weekly.pensionContribution)
        assertEquals(830.77, weekly.wageAfterPensionDeduction)
        assertEquals(0.0, weekly.taperingAmountDeduction)
        assertNull(weekly.studentLoanBreakdown)
        assertEquals(0.0, weekly.finalStudentLoanAmount)
        assertEquals(0.0, weekly.finalPostgraduateLoanAmount)
        assertEquals(92.31, weekly.otherAmount)

        val fourWeekly = result.fourWeekly
        assertEquals(PayPeriod.FOUR_WEEKLY, fourWeekly.payPeriod)
        assertEquals(327.05, fourWeekly.employeesNI)
        assertEquals(412.94, fourWeekly.employersNI)
        assertEquals(3692.31, fourWeekly.wages)
        assertEquals(966.92, fourWeekly.taxFree)
        assertEquals(471.09, fourWeekly.taxToPay)
        assertEquals(2524.94, fourWeekly.takeHome)
        assertEquals(369.23, fourWeekly.pensionContribution)
        assertEquals(3323.08, fourWeekly.wageAfterPensionDeduction)
        assertEquals(0.0, fourWeekly.taperingAmountDeduction)
        assertNull(fourWeekly.studentLoanBreakdown)
        assertEquals(0.0, fourWeekly.finalStudentLoanAmount)
        assertEquals(0.0, fourWeekly.finalPostgraduateLoanAmount)
        assertEquals(369.23, fourWeekly.otherAmount)

        val monthly = result.monthly
        assertEquals(PayPeriod.MONTHLY, monthly.payPeriod)
        assertEquals(354.30, monthly.employeesNI)
        assertEquals(447.35, monthly.employersNI)
        assertEquals(4000.0, monthly.wages)
        assertEquals(1047.5, monthly.taxFree)
        assertEquals(510.35, monthly.taxToPay)
        assertEquals(2735.35, monthly.takeHome)
        assertEquals(400.0, monthly.pensionContribution)
        assertEquals(3600.0, monthly.wageAfterPensionDeduction)
        assertEquals(0.0, monthly.taperingAmountDeduction)
        assertNull(monthly.studentLoanBreakdown)
        assertEquals(0.0, monthly.finalStudentLoanAmount)
        assertEquals(0.0, monthly.finalPostgraduateLoanAmount)
        assertEquals(400.0, monthly.otherAmount)

        val yearly = result.yearly
        assertEquals(PayPeriod.YEARLY, yearly.payPeriod)
        assertEquals(4251.60, yearly.employeesNI)
        assertEquals(5368.20, yearly.employersNI)
        assertEquals(48000.00, yearly.wages)
        assertEquals(12570.0, yearly.taxFree)
        assertEquals(6124.20, yearly.taxToPay)
        assertEquals(32824.2, yearly.takeHome)
        assertEquals(4800.0, yearly.pensionContribution)
        assertEquals(43200.0, yearly.wageAfterPensionDeduction)
        assertEquals(0.0, yearly.taperingAmountDeduction)
        assertNull(yearly.studentLoanBreakdown)
        assertEquals(0.0, yearly.finalStudentLoanAmount)
        assertEquals(0.0, yearly.finalPostgraduateLoanAmount)
        assertEquals(4800.0, yearly.otherAmount)
    }

    @Test
    fun `GIVEN TWENTY_TWENTY_THREE WHEN 100000 wage AND 5000 monthly pension contribution THEN calculates response`() {
        val result = Calculator(
            taxCode = "1257L",
            wages = 100000.0,
            payPeriod = PayPeriod.YEARLY,
            taxYear = TaxYear.TWENTY_TWENTY_THREE,
            pensionContribution = Calculator.PensionContribution(PensionMethod.MONTHLY_AMOUNT_IN_POUNDS, 5000.0)
        ).run()
        Logger.i(result.prettyPrintDataClass())

        assertEquals(Country.ENGLAND, result.country)
        assertFalse(result.isKCode)

        val weekly = result.weekly
        assertEquals(PayPeriod.WEEKLY, weekly.payPeriod)
        assertEquals(106.13, weekly.employeesNI)
        assertEquals(241.23, weekly.employersNI)
        assertEquals(1923.08, weekly.wages)
        assertEquals(241.73, weekly.taxFree)
        assertEquals(105.47, weekly.taxToPay)
        assertEquals(557.63, weekly.takeHome)
        assertEquals(1153.85, weekly.pensionContribution)
        assertEquals(769.23, weekly.wageAfterPensionDeduction)
        assertEquals(0.0, weekly.taperingAmountDeduction)
        assertNull(weekly.studentLoanBreakdown)
        assertEquals(0.0, weekly.finalStudentLoanAmount)
        assertEquals(0.0, weekly.finalPostgraduateLoanAmount)
        assertEquals(1153.85, weekly.otherAmount)

        val fourWeekly = result.fourWeekly
        assertEquals(PayPeriod.FOUR_WEEKLY, fourWeekly.payPeriod)
        assertEquals(424.51, fourWeekly.employeesNI)
        assertEquals(964.94, fourWeekly.employersNI)
        assertEquals(7692.31, fourWeekly.wages)
        assertEquals(966.92, fourWeekly.taxFree)
        assertEquals(421.86, fourWeekly.taxToPay)
        assertEquals(2230.56, fourWeekly.takeHome)
        assertEquals(4615.38, fourWeekly.pensionContribution)
        assertEquals(3076.92, fourWeekly.wageAfterPensionDeduction)
        assertEquals(0.0, fourWeekly.taperingAmountDeduction)
        assertNull(fourWeekly.studentLoanBreakdown)
        assertEquals(0.0, fourWeekly.finalStudentLoanAmount)
        assertEquals(0.0, fourWeekly.finalPostgraduateLoanAmount)
        assertEquals(4615.38, fourWeekly.otherAmount)

        val monthly = result.monthly
        assertEquals(PayPeriod.MONTHLY, monthly.payPeriod)
        assertEquals(459.88, monthly.employeesNI)
        assertEquals(1045.35, monthly.employersNI)
        assertEquals(8333.33, monthly.wages)
        assertEquals(1047.50, monthly.taxFree)
        assertEquals(457.02, monthly.taxToPay)
        assertEquals(2416.43, monthly.takeHome)
        assertEquals(5000.00, monthly.pensionContribution)
        assertEquals(3333.33, monthly.wageAfterPensionDeduction)
        assertEquals(0.0, monthly.taperingAmountDeduction)
        assertNull(monthly.studentLoanBreakdown)
        assertEquals(0.0, monthly.finalStudentLoanAmount)
        assertEquals(0.0, monthly.finalPostgraduateLoanAmount)
        assertEquals(5000.00, monthly.otherAmount)

        val yearly = result.yearly
        assertEquals(PayPeriod.YEARLY, yearly.payPeriod)
        assertEquals(5518.6, yearly.employeesNI)
        assertEquals(12544.2, yearly.employersNI)
        assertEquals(100000.00, yearly.wages)
        assertEquals(12570.0, yearly.taxFree)
        assertEquals(5484.20, yearly.taxToPay)
        assertEquals(28997.2, yearly.takeHome)
        assertEquals(60000.0, yearly.pensionContribution)
        assertEquals(40000.0, yearly.wageAfterPensionDeduction)
        assertEquals(0.0, yearly.taperingAmountDeduction)
        assertNull(yearly.studentLoanBreakdown)
        assertEquals(0.0, yearly.finalStudentLoanAmount)
        assertEquals(0.0, yearly.finalPostgraduateLoanAmount)
        assertEquals(60000.0, yearly.otherAmount)
    }

    @Test
    fun `GIVEN TWENTY_TWENTY_THREE WHEN 100000 wage AND 60 percent pension contribution THEN calculates response`() {
        val result = Calculator(
            taxCode = "1257L",
            wages = 100000.0,
            payPeriod = PayPeriod.YEARLY,
            taxYear = TaxYear.TWENTY_TWENTY_THREE,
            pensionContribution = Calculator.PensionContribution(PensionMethod.PERCENTAGE, 60.0)
        ).run()
        Logger.i(result.prettyPrintDataClass())

        assertEquals(Country.ENGLAND, result.country)
        assertFalse(result.isKCode)

        val weekly = result.weekly
        assertEquals(PayPeriod.WEEKLY, weekly.payPeriod)
        assertEquals(106.13, weekly.employeesNI)
        assertEquals(241.23, weekly.employersNI)
        assertEquals(1923.08, weekly.wages)
        assertEquals(241.73, weekly.taxFree)
        assertEquals(105.47, weekly.taxToPay)
        assertEquals(557.63, weekly.takeHome)
        assertEquals(1153.85, weekly.pensionContribution)
        assertEquals(769.23, weekly.wageAfterPensionDeduction)
        assertEquals(0.0, weekly.taperingAmountDeduction)
        assertNull(weekly.studentLoanBreakdown)
        assertEquals(0.0, weekly.finalStudentLoanAmount)
        assertEquals(0.0, weekly.finalPostgraduateLoanAmount)
        assertEquals(1153.85, weekly.otherAmount)

        val fourWeekly = result.fourWeekly
        assertEquals(PayPeriod.FOUR_WEEKLY, fourWeekly.payPeriod)
        assertEquals(424.51, fourWeekly.employeesNI)
        assertEquals(964.94, fourWeekly.employersNI)
        assertEquals(7692.31, fourWeekly.wages)
        assertEquals(966.92, fourWeekly.taxFree)
        assertEquals(421.86, fourWeekly.taxToPay)
        assertEquals(2230.56, fourWeekly.takeHome)
        assertEquals(4615.38, fourWeekly.pensionContribution)
        assertEquals(3076.92, fourWeekly.wageAfterPensionDeduction)
        assertEquals(0.0, fourWeekly.taperingAmountDeduction)
        assertNull(fourWeekly.studentLoanBreakdown)
        assertEquals(0.0, fourWeekly.finalStudentLoanAmount)
        assertEquals(0.0, fourWeekly.finalPostgraduateLoanAmount)
        assertEquals(4615.38, fourWeekly.otherAmount)

        val monthly = result.monthly
        assertEquals(PayPeriod.MONTHLY, monthly.payPeriod)
        assertEquals(459.88, monthly.employeesNI)
        assertEquals(1045.35, monthly.employersNI)
        assertEquals(8333.33, monthly.wages)
        assertEquals(1047.50, monthly.taxFree)
        assertEquals(457.02, monthly.taxToPay)
        assertEquals(2416.43, monthly.takeHome)
        assertEquals(5000.00, monthly.pensionContribution)
        assertEquals(3333.33, monthly.wageAfterPensionDeduction)
        assertEquals(0.0, monthly.taperingAmountDeduction)
        assertNull(monthly.studentLoanBreakdown)
        assertEquals(0.0, monthly.finalStudentLoanAmount)
        assertEquals(0.0, monthly.finalPostgraduateLoanAmount)
        assertEquals(5000.0, monthly.otherAmount)

        val yearly = result.yearly
        assertEquals(PayPeriod.YEARLY, yearly.payPeriod)
        assertEquals(5518.6, yearly.employeesNI)
        assertEquals(12544.2, yearly.employersNI)
        assertEquals(100000.00, yearly.wages)
        assertEquals(12570.0, yearly.taxFree)
        assertEquals(5484.20, yearly.taxToPay)
        assertEquals(28997.2, yearly.takeHome)
        assertEquals(60000.0, yearly.pensionContribution)
        assertEquals(40000.0, yearly.wageAfterPensionDeduction)
        assertEquals(0.0, yearly.taperingAmountDeduction)
        assertNull(yearly.studentLoanBreakdown)
        assertEquals(0.0, yearly.finalStudentLoanAmount)
        assertEquals(0.0, yearly.finalPostgraduateLoanAmount)
        assertEquals(60000.0, yearly.otherAmount)
    }

    @Test
    fun `GIVEN TWENTY_TWENTY_THREE WHEN 100000 wage AND 5420 monthly pension contribution THEN calculates response`() {
        val result = Calculator(
            taxCode = "1257L",
            wages = 100000.0,
            payPeriod = PayPeriod.YEARLY,
            taxYear = TaxYear.TWENTY_TWENTY_THREE,
            pensionContribution = Calculator.PensionContribution(PensionMethod.MONTHLY_AMOUNT_IN_POUNDS, 5420.0)
        ).run()
        Logger.i(result.prettyPrintDataClass())

        assertEquals(Country.ENGLAND, result.country)
        assertFalse(result.isKCode)

        val weekly = result.weekly
        assertEquals(PayPeriod.WEEKLY, weekly.payPeriod)
        assertEquals(106.13, weekly.employeesNI)
        assertEquals(241.23, weekly.employersNI)
        assertEquals(1923.08, weekly.wages)
        assertEquals(241.73, weekly.taxFree)
        assertEquals(86.08, weekly.taxToPay)
        assertEquals(480.1, weekly.takeHome)
        assertEquals(1250.77, weekly.pensionContribution)
        assertEquals(672.31, weekly.wageAfterPensionDeduction)
        assertEquals(0.0, weekly.taperingAmountDeduction)
        assertNull(weekly.studentLoanBreakdown)
        assertEquals(0.0, weekly.finalStudentLoanAmount)
        assertEquals(0.0, weekly.finalPostgraduateLoanAmount)
        assertEquals(1250.77, weekly.otherAmount)

        val fourWeekly = result.fourWeekly
        assertEquals(PayPeriod.FOUR_WEEKLY, fourWeekly.payPeriod)
        assertEquals(424.51, fourWeekly.employeesNI)
        assertEquals(964.94, fourWeekly.employersNI)
        assertEquals(7692.31, fourWeekly.wages)
        assertEquals(966.92, fourWeekly.taxFree)
        assertEquals(344.32, fourWeekly.taxToPay)
        assertEquals(1920.4, fourWeekly.takeHome)
        assertEquals(5003.08, fourWeekly.pensionContribution)
        assertEquals(2689.23, fourWeekly.wageAfterPensionDeduction)
        assertEquals(0.0, fourWeekly.taperingAmountDeduction)
        assertNull(fourWeekly.studentLoanBreakdown)
        assertEquals(0.0, fourWeekly.finalStudentLoanAmount)
        assertEquals(0.0, fourWeekly.finalPostgraduateLoanAmount)
        assertEquals(5003.08, fourWeekly.otherAmount)

        val monthly = result.monthly
        assertEquals(PayPeriod.MONTHLY, monthly.payPeriod)
        assertEquals(459.88, monthly.employeesNI)
        assertEquals(1045.35, monthly.employersNI)
        assertEquals(8333.33, monthly.wages)
        assertEquals(1047.50, monthly.taxFree)
        assertEquals(373.02, monthly.taxToPay)
        assertEquals(2080.43, monthly.takeHome)
        assertEquals(5420.0, monthly.pensionContribution)
        assertEquals(2913.33, monthly.wageAfterPensionDeduction)
        assertEquals(0.0, monthly.taperingAmountDeduction)
        assertNull(monthly.studentLoanBreakdown)
        assertEquals(0.0, monthly.finalStudentLoanAmount)
        assertEquals(0.0, monthly.finalPostgraduateLoanAmount)
        assertEquals(5420.0, monthly.otherAmount)

        val yearly = result.yearly
        assertEquals(PayPeriod.YEARLY, yearly.payPeriod)
        assertEquals(5518.6, yearly.employeesNI)
        assertEquals(12544.2, yearly.employersNI)
        assertEquals(100000.00, yearly.wages)
        assertEquals(12570.0, yearly.taxFree)
        assertEquals(4476.20, yearly.taxToPay)
        assertEquals(24965.2, yearly.takeHome)
        assertEquals(65040.0, yearly.pensionContribution)
        assertEquals(34960.0, yearly.wageAfterPensionDeduction)
        assertEquals(0.0, yearly.taperingAmountDeduction)
        assertNull(yearly.studentLoanBreakdown)
        assertEquals(0.0, yearly.finalStudentLoanAmount)
        assertEquals(0.0, yearly.finalPostgraduateLoanAmount)
        assertEquals(65040.0, yearly.otherAmount)
    }

    @Test
    fun `GIVEN TWENTY_TWENTY_THREE WHEN 100000 wage AND 65 percent pension contribution THEN calculates response`() {
        val result = Calculator(
            taxCode = "1257L",
            wages = 100000.0,
            payPeriod = PayPeriod.YEARLY,
            taxYear = TaxYear.TWENTY_TWENTY_THREE,
            pensionContribution = Calculator.PensionContribution(PensionMethod.PERCENTAGE, 65.0)
        ).run()
        Logger.i(result.prettyPrintDataClass())

        assertEquals(Country.ENGLAND, result.country)
        assertFalse(result.isKCode)

        val weekly = result.weekly
        assertEquals(PayPeriod.WEEKLY, weekly.payPeriod)
        assertEquals(106.13, weekly.employeesNI)
        assertEquals(241.23, weekly.employersNI)
        assertEquals(1923.08, weekly.wages)
        assertEquals(241.73, weekly.taxFree)
        assertEquals(86.23, weekly.taxToPay)
        assertEquals(480.72, weekly.takeHome)
        assertEquals(1250.00, weekly.pensionContribution)
        assertEquals(673.08, weekly.wageAfterPensionDeduction)
        assertEquals(0.0, weekly.taperingAmountDeduction)
        assertNull(weekly.studentLoanBreakdown)
        assertEquals(0.0, weekly.finalStudentLoanAmount)
        assertEquals(0.0, weekly.finalPostgraduateLoanAmount)
        assertEquals(1250.0, weekly.otherAmount)

        val fourWeekly = result.fourWeekly
        assertEquals(PayPeriod.FOUR_WEEKLY, fourWeekly.payPeriod)
        assertEquals(424.51, fourWeekly.employeesNI)
        assertEquals(964.94, fourWeekly.employersNI)
        assertEquals(7692.31, fourWeekly.wages)
        assertEquals(966.92, fourWeekly.taxFree)
        assertEquals(344.94, fourWeekly.taxToPay)
        assertEquals(1922.86, fourWeekly.takeHome)
        assertEquals(5000.00, fourWeekly.pensionContribution)
        assertEquals(2692.31, fourWeekly.wageAfterPensionDeduction)
        assertEquals(0.0, fourWeekly.taperingAmountDeduction)
        assertNull(fourWeekly.studentLoanBreakdown)
        assertEquals(0.0, fourWeekly.finalStudentLoanAmount)
        assertEquals(0.0, fourWeekly.finalPostgraduateLoanAmount)
        assertEquals(5000.0, fourWeekly.otherAmount)

        val monthly = result.monthly
        assertEquals(PayPeriod.MONTHLY, monthly.payPeriod)
        assertEquals(459.88, monthly.employeesNI)
        assertEquals(1045.35, monthly.employersNI)
        assertEquals(8333.33, monthly.wages)
        assertEquals(1047.50, monthly.taxFree)
        assertEquals(373.68, monthly.taxToPay)
        assertEquals(2083.1, monthly.takeHome)
        assertEquals(5416.67, monthly.pensionContribution)
        assertEquals(2916.67, monthly.wageAfterPensionDeduction)
        assertEquals(0.0, monthly.taperingAmountDeduction)
        assertNull(monthly.studentLoanBreakdown)
        assertEquals(0.0, monthly.finalStudentLoanAmount)
        assertEquals(0.0, monthly.finalPostgraduateLoanAmount)
        assertEquals(5416.67, monthly.otherAmount)

        val yearly = result.yearly
        assertEquals(PayPeriod.YEARLY, yearly.payPeriod)
        assertEquals(5518.6, yearly.employeesNI)
        assertEquals(12544.2, yearly.employersNI)
        assertEquals(100000.00, yearly.wages)
        assertEquals(12570.0, yearly.taxFree)
        assertEquals(4484.20, yearly.taxToPay)
        assertEquals(24997.2, yearly.takeHome)
        assertEquals(65000.0, yearly.pensionContribution)
        assertEquals(35000.0, yearly.wageAfterPensionDeduction)
        assertEquals(0.0, yearly.taperingAmountDeduction)
        assertNull(yearly.studentLoanBreakdown)
        assertEquals(0.0, yearly.finalStudentLoanAmount)
        assertEquals(0.0, yearly.finalPostgraduateLoanAmount)
        assertEquals(65000.0, yearly.otherAmount)
    }

    @Test
    fun `GIVEN TWENTY_TWENTY_THREE WHEN 24579 wage AND 1000 monthly pension contribution THEN calculates response`() {
        val result = Calculator(
            taxCode = "1257L",
            wages = 24579.0,
            payPeriod = PayPeriod.YEARLY,
            taxYear = TaxYear.TWENTY_TWENTY_THREE,
            pensionContribution = Calculator.PensionContribution(PensionMethod.MONTHLY_AMOUNT_IN_POUNDS, 1000.0)
        ).run()
        Logger.i(result.prettyPrintDataClass())

        assertEquals(Country.ENGLAND, result.country)
        assertFalse(result.isKCode)

        val weekly = result.weekly
        assertEquals(PayPeriod.WEEKLY, weekly.payPeriod)
        assertEquals(27.71, weekly.employeesNI)
        assertEquals(41.08, weekly.employersNI)
        assertEquals(472.67, weekly.wages)
        assertEquals(241.73, weekly.taxFree)
        assertEquals(0.0, weekly.taxToPay)
        assertEquals(214.19, weekly.takeHome)
        assertEquals(230.77, weekly.pensionContribution)
        assertEquals(241.90, weekly.wageAfterPensionDeduction)
        assertEquals(0.0, weekly.taperingAmountDeduction)
        assertNull(weekly.studentLoanBreakdown)
        assertEquals(0.0, weekly.finalStudentLoanAmount)
        assertEquals(0.0, weekly.finalPostgraduateLoanAmount)
        assertEquals(230.77, weekly.otherAmount)

        val fourWeekly = result.fourWeekly
        assertEquals(PayPeriod.FOUR_WEEKLY, fourWeekly.payPeriod)
        assertEquals(110.85, fourWeekly.employeesNI)
        assertEquals(164.32, fourWeekly.employersNI)
        assertEquals(1890.69, fourWeekly.wages)
        assertEquals(966.92, fourWeekly.taxFree)
        assertEquals(0.0, fourWeekly.taxToPay)
        assertEquals(856.76, fourWeekly.takeHome)
        assertEquals(923.08, fourWeekly.pensionContribution)
        assertEquals(967.62, fourWeekly.wageAfterPensionDeduction)
        assertEquals(0.0, fourWeekly.taperingAmountDeduction)
        assertNull(fourWeekly.studentLoanBreakdown)
        assertEquals(0.0, fourWeekly.finalStudentLoanAmount)
        assertEquals(0.0, fourWeekly.finalPostgraduateLoanAmount)
        assertEquals(923.08, fourWeekly.otherAmount)

        val monthly = result.monthly
        assertEquals(PayPeriod.MONTHLY, monthly.payPeriod)
        assertEquals(120.09, monthly.employeesNI)
        assertEquals(178.01, monthly.employersNI)
        assertEquals(2048.25, monthly.wages)
        assertEquals(1047.50, monthly.taxFree)
        assertEquals(0.0, monthly.taxToPay)
        assertEquals(928.16, monthly.takeHome)
        assertEquals(1000.0, monthly.pensionContribution)
        assertEquals(1048.25, monthly.wageAfterPensionDeduction)
        assertEquals(0.0, monthly.taperingAmountDeduction)
        assertNull(monthly.studentLoanBreakdown)
        assertEquals(0.0, monthly.finalStudentLoanAmount)
        assertEquals(0.0, monthly.finalPostgraduateLoanAmount)
        assertEquals(1000.0, monthly.otherAmount)

        val yearly = result.yearly
        assertEquals(PayPeriod.YEARLY, yearly.payPeriod)
        assertEquals(1441.08, yearly.employeesNI)
        assertEquals(2136.10, yearly.employersNI)
        assertEquals(24579.0, yearly.wages)
        assertEquals(12570.0, yearly.taxFree)
        assertEquals(0.0, yearly.taxToPay)
        assertEquals(11137.92, yearly.takeHome)
        assertEquals(12000.0, yearly.pensionContribution)
        assertEquals(12579.0, yearly.wageAfterPensionDeduction)
        assertEquals(0.0, yearly.taperingAmountDeduction)
        assertNull(yearly.studentLoanBreakdown)
        assertEquals(0.0, yearly.finalStudentLoanAmount)
        assertEquals(0.0, yearly.finalPostgraduateLoanAmount)
        assertEquals(12000.0, yearly.otherAmount)
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
        assertEquals(0.0, weekly.finalPostgraduateLoanAmount)
        assertEquals(0.0, weekly.otherAmount)

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
        assertEquals(0.0, fourWeekly.finalPostgraduateLoanAmount)
        assertEquals(0.0, fourWeekly.otherAmount)

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
        assertEquals(0.0, monthly.finalPostgraduateLoanAmount)
        assertEquals(0.0, monthly.otherAmount)

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
        assertEquals(0.0, yearly.finalPostgraduateLoanAmount)
        assertEquals(0.0, yearly.otherAmount)
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
        assertEquals(0.0, weekly.finalPostgraduateLoanAmount)
        assertEquals(0.0, weekly.otherAmount)

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
        assertEquals(0.0, fourWeekly.finalPostgraduateLoanAmount)
        assertEquals(0.0, fourWeekly.otherAmount)

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
        assertEquals(0.0, monthly.finalPostgraduateLoanAmount)
        assertEquals(0.0, monthly.otherAmount)

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
        assertEquals(0.0, yearly.finalPostgraduateLoanAmount)
        assertEquals(0.0, yearly.otherAmount)
    }

    @Test
    fun `GIVEN TWENTY_TWENTY_THREE AND student loan plan 2 AND post graduate plan WHEN 28800 wage THEN calculates response`() {
        val result = Calculator(
            taxCode = "1257L",
            wages = 28800.0,
            payPeriod = PayPeriod.YEARLY,
            taxYear = TaxYear.TWENTY_TWENTY_THREE,
            studentLoanPlans = Calculator.StudentLoanPlans(
                hasPlanTwo = true,
                hasPostgraduatePlan = true
            )
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
        assertEquals(2.0, weekly.finalStudentLoanAmount)
        assertEquals(9.0, weekly.finalPostgraduateLoanAmount)
        assertEquals(11.0, weekly.otherAmount)

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
        assertEquals(10.0, fourWeekly.finalStudentLoanAmount)
        assertEquals(36.0, fourWeekly.finalPostgraduateLoanAmount)
        assertEquals(46.0, fourWeekly.otherAmount)

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
        assertEquals(11.0, monthly.finalStudentLoanAmount)
        assertEquals(39.0, monthly.finalPostgraduateLoanAmount)
        assertEquals(50.0, monthly.otherAmount)

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
        assertEquals(135.0, yearly.finalStudentLoanAmount)
        assertEquals(468.0, yearly.finalPostgraduateLoanAmount)
        assertEquals(603.0, yearly.otherAmount)
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
                pensionContribution = Calculator.PensionContribution(PensionMethod.MONTHLY_AMOUNT_IN_POUNDS, 45000.0)
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
                pensionContribution = Calculator.PensionContribution(PensionMethod.PERCENTAGE, 120.0)
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

    @Test
    fun `GIVEN userSuppliedTaxCode false WHEN calculate THEN clarification contain NO_TAX_CODE_SUPPLIED`() {
        val result = Calculator(
            taxCode = "1257L",
            userSuppliedTaxCode = false,
            wages = 28800.0,
            payPeriod = PayPeriod.YEARLY,
            taxYear = TaxYear.TWENTY_TWENTY_THREE,
        ).run()

        assertTrue(result.listOfClarification.contains(Clarification.NO_TAX_CODE_SUPPLIED))
    }

    @Test
    fun `GIVEN isPensionAge true WHEN calculate THEN clarification contain HAVE_STATE_PENSION`() {
        val result = Calculator(
            taxCode = "1257L",
            wages = 28800.0,
            payPeriod = PayPeriod.YEARLY,
            isPensionAge = true,
            taxYear = TaxYear.TWENTY_TWENTY_THREE,
        ).run()

        assertTrue(result.listOfClarification.contains(Clarification.HAVE_STATE_PENSION))
    }

    @Test
    fun `GIVEN isPensionAge false WHEN calculate THEN clarification contain HAVE_NO_STATE_PENSION`() {
        val result = Calculator(
            taxCode = "1257L",
            wages = 28800.0,
            payPeriod = PayPeriod.YEARLY,
            isPensionAge = false,
            taxYear = TaxYear.TWENTY_TWENTY_THREE,
        ).run()

        assertTrue(result.listOfClarification.contains(Clarification.HAVE_NO_STATE_PENSION))
    }

    @Test
    fun `GIVEN tapering applied WHEN calculate THEN clarification contain INCOME_OVER_100K_WITH_TAPERING`() {
        val result = Calculator(
            taxCode = "1257L",
            userSuppliedTaxCode = false,
            wages = 125140.0,
            payPeriod = PayPeriod.YEARLY,
            taxYear = TaxYear.TWENTY_TWENTY_THREE,
        ).run()

        assertTrue(result.listOfClarification.contains(Clarification.INCOME_OVER_100K_WITH_TAPERING))
    }

    @Test
    fun `GIVEN tapering not apply AND wage is over 100k WHEN calculate THEN clarification contain INCOME_OVER_100K`() {
        val result = Calculator(
            taxCode = "1257L",
            userSuppliedTaxCode = true,
            wages = 125140.0,
            payPeriod = PayPeriod.YEARLY,
            taxYear = TaxYear.TWENTY_TWENTY_THREE,
        ).run()

        assertTrue(result.listOfClarification.contains(Clarification.INCOME_OVER_100K))
    }

    @Test
    fun `GIVEN tax code is k code WHEN calculate THEN clarification contain K_CODE`() {
        val result = Calculator(
            taxCode = "K1257X",
            wages = 28800.0,
            payPeriod = PayPeriod.YEARLY,
            isPensionAge = true,
            taxYear = TaxYear.TWENTY_TWENTY_THREE,
        ).run()

        assertTrue(result.listOfClarification.contains(Clarification.K_CODE))
    }

    @Test
    fun `GIVEN has student loan AND postgrad loan AND wage is below both yearlyThreshold WHEN calculate THEN clarification contain contains INCOME_BELOW_STUDENT_AND_POSTGRAD_LOAN`() {
        val result = Calculator(
            taxCode = "1257L",
            wages = 15000.0,
            payPeriod = PayPeriod.YEARLY,
            taxYear = TaxYear.TWENTY_TWENTY_THREE,
            studentLoanPlans = Calculator.StudentLoanPlans(
                hasPlanTwo = true,
                hasPostgraduatePlan = true
            )
        ).run()

        val listOfExpectedResult = mutableListOf(
            Clarification.HAVE_NO_STATE_PENSION,
            Clarification.INCOME_BELOW_STUDENT_AND_POSTGRAD_LOAN,
        )
        assertEquals(listOfExpectedResult, result.listOfClarification)
    }

    @Test
    fun `GIVEN has student loan only AND wage is below yearlyThreshold WHEN calculate THEN clarification contain contains INCOME_BELOW_STUDENT_LOAN`() {
        val result = Calculator(
            taxCode = "1257L",
            wages = 15000.0,
            payPeriod = PayPeriod.YEARLY,
            taxYear = TaxYear.TWENTY_TWENTY_THREE,
            studentLoanPlans = Calculator.StudentLoanPlans(
                hasPlanTwo = true,
            )
        ).run()

        val listOfExpectedResult = mutableListOf(
            Clarification.HAVE_NO_STATE_PENSION,
            Clarification.INCOME_BELOW_STUDENT_LOAN,
        )
        assertEquals(listOfExpectedResult, result.listOfClarification)
    }

    @Test
    fun `GIVEN has postgrad loan only AND wage is below yearlyThreshold WHEN calculate THEN clarification contain contains INCOME_BELOW_POSTGRAD_LOAN`() {
        val result = Calculator(
            taxCode = "1257L",
            wages = 15000.0,
            payPeriod = PayPeriod.YEARLY,
            taxYear = TaxYear.TWENTY_TWENTY_THREE,
            studentLoanPlans = Calculator.StudentLoanPlans(
                hasPostgraduatePlan = true,
            )
        ).run()

        val listOfExpectedResult = mutableListOf(
            Clarification.HAVE_NO_STATE_PENSION,
            Clarification.INCOME_BELOW_POSTGRAD_LOAN,
        )
        assertEquals(listOfExpectedResult, result.listOfClarification)
    }

    @Test
    fun `GIVEN has student loan AND postgrad loan AND wage is only below student loan yearlyThreshold WHEN calculate THEN clarification contain contains INCOME_BELOW_STUDENT_BUT_ABOVE_POSTGRAD_LOAN`() {
        val result = Calculator(
            taxCode = "1257L",
            wages = 23000.0, // value needs to be between 21000 (postgrad threshold) to 27295 (plan 2 threshold).
            payPeriod = PayPeriod.YEARLY,
            taxYear = TaxYear.TWENTY_TWENTY_THREE,
            studentLoanPlans = Calculator.StudentLoanPlans(
                hasPlanTwo = true,
                hasPostgraduatePlan = true
            )
        ).run()

        val listOfExpectedResult = mutableListOf(
            Clarification.HAVE_NO_STATE_PENSION,
            Clarification.INCOME_BELOW_STUDENT_BUT_ABOVE_POSTGRAD_LOAN,
        )
        assertEquals(listOfExpectedResult, result.listOfClarification)
    }

    @Test
    fun `GIVEN multiple clarifications met WHEN calculate THEN return a list of clarifications`() {
        val result = Calculator(
            taxCode = "K1257X",
            userSuppliedTaxCode = false,
            wages = 125140.0,
            payPeriod = PayPeriod.YEARLY,
            isPensionAge = true,
            taxYear = TaxYear.TWENTY_TWENTY_THREE,
            studentLoanPlans = Calculator.StudentLoanPlans(
                hasPlanTwo = true,
                hasPostgraduatePlan = true
            )
        ).run()

        val listOfExpectedResult = mutableListOf(
            Clarification.NO_TAX_CODE_SUPPLIED,
            Clarification.HAVE_STATE_PENSION,
            Clarification.K_CODE,
            Clarification.INCOME_OVER_100K,
        )
        assertEquals(listOfExpectedResult, result.listOfClarification)
    }

    @Test
    fun `GIVEN tax code is scottish tax code AND userPaysScottishTax true WHEN calculate THEN clarification contains SCOTTISH_INCOME_APPLIED`() {
        val result = Calculator(
            taxCode = "S1257L",
            userPaysScottishTax = true,
            wages = 30000.0,
            payPeriod = PayPeriod.YEARLY,
            taxYear = TaxYear.TWENTY_TWENTY_THREE,
            studentLoanPlans = Calculator.StudentLoanPlans(
                hasPlanTwo = true,
                hasPostgraduatePlan = true
            )
        ).run()

        val listOfExpectedResult = mutableListOf(
            Clarification.HAVE_NO_STATE_PENSION,
            Clarification.SCOTTISH_INCOME_APPLIED,
        )

        assertEquals(listOfExpectedResult, result.listOfClarification)
    }

    @Test
    fun `GIVEN tax code is scottish tax code AND userPaysScottishTax false WHEN calculate THEN clarification contains SCOTTISH_CODE_BUT_OTHER_RATE`() {
        val result = Calculator(
            taxCode = "S1257L",
            userPaysScottishTax = false,
            wages = 30000.0,
            payPeriod = PayPeriod.YEARLY,
            taxYear = TaxYear.TWENTY_TWENTY_THREE,
            studentLoanPlans = Calculator.StudentLoanPlans(
                hasPlanTwo = true,
                hasPostgraduatePlan = true
            )
        ).run()

        val listOfExpectedResult = mutableListOf(
            Clarification.HAVE_NO_STATE_PENSION,
            Clarification.SCOTTISH_CODE_BUT_OTHER_RATE,
        )

        assertEquals(listOfExpectedResult, result.listOfClarification)
    }

    @Test
    fun `GIVEN tax code is not scottish tax code AND userPaysScottishTax true WHEN calculate THEN clarification contains NON_SCOTTISH_CODE_BUT_SCOTTISH_RATE`() {
        val result = Calculator(
            taxCode = "1257L",
            userPaysScottishTax = true,
            wages = 30000.0,
            payPeriod = PayPeriod.YEARLY,
            taxYear = TaxYear.TWENTY_TWENTY_THREE,
            studentLoanPlans = Calculator.StudentLoanPlans(
                hasPlanTwo = true,
                hasPostgraduatePlan = true
            )
        ).run()

        val listOfExpectedResult = mutableListOf(
            Clarification.HAVE_NO_STATE_PENSION,
            Clarification.NON_SCOTTISH_CODE_BUT_SCOTTISH_RATE,
        )

        assertEquals(listOfExpectedResult, result.listOfClarification)
    }

    @Test
    fun `GIVEN pension exceed annual allowance WHEN calculate THEN clarification contains PENSION_EXCEED_ANNUAL_ALLOWANCE`() {
        val result = Calculator(
            taxCode = "1257L",
            wages = 90000.0,
            payPeriod = PayPeriod.YEARLY,
            taxYear = TaxYear.TWENTY_TWENTY_THREE,
            pensionContribution = Calculator.PensionContribution(PensionMethod.MONTHLY_AMOUNT_IN_POUNDS, 5420.0)
        ).run()

        val listOfExpectedResult = mutableListOf(
            Clarification.HAVE_NO_STATE_PENSION,
            Clarification.PENSION_EXCEED_ANNUAL_ALLOWANCE,
        )

        assertEquals(listOfExpectedResult, result.listOfClarification)
    }

    @Test
    fun `GIVEN pension below annual allowance WHEN calculate THEN clarification contains PENSION_BELOW_ANNUAL_ALLOWANCE`() {
        val result = Calculator(
            taxCode = "1257L",
            wages = 90000.0,
            payPeriod = PayPeriod.YEARLY,
            taxYear = TaxYear.TWENTY_TWENTY_THREE,
            pensionContribution = Calculator.PensionContribution(PensionMethod.MONTHLY_AMOUNT_IN_POUNDS, 800.0)
        ).run()

        val listOfExpectedResult = mutableListOf(
            Clarification.HAVE_NO_STATE_PENSION,
            Clarification.PENSION_BELOW_ANNUAL_ALLOWANCE,
        )

        assertEquals(listOfExpectedResult, result.listOfClarification)
    }
}
