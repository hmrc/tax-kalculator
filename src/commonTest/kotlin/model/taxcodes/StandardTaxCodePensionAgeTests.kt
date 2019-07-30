package calculator

import Calculator
import model.CalculatorResponse
import model.PayPeriod
import kotlin.test.Test
import kotlin.test.assertEquals

class StandardPensionAgeTaxCodeTestsYearly {

    //    1250L @ 100K
    @Test
    fun `1250L Pension Age Wales YEARLY 100k`() {
        val calculator: CalculatorResponse =
            Calculator("C1250L", 100000.0, pensionAge = true, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(27498.2, calculator.yearly.taxToPay)
        assertEquals(0.0, calculator.yearly.employersNI)
        assertEquals(0.0, calculator.yearly.employeesNI)
    }

    @Test
    fun `1250L Pension Age England YEARLY 100k`() {
        val calculator =
            Calculator("1250L", 100000.0, pensionAge = true, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(27498.2, calculator.yearly.taxToPay)
        assertEquals(0.0, calculator.yearly.employersNI)
        assertEquals(0.0, calculator.yearly.employeesNI)
    }

    @Test
    fun `1185L Pension Age England YEARLY 100k`() {
        val calculator =
            Calculator("1185L", 100000.0, pensionAge = true, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(27758.2, calculator.yearly.taxToPay)
        assertEquals(0.0, calculator.yearly.employersNI)
        assertEquals(0.0, calculator.yearly.employeesNI)
    }

    @Test
    fun `1250L Pension Age Scotland YEARLY 100k`() {
        val calculator =
            Calculator("S1250L", 100000.0, pensionAge = true, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(29542.359999999997, calculator.yearly.taxToPay)
        assertEquals(0.0, calculator.yearly.employersNI)
        assertEquals(0.0, calculator.yearly.employeesNI)
    }

    //    1250L @ 20K
    @Test
    fun `1250L Pension Age England YEARLY 20k`() {
        val calculator =
            Calculator("1250L", 20000.0, pensionAge = true, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(1498.2, calculator.yearly.taxToPay)
        assertEquals(0.0, calculator.yearly.employersNI)
        assertEquals(0.0, calculator.yearly.employeesNI)
    }

    @Test
    fun `1250L Pension Age Wales YEARLY 20k`() {
        val calculator =
            Calculator("C1250L", 20000.0, pensionAge = true, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(1498.2, calculator.yearly.taxToPay)
        assertEquals(0.0, calculator.yearly.employersNI)
        assertEquals(0.0, calculator.yearly.employeesNI)
    }

    @Test
    fun `1250L Pension Age Scotland YEARLY 20k`() {
        val calculator =
            Calculator("S1250L", 20000.0, pensionAge = true, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(1477.8000000000002, calculator.yearly.taxToPay)
        assertEquals(0.0, calculator.yearly.employersNI)
        assertEquals(0.0, calculator.yearly.employeesNI)
    }
}

class StandardTaxCodeTestsOtherTimePensionAgePeriods {
    @Test
    fun `1250L Pension Age England MONTHLY 2k`() {
        val calculator =
            Calculator("1250L", 2000.0, pensionAge = true, payPeriod = PayPeriod.MONTHLY, taxYear = 2019).run()
        assertEquals(0.0, calculator.yearly.employersNI)
        assertEquals(0.0, calculator.yearly.employeesNI)
        assertEquals(2298.2000000000003, calculator.yearly.taxToPay)
    }

    @Test
    fun `1250L Pension Age England FOURWEEKLY 2k`() {
        val calculator =
            Calculator("1250L", 2000.0, pensionAge = true, payPeriod = PayPeriod.FOUR_WEEKLY, taxYear = 2019).run()
        assertEquals(0.0, calculator.yearly.employersNI)
        assertEquals(0.0, calculator.yearly.employeesNI)
        assertEquals(2698.2000000000003, calculator.yearly.taxToPay)
    }

    @Test
    fun `1250L Pension Age England WEEKLY 500 pounds`() {
        val calculator =
            Calculator("1250L", 500.0, pensionAge = true, payPeriod = PayPeriod.WEEKLY, taxYear = 2019).run()
        assertEquals(0.0, calculator.yearly.employersNI)
        assertEquals(0.0, calculator.yearly.employeesNI)
        assertEquals(2698.2000000000003, calculator.yearly.taxToPay)
    }

    @Test
    fun `1250L Pension Age England HOURLY 20 pounds`() {
        val calculator =
            Calculator(
                "1250L",
                20.0,
                pensionAge = true,
                payPeriod = PayPeriod.HOURLY,
                hoursPerWeek = 37.5,
                taxYear = 2019
            ).run()
        assertEquals(0.0, calculator.yearly.employersNI)
        assertEquals(0.0, calculator.yearly.employeesNI)
        assertEquals(5298.200000000001, calculator.yearly.taxToPay)
    }
}