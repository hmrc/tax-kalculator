/*
 * Copyright 2022 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.gov.hmrc.calculator.utils.taxcode

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue
import uk.gov.hmrc.calculator.exception.InvalidTaxCodeException
import uk.gov.hmrc.calculator.model.Country.SCOTLAND
import uk.gov.hmrc.calculator.model.taxcodes.S0T
import uk.gov.hmrc.calculator.model.taxcodes.SBR
import uk.gov.hmrc.calculator.model.taxcodes.SD0
import uk.gov.hmrc.calculator.model.taxcodes.SD1
import uk.gov.hmrc.calculator.model.taxcodes.SD2
import uk.gov.hmrc.calculator.model.taxcodes.SKCode
import uk.gov.hmrc.calculator.model.taxcodes.SLCode
import uk.gov.hmrc.calculator.model.taxcodes.STCode
import uk.gov.hmrc.calculator.model.taxcodes.ScottishEmergencyCode
import uk.gov.hmrc.calculator.model.taxcodes.ScottishMCode
import uk.gov.hmrc.calculator.model.taxcodes.ScottishNCode
import uk.gov.hmrc.calculator.model.taxcodes.ScottishTaxCode

class StringScottishTaxCodeTests {

    @Test
    fun `Scotland no allowance`() {
        assertTrue("S0T".toTaxCode() is S0T)
    }

    @Test
    fun `Scotland no allowance but with space`() {
        assertTrue("S 0T".toTaxCode() is S0T)
    }

    @Test
    fun `Scotland basic rate`() {
        assertTrue("SBR".toTaxCode() is SBR)
        val taxCode: SBR = "SBR".toTaxCode() as SBR
        assertEquals(SCOTLAND, taxCode.country)
        assertEquals(2, taxCode.taxAllAtBand)
        assertEquals(0.0, taxCode.taxFreeAmount)
    }

    @Test
    fun `Scotland intermediate`() {
        assertTrue("SD0".toTaxCode() is SD0)
        val taxCode: SD0 = "SD0".toTaxCode() as SD0
        assertEquals(SCOTLAND, taxCode.country)
        assertEquals(3, taxCode.taxAllAtBand)
        assertEquals(0.0, taxCode.taxFreeAmount)
    }

    @Test
    fun `Scotland higher`() {
        assertTrue("SD1".toTaxCode() is SD1)
        val taxCode: SD1 = "SD1".toTaxCode() as SD1
        assertEquals(SCOTLAND, taxCode.country)
        assertEquals(4, taxCode.taxAllAtBand)
        assertEquals(0.0, taxCode.taxFreeAmount)
    }

    @Test
    fun `Scotland top`() {
        assertTrue("SD2".toTaxCode() is SD2)
        val taxCode: SD2 = "SD2".toTaxCode() as SD2
        assertEquals(SCOTLAND, taxCode.country)
        assertEquals(5, taxCode.taxAllAtBand)
        assertEquals(0.0, taxCode.taxFreeAmount)
    }

    @Test
    fun `Scotland STCode`() {
        assertTrue("S150T".toTaxCode() is STCode)
        assertEquals(1509.0, "S150T".toTaxCode().taxFreeAmount)
        assertEquals(SCOTLAND, "S150T".toTaxCode().country)
    }

    @Test
    fun `Scotland SLCode`() {
        assertTrue("S1250L".toTaxCode() is SLCode)
        assertEquals(12509.0, "S1250L".toTaxCode().taxFreeAmount)
        assertEquals(SCOTLAND, "S1250L".toTaxCode().country)
    }

    @Test
    fun `Scotland invalid`() {
        assertFailsWith<InvalidTaxCodeException> {
            "SD3".toTaxCode()
        }
    }

    @Test
    fun `Scottish Emergency X`() {
        assertTrue("S1250X".toTaxCode() is ScottishEmergencyCode)
        assertEquals(12509.0, "S1250X".toTaxCode().taxFreeAmount)
        assertEquals(SCOTLAND, "S1250X".toTaxCode().country)
    }

    @Test
    fun `Scottish Emergency M1`() {
        assertTrue("S1250M1".toTaxCode() is ScottishEmergencyCode)
        assertEquals(12509.0, "S1250M1".toTaxCode().taxFreeAmount)
        assertEquals(SCOTLAND, "S1250M1".toTaxCode().country)
    }

    @Test
    fun `Scottish Emergency W1`() {
        assertTrue("S1250W1".toTaxCode() is ScottishEmergencyCode)
        assertEquals(12509.0, "S1250W1".toTaxCode().taxFreeAmount)
        assertEquals(SCOTLAND, "S1250W1".toTaxCode().country)
    }

    @Test
    fun `Scottish Emergency 1150W1`() {
        assertTrue("S1150W1".toTaxCode() is ScottishEmergencyCode)
        assertEquals(11509.0, "S1150W1".toTaxCode().taxFreeAmount)
        assertEquals(SCOTLAND, "S1150W1".toTaxCode().country)
    }

    @Test
    fun `Scottish Emergency 1150M1`() {
        assertTrue("S1150M1".toTaxCode() is ScottishEmergencyCode)
        assertEquals(11509.0, "S1150M1".toTaxCode().taxFreeAmount)
        assertEquals(SCOTLAND, "S1150M1".toTaxCode().country)
    }

    @Test
    fun `Scottish Emergency 1150M1 with space`() {
        val taxCode = "S1150 M1".toTaxCode()
        assertTrue(taxCode is ScottishEmergencyCode)
        assertEquals(11509.0, taxCode.taxFreeAmount)
        assertEquals(SCOTLAND, taxCode.country)
    }

    @Test
    fun `Scottish Emergency 1150M1 lowercase`() {
        val taxCode = "s1150m1".toTaxCode()
        assertTrue(taxCode is ScottishEmergencyCode)
        assertEquals(11509.0, taxCode.taxFreeAmount)
        assertEquals(SCOTLAND, taxCode.country)
    }

    @Test
    fun `Scottish 1250M`() {
        assertTrue("S1250M".toTaxCode() is ScottishMCode)
        val taxCode: ScottishMCode = "S1250M".toTaxCode() as ScottishMCode

        assertEquals(12509.0, taxCode.taxFreeAmount)
        assertEquals(SCOTLAND, taxCode.country)
        assertEquals(true, taxCode.increasedTaxAllowance)
    }

    @Test
    fun `Scottish 1250N`() {
        assertTrue("S1250N".toTaxCode() is ScottishNCode)
        val taxCode: ScottishNCode = "S1250N".toTaxCode() as ScottishNCode

        assertEquals(12509.0, taxCode.taxFreeAmount)
        assertEquals(SCOTLAND, taxCode.country)
        assertEquals(false, taxCode.increasedTaxAllowance)
    }

    @Test
    fun `Scottish SK100`() {
        assertTrue("SK100".toTaxCode() is SKCode)
        val taxCode: SKCode = "SK100".toTaxCode() as SKCode
        assertEquals(0.0, taxCode.taxFreeAmount)
        assertEquals(SCOTLAND, taxCode.country)
        assertEquals(1009.0, taxCode.amountToAddToWages)
    }

    @Test
    fun `Scottish invalid emergency K code`() {
        assertFailsWith<InvalidTaxCodeException> {
            "SK100F".toTaxCode()
        }
    }

    @Test
    fun `Scottish K code with invalid char`() {
        assertFailsWith<InvalidTaxCodeException> {
            "SK100-".toTaxCode()
        }
    }

    @Test
    fun `Scotland S1212LX`() {
        val taxCode = "S1212LX".toTaxCode()
        assertTrue(taxCode is ScottishTaxCode)
        assertTrue(taxCode is ScottishEmergencyCode)
        assertEquals(12129.0, taxCode.taxFreeAmount)
        assertEquals(SCOTLAND, taxCode.country)
    }

    @Test
    fun `Scotland S141TX`() {
        val taxCode = "S141TX".toTaxCode()
        assertTrue(taxCode is ScottishTaxCode)
        assertTrue(taxCode is ScottishEmergencyCode)
        assertEquals(1419.0, taxCode.taxFreeAmount)
        assertEquals(SCOTLAND, taxCode.country)
    }
}
