package utils

import model.Country.*
import model.bands.TaxBands
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ExtensionFunctionTest {

    @Test
    fun `Scotland Type When Prefix Is S`() {
        assertEquals(SCOTLAND, "S1250L".country())
    }

    @Test
    fun `Wales TypeWhen Prefix Is C`() {
        assertEquals(WALES, "C1250L".country())
    }

    @Test
    fun `England Type When No Prefix`() {
        assertEquals(ENGLAND, "1250L".country())
    }

    @Test
    fun `NT Country None`() {
        assertEquals(NONE, "NT".country())
    }

    @Test
    fun `Other Tax Codes default to English`() {
        assertEquals(ENGLAND, "XX".country())
    }

    @Test
    fun `Invalid Wage For Which Band Contains`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            TaxBands(ENGLAND, 2019).bands.whichBandContains(-1.0)
        }
        assertEquals("-1.0 are not in any band!", exception.message)
    }

    @Test
    fun `Valid Wage For Which Band Contains`() {
        assertEquals(1, TaxBands(ENGLAND, 2019).bands.whichBandContains(20000.0))
    }

}
