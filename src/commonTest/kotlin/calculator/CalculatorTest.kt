package calculator

import Calculator
import model.PayPeriod.HOURLY
import kotlin.test.Test
import kotlin.test.assertFailsWith

class CalculatorTest {

    @Test
    fun `Error When Hours=0 And PayPeriod Is HOURLY`() {
//        thrown.expect(IllegalArgumentException::class.java)

        assertFailsWith<IllegalArgumentException> {
            Calculator("1250L", 20.0, payPeriod = HOURLY, hoursPerWeek = 0.0, taxYear = 2019)
        }
//        thrown.expectMessage("Hours must be greater than 0 for PayPeriod = HOURLY")
    }
}