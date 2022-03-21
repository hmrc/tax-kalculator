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
package uk.gov.hmrc.calculator.model.bands

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import uk.gov.hmrc.calculator.exception.InvalidTaxYearException
import uk.gov.hmrc.calculator.model.Country.ENGLAND
import uk.gov.hmrc.calculator.model.Country.SCOTLAND
import uk.gov.hmrc.calculator.model.Country.WALES
import uk.gov.hmrc.calculator.model.TaxYear

class TaxBandsTests {

    @Test
    fun `GIVEN invalid year WHEN get bands THEN fail with exception`() {
        val exception = assertFailsWith<InvalidTaxYearException> {
            TaxBands.getBands(
                TaxYear.fromInt(2017),
                ENGLAND
            )
        }
        assertEquals(exception.message, "2017")
    }

    @Test
    fun `GIVEN year is 2020 WHEN get bands for Scotland THEN bands are as expected`() {
        val taxBands = TaxBands.getBands(TaxYear.TWENTY_TWENTY, SCOTLAND)

        assertEquals(0.00, taxBands[0].lower)
        assertEquals(2076.00, taxBands[0].upper)
        assertEquals(0.19, taxBands[0].percentageAsDecimal)

        assertEquals(2076.00, taxBands[1].lower)
        assertEquals(12649.00, taxBands[1].upper)
        assertEquals(0.20, taxBands[1].percentageAsDecimal)

        assertEquals(12649.00, taxBands[2].lower)
        assertEquals(30921.00, taxBands[2].upper)
        assertEquals(0.21, taxBands[2].percentageAsDecimal)

        assertEquals(30921.00, taxBands[3].lower)
        assertEquals(150000.00, taxBands[3].upper)
        assertEquals(0.41, taxBands[3].percentageAsDecimal)

        assertEquals(150000.00, taxBands[4].lower)
        assertEquals(-1.0, taxBands[4].upper)
        assertEquals(0.46, taxBands[4].percentageAsDecimal)
    }

    @Test
    fun `GIVEN year is 2020 WHEN get bands for ENGLAND THEN bands are as expected`() {
        val taxBands = TaxBands.getBands(TaxYear.TWENTY_TWENTY, ENGLAND)

        assertEquals(0.00, taxBands[0].lower)
        assertEquals(37500.0, taxBands[0].upper)
        assertEquals(0.2, taxBands[0].percentageAsDecimal)

        assertEquals(37500.0, taxBands[1].lower)
        assertEquals(150000.0, taxBands[1].upper)
        assertEquals(0.4, taxBands[1].percentageAsDecimal)

        assertEquals(150000.0, taxBands[2].lower)
        assertEquals(-1.0, taxBands[2].upper)
        assertEquals(0.45, taxBands[2].percentageAsDecimal)
    }

    @Test
    fun `GIVEN year is 2020 WHEN get bands for WALES THEN bands are as expected`() {
        val taxBands = TaxBands.getBands(TaxYear.TWENTY_TWENTY, WALES)

        assertEquals(0.00, taxBands[0].lower)
        assertEquals(37500.0, taxBands[0].upper)
        assertEquals(0.2, taxBands[0].percentageAsDecimal)

        assertEquals(37500.0, taxBands[1].lower)
        assertEquals(150000.0, taxBands[1].upper)
        assertEquals(0.4, taxBands[1].percentageAsDecimal)

        assertEquals(150000.0, taxBands[2].lower)
        assertEquals(-1.0, taxBands[2].upper)
        assertEquals(0.45, taxBands[2].percentageAsDecimal)
    }

    @Test
    fun `GIVEN year is 2021 WHEN get bands for Scotland THEN bands are as expected`() {
        val taxBands = TaxBands.getBands(TaxYear.TWENTY_TWENTY_ONE, SCOTLAND)

        assertEquals(0.0, taxBands[0].lower)
        assertEquals(2097.00, taxBands[0].upper)
        assertEquals(0.19, taxBands[0].percentageAsDecimal)

        assertEquals(2097.00, taxBands[1].lower)
        assertEquals(12726.00, taxBands[1].upper)
        assertEquals(0.20, taxBands[1].percentageAsDecimal)

        assertEquals(12726.00, taxBands[2].lower)
        assertEquals(31092.00, taxBands[2].upper)
        assertEquals(0.21, taxBands[2].percentageAsDecimal)

        assertEquals(31092.00, taxBands[3].lower)
        assertEquals(150000.00, taxBands[3].upper)
        assertEquals(0.41, taxBands[3].percentageAsDecimal)

        assertEquals(150000.00, taxBands[4].lower)
        assertEquals(-1.00, taxBands[4].upper)
        assertEquals(0.46, taxBands[4].percentageAsDecimal)
    }

