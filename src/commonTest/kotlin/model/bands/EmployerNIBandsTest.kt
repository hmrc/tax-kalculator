package model.bands

import utils.UnsupportedTaxYear
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class EmployerNIBandsTest {

    @Test
    fun invalidYear() {
        val exception = assertFailsWith<UnsupportedTaxYear> { EmployerNIBands(2017) }
        assertEquals(exception.message, "2017")
    }

    @Test
    fun `Employee NI 2019 England`() {
        val band = EmployerNIBands(2019).bands[2]
        assertEquals(0.138, band.percentageAsDecimal)
        assertEquals(false, band.inBand(1000.0))
        assertEquals(true, band.inBand(10000.0))
    }

    @Test
    fun `Employee NI 2019 Scotland`() {
        val band = EmployerNIBands(2019).bands[2]
        assertEquals(0.138, band.percentageAsDecimal)
        assertEquals(false, band.inBand(1000.0))
        assertEquals(true, band.inBand(10000.0))
    }

    @Test
    fun `Employee NI 2019 Wales`() {
        val band = EmployerNIBands(2019).bands[2]
        assertEquals(0.138, band.percentageAsDecimal)
        assertEquals(false, band.inBand(1000.0))
        assertEquals(true, band.inBand(10000.0))
    }
}
