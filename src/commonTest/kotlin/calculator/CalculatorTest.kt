package calculator

import Calculator
import model.PayPeriod.HOURLY
import org.junit.Rule
import kotlin.test.Test
import org.junit.rules.ExpectedException

class CalculatorTest {

    @Rule
    @JvmField
    var thrown: ExpectedException = ExpectedException.none()

    @Test
    fun `Error When Hours=0 And PayPeriod Is HOURLY`() {
        thrown.expect(IllegalArgumentException::class.java)
        thrown.expectMessage("Hours must be greater than 0 for PayPeriod = HOURLY")
        Calculator("1250L", 20.0, payPeriod = HOURLY, hoursPerWeek = 0.0, taxYear = 2019)
    }
}