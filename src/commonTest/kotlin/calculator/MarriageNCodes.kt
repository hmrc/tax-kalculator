package calculator

import Calculator
import model.PayPeriod
import kotlin.test.assertEquals
import kotlin.test.Test

class MarriageNCodes {

    @Test
    fun `Wales N Code 20k`() {
        val calculator = Calculator("C1250N", 20000.0, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(1498.2, calculator.yearly.taxToPay)
        assertEquals(1568.784, calculator.yearly.employersNI)
        assertEquals(1364.1599999999999, calculator.yearly.employeesNI)
    }

    @Test
    fun `Scotland N Code 20k`() {
        val calculator = Calculator("S1250N", 20000.0, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(1477.8000000000002, calculator.yearly.taxToPay)
        assertEquals(1568.784, calculator.yearly.employersNI)
        assertEquals(1364.1599999999999, calculator.yearly.employeesNI)
    }

    @Test
    fun `England N Code 20k`() {
        val calculator = Calculator("1250N", 20000.0, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(1498.2, calculator.yearly.taxToPay)
        assertEquals(1568.784, calculator.yearly.employersNI)
        assertEquals(1364.1599999999999, calculator.yearly.employeesNI)
    }
}
