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
package uk.gov.hmrc.calculator

import kotlin.test.Test
import kotlin.test.assertEquals

internal class CalculatorUtilsTests {

    @Test
    fun `WHEN 2020 THEN default tax code returned`() {
        val result = CalculatorUtils.defaultTaxCode(2020)

        assertEquals(
            2020,
            result.year
        )
        assertEquals(
            "1250L",
            result.taxCode
        )
    }

    @Test
    fun `WHEN 2021 THEN default tax code returned`() {
        val result = CalculatorUtils.defaultTaxCode(2021)

        assertEquals(
            2021,
            result.year
        )
        assertEquals(
            "1257L",
            result.taxCode
        )
    }

    @Test
    fun `WHEN 2022 THEN default tax code returned`() {
        val result = CalculatorUtils.defaultTaxCode(2022)

        assertEquals(
            2022,
            result.year
        )
        assertEquals(
            "1257L",
            result.taxCode
        )
    }

    /*
    Expected to fail when the tax changes and the default tax code differs from the previous year.
     */
    @Test
    fun `WHEN no year supplied THEN default tax code returned`() {
        val result = CalculatorUtils.defaultTaxCode()

        assertEquals(
            2026,
            result.year
        )
        assertEquals(
            "1257L",
            result.taxCode
        )
    }
}
