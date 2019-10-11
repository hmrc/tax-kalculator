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
package uk.gov.hmrc.calculator.utils

import uk.gov.hmrc.calculator.exception.InvalidPayPeriodException
import uk.gov.hmrc.calculator.model.PayPeriod.FOUR_WEEKLY
import uk.gov.hmrc.calculator.model.PayPeriod.HOURLY
import uk.gov.hmrc.calculator.model.PayPeriod.MONTHLY
import uk.gov.hmrc.calculator.model.PayPeriod.WEEKLY
import uk.gov.hmrc.calculator.model.PayPeriod.YEARLY
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class WageConverterTest {
    @Test
    fun `Week Invalid When Converting From Year`() {
        assertFailsWith<InvalidPayPeriodException> {
            100.0.convertAmountFromYearlyToPayPeriod(HOURLY)
        }
    }

    @Test
    fun `Convert monthly to yearly`() {
        assertEquals(12000.0, 1000.0.convertWageToYearly(MONTHLY))
    }

    @Test
    fun `Convert FOUR_WEEKLY to yearly`() {
        assertEquals(13000.0, 1000.0.convertWageToYearly(FOUR_WEEKLY))
    }

    @Test
    fun `Convert WEEKLY to yearly`() {
        assertEquals(52000.0, 1000.0.convertWageToYearly(WEEKLY))
    }

    @Test
    fun `Convert HOURLY to yearly`() {
        assertEquals(5200.0, 10.0.convertWageToYearly(HOURLY, 10.0))
    }

    @Test
    fun `Convert YEARLY to yearly`() {
        assertEquals(5200.0, 5200.0.convertWageToYearly(YEARLY))
    }
}