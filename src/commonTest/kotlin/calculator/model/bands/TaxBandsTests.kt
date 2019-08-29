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
package calculator.model.bands

import calculator.model.Country.ENGLAND
import calculator.model.Country.SCOTLAND
import calculator.model.Country.WALES
import calculator.utils.UnsupportedTaxYear
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class TaxBandsTests {

    @Test
    fun invalidYear() {
        val exception = assertFailsWith<UnsupportedTaxYear> { TaxBands(ENGLAND, 2017) }
        assertEquals(exception.message, "2017")
    }

    @Test
    fun bandsForEngland2019() {
        val taxBand = TaxBands(ENGLAND, 2019).bands[1]
        assertEquals(50000.00, taxBand.upper)
        assertEquals(12509.00, taxBand.lower)
        assertEquals(0.20, taxBand.percentageAsDecimal)
        assertEquals(false, taxBand.inBand(12509.00))
        assertEquals(false, taxBand.inBand(12508.00))
        assertEquals(true, taxBand.inBand(12510.00))
        assertEquals(true, taxBand.inBand(50000.00))
        assertEquals(true, taxBand.inBand(49999.00))
        assertEquals(false, taxBand.inBand(500001.00))
    }

    @Test
    fun bandsForWales2019() {
        val taxBand = TaxBands(WALES, 2019).bands[1]
        assertEquals(50000.00, taxBand.upper)
        assertEquals(12509.00, taxBand.lower)
        assertEquals(0.20, taxBand.percentageAsDecimal)
        assertEquals(false, taxBand.inBand(12509.00))
        assertEquals(false, taxBand.inBand(12508.00))
        assertEquals(true, taxBand.inBand(12510.00))
        assertEquals(true, taxBand.inBand(50000.00))
        assertEquals(true, taxBand.inBand(49999.00))
        assertEquals(false, taxBand.inBand(500001.00))
    }

    @Test
    fun bandsForScotland2019() {
        val taxBand = TaxBands(SCOTLAND, 2019).bands[1]
        assertEquals(14549.00, taxBand.upper)
        assertEquals(12509.00, taxBand.lower)
        assertEquals(0.19, taxBand.percentageAsDecimal)

        assertEquals(false, taxBand.inBand(12509.00))
        assertEquals(false, taxBand.inBand(12508.00))
        assertEquals(true, taxBand.inBand(12510.00))
        assertEquals(true, taxBand.inBand(14549.00))
        assertEquals(true, taxBand.inBand(14548.00))
        assertEquals(false, taxBand.inBand(14550.00))
    }

    @Test
    fun bandsForWales2020() {
        val taxBand = TaxBands(WALES, 2020).bands[0]
        assertEquals(20000.00, taxBand.upper)
        assertEquals(0.00, taxBand.lower)
        assertEquals(0.10, taxBand.percentageAsDecimal)
    }

    @Test
    fun bandsForScotland2020() {
        val taxBand = TaxBands(SCOTLAND, 2020).bands[0]
        assertEquals(21000.00, taxBand.upper)
        assertEquals(0.00, taxBand.lower)
        assertEquals(0.15, taxBand.percentageAsDecimal)
    }

    @Test
    fun bandsForEngland2020() {
        val taxBand = TaxBands(ENGLAND, 2020).bands[0]
        assertEquals(22000.00, taxBand.upper)
        assertEquals(0.00, taxBand.lower)
        assertEquals(0.20, taxBand.percentageAsDecimal)
    }
}
