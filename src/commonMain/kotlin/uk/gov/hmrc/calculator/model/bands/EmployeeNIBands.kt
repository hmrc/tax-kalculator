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
package uk.gov.hmrc.calculator.model.bands

import uk.gov.hmrc.calculator.utils.UnsupportedTaxYear

internal class EmployeeNIBands(taxYear: Int) {

    private val employeeNIBands2019: List<EmployeeNIBand> = listOf(
        EmployeeNIBand(0.0, 6136.00, 0.0),
        EmployeeNIBand(6136.0, 8632.00, 0.0),
        EmployeeNIBand(8632.0, 50000.00, 0.12),
        EmployeeNIBand(50000.0, -1.0, 0.02)
    )

    internal val bands: List<EmployeeNIBand> = when (taxYear) {
        2019 -> employeeNIBands2019
        else -> throw UnsupportedTaxYear("$taxYear")
    }
}
