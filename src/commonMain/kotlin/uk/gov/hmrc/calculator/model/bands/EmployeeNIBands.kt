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
import com.soywiz.klock.DateTime
import com.soywiz.klock.DateTimeTz

internal class EmployeeNIBands(taxYear: TaxYear) {

    private val employeeNIBands2020: List<EmployeeNIBand> = listOf(
        EmployeeNIBand(9500.0, 50000.00, 0.12),
        EmployeeNIBand(50000.0, -1.0, 0.02)
    )

    private val employeeNIBands2021: List<EmployeeNIBand> = listOf(
        EmployeeNIBand(9568.0, 50270.00, 0.12),
        EmployeeNIBand(50270.0, -1.0, 0.02)
    )

    private val employeeNIBands2022: List<EmployeeNIBand> = listOf(
        EmployeeNIBand(9880.0, 50270.00, 0.1325),
        EmployeeNIBand(50270.0, -1.0, 0.0325)
    )

    private val employeeNIBands2022Revised: List<EmployeeNIBand> = listOf(
        EmployeeNIBand(12570.0, 50270.00, 0.1325),
        EmployeeNIBand(50270.0, -1.0, 0.0325)
    )

    internal val bands: List<EmployeeNIBand> = when (taxYear) {
        TaxYear.TWENTY_TWENTY -> employeeNIBands2020
        TaxYear.TWENTY_TWENTY_ONE -> employeeNIBands2021
        TaxYear.TWENTY_TWENTY_TWO -> if (DateTime.now().local > DateTime(
                year = 2022,
                month = 7,
                day = 5
            ).local) employeeNIBands2022Revised else employeeNIBands2022
    }
}
