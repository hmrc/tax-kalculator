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

import kotlin.test.Test
import kotlin.test.assertEquals

class TaxCodeExtensionsKtTest {

    @Test
    fun `GIVEN taxCode contains BR WHEN convertTaxCodeToScottishTaxCode THEN return code begin with s`() {
        assertEquals("S1234", "BR1234".convertTaxCodeToScottishTaxCode())
    }

    @Test
    fun `GIVEN taxCode contains D WHEN convertTaxCodeToScottishTaxCode THEN return code begin with s`() {
        assertEquals("S1234", "D1234".convertTaxCodeToScottishTaxCode())
    }

    @Test
    fun `GIVEN taxCode contains K WHEN convertTaxCodeToScottishTaxCode THEN return code begin with s`() {
        assertEquals("S1234", "K1234".convertTaxCodeToScottishTaxCode())
    }

    @Test
    fun `GIVEN taxCode contains S WHEN convertTaxCodeToScottishTaxCode THEN return code begin with s`() {
        assertEquals("S1234", "S1234".convertTaxCodeToScottishTaxCode())
    }

    @Test
    fun `GIVEN taxCode contains C WHEN convertTaxCodeToScottishTaxCode THEN return code begin with s`() {
        assertEquals("S1234", "C1234".convertTaxCodeToScottishTaxCode())
    }

    @Test
    fun `GIVEN taxCode contains NT WHEN convertTaxCodeToScottishTaxCode THEN return code begin with s`() {
        assertEquals("S1234", "NT1234".convertTaxCodeToScottishTaxCode())
    }

    @Test
    fun `GIVEN taxCode contains SBR WHEN convertTaxCodeToScottishTaxCode THEN return code begin with s`() {
        assertEquals("S1234", "SBR1234".convertTaxCodeToScottishTaxCode())
    }

    @Test
    fun `GIVEN taxCode contains SD WHEN convertTaxCodeToScottishTaxCode THEN return code begin with s`() {
        assertEquals("S1234", "SD1234".convertTaxCodeToScottishTaxCode())
    }

    @Test
    fun `GIVEN taxCode contains SK WHEN convertTaxCodeToScottishTaxCode THEN return code begin with s`() {
        assertEquals("S1234", "SK1234".convertTaxCodeToScottishTaxCode())
    }

    @Test
    fun `GIVEN taxCode contains CBR WHEN convertTaxCodeToScottishTaxCode THEN return code begin with s`() {
        assertEquals("S1234", "CBR1234".convertTaxCodeToScottishTaxCode())
    }

    @Test
    fun `GIVEN taxCode contains CD WHEN convertTaxCodeToScottishTaxCode THEN return code begin with s`() {
        assertEquals("S1234", "CD1234".convertTaxCodeToScottishTaxCode())
    }

    @Test
    fun `GIVEN taxCode contains CK WHEN convertTaxCodeToScottishTaxCode THEN return code begin with s`() {
        assertEquals("S1234", "CK1234".convertTaxCodeToScottishTaxCode())
    }
}
