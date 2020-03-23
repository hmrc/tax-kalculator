/*
 * Copyright 2020 HM Revenue & Customs
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
package uk.gov.hmrc.calculator.model.bands

import com.soywiz.klock.DateTime
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import uk.gov.hmrc.calculator.exception.InvalidTaxYearException
import uk.gov.hmrc.calculator.model.Country.ENGLAND
import uk.gov.hmrc.calculator.model.Country.SCOTLAND
import uk.gov.hmrc.calculator.model.Country.WALES

class TaxBandsTests {

    @Test
    fun invalidYear() {
        val exception = assertFailsWith<InvalidTaxYearException> {
            TaxBands(
                ENGLAND,
                2017
            )
        }
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
    fun bandsForScotland2020Before11thMay() {
        val todayDate = DateTime(2020, 5, 1)
        val taxBand = TaxBands(SCOTLAND, 2020, todayDate).bands[1]
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
    fun bandsForScotland2020After11thMay() {
        val todayDate = DateTime(2020, 5, 13)
        val taxBand = TaxBands(SCOTLAND, 2020, todayDate).bands[1]
        assertEquals(14585.00, taxBand.upper)
        assertEquals(12500.00, taxBand.lower)
        assertEquals(0.19, taxBand.percentageAsDecimal)

        assertEquals(false, taxBand.inBand(12500.00))
        assertEquals(false, taxBand.inBand(12409.00))
        assertEquals(true, taxBand.inBand(12501.00))
        assertEquals(true, taxBand.inBand(14585.00))
        assertEquals(true, taxBand.inBand(14584.00))
        assertEquals(false, taxBand.inBand(14586.00))
    }
}
