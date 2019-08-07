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
package calculator.utils

import calculator.model.Country
import calculator.model.bands.TaxBands
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ExtensionFunctionTest {

    @Test
    fun `Scotland Type When Prefix Is S`() {
        assertEquals(Country.SCOTLAND, "S1250L".country())
    }

    @Test
    fun `Wales TypeWhen Prefix Is C`() {
        assertEquals(Country.WALES, "C1250L".country())
    }

    @Test
    fun `England Type When No Prefix`() {
        assertEquals(Country.ENGLAND, "1250L".country())
    }

    @Test
    fun `NT Country None`() {
        assertEquals(Country.NONE, "NT".country())
    }

    @Test
    fun `Other Tax Codes default to English`() {
        assertEquals(Country.ENGLAND, "XX".country())
    }

    @Test
    fun `Invalid Wage For Which Band Contains`() {
        val exception = assertFailsWith<ConfigurationError> {
            TaxBands(Country.ENGLAND, 2019).bands.whichBandContains(-1.0)
        }
        assertEquals("-1.0 are not in any band!", exception.message)
    }

    @Test
    fun `Valid Wage For Which Band Contains`() {
        assertEquals(1, TaxBands(Country.ENGLAND, 2019).bands.whichBandContains(20000.0))
    }
}
