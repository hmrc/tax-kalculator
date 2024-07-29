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

import uk.gov.hmrc.calculator.exception.InvalidTaxCodeException
import uk.gov.hmrc.calculator.model.Country.SCOTLAND
import uk.gov.hmrc.calculator.model.taxcodes.S0T
import uk.gov.hmrc.calculator.model.taxcodes.SBR
import uk.gov.hmrc.calculator.model.taxcodes.SD0
import uk.gov.hmrc.calculator.model.taxcodes.SD1
import uk.gov.hmrc.calculator.model.taxcodes.SD2
import uk.gov.hmrc.calculator.model.taxcodes.SD3
import uk.gov.hmrc.calculator.model.taxcodes.SKCode
import uk.gov.hmrc.calculator.model.taxcodes.SLCode
import uk.gov.hmrc.calculator.model.taxcodes.STCode
import uk.gov.hmrc.calculator.model.taxcodes.ScottishEmergencyCode
import uk.gov.hmrc.calculator.model.taxcodes.ScottishMCode
import uk.gov.hmrc.calculator.model.taxcodes.ScottishNCode
import uk.gov.hmrc.calculator.model.taxcodes.ScottishTaxCode
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class ScottishTaxCodeExtensionsTests {

    @Test
    fun `Scotland no allowance`() {
        assertTrue("S0T".toTaxCode() is S0T)
    }

    @Test
    fun `Scotland no allowance force scottish rate`() {
        assertTrue("S0T".toTaxCode(forceScottishTaxCode = true) is S0T)
    }

    @Test
    fun `English no allowance force scottish rate`() {
        assertTrue("0T".toTaxCode(forceScottishTaxCode = true) is S0T)
    }

    @Test
    fun `Welsh no allowance force scottish rate`() {
        assertTrue("C0T".toTaxCode(forceScottishTaxCode = true) is S0T)
    }

    @Test
    fun `Scotland no allowance ignore additional code`() {
        assertTrue("S0TABC".toTaxCode() is S0T)
    }

    @Test
    fun `Scotland no allowance ignore additional code force scottish rate`() {
        assertTrue("S0TABC".toTaxCode(forceScottishTaxCode = true) is S0T)
    }

    @Test
    fun `English no allowance ignore additional code force scottish rate`() {
        assertTrue("0TABC".toTaxCode(forceScottishTaxCode = true) is S0T)
    }

    @Test
    fun `Welsh no allowance ignore additional code force scottish rate`() {
        assertTrue("C0TABC".toTaxCode(forceScottishTaxCode = true) is S0T)
    }

    @Test
    fun `Scotland no allowance but with space`() {
        assertTrue("S 0T".toTaxCode() is S0T)
    }

    @Test
    fun `Scotland no allowance but with space force scottish rate`() {
        assertTrue("S 0T".toTaxCode(forceScottishTaxCode = true) is S0T)
    }

    @Test
    fun `English no allowance but with space force scottish rate`() {
        assertTrue(" 0T".toTaxCode(forceScottishTaxCode = true) is S0T)
    }

    @Test
    fun `Welsh no allowance but with space force scottish rate`() {
        assertTrue("C 0T".toTaxCode(forceScottishTaxCode = true) is S0T)
    }

    @Test
    fun `Scotland basic rate`() {
        assertTrue("SBR".toTaxCode() is SBR)
        assertTrue("SBR".toTaxCode(forceScottishTaxCode = true) is SBR)
        assertTrue("BR".toTaxCode(forceScottishTaxCode = true) is SBR)
        assertTrue("CBR".toTaxCode(forceScottishTaxCode = true) is SBR)
        val taxCode: SBR = "SBR".toTaxCode() as SBR
        assertEquals(SCOTLAND, taxCode.country)
        assertEquals(1, taxCode.taxAllAtBand)
        assertEquals(0.0, taxCode.taxFreeAmount)
    }

    @Test
    fun `Scotland basic rate ignore additional code`() {
        assertTrue("SBRABC".toTaxCode() is SBR)
        assertTrue("SBRABC".toTaxCode(forceScottishTaxCode = true) is SBR)
        assertTrue("BRABC".toTaxCode(forceScottishTaxCode = true) is SBR)
        assertTrue("CBRABC".toTaxCode(forceScottishTaxCode = true) is SBR)
        val taxCode: SBR = "SBRABC".toTaxCode() as SBR
        assertEquals(SCOTLAND, taxCode.country)
        assertEquals(1, taxCode.taxAllAtBand)
        assertEquals(0.0, taxCode.taxFreeAmount)
    }

    @Test
    fun `Scotland intermediate`() {
        assertTrue("SD0".toTaxCode() is SD0)
        assertTrue("SD0".toTaxCode(forceScottishTaxCode = true) is SD0)
        assertTrue("D0".toTaxCode(forceScottishTaxCode = true) is SD0)
        assertTrue("CD0".toTaxCode(forceScottishTaxCode = true) is SD0)
        val taxCode: SD0 = "SD0".toTaxCode() as SD0
        assertEquals(SCOTLAND, taxCode.country)
        assertEquals(2, taxCode.taxAllAtBand)
        assertEquals(0.0, taxCode.taxFreeAmount)
    }

    @Test
    fun `Scotland intermediate ignore additional code`() {
        assertTrue("SD0ABC".toTaxCode() is SD0)
        assertTrue("SD0ABC".toTaxCode(forceScottishTaxCode = true) is SD0)
        assertTrue("D0ABC".toTaxCode(forceScottishTaxCode = true) is SD0)
        assertTrue("CD0ABC".toTaxCode(forceScottishTaxCode = true) is SD0)
        val taxCode: SD0 = "SD0ABC".toTaxCode() as SD0
        assertEquals(SCOTLAND, taxCode.country)
        assertEquals(2, taxCode.taxAllAtBand)
        assertEquals(0.0, taxCode.taxFreeAmount)
    }

    @Test
    fun `Scotland higher`() {
        assertTrue("SD1".toTaxCode() is SD1)
        assertTrue("SD1".toTaxCode(forceScottishTaxCode = true) is SD1)
        assertTrue("D1".toTaxCode(forceScottishTaxCode = true) is SD1)
        assertTrue("CD1".toTaxCode(forceScottishTaxCode = true) is SD1)
        val taxCode: SD1 = "SD1".toTaxCode() as SD1
        assertEquals(SCOTLAND, taxCode.country)
        assertEquals(3, taxCode.taxAllAtBand)
        assertEquals(0.0, taxCode.taxFreeAmount)
    }

    @Test
    fun `Scotland higher ignore additional code`() {
        assertTrue("SD1ABC".toTaxCode() is SD1)
        assertTrue("SD1ABC".toTaxCode(forceScottishTaxCode = true) is SD1)
        assertTrue("D1ABC".toTaxCode(forceScottishTaxCode = true) is SD1)
        assertTrue("CD1ABC".toTaxCode(forceScottishTaxCode = true) is SD1)
        val taxCode: SD1 = "SD1ABC".toTaxCode() as SD1
        assertEquals(SCOTLAND, taxCode.country)
        assertEquals(3, taxCode.taxAllAtBand)
        assertEquals(0.0, taxCode.taxFreeAmount)
    }

    @Test
    fun `Scotland advanced`() {
        assertTrue("SD2".toTaxCode() is SD2)
        assertTrue("SD2".toTaxCode(forceScottishTaxCode = true) is SD2)
        val taxCode: SD2 = "SD2".toTaxCode() as SD2
        assertEquals(SCOTLAND, taxCode.country)
        assertEquals(4, taxCode.taxAllAtBand)
        assertEquals(0.0, taxCode.taxFreeAmount)

        assertFailsWith<InvalidTaxCodeException> {
            "D2".toTaxCode(forceScottishTaxCode = true)
            "CD2".toTaxCode(forceScottishTaxCode = true)
        }
    }

    @Test
    fun `Scotland advanced ignore additional code`() {
        assertTrue("SD2ABC".toTaxCode() is SD2)
        assertTrue("SD2ABC".toTaxCode(forceScottishTaxCode = true) is SD2)
        val taxCode: SD2 = "SD2ABC".toTaxCode() as SD2
        assertEquals(SCOTLAND, taxCode.country)
        assertEquals(4, taxCode.taxAllAtBand)
        assertEquals(0.0, taxCode.taxFreeAmount)

        assertFailsWith<InvalidTaxCodeException> {
            "D2ABC".toTaxCode(forceScottishTaxCode = true)
            "CD2ABC".toTaxCode(forceScottishTaxCode = true)
        }
    }

    @Test
    fun `Scotland top`() {
        assertTrue("SD3".toTaxCode() is SD3)
        assertTrue("SD3".toTaxCode(forceScottishTaxCode = true) is SD3)
        val taxCode: SD3 = "SD3".toTaxCode() as SD3
        assertEquals(SCOTLAND, taxCode.country)
        assertEquals(5, taxCode.taxAllAtBand)
        assertEquals(0.0, taxCode.taxFreeAmount)

        assertFailsWith<InvalidTaxCodeException> {
            "D3".toTaxCode(forceScottishTaxCode = true)
            "CD3".toTaxCode(forceScottishTaxCode = true)
        }
    }

    @Test
    fun `Scotland top ignore additional code`() {
        assertTrue("SD3ABC".toTaxCode() is SD3)
        assertTrue("SD3ABC".toTaxCode(forceScottishTaxCode = true) is SD3)
        val taxCode: SD3 = "SD3ABC".toTaxCode() as SD3
        assertEquals(SCOTLAND, taxCode.country)
        assertEquals(5, taxCode.taxAllAtBand)
        assertEquals(0.0, taxCode.taxFreeAmount)

        assertFailsWith<InvalidTaxCodeException> {
            "D3ABC".toTaxCode(forceScottishTaxCode = true)
            "CD3ABC".toTaxCode(forceScottishTaxCode = true)
        }
    }

    @Test
    fun `Scotland STCode`() {
        assertTrue("S150T".toTaxCode() is STCode)
        assertTrue("S150T".toTaxCode(forceScottishTaxCode = true) is STCode)
        assertTrue("150T".toTaxCode(forceScottishTaxCode = true) is STCode)
        assertTrue("C150T".toTaxCode(forceScottishTaxCode = true) is STCode)
        assertEquals(1500.0, "S150T".toTaxCode().taxFreeAmount)
        assertEquals(SCOTLAND, "S150T".toTaxCode().country)
    }

    @Test
    fun `Scotland SLCode`() {
        assertTrue("S1250L".toTaxCode() is SLCode)
        assertTrue("S1250L".toTaxCode(forceScottishTaxCode = true) is SLCode)
        assertTrue("1250L".toTaxCode(forceScottishTaxCode = true) is SLCode)
        assertTrue("C1250L".toTaxCode(forceScottishTaxCode = true) is SLCode)
        assertEquals(12509.0, "S1250L".toTaxCode().taxFreeAmount)
        assertEquals(SCOTLAND, "S1250L".toTaxCode().country)
    }

    @Test
    fun `Scotland invalid`() {
        assertFailsWith<InvalidTaxCodeException> {
            "SD4".toTaxCode()
            "SD4".toTaxCode(forceScottishTaxCode = true)
            "D4".toTaxCode(forceScottishTaxCode = true)
            "CD4".toTaxCode(forceScottishTaxCode = true)
        }
    }

    @Test
    fun `Scottish Emergency X`() {
        assertTrue("S1250X".toTaxCode() is ScottishEmergencyCode)
        assertTrue("S1250X".toTaxCode(forceScottishTaxCode = true) is ScottishEmergencyCode)
        assertTrue("1250X".toTaxCode(forceScottishTaxCode = true) is ScottishEmergencyCode)
        assertTrue("C1250X".toTaxCode(forceScottishTaxCode = true) is ScottishEmergencyCode)
        assertEquals(12509.0, "S1250X".toTaxCode().taxFreeAmount)
        assertEquals(SCOTLAND, "S1250X".toTaxCode().country)
    }

    @Test
    fun `Scottish Emergency M1`() {
        assertTrue("S1250M1".toTaxCode() is ScottishEmergencyCode)
        assertTrue("S1250M1".toTaxCode(forceScottishTaxCode = true) is ScottishEmergencyCode)
        assertTrue("1250M1".toTaxCode(forceScottishTaxCode = true) is ScottishEmergencyCode)
        assertTrue("C1250M1".toTaxCode(forceScottishTaxCode = true) is ScottishEmergencyCode)
        assertEquals(12509.0, "S1250M1".toTaxCode().taxFreeAmount)
        assertEquals(SCOTLAND, "S1250M1".toTaxCode().country)
    }

    @Test
    fun `Scottish Emergency W1`() {
        assertTrue("S1250W1".toTaxCode() is ScottishEmergencyCode)
        assertTrue("S1250W1".toTaxCode(forceScottishTaxCode = true) is ScottishEmergencyCode)
        assertTrue("1250W1".toTaxCode(forceScottishTaxCode = true) is ScottishEmergencyCode)
        assertTrue("C1250W1".toTaxCode(forceScottishTaxCode = true) is ScottishEmergencyCode)
        assertEquals(12509.0, "S1250W1".toTaxCode().taxFreeAmount)
        assertEquals(SCOTLAND, "S1250W1".toTaxCode().country)
    }

    @Test
    fun `Scottish Emergency 1150W1`() {
        assertTrue("S1150W1".toTaxCode() is ScottishEmergencyCode)
        assertTrue("S1150W1".toTaxCode(forceScottishTaxCode = true) is ScottishEmergencyCode)
        assertTrue("1150W1".toTaxCode(forceScottishTaxCode = true) is ScottishEmergencyCode)
        assertTrue("C1150W1".toTaxCode(forceScottishTaxCode = true) is ScottishEmergencyCode)
        assertEquals(11509.0, "S1150W1".toTaxCode().taxFreeAmount)
        assertEquals(SCOTLAND, "S1150W1".toTaxCode().country)
    }

    @Test
    fun `Scottish Emergency 1150M1`() {
        assertTrue("S1150M1".toTaxCode() is ScottishEmergencyCode)
        assertTrue("S1150M1".toTaxCode(forceScottishTaxCode = true) is ScottishEmergencyCode)
        assertTrue("1150M1".toTaxCode(forceScottishTaxCode = true) is ScottishEmergencyCode)
        assertTrue("C1150M1".toTaxCode(forceScottishTaxCode = true) is ScottishEmergencyCode)
        assertEquals(11509.0, "S1150M1".toTaxCode().taxFreeAmount)
        assertEquals(SCOTLAND, "S1150M1".toTaxCode().country)
    }

    @Test
    fun `Scottish Emergency 1150M1 with space`() {
        val taxCode = "S1150 M1".toTaxCode()
        assertTrue("S1150 M1".toTaxCode(forceScottishTaxCode = true) is ScottishEmergencyCode)
        assertTrue("1150 M1".toTaxCode(forceScottishTaxCode = true) is ScottishEmergencyCode)
        assertTrue("C1150 M1".toTaxCode(forceScottishTaxCode = true) is ScottishEmergencyCode)
        assertTrue(taxCode is ScottishEmergencyCode)
        assertEquals(11509.0, taxCode.taxFreeAmount)
        assertEquals(SCOTLAND, taxCode.country)
    }

    @Test
    fun `Scottish Emergency 1150M1 lowercase`() {
        val taxCode = "s1150m1".toTaxCode()
        assertTrue("s1150m1".toTaxCode(forceScottishTaxCode = true) is ScottishEmergencyCode)
        assertTrue("s1150m1".toTaxCode(forceScottishTaxCode = true) is ScottishEmergencyCode)
        assertTrue("s1150m1".toTaxCode(forceScottishTaxCode = true) is ScottishEmergencyCode)
        assertTrue(taxCode is ScottishEmergencyCode)
        assertEquals(11509.0, taxCode.taxFreeAmount)
        assertEquals(SCOTLAND, taxCode.country)
    }

    @Test
    fun `Scottish 1250M`() {
        assertTrue("S1250M".toTaxCode() is ScottishMCode)
        assertTrue("S1250M".toTaxCode(forceScottishTaxCode = true) is ScottishMCode)
        assertTrue("1250M".toTaxCode(forceScottishTaxCode = true) is ScottishMCode)
        assertTrue("C1250M".toTaxCode(forceScottishTaxCode = true) is ScottishMCode)
        val taxCode: ScottishMCode = "S1250M".toTaxCode() as ScottishMCode

        assertEquals(12509.0, taxCode.taxFreeAmount)
        assertEquals(SCOTLAND, taxCode.country)
        assertEquals(true, taxCode.increasedTaxAllowance)
    }

    @Test
    fun `Scottish 1250N`() {
        assertTrue("S1250N".toTaxCode() is ScottishNCode)
        assertTrue("S1250N".toTaxCode(forceScottishTaxCode = true) is ScottishNCode)
        assertTrue("1250N".toTaxCode(forceScottishTaxCode = true) is ScottishNCode)
        assertTrue("C1250N".toTaxCode(forceScottishTaxCode = true) is ScottishNCode)
        val taxCode: ScottishNCode = "S1250N".toTaxCode() as ScottishNCode

        assertEquals(12509.0, taxCode.taxFreeAmount)
        assertEquals(SCOTLAND, taxCode.country)
        assertEquals(false, taxCode.increasedTaxAllowance)
    }

    @Test
    fun `Scottish SK100`() {
        assertTrue("SK100".toTaxCode() is SKCode)
        assertTrue("SK100".toTaxCode(forceScottishTaxCode = true) is SKCode)
        assertTrue("K100".toTaxCode(forceScottishTaxCode = true) is SKCode)
        assertTrue("CK100".toTaxCode(forceScottishTaxCode = true) is SKCode)
        val taxCode: SKCode = "SK100".toTaxCode() as SKCode
        assertEquals(0.0, taxCode.taxFreeAmount)
        assertEquals(SCOTLAND, taxCode.country)
        assertEquals(1000.0, taxCode.amountToAddToWages)
    }

    @Test
    fun `Scottish invalid emergency K code`() {
        assertFailsWith<InvalidTaxCodeException> {
            "SK100F".toTaxCode()
            "SK100F".toTaxCode(forceScottishTaxCode = true)
            "K100F".toTaxCode(forceScottishTaxCode = true)
            "CK100F".toTaxCode(forceScottishTaxCode = true)
        }
    }

    @Test
    fun `Scottish K code with invalid char`() {
        assertFailsWith<InvalidTaxCodeException> {
            "SK100-".toTaxCode()
            "SK100-".toTaxCode(forceScottishTaxCode = true)
            "K100-".toTaxCode(forceScottishTaxCode = true)
            "CK100-".toTaxCode(forceScottishTaxCode = true)
        }
    }

    @Test
    fun `Scotland S1212LX`() {
        val taxCode = "S1212LX".toTaxCode()
        assertTrue("S1212LX".toTaxCode(forceScottishTaxCode = true) is ScottishEmergencyCode)
        assertTrue("1212LX".toTaxCode(forceScottishTaxCode = true) is ScottishEmergencyCode)
        assertTrue("C1212LX".toTaxCode(forceScottishTaxCode = true) is ScottishEmergencyCode)
        assertTrue(taxCode is ScottishTaxCode)
        assertTrue(taxCode is ScottishEmergencyCode)
        assertEquals(12129.0, taxCode.taxFreeAmount)
        assertEquals(SCOTLAND, taxCode.country)
    }

    @Test
    fun `Scotland S141TX`() {
        val taxCode = "S141TX".toTaxCode()
        assertTrue("S141TX".toTaxCode(forceScottishTaxCode = true) is ScottishEmergencyCode)
        assertTrue("141TX".toTaxCode(forceScottishTaxCode = true) is ScottishEmergencyCode)
        assertTrue("C141TX".toTaxCode(forceScottishTaxCode = true) is ScottishEmergencyCode)
        assertTrue(taxCode is ScottishTaxCode)
        assertTrue(taxCode is ScottishEmergencyCode)
        assertEquals(1419.0, taxCode.taxFreeAmount)
        assertEquals(SCOTLAND, taxCode.country)
    }
}
