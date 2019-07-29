package calculator

import Calculator
import model.CalculatorResponse
import model.PayPeriod
import kotlin.test.Test
import kotlin.test.assertEquals

class StandardTaxCodeTestsYearly {

    //    1250L @ 100K
    @Test
    fun `1250L WalesYEARLY 100k`() {
        val calculator: CalculatorResponse =
            Calculator("C1250L", 100000.0, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(27498.2, calculator.yearly.taxToPay)
        assertEquals(12608.784000000001, calculator.yearly.employersNI)
        assertEquals(5964.16, calculator.yearly.employeesNI)
    }

    @Test
    fun `1250L England YEARLY 100k`() {
        val calculator = Calculator("1250L", 100000.0, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(27498.2, calculator.yearly.taxToPay)
        assertEquals(12608.784000000001, calculator.yearly.employersNI)
        assertEquals(5964.16, calculator.yearly.employeesNI)
    }

    @Test
    fun `1185L England YEARLY 100k`() {
        val calculator = Calculator("1185L", 100000.0, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(27758.2, calculator.yearly.taxToPay)
        assertEquals(12608.784000000001, calculator.yearly.employersNI)
        assertEquals(5964.16, calculator.yearly.employeesNI)
    }

    @Test
    fun `1250L Scotland YEARLY 100k`() {
        val calculator = Calculator("S1250L", 100000.0, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(29542.359999999997, calculator.yearly.taxToPay)
        assertEquals(12608.784000000001, calculator.yearly.employersNI)
        assertEquals(5964.16, calculator.yearly.employeesNI)
    }

    //    1250L @ 20K
    @Test
    fun `1250L England YEARLY 20k`() {
        val calculator = Calculator("1250L", 20000.0, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(1498.2, calculator.yearly.taxToPay)
        assertEquals(1568.784, calculator.yearly.employersNI)
        assertEquals(1364.1599999999999, calculator.yearly.employeesNI)
    }

    @Test
    fun `1250L Wales YEARLY 20k`() {
        val calculator = Calculator("C1250L", 20000.0, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(1498.2, calculator.yearly.taxToPay)
        assertEquals(1568.784, calculator.yearly.employersNI)
        assertEquals(1364.1599999999999, calculator.yearly.employeesNI)
    }

    @Test
    fun `1250L Scotland YEARLY 20k`() {
        val calculator = Calculator("S1250L", 20000.0, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(1477.8000000000002, calculator.yearly.taxToPay)
        assertEquals(1568.784, calculator.yearly.employersNI)
        assertEquals(1364.1599999999999, calculator.yearly.employeesNI)
    }
}

class StandardTaxCodeTestsOtherTimePeriods {
    @Test
    fun `1250L England MONTHLY 2k`() {
        val calculator = Calculator("1250L", 2000.0, payPeriod = PayPeriod.MONTHLY, taxYear = 2019).run()
        assertEquals(1844.1599999999999, calculator.yearly.employeesNI)
        assertEquals(2120.784, calculator.yearly.employersNI)
        assertEquals(2298.2000000000003, calculator.yearly.taxToPay)
    }

    @Test
    fun `1250L England FOURWEEKLY 2k`() {
        val calculator = Calculator("1250L", 2000.0, payPeriod = PayPeriod.FOUR_WEEKLY, taxYear = 2019).run()
        assertEquals(2084.16, calculator.yearly.employeesNI)
        assertEquals(2396.784, calculator.yearly.employersNI)
        assertEquals(2698.2000000000003, calculator.yearly.taxToPay)
    }

    @Test
    fun `1250L England WEEKLY 500 pounds`() {
        val calculator = Calculator("1250L", 500.0, payPeriod = PayPeriod.WEEKLY, taxYear = 2019).run()
        assertEquals(2084.16, calculator.yearly.employeesNI)
        assertEquals(2396.784, calculator.yearly.employersNI)
        assertEquals(2698.2000000000003, calculator.yearly.taxToPay)
    }

    @Test
    fun `1250L England HOURLY 20 pounds`() {
        val calculator =
            Calculator("1250L", 20.0, payPeriod = PayPeriod.HOURLY, hoursPerWeek = 37.5, taxYear = 2019).run()
        assertEquals(3644.16, calculator.yearly.employeesNI)
        assertEquals(4190.784000000001, calculator.yearly.employersNI)
        assertEquals(5298.200000000001, calculator.yearly.taxToPay)
    }
}