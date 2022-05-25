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
import kotlin.test.Test
import kotlin.test.assertEquals

class EmployeeNIBandsTests {

    @Test
    fun `WHEN year is 2020 THEN band ranges correct`() {
        val bands = EmployeeNIBands(TaxYear.TWENTY_TWENTY).bands

        val band0 = bands[0]
        assertEquals(0.12, band0.percentageAsDecimal)
        assertEquals(true, band0.inBand(12000.0))
        assertEquals(false, band0.inBand(650000.0))

        val band1 = bands[1]
        assertEquals(0.02, band1.percentageAsDecimal)
        assertEquals(true, band1.inBand(650000.0))
        assertEquals(false, band1.inBand(1000.0))
    }

    @Test
    fun `WHEN year is 2021 THEN band ranges correct`() {
        val bands = EmployeeNIBands(TaxYear.TWENTY_TWENTY_ONE).bands

        val band0 = bands[0]
        assertEquals(0.12, band0.percentageAsDecimal)
        assertEquals(true, band0.inBand(12000.0))
        assertEquals(false, band0.inBand(650000.0))

        val band1 = bands[1]
        assertEquals(0.02, band1.percentageAsDecimal)
        assertEquals(true, band1.inBand(650000.0))
        assertEquals(false, band1.inBand(1000.0))
    }

    @Test
    fun `WHEN year is 2022 THEN band ranges correct`() {
        val bands = EmployeeNIBands(TaxYear.TWENTY_TWENTY_TWO).bands
        println(bands)
        val band0 = bands[0]
        assertEquals(0.1325, band0.percentageAsDecimal)
        assertEquals(true, band0.inBand(12000.0))
        assertEquals(false, band0.inBand(650000.0))

        val band1 = bands[1]
        assertEquals(0.0325, band1.percentageAsDecimal)
        assertEquals(true, band1.inBand(650000.0))
        assertEquals(false, band1.inBand(1000.0))
    }
    @Test
    fun `WHEN year is 2022Revised THEN band ranges correct`() {
        val bands = EmployeeNIBands(TaxYear.TWENTY_TWENTY_TWO_REVISED).bands
        println(bands)
        val band0 = bands[0]
        assertEquals(0.1325, band0.percentageAsDecimal)
        assertEquals(true, band0.inBand(12571.0))
        assertEquals(false, band0.inBand(650000.0))

        val band1 = bands[1]
        assertEquals(0.0325, band1.percentageAsDecimal)
        assertEquals(true, band1.inBand(650000.0))
        assertEquals(false, band1.inBand(1000.0))
    }
}
