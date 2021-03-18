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

import uk.gov.hmrc.calculator.exception.InvalidTaxYearException
import uk.gov.hmrc.calculator.model.Country
import uk.gov.hmrc.calculator.model.Country.SCOTLAND
import uk.gov.hmrc.calculator.model.Country.WALES
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
        TaxBand(0.0, 12509.00, 0.0),
        TaxBand(12509.00, 14585.00, 0.19),
        TaxBand(14585.00, 25158.00, 0.20),
        TaxBand(25158.00, 43430.00, 0.21),
        TaxBand(43430.00, 150000.00, 0.41),
        TaxBand(150000.0, -1.0, 0.46)
    )

    private fun scottish2021Bands() = listOf(
        TaxBand(0.0, 12579.00, 0.0),
        TaxBand(12579.00, 14676.00, 0.19),
        TaxBand(14676.00, 27402.00, 0.20),
        TaxBand(27402.00, 58494.00, 0.21),
        TaxBand(58494.00, 150000.00, 0.41),
        TaxBand(150000.0, -1.0, 0.46)
    )

    private fun restOfUK2020Bands() = listOf(
        TaxBand(0.0, 12509.00, 0.0),
        TaxBand(12509.0, 50000.00, 0.2),
        TaxBand(50000.0, 150000.00, 0.4),
        TaxBand(150000.0, -1.0, 0.45)
    )

    private fun restOfUK2021Bands() = listOf(
        TaxBand(0.0, 12579.00, 0.0),
        TaxBand(12579.0, 50279.00, 0.2),
        TaxBand(50279.0, 150000.00, 0.4),
        TaxBand(150000.0, -1.0, 0.45)
    )

    fun getAdjustedBands(taxYear: Int, taxCode: TaxCode): List<Band> {
        val taxBands = getBands(taxYear, taxCode.country).toMutableList()

        // The full tax free amount e.g. 12509
        val bandAdjuster = taxBands[0].upper.toInt()

        taxBands[0].upper = taxCode.taxFreeAmount
        taxBands[1].lower = taxCode.taxFreeAmount
        taxBands[1].upper = taxBands[1].upper + taxCode.taxFreeAmount - bandAdjuster

        for (bandNumber in 2 until taxBands.size) {
            taxBands[bandNumber].lower = taxBands[bandNumber].lower + taxCode.taxFreeAmount - bandAdjuster
            if (taxBands[bandNumber].upper != -1.0) {
                taxBands[bandNumber].upper = taxBands[bandNumber].upper + taxCode.taxFreeAmount - bandAdjuster
            }
        }
        return taxBands
    }
}
