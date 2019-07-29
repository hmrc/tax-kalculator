package calculator

import Calculator
import model.PayPeriod
import kotlin.test.Test
import kotlin.test.assertEquals

class D2TaxCodesTests {


    @Test
    fun `D2 Scotland 100K`() {
        val calculator = Calculator("SD2", 100000.0, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(46000.00, calculator.yearly.taxToPay)
        assertEquals(12608.784000000001, calculator.yearly.employersNI)
        assertEquals(5964.16, calculator.yearly.employeesNI)
    }
}