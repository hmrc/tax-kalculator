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
package uk.gov.hmrc.calculator.utils

import uk.gov.hmrc.calculator.exception.InvalidDaysException
import uk.gov.hmrc.calculator.exception.InvalidHoursException
import uk.gov.hmrc.calculator.exception.InvalidPayPeriodException
import uk.gov.hmrc.calculator.model.PayPeriod.DAILY
import uk.gov.hmrc.calculator.model.PayPeriod.FOUR_WEEKLY
import uk.gov.hmrc.calculator.model.PayPeriod.HOURLY
import uk.gov.hmrc.calculator.model.PayPeriod.MONTHLY
import uk.gov.hmrc.calculator.model.PayPeriod.WEEKLY
import uk.gov.hmrc.calculator.model.PayPeriod.YEARLY
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class PayPeriodTestExtensions {

    @Test
    fun `Week Invalid When Converting From Year`() {
        assertFailsWith<InvalidPayPeriodException> {
            100.0.convertAmountFromYearlyToPayPeriod(HOURLY)
        }
    }

    @Test
    fun `GIVEN paid daily 5 times per week WHEN convert to yearly THEN result is correct`() {
        assertEquals(26000.0, 100.0.convertWageToYearly(DAILY, 5.0))
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

    @Test
    fun `Convert to HOURLY invalid when hours per week null`() {
        assertFailsWith<InvalidHoursException> {
            10.0.convertWageToYearly(HOURLY, null)
        }
    }

    @Test
    fun `Convert to HOURLY invalid when hours per week zero`() {
        assertFailsWith<InvalidHoursException> {
            10.0.convertWageToYearly(HOURLY, 0.0)
        }
    }

    @Test
    fun `Convert to HOURLY invalid when hours per week too high`() {
        assertFailsWith<InvalidHoursException> {
            10.0.convertWageToYearly(HOURLY, 169.0)
        }
    }

    @Test
    fun `Convert to DAILY invalid when days per week too high`() {
        assertFailsWith<InvalidDaysException> {
            50.0.convertWageToYearly(DAILY, 8.0)
        }
    }

    @Test
    fun `Convert to DAILY invalid when days per week is 0`() {
        assertFailsWith<InvalidDaysException> {
            50.0.convertWageToYearly(DAILY, 0.0)
        }
    }

    @Test
    fun `Convert to DAILY invalid when days per week is null`() {
        assertFailsWith<InvalidDaysException> {
            50.0.convertWageToYearly(DAILY, null)
        }
    }

    @Test
    fun `Convert YEARLY to monthly`() {
        assertEquals(1000.0, 12000.0.convertWageToMonthly(YEARLY))
    }

    @Test
    fun `Convert MONTHLY to monthly`() {
        assertEquals(1200.0, 1200.0.convertWageToMonthly(MONTHLY))
    }

    @Test
    fun `Convert FOUR_WEEKLY to monthly`() {
        assertEquals(1690.0, 1560.0.convertWageToMonthly(FOUR_WEEKLY))
    }

    @Test
    fun `Convert WEEKLY to monthly`() {
        assertEquals(1300.0, 300.0.convertWageToMonthly(WEEKLY))
    }

    @Test
    fun `Convert DAILY to monthly`() {
        assertEquals(1300.0, 100.0.convertWageToMonthly(DAILY, 3.0))
    }

    @Test
    fun `Convert HOURLY to monthly`() {
        assertEquals(1300.0, 30.0.convertWageToMonthly(HOURLY, 10.0))
    }
}
