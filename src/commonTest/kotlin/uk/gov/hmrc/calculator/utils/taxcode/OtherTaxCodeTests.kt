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
import uk.gov.hmrc.calculator.model.Country.NONE
import uk.gov.hmrc.calculator.model.taxcodes.NoTaxTaxCode
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class OtherTaxCodeTests {

    @Test
    fun `NT tax code`() {
        val taxCode = "NT".toTaxCode()
        assertTrue(taxCode is NoTaxTaxCode)
        assertEquals(0.0, taxCode.taxFreeAmount)
        assertEquals(NONE, taxCode.country)
    }

    @Test
    fun `Lowercase NT tax code`() {
        val taxCode = "nt".toTaxCode()
        assertTrue(taxCode is NoTaxTaxCode)
        assertEquals(0.0, taxCode.taxFreeAmount)
        assertEquals(NONE, taxCode.country)
    }

    @Test
    fun `Empty Invalid`() {
        assertFailsWith<InvalidTaxCodeException> {
            "".toTaxCode()
        }
    }

    @Test
    fun `Blank Invalid`() {
        assertFailsWith<InvalidTaxCodeException> {
            "    ".toTaxCode()
        }
    }
}
