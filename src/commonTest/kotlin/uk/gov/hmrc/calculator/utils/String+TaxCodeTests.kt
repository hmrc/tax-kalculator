/*
 * Copyright 2019 HM Revenue & Customs
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
package uk.gov.hmrc.calculator.utils

import uk.gov.hmrc.calculator.model.Country.ENGLAND
import uk.gov.hmrc.calculator.model.Country.NONE
import uk.gov.hmrc.calculator.model.Country.SCOTLAND
import uk.gov.hmrc.calculator.model.Country.WALES
import uk.gov.hmrc.calculator.model.taxcodes.BR
import uk.gov.hmrc.calculator.model.taxcodes.C0T
import uk.gov.hmrc.calculator.model.taxcodes.CBR
import uk.gov.hmrc.calculator.model.taxcodes.CD0
import uk.gov.hmrc.calculator.model.taxcodes.CD1
import uk.gov.hmrc.calculator.model.taxcodes.CKCode
import uk.gov.hmrc.calculator.model.taxcodes.CLCode
import uk.gov.hmrc.calculator.model.taxcodes.CTCode
import uk.gov.hmrc.calculator.model.taxcodes.D0
import uk.gov.hmrc.calculator.model.taxcodes.D1
import uk.gov.hmrc.calculator.model.taxcodes.EnglishEmergencyCode
import uk.gov.hmrc.calculator.model.taxcodes.EnglishMCode
import uk.gov.hmrc.calculator.model.taxcodes.EnglishNCode
import uk.gov.hmrc.calculator.model.taxcodes.KCode
import uk.gov.hmrc.calculator.model.taxcodes.LCode
import uk.gov.hmrc.calculator.model.taxcodes.NoTaxTaxCode
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
import uk.gov.hmrc.calculator.model.taxcodes.TCode
import uk.gov.hmrc.calculator.model.taxcodes.WelshEmergencyCode
import uk.gov.hmrc.calculator.model.taxcodes.WelshMCode
import uk.gov.hmrc.calculator.model.taxcodes.WelshNCode
import uk.gov.hmrc.calculator.model.taxcodes.ZeroT
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class StringTaxCodeTests {

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
        assertFailsWith<InvalidTaxCode> {
            "SD3".toTaxCode()
        }
    }

    @Test
    fun `Welsh no allowance`() {
        assertTrue("C0T".toTaxCode() is C0T)
    }

    @Test
    fun `Welsh basic rate`() {
        assertTrue("CBR".toTaxCode() is CBR)

        val taxCode: CBR = "CBR".toTaxCode() as CBR
        assertEquals(WALES, taxCode.country)
        assertEquals(1, taxCode.taxAllAtBand)
        assertEquals(0.0, taxCode.taxFreeAmount)
    }

    @Test
    fun `Welsh higher`() {
        assertTrue("CD0".toTaxCode() is CD0)
        val taxCode: CD0 = "CD0".toTaxCode() as CD0
        assertEquals(WALES, taxCode.country)
        assertEquals(2, taxCode.taxAllAtBand)
        assertEquals(0.0, taxCode.taxFreeAmount)
    }

    @Test
    fun `Welsh additional`() {
        assertTrue("CD1".toTaxCode() is CD1)
        val taxCode: CD1 = "CD1".toTaxCode() as CD1
        assertEquals(WALES, taxCode.country)
        assertEquals(3, taxCode.taxAllAtBand)
        assertEquals(0.0, taxCode.taxFreeAmount)
    }

    @Test
    fun `Welsh CTCode`() {
        assertTrue("C150T".toTaxCode() is CTCode)
        assertEquals(1509.0, "C150T".toTaxCode().taxFreeAmount)
        assertEquals(WALES, "C150T".toTaxCode().country)
    }

    @Test
    fun `Welsh CLCode`() {
        assertTrue("C1250L".toTaxCode() is CLCode)
        assertEquals(12509.0, "C1250L".toTaxCode().taxFreeAmount)
        assertEquals(WALES, "C1250L".toTaxCode().country)
    }

    @Test
    fun `Welsh invalid`() {
        assertFailsWith<InvalidTaxCode> {
            "CD2".toTaxCode()
        }
    }

    @Test
    fun `English no allowance`() {
        assertTrue("0T".toTaxCode() is ZeroT)
    }

    @Test
    fun `English basic rate`() {
        assertTrue("BR".toTaxCode() is BR)
        val taxCode: BR = "BR".toTaxCode() as BR
        assertEquals(ENGLAND, taxCode.country)
        assertEquals(1, taxCode.taxAllAtBand)
        assertEquals(0.0, taxCode.taxFreeAmount)
    }

    @Test
    fun `English higher`() {
        assertTrue("D0".toTaxCode() is D0)
        val taxCode: D0 = "D0".toTaxCode() as D0
        assertEquals(ENGLAND, taxCode.country)
        assertEquals(2, taxCode.taxAllAtBand)
        assertEquals(0.0, taxCode.taxFreeAmount)
    }

    @Test
    fun `English additional`() {
        assertTrue("D1".toTaxCode() is D1)
        val taxCode: D1 = "D1".toTaxCode() as D1
        assertEquals(ENGLAND, taxCode.country)
        assertEquals(3, taxCode.taxAllAtBand)
        assertEquals(0.0, taxCode.taxFreeAmount)
    }

    @Test
    fun `English TCode`() {
        assertTrue("150T".toTaxCode() is TCode)
        assertEquals(1509.0, "150T".toTaxCode().taxFreeAmount)
        assertEquals(ENGLAND, "150T".toTaxCode().country)
    }

    @Test
    fun `English LCode`() {
        assertTrue("1250L".toTaxCode() is LCode)
        assertEquals(12509.0, "1250L".toTaxCode().taxFreeAmount)
        assertEquals(ENGLAND, "1250L".toTaxCode().country)
    }

    @Test
    fun `ENGLAND Emergency X`() {
        assertTrue("1250X".toTaxCode() is EnglishEmergencyCode)
        assertEquals(12509.0, "1250X".toTaxCode().taxFreeAmount)
        assertEquals(ENGLAND, "1250X".toTaxCode().country)
    }

    @Test
    fun `ENGLAND Emergency M1`() {
        assertTrue("1250M1".toTaxCode() is EnglishEmergencyCode)
        assertEquals(12509.0, "1250M1".toTaxCode().taxFreeAmount)
        assertEquals(ENGLAND, "1250M1".toTaxCode().country)
    }

    @Test
    fun `ENGLAND Emergency W1`() {
        assertTrue("1250W1".toTaxCode() is EnglishEmergencyCode)
        assertEquals(12509.0, "1250W1".toTaxCode().taxFreeAmount)
        assertEquals(ENGLAND, "1250W1".toTaxCode().country)
    }

    @Test
    fun `ENGLAND Emergency 1150W1`() {
        assertTrue("1150W1".toTaxCode() is EnglishEmergencyCode)
        assertEquals(11509.0, "1150W1".toTaxCode().taxFreeAmount)
        assertEquals(ENGLAND, "1150W1".toTaxCode().country)
    }

    @Test
    fun `ENGLAND Emergency 1150M1`() {
        assertTrue("1150M1".toTaxCode() is EnglishEmergencyCode)
        assertEquals(11509.0, "1150M1".toTaxCode().taxFreeAmount)
        assertEquals(ENGLAND, "1150M1".toTaxCode().country)
    }

    @Test
    fun `Wales Emergency X`() {
        assertTrue("C1250X".toTaxCode() is WelshEmergencyCode)
        assertEquals(12509.0, "C1250X".toTaxCode().taxFreeAmount)
        assertEquals(WALES, "C1250X".toTaxCode().country)
    }

    @Test
    fun `WALES Emergency M1`() {
        assertTrue("C1250M1".toTaxCode() is WelshEmergencyCode)
        assertEquals(12509.0, "C1250M1".toTaxCode().taxFreeAmount)
        assertEquals(WALES, "C1250M1".toTaxCode().country)
    }

    @Test
    fun `WALES Emergency W1`() {
        assertTrue("C1250W1".toTaxCode() is WelshEmergencyCode)
        assertEquals(12509.0, "C1250W1".toTaxCode().taxFreeAmount)
        assertEquals(WALES, "C1250W1".toTaxCode().country)
    }

    @Test
    fun `WALES Emergency 1150W1`() {
        assertTrue("C1150W1".toTaxCode() is WelshEmergencyCode)
        assertEquals(11509.0, "C1150W1".toTaxCode().taxFreeAmount)
        assertEquals(WALES, "C1150W1".toTaxCode().country)
    }

    @Test
    fun `WALES Emergency 1150M1`() {
        assertTrue("C1150M1".toTaxCode() is WelshEmergencyCode)
        assertEquals(11509.0, "C1150M1".toTaxCode().taxFreeAmount)
        assertEquals(WALES, "C1150M1".toTaxCode().country)
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
    fun `English 1250M`() {
        assertTrue("1250M".toTaxCode() is EnglishMCode)
        val taxCode: EnglishMCode = "1250M".toTaxCode() as EnglishMCode

        assertEquals(12509.0, taxCode.taxFreeAmount)
        assertEquals(ENGLAND, taxCode.country)
        assertEquals(true, taxCode.increasedTaxAllowance)
    }

    @Test
    fun `English 1250N`() {
        assertTrue("1250N".toTaxCode() is EnglishNCode)
        val taxCode: EnglishNCode = "1250N".toTaxCode() as EnglishNCode

        assertEquals(12509.0, taxCode.taxFreeAmount)
        assertEquals(ENGLAND, taxCode.country)
        assertEquals(false, taxCode.increasedTaxAllowance)
    }

    @Test
    fun `Wales 1250M`() {
        assertTrue("C1250M".toTaxCode() is WelshMCode)
        val taxCode: WelshMCode = "C1250M".toTaxCode() as WelshMCode

        assertEquals(12509.0, taxCode.taxFreeAmount)
        assertEquals(WALES, taxCode.country)
        assertEquals(true, taxCode.increasedTaxAllowance)
    }

    @Test
    fun `NT (not tax) tax code`() {
        assertTrue("NT".toTaxCode() is NoTaxTaxCode)
        val taxCode: NoTaxTaxCode = "NT".toTaxCode() as NoTaxTaxCode

        assertEquals(0.0, taxCode.taxFreeAmount)
        assertEquals(NONE, taxCode.country)
    }

    @Test
    fun `Wales 1250N`() {
        assertTrue("C1250N".toTaxCode() is WelshNCode)
        val taxCode: WelshNCode = "C1250N".toTaxCode() as WelshNCode
        assertEquals(12509.0, taxCode.taxFreeAmount)
        assertEquals(WALES, taxCode.country)
        assertEquals(false, taxCode.increasedTaxAllowance)
    }

    @Test
    fun `Wales CK100`() {
        assertTrue("CK100".toTaxCode() is CKCode)
        val taxCode: CKCode = "CK100".toTaxCode() as CKCode
        assertEquals(0.0, taxCode.taxFreeAmount)
        assertEquals(WALES, taxCode.country)
        assertEquals(1009.0, taxCode.amountToAddToWages)
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
    fun `England K100`() {
        assertTrue("K100".toTaxCode() is KCode)
        val taxCode: KCode = "K100".toTaxCode() as KCode
        assertEquals(0.0, taxCode.taxFreeAmount)
        assertEquals(ENGLAND, taxCode.country)
        assertEquals(1009.0, taxCode.amountToAddToWages)
    }

    @Test
    fun `English Invalid`() {
        assertFailsWith<InvalidTaxCode> {
            "D2".toTaxCode()
        }
    }
}