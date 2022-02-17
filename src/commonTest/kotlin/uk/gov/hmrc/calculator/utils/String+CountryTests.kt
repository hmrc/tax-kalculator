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
package uk.gov.hmrc.calculator.utils

import kotlin.test.Test
import kotlin.test.assertEquals
import uk.gov.hmrc.calculator.model.Country.ENGLAND
import uk.gov.hmrc.calculator.model.Country.NONE
import uk.gov.hmrc.calculator.model.Country.SCOTLAND
import uk.gov.hmrc.calculator.model.Country.WALES

class StringCountryTests {

    @Test
    fun `Scotland Type When Prefix Is S`() {
        assertEquals(SCOTLAND, "S1250L".toCountry())
    }

    @Test
    fun `Wales Type When Prefix Is C`() {
        assertEquals(WALES, "C1250L".toCountry())
    }

    @Test
    fun `England Type When No Prefix`() {
        assertEquals(ENGLAND, "1250L".toCountry())
    }

    @Test
    fun `NT Country None`() {
        assertEquals(NONE, "NT".toCountry())
    }

    @Test
    fun `Other Tax Codes default to English`() {
        assertEquals(ENGLAND, "XX".toCountry())
    }
}
