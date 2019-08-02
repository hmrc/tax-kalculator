package model.taxcodes

import Calculator
import model.PayPeriod
import kotlin.test.Test
import kotlin.test.assertEquals

class BRCodeTests {
    @Test
    fun `BR England 100K`() {
        val calculator = Calculator("BR", 100000.0, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(20000.0, calculator.yearly.taxToPay)
        assertEquals(12608.784000000001, calculator.yearly.employersNI)
        assertEquals(5964.16, calculator.yearly.employeesNI)
    }

    @Test
    fun `BR Wales 100K`() {
        val calculator = Calculator("CBR", 100000.0, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(20000.0, calculator.yearly.taxToPay)
        assertEquals(12608.784000000001, calculator.yearly.employersNI)
        assertEquals(5964.16, calculator.yearly.employeesNI)
    }

    @Test
    fun `BR Scotland 100K`() {
        val calculator = Calculator("SBR", 100000.0, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(20000.0, calculator.yearly.taxToPay)
        assertEquals(12608.784000000001, calculator.yearly.employersNI)
        assertEquals(5964.16, calculator.yearly.employeesNI)
    }
}
