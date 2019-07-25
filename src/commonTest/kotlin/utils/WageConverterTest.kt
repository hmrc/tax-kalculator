package utils

import model.PayPeriod
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class WageConverterTest {
    @Test
    fun `Week Invalid When Converting From Year`() {
        assertFailsWith<IllegalArgumentException> {
            100.0.convertAmountFromYearlyToPayPeriod(PayPeriod.HOURLY)
        }
    }

    @Test
    fun `Convert monthly to yearly`() {
        assertEquals(12000.0, 1000.0.convertWageToYearly(PayPeriod.MONTHLY))
    }

    @Test
    fun `Convert FOUR_WEEKLY to yearly`() {
        assertEquals(13000.0, 1000.0.convertWageToYearly(PayPeriod.FOUR_WEEKLY))
    }

    @Test
    fun `Convert WEEKLY to yearly`() {
        assertEquals(52000.0, 1000.0.convertWageToYearly(PayPeriod.WEEKLY))
    }

    @Test
    fun `Convert HOURLY to yearly`() {
        assertEquals(5200.0, 10.0.convertWageToYearly(PayPeriod.HOURLY, 10.0))
    }

    @Test
    fun `Convert YEARLY to yearly`() {
        assertEquals(5200.0, 5200.0.convertWageToYearly(PayPeriod.YEARLY))
    }

}