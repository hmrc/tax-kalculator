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

internal object TaxBands {

    private object Year2020 {
        val scotland: List<Band> = listOf(
            TaxBand(0.0, 12509.00, 0.0),
            TaxBand(12509.00, 14585.00, 0.19),
            TaxBand(14585.00, 25158.00, 0.20),
            TaxBand(25158.00, 43430.00, 0.21),
            TaxBand(43430.00, 150000.00, 0.41),
            TaxBand(150000.0, -1.0, 0.46)
        )
        val other: List<Band> = listOf(
            TaxBand(0.0, 12509.00, 0.0),
            TaxBand(12509.0, 50000.00, 0.2),
            TaxBand(50000.0, 150000.00, 0.4),
            TaxBand(150000.0, -1.0, 0.45)
        )
    }

    fun getBands(taxYear: Int, country: Country) = when (taxYear) {
        2020 -> when (country) {
            SCOTLAND -> Year2020.scotland
            else -> Year2020.other
        }
        else -> throw InvalidTaxYearException("$taxYear")
    }
}
