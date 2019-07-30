package calculator

import Calculator
import model.PayPeriod
import kotlin.test.Test
import kotlin.test.assertEquals

class GeneralTaxCodeTests {

    @Test
    fun `NT Yearly 20k`() {
        val taxCode = "NT"
        val wages = 20000.00
        val response = Calculator(taxCode, wages, payPeriod = PayPeriod.YEARLY).run()
        assertEquals(0.0, response.yearly.taxToPay)
        assertEquals(1568.784, response.yearly.employersNI)
        assertEquals(1364.1599999999999, response.yearly.employeesNI)
        assertEquals(1364.1599999999999, response.yearly.totalDeductions)
    }
}