package model.taxcodes

import Calculator
import model.PayPeriod
import kotlin.test.Test
import kotlin.test.assertEquals


class KTaxCodesTests {

    @Test
    fun `CK100 Wales 100K`() {
        val calculator = Calculator("CK100", 100000.0, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(32905.4, calculator.yearly.taxToPay)
        assertEquals(12608.784000000001, calculator.yearly.employersNI)
        assertEquals(5964.16, calculator.yearly.employeesNI)
    }

    @Test
    fun `K100 England 100K`() {
        val calculator = Calculator("K100", 100000.0, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(32905.4, calculator.yearly.taxToPay)
        assertEquals(12608.784000000001, calculator.yearly.employersNI)
        assertEquals(5964.16, calculator.yearly.employeesNI)
    }

    @Test
    fun `SK100 Scotland 100K`() {
        val calculator = Calculator("SK100", 100000.0, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(35084.74, calculator.yearly.taxToPay)
        assertEquals(12608.784000000001, calculator.yearly.employersNI)
        assertEquals(5964.16, calculator.yearly.employeesNI)
    }
}