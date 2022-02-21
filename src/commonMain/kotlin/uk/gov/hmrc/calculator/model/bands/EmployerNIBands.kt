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

import uk.gov.hmrc.calculator.model.TaxYear

internal class EmployerNIBands(taxYear: TaxYear) {

    private val employerNIBands2020: List<EmployerNIBand> = listOf(
        EmployerNIBand(0.0, 6240.00, 0.0),
        EmployerNIBand(6240.0, 8788.00, 0.0),
        EmployerNIBand(8788.0, 50000.00, 0.138),
        EmployerNIBand(50000.0, -1.0, 0.138)
    )

    private val employerNIBands2021: List<EmployerNIBand> = listOf(
        EmployerNIBand(0.0, 6240.00, 0.0),
        EmployerNIBand(6240.0, 8840.00, 0.0),
        EmployerNIBand(8840.0, 50270.00, 0.138),
        EmployerNIBand(50270.0, -1.0, 0.138)
    )

    private val employerNIBands2022: List<EmployerNIBand> = listOf(
        EmployerNIBand(0.0, 6396.00, 0.0),
        EmployerNIBand(6396.0, 9100.00, 0.0),
        EmployerNIBand(9100.0, 50270.00, 0.1505),
        EmployerNIBand(50270.0, -1.0, 0.1505)
    )

    internal val bands: List<EmployerNIBand> = when (taxYear) {
        TaxYear.TWENTY_TWENTY -> employerNIBands2020
        TaxYear.TWENTY_TWENTY_ONE -> employerNIBands2021
        TaxYear.TWENTY_TWENTY_TWO -> employerNIBands2022
    }
}
