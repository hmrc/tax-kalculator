package calculator

import Calculator
import model.PayPeriod
import kotlin.test.Test
import kotlin.test.assertEquals

class EmergencyTaxCodeTests {

    @Test
    fun `Wales C1250X 20k`() {
        val taxCode = "C1250X"
        val wages = 20000.00
        val response = Calculator(taxCode, wages, payPeriod = PayPeriod.YEARLY).run()
        assertEquals(1498.2, response.yearly.taxToPay)
        assertEquals(1568.784, response.yearly.employersNI)
        assertEquals(1364.1599999999999, response.yearly.employeesNI)
        assertEquals(2862.3599999999997, response.yearly.totalDeductions)
    }

    @Test
    fun `Wales C1250M1 20k`() {
        val taxCode = "C1250M1"
        val wages = 20000.00
        val response = Calculator(taxCode, wages, payPeriod = PayPeriod.YEARLY).run()
        assertEquals(1498.2, response.yearly.taxToPay)
        assertEquals(1568.784, response.yearly.employersNI)
        assertEquals(1364.1599999999999, response.yearly.employeesNI)
        assertEquals(2862.3599999999997, response.yearly.totalDeductions)
    }

    @Test
    fun `Wales C1250W1 20k`() {
        val taxCode = "C1250W1"
        val wages = 20000.00
        val response = Calculator(taxCode, wages, payPeriod = PayPeriod.YEARLY).run()
        assertEquals(1498.2, response.yearly.taxToPay)
        assertEquals(1568.784, response.yearly.employersNI)
        assertEquals(1364.1599999999999, response.yearly.employeesNI)
        assertEquals(2862.3599999999997, response.yearly.totalDeductions)
    }

    @Test
    fun `Scotland S1250X 20k`() {
        val taxCode = "S1250X"
        val wages = 20000.00
        val response = Calculator(taxCode, wages, payPeriod = PayPeriod.YEARLY).run()
        assertEquals(1477.8000000000002, response.yearly.taxToPay)
        assertEquals(1568.784, response.yearly.employersNI)
        assertEquals(1364.1599999999999, response.yearly.employeesNI)
        assertEquals(2841.96, response.yearly.totalDeductions)
    }

    @Test
    fun `Scotland S1250M1 20k`() {
        val taxCode = "S1250M1"
        val wages = 20000.00
        val response = Calculator(taxCode, wages, payPeriod = PayPeriod.YEARLY).run()
        assertEquals(1477.8000000000002, response.yearly.taxToPay)
        assertEquals(1568.784, response.yearly.employersNI)
        assertEquals(1364.1599999999999, response.yearly.employeesNI)
        assertEquals(2841.96, response.yearly.totalDeductions)
    }

    @Test
    fun `Scotland S1250W1 20k`() {
        val taxCode = "S1250W1"
        val wages = 20000.00
        val response = Calculator(taxCode, wages, payPeriod = PayPeriod.YEARLY).run()
        assertEquals(1477.8000000000002, response.yearly.taxToPay)
        assertEquals(1568.784, response.yearly.employersNI)
        assertEquals(1364.1599999999999, response.yearly.employeesNI)
        assertEquals(2841.96, response.yearly.totalDeductions)
    }


    @Test
    fun `England 1250X 20k`() {
        val taxCode = "1250X"
        val wages = 20000.00
        val response = Calculator(taxCode, wages, payPeriod = PayPeriod.YEARLY).run()
        assertEquals(1498.2, response.yearly.taxToPay)
        assertEquals(1568.784, response.yearly.employersNI)
        assertEquals(1364.1599999999999, response.yearly.employeesNI)
        assertEquals(2862.3599999999997, response.yearly.totalDeductions)
    }

    @Test
    fun `England 1250M1 20k`() {
        val taxCode = "1250M1"
        val wages = 20000.00
        val response = Calculator(taxCode, wages, payPeriod = PayPeriod.YEARLY).run()
        assertEquals(1498.2, response.yearly.taxToPay)
        assertEquals(1568.784, response.yearly.employersNI)
        assertEquals(1364.1599999999999, response.yearly.employeesNI)
        assertEquals(2862.3599999999997, response.yearly.totalDeductions)
    }

    @Test
    fun `England 1250W1 20k`() {
        val taxCode = "1250W1"
        val wages = 20000.00
        val response = Calculator(taxCode, wages, payPeriod = PayPeriod.YEARLY).run()
        assertEquals(1498.2, response.yearly.taxToPay)
        assertEquals(1568.784, response.yearly.employersNI)
        assertEquals(1364.1599999999999, response.yearly.employeesNI)
        assertEquals(2862.3599999999997, response.yearly.totalDeductions)
    }
}