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

import uk.gov.hmrc.calculator.model.PayPeriod

object WageConverterUtils {

    // Convert Pay Period to Yearly
    fun convertHourlyWageToYearly(hourlyWage: Double, hoursWorked: Double) =
        hourlyWage.convertWageToYearly(PayPeriod.HOURLY, hoursWorked)

    fun convertDailyWageToYearly(dailyWage: Double, daysWorked: Double) =
        dailyWage.convertWageToYearly(PayPeriod.DAILY, daysWorked)

    fun convertWeeklyWageToYearly(weeklyWage: Double) =
        weeklyWage.convertWageToYearly(PayPeriod.WEEKLY)

    fun convertFourWeeklyWageToYearly(fourWeeklyWage: Double) =
        fourWeeklyWage.convertWageToYearly(PayPeriod.FOUR_WEEKLY)

    fun convertMonthlyWageToYearly(monthlyWage: Double) =
        monthlyWage.convertWageToYearly(PayPeriod.MONTHLY)

    // Convert Yearly Wage to Pay Period
    fun convertYearlyWageToWeekly(yearlyWage: Double) =
        yearlyWage.convertAmountFromYearlyToPayPeriod(PayPeriod.WEEKLY)

    fun convertYearlyWageToFourWeekly(yearlyWage: Double) =
        yearlyWage.convertAmountFromYearlyToPayPeriod(PayPeriod.FOUR_WEEKLY)

    fun convertYearlyWageToMonthly(yearlyWage: Double) =
        yearlyWage.convertAmountFromYearlyToPayPeriod(PayPeriod.MONTHLY)
}