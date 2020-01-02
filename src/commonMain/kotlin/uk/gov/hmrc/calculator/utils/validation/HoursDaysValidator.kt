/*
 * Copyright 2020 HM Revenue & Customs
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
package uk.gov.hmrc.calculator.utils.validation

object HoursDaysValidator {

    fun isValidHoursPerWeek(hours: Double) =
        isAboveMinimumHoursPerWeek(hours) && isBelowMaximumHoursPerWeek(
            hours
        ) && hours.isTwoDecimalPlacesOrFewer()

    fun isAboveMinimumHoursPerWeek(hours: Double) = hours >= 1

    fun isBelowMaximumHoursPerWeek(hours: Double) = hours <= 168

    fun isValidDaysPerWeek(days: Double) =
        isAboveMinimumDaysPerWeek(days) && isBelowMaximumDaysPerWeek(
            days
        ) && days.isTwoDecimalPlacesOrFewer()

    fun isAboveMinimumDaysPerWeek(days: Double) = days >= 1

    fun isBelowMaximumDaysPerWeek(days: Double) = days <= 7

    private fun Double.isTwoDecimalPlacesOrFewer(): Boolean {
        val splitByDecimalPoint = toString().split(".")
        return splitByDecimalPoint.size == 2 && splitByDecimalPoint.last().length <= 2
    }
}
