package utils

import model.PayPeriod
import org.junit.Rule
import kotlin.test.Test
import org.junit.rules.ExpectedException

class WageConverterTest {
    @Rule
    @JvmField
    var thrown: ExpectedException = ExpectedException.none()


    @Test
    fun `Week Invalid When Converting From Year`() {
        thrown.expect(IllegalArgumentException::class.java)
        thrown.expectMessage("Amounts are not displayed hour by hour")
        WageConverter().convertAmountFromYearlyToPayPeriod(PayPeriod.HOURLY, 100.0)
    }
}