    @Test
    fun `GIVEN year is 2021 WHEN get bands for England THEN bands are as expected`() {
        val taxBands = TaxBands.getBands(TaxYear.TWENTY_TWENTY_ONE, ENGLAND)

        assertEquals(0.0, taxBands[0].lower)
        assertEquals(37700.00, taxBands[0].upper)
        assertEquals(0.2, taxBands[0].percentageAsDecimal)

        assertEquals(37700.00, taxBands[1].lower)
        assertEquals(150000.0, taxBands[1].upper)
        assertEquals(0.4, taxBands[1].percentageAsDecimal)

        assertEquals(150000.0, taxBands[2].lower)
        assertEquals(-1.0, taxBands[2].upper)
        assertEquals(0.45, taxBands[2].percentageAsDecimal)
    }

    @Test
    fun `GIVEN year is 2021 WHEN get bands for Wales THEN bands are as expected`() {
        val taxBands = TaxBands.getBands(TaxYear.TWENTY_TWENTY_ONE, WALES)

        assertEquals(0.0, taxBands[0].lower)
        assertEquals(37700.00, taxBands[0].upper)
        assertEquals(0.2, taxBands[0].percentageAsDecimal)

        assertEquals(37700.00, taxBands[1].lower)
        assertEquals(150000.0, taxBands[1].upper)
        assertEquals(0.4, taxBands[1].percentageAsDecimal)

        assertEquals(150000.0, taxBands[2].lower)
        assertEquals(-1.0, taxBands[2].upper)
        assertEquals(0.45, taxBands[2].percentageAsDecimal)
    }

    @Test
    fun `GIVEN year is 2022 WHEN get bands for Scotland THEN bands are as expected`() {
        val taxBands = TaxBands.getBands(TaxYear.TWENTY_TWENTY_TWO, SCOTLAND)

        assertEquals(0.0, taxBands[0].lower)
        assertEquals(2162.00, taxBands[0].upper)
        assertEquals(0.19, taxBands[0].percentageAsDecimal)

        assertEquals(2162.00, taxBands[1].lower)
        assertEquals(13118.00, taxBands[1].upper)
        assertEquals(0.20, taxBands[1].percentageAsDecimal)

        assertEquals(13118.00, taxBands[2].lower)
        assertEquals(31092.00, taxBands[2].upper)
        assertEquals(0.21, taxBands[2].percentageAsDecimal)

        assertEquals(31092.00, taxBands[3].lower)
        assertEquals(150000.00, taxBands[3].upper)
        assertEquals(0.41, taxBands[3].percentageAsDecimal)

        assertEquals(150000.00, taxBands[4].lower)
        assertEquals(-1.00, taxBands[4].upper)
        assertEquals(0.46, taxBands[4].percentageAsDecimal)
    }

    @Test
    fun `GIVEN year is 2022 WHEN get bands for England THEN bands are as expected`() {
        val taxBands = TaxBands.getBands(TaxYear.TWENTY_TWENTY_TWO, ENGLAND)

        assertEquals(0.0, taxBands[0].lower)
        assertEquals(37700.00, taxBands[0].upper)
        assertEquals(0.2, taxBands[0].percentageAsDecimal)

        assertEquals(37700.00, taxBands[1].lower)
        assertEquals(150000.0, taxBands[1].upper)
        assertEquals(0.4, taxBands[1].percentageAsDecimal)

        assertEquals(150000.0, taxBands[2].lower)
        assertEquals(-1.0, taxBands[2].upper)
        assertEquals(0.45, taxBands[2].percentageAsDecimal)
    }

    @Test
    fun `GIVEN year is 2022 WHEN get bands for Wales THEN bands are as expected`() {
        val taxBands = TaxBands.getBands(TaxYear.TWENTY_TWENTY_TWO, WALES)

        assertEquals(0.0, taxBands[0].lower)
        assertEquals(37700.00, taxBands[0].upper)
        assertEquals(0.2, taxBands[0].percentageAsDecimal)

        assertEquals(37700.00, taxBands[1].lower)
        assertEquals(150000.0, taxBands[1].upper)
        assertEquals(0.4, taxBands[1].percentageAsDecimal)

        assertEquals(150000.0, taxBands[2].lower)
        assertEquals(-1.0, taxBands[2].upper)
        assertEquals(0.45, taxBands[2].percentageAsDecimal)
    }
}
