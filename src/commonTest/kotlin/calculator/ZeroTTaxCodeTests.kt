package calculator

import Calculator
import model.PayPeriod
import kotlin.test.Test
import kotlin.test.assertEquals

class ZeroTTaxCodeTests {

    @Test
    fun `0T England 40k`() {
        val calculator = Calculator("0T", 40000.0, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(3764.16, calculator.yearly.employeesNI)
        assertEquals(8501.800000000001, calculator.yearly.taxToPay)
        assertEquals(4328.784000000001, calculator.yearly.employersNI)
    }

    @Test
    fun `0T England 37K`() {
        val calculator = Calculator("0T", 37000.0, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(3404.16, calculator.yearly.employeesNI)
        assertEquals(3914.784, calculator.yearly.employersNI)
        assertEquals(7400.00, calculator.yearly.taxToPay)
    }

    @Test
    fun `0T England 38K`() {
        val calculator = Calculator("0T", 38000.0, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(3524.16, calculator.yearly.employeesNI)
        assertEquals(4052.7840000000006, calculator.yearly.employersNI)
        assertEquals(7701.8000000000010, calculator.yearly.taxToPay)
    }

    @Test
    fun `0T England 137K`() {
        val calculator = Calculator("0T", 137000.0, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(6704.16, calculator.yearly.employeesNI)
        assertEquals(17714.784000000003, calculator.yearly.employersNI)
        assertEquals(47301.8, calculator.yearly.taxToPay)
    }

    @Test
    fun `0T England 138K`() {
        val calculator = Calculator("0T", 138000.0, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(6724.16, calculator.yearly.employeesNI)
        assertEquals(17852.784000000003, calculator.yearly.employersNI)
        assertEquals(47727.25, calculator.yearly.taxToPay)
    }

    @Test
    fun `0T England 300k`() {
        val calculator = Calculator("0T", 300000.0, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(9964.16, calculator.yearly.employeesNI)
        assertEquals(120627.25, calculator.yearly.taxToPay)
    }

    @Test
    fun `C0T England 40k`() {
        val calculator = Calculator("C0T", 40000.0, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(3764.16, calculator.yearly.employeesNI)
        assertEquals(8501.800000000001, calculator.yearly.taxToPay)
        assertEquals(4328.784000000001, calculator.yearly.employersNI)
    }

    @Test
    fun `C0T England 37K`() {
        val calculator = Calculator("C0T", 37000.0, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(3404.16, calculator.yearly.employeesNI)
        assertEquals(3914.784, calculator.yearly.employersNI)
        assertEquals(7400.00, calculator.yearly.taxToPay)
    }

    @Test
    fun `C0T England 38K`() {
        val calculator = Calculator("C0T", 38000.0, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(3524.16, calculator.yearly.employeesNI)
        assertEquals(4052.7840000000006, calculator.yearly.employersNI)
        assertEquals(7701.800000000001, calculator.yearly.taxToPay)
    }

    @Test
    fun `C0T England 137K`() {
        val calculator = Calculator("C0T", 137000.0, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(6704.16, calculator.yearly.employeesNI)
        assertEquals(17714.784000000003, calculator.yearly.employersNI)
        assertEquals(47301.8, calculator.yearly.taxToPay)
    }

    @Test
    fun `C0T England 138K`() {
        val calculator = Calculator("C0T", 138000.0, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(6724.16, calculator.yearly.employeesNI)
        assertEquals(17852.784000000003, calculator.yearly.employersNI)
        assertEquals(47727.25, calculator.yearly.taxToPay)
    }

    @Test
    fun `C0T England 300k`() {
        val calculator = Calculator("C0T", 300000.0, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(9964.16, calculator.yearly.employeesNI)
        assertEquals(120627.25, calculator.yearly.taxToPay)
    }


    @Test
    fun `S0T Scotland 30k`() {
        val calculator = Calculator("S0T", 30000.0, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(2564.16, calculator.yearly.employeesNI)
        assertEquals(6155.25, calculator.yearly.taxToPay)
    }

    @Test
    fun `S0T Scotland 31k`() {
        val calculator = Calculator("S0T", 31000.0, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(2684.16, calculator.yearly.employeesNI)
        assertEquals(6381.05, calculator.yearly.taxToPay)
    }

    @Test
    fun `S0T Scotland 137000`() {
        val calculator = Calculator("S0T", 137000.0, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(6704.16, calculator.yearly.employeesNI)
        assertEquals(17714.784000000003, calculator.yearly.employersNI)
        assertEquals(49841.05, calculator.yearly.taxToPay)
    }

    @Test
    fun `S0T Scotland 138000`() {
        val calculator = Calculator("S0T", 138000.0, payPeriod = PayPeriod.YEARLY, taxYear = 2019).run()
        assertEquals(6724.16, calculator.yearly.employeesNI)
        assertEquals(17852.784000000003, calculator.yearly.employersNI)
        assertEquals(50276.5, calculator.yearly.taxToPay)
    }
}