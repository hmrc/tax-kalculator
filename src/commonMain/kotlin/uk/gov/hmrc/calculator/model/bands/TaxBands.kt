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

import uk.gov.hmrc.calculator.exception.InvalidTaxYearException
import uk.gov.hmrc.calculator.model.Country
import uk.gov.hmrc.calculator.model.Country.SCOTLAND
import uk.gov.hmrc.calculator.model.taxcodes.TaxCode

internal object TaxBands {

    fun getBands(taxYear: Int, country: Country) = when (taxYear) {
        2020 -> when (country) {
            SCOTLAND -> scottish2020Bands()
            else -> restOfUK2020Bands()
        }
        2021 -> when (country) {
            SCOTLAND -> scottish2021Bands()
            else -> restOfUK2021Bands()
        }
        else -> throw InvalidTaxYearException("$taxYear")
    }

    private fun scottish2020Bands() = listOf(
        TaxBand(0.00, 2076.00, 0.19),
        TaxBand(2076.00, 12649.00, 0.20),
        TaxBand(12649.00, 30921.00, 0.21),
        TaxBand(30921.00, 150000.00, 0.41),
        TaxBand(150000.0, -1.0, 0.46)
    )

    private fun scottish2021Bands() = listOf(
        TaxBand(0.00, 2097.00, 0.19),
        TaxBand(2097.00, 12726.00, 0.20),
        TaxBand(12726.00, 31092.00, 0.21),
        TaxBand(31092.00, 150000.00, 0.41),
        TaxBand(150000.00, -1.0, 0.46)
    )

    private fun restOfUK2020Bands() = listOf(
        TaxBand(0.00, 37491.00, 0.2),
        TaxBand(37491.0, 150000.00, 0.4),
        TaxBand(150000.0, -1.0, 0.45)
    )

    private fun restOfUK2021Bands() = listOf(
        TaxBand(0.0, 37700.00, 0.2),
        TaxBand(37700.00, 150000.00, 0.4),
        TaxBand(150000.0, -1.0, 0.45)
    )

    fun getAdjustedBands(taxYear: Int, taxCode: TaxCode): List<Band> {
        val taxBands = getBands(taxYear, taxCode.country).toMutableList()

        taxBands.add(0, TaxBand(0.0, taxCode.taxFreeAmount, 0.0))

        taxBands[1].lower = taxCode.taxFreeAmount
        taxBands[1].upper = taxBands[1].upper + taxCode.taxFreeAmount

        for (bandNumber in 2 until taxBands.size) {
            taxBands[bandNumber].lower = taxBands[bandNumber].lower + taxCode.taxFreeAmount
            if (taxBands[bandNumber].upper != -1.0) {
                taxBands[bandNumber].upper = taxBands[bandNumber].upper + taxCode.taxFreeAmount
            }
        }
        return taxBands
    }
}
