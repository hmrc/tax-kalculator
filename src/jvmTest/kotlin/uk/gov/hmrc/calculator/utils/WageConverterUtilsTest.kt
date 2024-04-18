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
package uk.gov.hmrc.calculator.utils

import org.junit.jupiter.api.Test
import uk.gov.hmrc.calculator.utils.WageConverterUtils.convertDailyWageToYearly
import uk.gov.hmrc.calculator.utils.WageConverterUtils.convertFourWeeklyWageToYearly
import uk.gov.hmrc.calculator.utils.WageConverterUtils.convertHourlyWageToYearly
import uk.gov.hmrc.calculator.utils.WageConverterUtils.convertMonthlyWageToYearly
import uk.gov.hmrc.calculator.utils.WageConverterUtils.convertWeeklyWageToYearly
import uk.gov.hmrc.calculator.utils.WageConverterUtils.convertYearlyWageToFourWeekly
import uk.gov.hmrc.calculator.utils.WageConverterUtils.convertYearlyWageToMonthly
import uk.gov.hmrc.calculator.utils.WageConverterUtils.convertYearlyWageToWeekly
import kotlin.test.assertEquals

class WageConverterUtilsTest {

    @Test
    fun `WHEN convertHourlyWageToYearly THEN return correct result`() {
        val hoursWorked = 10.0
        val wage = 10.0

        assertEquals(5200.0, convertHourlyWageToYearly(wage, hoursWorked))
    }

    @Test
    fun `WHEN convertDailyWageToYearly THEN return correct result`() {
        val daysWorked = 5.0
        val wage = 10.0

        assertEquals(2600.0, convertDailyWageToYearly(wage, daysWorked))
    }

    @Test
    fun `WHEN convertWeeklyWageToYearly THEN return correct result`() {
        val wage = 10.0

        assertEquals(520.0, convertWeeklyWageToYearly(wage))
    }

    @Test
    fun `WHEN convertFourWeeklyWageToYearly THEN return correct result`() {
        val wage = 10.0

        assertEquals(130.0, convertFourWeeklyWageToYearly(wage))
    }

    @Test
    fun `WHEN convertMonthlyWageToYearly THEN return correct result`() {
        val wage = 10.0

        assertEquals(120.0, convertMonthlyWageToYearly(wage))
    }

    @Test
    fun `WHEN convertYearlyWageToWeekly THEN return correct result`() {
        val wage = 520.0

        assertEquals(10.0, convertYearlyWageToWeekly(wage))
    }

    @Test
    fun `WHEN convertYearlyWageToFourWeekly THEN return correct result`() {
        val wage = 130.0

        assertEquals(10.0, convertYearlyWageToFourWeekly(wage))
    }

    @Test
    fun `WHEN convertYearlyWageToMonthly THEN return correct result`() {
        val wage = 120.0

        assertEquals(10.0, convertYearlyWageToMonthly(wage))
    }
}