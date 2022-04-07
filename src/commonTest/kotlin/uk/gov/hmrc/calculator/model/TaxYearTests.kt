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
package uk.gov.hmrc.calculator.model

import uk.gov.hmrc.calculator.exception.InvalidTaxYearException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class TaxYearTests {

    @Test
    fun `WHEN year valid THEN returns type`() {
        assertEquals(TaxYear.TWENTY_TWENTY, TaxYear.fromInt(2020))
        assertEquals(TaxYear.TWENTY_TWENTY_ONE, TaxYear.fromInt(2021))
        assertEquals(TaxYear.TWENTY_TWENTY_TWO, TaxYear.fromInt(2022))
    }

    @Test
    fun `WHEN year invalid THEN throws exception`() {
        assertFailsWith<InvalidTaxYearException> {
            TaxYear.fromInt(2048)
        }
    }
}
