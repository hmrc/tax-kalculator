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
import uk.gov.hmrc.calculator.model.PayPeriod
import uk.gov.hmrc.calculator.model.PayPeriod.DAILY
import uk.gov.hmrc.calculator.model.PayPeriod.FOUR_WEEKLY
import uk.gov.hmrc.calculator.model.PayPeriod.HOURLY
import uk.gov.hmrc.calculator.model.PayPeriod.MONTHLY
import uk.gov.hmrc.calculator.model.PayPeriod.WEEKLY
import uk.gov.hmrc.calculator.model.PayPeriod.YEARLY
import uk.gov.hmrc.calculator.utils.validation.HoursDaysValidator
import kotlin.jvm.JvmSynthetic

@JvmSynthetic
internal fun Double.convertWageToYearly(
    payPeriod: PayPeriod,
    hoursOrDaysWorkedForPayPeriod: Double? = null
): Double {
    return when (payPeriod) {
        HOURLY -> hourlyToYearly(hoursOrDaysWorkedForPayPeriod)
        DAILY -> dailyToYearly(hoursOrDaysWorkedForPayPeriod)
        WEEKLY -> this * 52
        FOUR_WEEKLY -> this * 13
        MONTHLY -> this * 12
        YEARLY -> this
    }
}

@JvmSynthetic
internal fun Double.convertAmountFromYearlyToPayPeriod(
    payPeriod: PayPeriod
): Double {
    return when (payPeriod) {
        WEEKLY -> this / 52
        FOUR_WEEKLY -> this / 13
        MONTHLY -> this / 12
        YEARLY -> this
        else -> throw InvalidPayPeriodException("$payPeriod is not supported")
    }
}

private fun Double.hourlyToYearly(hoursWorked: Double?): Double {
    return if (hoursWorked != null && HoursDaysValidator.isValidHoursPerWeek(hoursWorked))
        this * hoursWorked * 52
    else throw InvalidHoursException("The number of hours must be between 1 and 168 when PayPeriod is HOURLY")
}

private fun Double.dailyToYearly(daysWorked: Double?): Double {
    return if (daysWorked != null && HoursDaysValidator.isValidDaysPerWeek(daysWorked))
        this * daysWorked * 52
    else throw InvalidDaysException("The number of days must be between 1 and 7 when PayPeriod is DAILY")
}
