/*
 * Copyright 2023 HM Revenue & Customs
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

import uk.gov.hmrc.calculator.Calculator
import kotlin.test.Test
import kotlin.test.assertEquals

class PensionCalculationTest {

    @Test
    fun `GIVEN pensionMethod PERCENTAGE AND pensionContributionAmount 10 percent WHEN calculateYearlyPension THEN return pension amount`() {
        val result = calculateYearlyPension(
            1000.0,
            pensionContribution = Calculator.PensionContribution(PensionMethod.PERCENTAGE, 10.0)
        )

        assertEquals(100.0, result)
    }

    @Test
    fun `GIVEN pensionMethod MONTHLY_AMOUNT_IN_POUNDS AND pensionContributionAmount is 100 WHEN calculateYearlyPension THEN return pension amount`() {
        val result = calculateYearlyPension(
            1000.0,
            pensionContribution = Calculator.PensionContribution(PensionMethod.MONTHLY_AMOUNT_IN_POUNDS, 100.0)
        )

        assertEquals(1200.0, result)
    }
}
