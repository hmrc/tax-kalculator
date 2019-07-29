package model.bands

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class EmployeeNIBandsTest {

    @Test
    fun invalidYear() {
        val exception = assertFailsWith<IllegalArgumentException> { EmployeeNIBands(2017) }
        assertEquals(exception.message, "Unsupported Year")
    }

    @Test
    fun `Employee NI 2019 England`() {
        val band = EmployeeNIBands(2019).bands[2]
        assertEquals(0.12, band.percentageAsDecimal)
        assertEquals(false, band.inBand(1000.0))
        assertEquals(true, band.inBand(10000.0))
    }

    @Test
    fun `Employee NI 2019 Scotland`() {
        val band = EmployeeNIBands(2019).bands[2]
        assertEquals(0.12, band.percentageAsDecimal)
        assertEquals(false, band.inBand(1000.0))
        assertEquals(true, band.inBand(10000.0))
    }

    @Test
    fun `Employee NI 2019 Wales`() {
        val band = EmployeeNIBands(2019).bands[2]
        assertEquals(0.12, band.percentageAsDecimal)
        assertEquals(false, band.inBand(1000.0))
        assertEquals(true, band.inBand(10000.0))
    }

    @Test
    fun `Employee NI 2019 Wales Massive Wages`() {
        val band = EmployeeNIBands(2019).bands[3]
        assertEquals(0.02, band.percentageAsDecimal)
        assertEquals(false, band.inBand(1000.0))
        assertEquals(true, band.inBand(100000.0))
    }
}
