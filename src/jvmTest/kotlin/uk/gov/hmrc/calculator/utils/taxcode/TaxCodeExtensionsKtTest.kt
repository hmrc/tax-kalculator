/*
 * Copyright 2024 HM Revenue & Customs
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

import org.junit.jupiter.api.Assertions.assertNull
import uk.gov.hmrc.calculator.utils.clarification.Clarification
import kotlin.test.Test
import kotlin.test.assertEquals

class TaxCodeExtensionsKtTest {

    @Test
    fun `GIVEN is scottish tax code AND userPaysScottishTax true WHEN getTaxCodeClarification THEN return SCOTTISH_INCOME_APPLIED`() {
        val result = getTaxCodeClarification("S1257L", userPaysScottishTax = true)
        assertEquals(Clarification.SCOTTISH_INCOME_APPLIED, result)
    }

    @Test
    fun `GIVEN is scottish tax code AND userPaysScottishTax false WHEN getTaxCodeClarification THEN return SCOTTISH_CODE_BUT_OTHER_RATE`() {
        val result = getTaxCodeClarification("S1257L", userPaysScottishTax = false)
        assertEquals(Clarification.SCOTTISH_CODE_BUT_OTHER_RATE, result)
    }

    @Test
    fun `GIVEN is not scottish tax code AND userPaysScottishTax true WHEN getTaxCodeClarification THEN return NON_SCOTTISH_CODE_BUT_SCOTTISH_RATE`() {
        val result = getTaxCodeClarification("1257L", userPaysScottishTax = true)
        assertEquals(Clarification.NON_SCOTTISH_CODE_BUT_SCOTTISH_RATE, result)
    }

    @Test
    fun `GIVEN is not scottish tax code AND userPaysScottishTax false WHEN getTaxCodeClarification THEN return null`() {
        val result = getTaxCodeClarification("1257L", userPaysScottishTax = false)
        assertNull(result)
    }

    @Test
    fun `GIVEN is K code AND userPaysScottishTax false WHEN getKCodeClarification THEN return K_CODE`() {
        val result = getKCodeClarification("K1000", userPaysScottishTaxCode = false)
        assertEquals(Clarification.K_CODE, result)
    }

    @Test
    fun `GIVEN is K code AND userPaysScottishTax true WHEN getKCodeClarification THEN return SK_CODE`() {
        val result = getKCodeClarification("K1000", userPaysScottishTaxCode = true)
        assertEquals(Clarification.SK_CODE, result)
    }

    @Test
    fun `GIVEN is CK code AND userPaysScottishTax false WHEN getKCodeClarification THEN return CK_CODE`() {
        val result = getKCodeClarification("CK1000", userPaysScottishTaxCode = false)
        assertEquals(Clarification.CK_CODE, result)
    }

    @Test
    fun `GIVEN is CK code AND userPaysScottishTax true WHEN getKCodeClarification THEN return SK_CODE`() {
        val result = getKCodeClarification("CK1000", userPaysScottishTaxCode = true)
        assertEquals(Clarification.SK_CODE, result)
    }

    @Test
    fun `GIVEN is SK code AND userPaysScottishTax false WHEN getKCodeClarification THEN return SK_CODE`() {
        val result = getKCodeClarification("SK1000", userPaysScottishTaxCode = false)
        assertEquals(Clarification.SK_CODE, result)
    }

    @Test
    fun `GIVEN is SK code AND userPaysScottishTax true WHEN getKCodeClarification THEN return SK_CODE`() {
        val result = getKCodeClarification("SK1000", userPaysScottishTaxCode = true)
        assertEquals(Clarification.SK_CODE, result)
    }

    @Test
    fun `GIVEN is none K code AND userPaysScottishTax false WHEN getKCodeClarification THEN return null`() {
        val result = getKCodeClarification("1000L", userPaysScottishTaxCode = false)
        assertNull(result)
    }

    @Test
    fun `GIVEN is none K code AND userPaysScottishTax true WHEN getKCodeClarification THEN return null`() {
        val result = getKCodeClarification("1000L", userPaysScottishTaxCode = true)
        assertNull(result)
    }
}
