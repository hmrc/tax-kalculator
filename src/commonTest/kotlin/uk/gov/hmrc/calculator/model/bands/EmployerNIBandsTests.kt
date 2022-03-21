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
import uk.gov.hmrc.calculator.model.TaxYear

class EmployerNIBandsTests {

    @Test
    fun `WHEN year is 2020 THEN band ranges correct`() {
        val bands = EmployerNIBands(TaxYear.TWENTY_TWENTY).bands

        val band0 = bands[0]
        assertEquals(0.138, band0.percentageAsDecimal)
        assertEquals(true, band0.inBand(12000.0))
        assertEquals(false, band0.inBand(650000.0))

        val band1 = bands[1]
        assertEquals(0.138, band1.percentageAsDecimal)
        assertEquals(true, band1.inBand(650000.0))
        assertEquals(false, band1.inBand(1000.0))
    }

    @Test
    fun `WHEN year is 2021 THEN band ranges correct`() {
        val bands = EmployerNIBands(TaxYear.TWENTY_TWENTY_ONE).bands

        val band1 = bands[0]
        assertEquals(0.138, band1.percentageAsDecimal)
        assertEquals(true, band1.inBand(12000.0))
        assertEquals(false, band1.inBand(650000.0))

        val band2 = bands[1]
        assertEquals(0.138, band2.percentageAsDecimal)
        assertEquals(true, band2.inBand(650000.0))
        assertEquals(false, band2.inBand(1000.0))
    }

    @Test
    fun `WHEN year is 2022 THEN band ranges correct`() {
        val bands = EmployerNIBands(TaxYear.TWENTY_TWENTY_TWO).bands

        val band1 = bands[0]
        assertEquals(0.1505, band1.percentageAsDecimal)
        assertEquals(true, band1.inBand(12000.0))
        assertEquals(false, band1.inBand(650000.0))

        val band2 = bands[1]
        assertEquals(0.1505, band2.percentageAsDecimal)
        assertEquals(true, band2.inBand(650000.0))
        assertEquals(false, band2.inBand(1000.0))
    }
}
