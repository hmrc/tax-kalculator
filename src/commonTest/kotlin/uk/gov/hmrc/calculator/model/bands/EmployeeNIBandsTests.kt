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
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class EmployeeNIBandsTests {

    @Test
    fun invalidYear() {
        val exception = assertFailsWith<UnsupportedTaxYear> {
            EmployeeNIBands(
                2017
            )
        }
        assertEquals(exception.message, "2017")
    }

    @Test
    fun `Employee NI 2019 England`() {
        val band = EmployeeNIBands(2019).bands[2]
        assertEquals(0.12, band.percentageAsDecimal)
        assertEquals(false, band.inBand(1000.0))
        assertEquals(true, band.inBand(10000.0))
    }

    @Test
    fun `Employee NI 2019 Scotland`() {
        val band = EmployeeNIBands(2019).bands[2]
        assertEquals(0.12, band.percentageAsDecimal)
        assertEquals(false, band.inBand(1000.0))
        assertEquals(true, band.inBand(10000.0))
    }

    @Test
    fun `Employee NI 2019 Wales`() {
        val band = EmployeeNIBands(2019).bands[2]
        assertEquals(0.12, band.percentageAsDecimal)
        assertEquals(false, band.inBand(1000.0))
        assertEquals(true, band.inBand(10000.0))
    }

    @Test
    fun `Employee NI 2019 Wales Massive Wages`() {
        val band = EmployeeNIBands(2019).bands[3]
        assertEquals(0.02, band.percentageAsDecimal)
        assertEquals(false, band.inBand(1000.0))
        assertEquals(true, band.inBand(100000.0))
    }
}
