package model.taxcodes

import Calculator
import model.PayPeriod
import kotlin.test.Test
import kotlin.test.assertEquals

class TCodesTests {

    @Test
    fun `100T England 40k`() {
        val calculator = Calculator("100T", 40000.0, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(8098.200000000001, calculator.yearly.taxToPay)
        assertEquals(3764.16, calculator.yearly.employeesNI)
        assertEquals(4328.784000000001, calculator.yearly.employersNI)
    }

    @Test
    fun `100T England 138499`() {
        val calculator = Calculator("100T", 138499.0, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(6734.139999999999, calculator.yearly.employeesNI)
        assertEquals(17921.646, calculator.yearly.employersNI)
        assertEquals(47497.80, calculator.yearly.taxToPay)
    }

    @Test
    fun `100T England 138501`() {
        val calculator = Calculator("100T", 138501.0, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(6734.18, calculator.yearly.employeesNI)
        assertEquals(17921.922000000002, calculator.yearly.employersNI)
        assertEquals(47498.649999999994, calculator.yearly.taxToPay)
    }

    @Test
    fun `C100T England 138499`() {
        val calculator = Calculator("C100T", 138499.0, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(6734.139999999999, calculator.yearly.employeesNI)
        assertEquals(17921.646, calculator.yearly.employersNI)
        assertEquals(47497.80, calculator.yearly.taxToPay)
    }

    @Test
    fun `C100T England 138501`() {
        val calculator = Calculator("C100T", 138501.0, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(6734.18, calculator.yearly.employeesNI)
        assertEquals(17921.922000000002, calculator.yearly.employersNI)
        assertEquals(47498.649999999994, calculator.yearly.taxToPay)
    }

    @Test
    fun `S100T Scotland 138501`() {
        val calculator = Calculator("S100T", 138501.00, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(6734.18, calculator.yearly.employeesNI)
        assertEquals(17921.922000000002, calculator.yearly.employersNI)
        assertEquals(50042.82, calculator.yearly.taxToPay)
    }

    @Test
    fun `S100T Scotland 138499`() {
        val calculator = Calculator("S100T", 138499.00, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(6734.139999999999, calculator.yearly.employeesNI)
        assertEquals(17921.646, calculator.yearly.employersNI)
        assertEquals(50041.95, calculator.yearly.taxToPay)
    }
}