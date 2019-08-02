package model.taxcodes

import Calculator
import model.PayPeriod
import kotlin.test.Test
import kotlin.test.assertEquals


class D0TaxCodesTests {

    @Test
    fun `D0 Wales 100K`() {
        val calculator = Calculator("CD0", 100000.0, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(40000.00, calculator.yearly.taxToPay)
        assertEquals(12608.784000000001, calculator.yearly.employersNI)
        assertEquals(5964.16, calculator.yearly.employeesNI)
    }

    @Test
    fun `D0 England 100K`() {
        val calculator = Calculator("D0", 100000.0, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(40000.00, calculator.yearly.taxToPay)
        assertEquals(12608.784000000001, calculator.yearly.employersNI)
        assertEquals(5964.16, calculator.yearly.employeesNI)
    }

    @Test
    fun `D0 Scotland 100K`() {
        val calculator = Calculator("SD0", 100000.0, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(21000.00, calculator.yearly.taxToPay)
        assertEquals(12608.784000000001, calculator.yearly.employersNI)
        assertEquals(5964.16, calculator.yearly.employeesNI)
    }
}