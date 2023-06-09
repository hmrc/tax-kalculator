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
import uk.gov.hmrc.calculator.model.Country.WALES
import uk.gov.hmrc.calculator.model.taxcodes.C0T
import uk.gov.hmrc.calculator.model.taxcodes.CBR
import uk.gov.hmrc.calculator.model.taxcodes.CD0
import uk.gov.hmrc.calculator.model.taxcodes.CD1
import uk.gov.hmrc.calculator.model.taxcodes.CKCode
import uk.gov.hmrc.calculator.model.taxcodes.CLCode
import uk.gov.hmrc.calculator.model.taxcodes.CTCode
import uk.gov.hmrc.calculator.model.taxcodes.WelshEmergencyCode
import uk.gov.hmrc.calculator.model.taxcodes.WelshMCode
import uk.gov.hmrc.calculator.model.taxcodes.WelshNCode
import uk.gov.hmrc.calculator.model.taxcodes.WelshTaxCode
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class WelshTaxCodeExtensionsTests {

    @Test
    fun `Welsh no allowance`() {
        assertTrue("C0T".toTaxCode() is C0T)
    }

    @Test
    fun `Welsh no allowance ignore additional code`() {
        assertTrue("C0TABC".toTaxCode() is C0T)
    }

    @Test
    fun `Welsh basic rate`() {
        assertTrue("CBR".toTaxCode() is CBR)

        val taxCode: CBR = "CBR".toTaxCode() as CBR
        assertEquals(WALES, taxCode.country)
        assertEquals(0, taxCode.taxAllAtBand)
        assertEquals(0.0, taxCode.taxFreeAmount)
    }

    @Test
    fun `Welsh basic rate ignore additional code`() {
        assertTrue("CBRABC".toTaxCode() is CBR)

        val taxCode: CBR = "CBRABC".toTaxCode() as CBR
        assertEquals(WALES, taxCode.country)
        assertEquals(0, taxCode.taxAllAtBand)
        assertEquals(0.0, taxCode.taxFreeAmount)
    }

    @Test
    fun `Welsh higher`() {
        assertTrue("CD0".toTaxCode() is CD0)
        val taxCode: CD0 = "CD0".toTaxCode() as CD0
        assertEquals(WALES, taxCode.country)
        assertEquals(1, taxCode.taxAllAtBand)
        assertEquals(0.0, taxCode.taxFreeAmount)
    }

    @Test
    fun `Welsh higher ignore additional code`() {
        assertTrue("CD0ABC".toTaxCode() is CD0)
        val taxCode: CD0 = "CD0ABC".toTaxCode() as CD0
        assertEquals(WALES, taxCode.country)
        assertEquals(1, taxCode.taxAllAtBand)
        assertEquals(0.0, taxCode.taxFreeAmount)
    }

    @Test
    fun `Welsh additional`() {
        assertTrue("CD1".toTaxCode() is CD1)
        val taxCode: CD1 = "CD1".toTaxCode() as CD1
        assertEquals(WALES, taxCode.country)
        assertEquals(2, taxCode.taxAllAtBand)
        assertEquals(0.0, taxCode.taxFreeAmount)
    }

    @Test
    fun `Welsh additional ignore additional code`() {
        assertTrue("CD1ABC".toTaxCode() is CD1)
        val taxCode: CD1 = "CD1ABC".toTaxCode() as CD1
        assertEquals(WALES, taxCode.country)
        assertEquals(2, taxCode.taxAllAtBand)
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
        assertFailsWith<InvalidTaxCodeException> {
            "CD2".toTaxCode()
        }
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
    fun `Wales 1250M`() {
        assertTrue("C1250M".toTaxCode() is WelshMCode)
        val taxCode: WelshMCode = "C1250M".toTaxCode() as WelshMCode

        assertEquals(12509.0, taxCode.taxFreeAmount)
        assertEquals(WALES, taxCode.country)
        assertEquals(true, taxCode.increasedTaxAllowance)
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
    fun `Wales C1212LX`() {
        val taxCode = "C1212LX".toTaxCode()
        assertTrue(taxCode is WelshTaxCode)
        assertTrue(taxCode is WelshEmergencyCode)
        assertEquals(12129.0, taxCode.taxFreeAmount)
        assertEquals(WALES, taxCode.country)
    }

    @Test
    fun `Wales C141TX`() {
        val taxCode = "C141TX".toTaxCode()
        assertTrue(taxCode is WelshTaxCode)
        assertTrue(taxCode is WelshEmergencyCode)
        assertEquals(1419.0, taxCode.taxFreeAmount)
        assertEquals(WALES, taxCode.country)
    }
}
