/*
 * Copyright 2024 HM Revenue & Customs
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
package uk.gov.hmrc.calculator.model.pension

import uk.gov.hmrc.calculator.model.TaxYear
import kotlin.test.Test
import kotlin.test.assertEquals

class PensionAllowancesTest {

    @Test
    fun `GIVEN year is 2020 WHEN getPensionAllowances THEN return populated object`() {
        val result = PensionAllowances.getPensionAllowances(TaxYear.TWENTY_TWENTY)

        assertEquals(1073100.0, result.standardLifetimeAllowance)
        assertEquals(40000.0, result.annualAllowance)
    }

    @Test
    fun `GIVEN year is 2021 WHEN getPensionAllowances THEN return populated object`() {
        val result = PensionAllowances.getPensionAllowances(TaxYear.TWENTY_TWENTY_ONE)

        assertEquals(1073100.0, result.standardLifetimeAllowance)
        assertEquals(40000.0, result.annualAllowance)
    }

    @Test
    fun `GIVEN year is 2022 WHEN getPensionAllowances THEN return populated object`() {
        val result = PensionAllowances.getPensionAllowances(TaxYear.TWENTY_TWENTY_TWO)

        assertEquals(1073100.0, result.standardLifetimeAllowance)
        assertEquals(40000.0, result.annualAllowance)
    }

    @Test
    fun `GIVEN year is 2023 WHEN getPensionAllowances THEN return populated object`() {
        val result = PensionAllowances.getPensionAllowances(TaxYear.TWENTY_TWENTY_THREE)

        assertEquals(1073100.0, result.standardLifetimeAllowance)
        assertEquals(60000.0, result.annualAllowance)
    }

    @Test
    fun `GIVEN year is 2023 revised WHEN getPensionAllowances THEN return populated object`() {
        val result = PensionAllowances.getPensionAllowances(TaxYear.TWENTY_TWENTY_THREE_JANUARY_REVISED)

        assertEquals(1073100.0, result.standardLifetimeAllowance)
        assertEquals(60000.0, result.annualAllowance)
    }

    @Test
    fun `GIVEN year is 2024 WHEN getPensionAllowances THEN return populated object`() {
        val result = PensionAllowances.getPensionAllowances(TaxYear.TWENTY_TWENTY_FOUR)

        assertEquals(1073100.0, result.standardLifetimeAllowance)
        assertEquals(60000.0, result.annualAllowance)
    }
}
