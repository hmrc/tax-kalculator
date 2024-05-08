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
import uk.gov.hmrc.calculator.model.Country.ENGLAND
import uk.gov.hmrc.calculator.model.taxcodes.BR
import uk.gov.hmrc.calculator.model.taxcodes.D0
import uk.gov.hmrc.calculator.model.taxcodes.D1
import uk.gov.hmrc.calculator.model.taxcodes.EmergencyTaxCode
import uk.gov.hmrc.calculator.model.taxcodes.EnglishEmergencyCode
import uk.gov.hmrc.calculator.model.taxcodes.EnglishMCode
import uk.gov.hmrc.calculator.model.taxcodes.EnglishNCode
import uk.gov.hmrc.calculator.model.taxcodes.EnglishTaxCode
import uk.gov.hmrc.calculator.model.taxcodes.KCode
import uk.gov.hmrc.calculator.model.taxcodes.LCode
import uk.gov.hmrc.calculator.model.taxcodes.TCode
import uk.gov.hmrc.calculator.model.taxcodes.ZeroT
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class EnglishTaxCodeExtensionsTests {
    @Test
    fun `English no allowance`() {
        assertTrue("0T".toTaxCode() is ZeroT)
    }

    @Test
    fun `English no allowance ignore additional code`() {
        assertTrue("0TABC".toTaxCode() is ZeroT)
    }

    @Test
    fun `English basic rate`() {
        assertTrue("BR".toTaxCode() is BR)
        val taxCode: BR = "BR".toTaxCode() as BR
        assertEquals(ENGLAND, taxCode.country)
        assertEquals(0, taxCode.taxAllAtBand)
        assertEquals(0.0, taxCode.taxFreeAmount)
    }

    @Test
    fun `English basic rate ignore additional code`() {
        assertTrue("BRABC".toTaxCode() is BR)
        val taxCode: BR = "BRABC".toTaxCode() as BR
        assertEquals(ENGLAND, taxCode.country)
        assertEquals(0, taxCode.taxAllAtBand)
        assertEquals(0.0, taxCode.taxFreeAmount)
    }

    @Test
    fun `English higher`() {
        assertTrue("D0".toTaxCode() is D0)
        val taxCode: D0 = "D0".toTaxCode() as D0
        assertEquals(ENGLAND, taxCode.country)
        assertEquals(1, taxCode.taxAllAtBand)
        assertEquals(0.0, taxCode.taxFreeAmount)
    }

    @Test
    fun `English higher ignore additional code`() {
        assertTrue("D0ABC".toTaxCode() is D0)
        val taxCode: D0 = "D0ABC".toTaxCode() as D0
        assertEquals(ENGLAND, taxCode.country)
        assertEquals(1, taxCode.taxAllAtBand)
        assertEquals(0.0, taxCode.taxFreeAmount)
    }

    @Test
    fun `English additional`() {
        assertTrue("D1".toTaxCode() is D1)
        val taxCode: D1 = "D1".toTaxCode() as D1
        assertEquals(ENGLAND, taxCode.country)
        assertEquals(2, taxCode.taxAllAtBand)
        assertEquals(0.0, taxCode.taxFreeAmount)
    }

    @Test
    fun `English additional ignore additional code`() {
        assertTrue("D1ABC".toTaxCode() is D1)
        val taxCode: D1 = "D1ABC".toTaxCode() as D1
        assertEquals(ENGLAND, taxCode.country)
        assertEquals(2, taxCode.taxAllAtBand)
        assertEquals(0.0, taxCode.taxFreeAmount)
    }

    @Test
    fun `English TCode`() {
        assertTrue("150T".toTaxCode() is TCode)
        assertEquals(1500.0, "150T".toTaxCode().taxFreeAmount)
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
    fun `England K100`() {
        assertTrue("K100".toTaxCode() is KCode)
        val taxCode: KCode = "K100".toTaxCode() as KCode
        assertEquals(0.0, taxCode.taxFreeAmount)
        assertEquals(ENGLAND, taxCode.country)
        assertEquals(1000.0, taxCode.amountToAddToWages)
    }
    @Test
    fun `England K100X`() {
        assertTrue("K100X".toTaxCode() is KCode)
        val taxCode: KCode = "K100X".toTaxCode() as KCode
        assertEquals(0.0, taxCode.taxFreeAmount)
        assertEquals(ENGLAND, taxCode.country)
        assertEquals(1000.0, taxCode.amountToAddToWages)
    }

    @Test
    fun `England K100 lowercase with spaces`() {
        val taxCode = "k 100 ".toTaxCode()
        assertTrue(taxCode is KCode)
        assertEquals(0.0, taxCode.taxFreeAmount)
        assertEquals(ENGLAND, taxCode.country)
        assertEquals(1000.0, taxCode.amountToAddToWages)
    }

    @Test
    fun `England 1212LX`() {
        val taxCode = "1212LX".toTaxCode()
        assertTrue(taxCode is EnglishTaxCode)
        assertTrue(taxCode is EmergencyTaxCode)
        assertEquals(12129.0, taxCode.taxFreeAmount)
        assertEquals(ENGLAND, taxCode.country)
    }

    @Test
    fun `England 141TX`() {
        val taxCode = "141TX".toTaxCode()
        assertTrue(taxCode is EnglishTaxCode)
        assertTrue(taxCode is EmergencyTaxCode)
        assertEquals(1419.0, taxCode.taxFreeAmount)
        assertEquals(ENGLAND, taxCode.country)
    }

    @Test
    fun `English Invalid`() {
        assertFailsWith<InvalidTaxCodeException> {
            "D2".toTaxCode()
        }
    }
}
