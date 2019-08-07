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
package calculator.utils

import calculator.model.BandBreakdown
import calculator.model.PayPeriod

internal fun Double.convertWageToYearly(
    payPeriod: PayPeriod,
    hoursPerWeek: Double? = null
): Double {
    return when (payPeriod) {
        PayPeriod.HOURLY -> {
            if (hoursPerWeek != null && hoursPerWeek > 0) this * hoursPerWeek * 52
            else throw InvalidHours("The number of hours must be greater than 0 when PayPeriod is HOURLY")
        }
        PayPeriod.WEEKLY -> this * 52
        PayPeriod.FOUR_WEEKLY -> this * 13
        PayPeriod.MONTHLY -> this * 12
        PayPeriod.YEARLY -> this
    }
}

internal fun List<BandBreakdown>.convertListOfBandBreakdownForPayPeriod(payPeriod: PayPeriod): List<BandBreakdown> =
    this.map { bandBreakdown ->
        BandBreakdown(
            percentage = bandBreakdown.percentage,
            amount = bandBreakdown.amount.convertAmountFromYearlyToPayPeriod(payPeriod)
        )
    }

internal fun Double.convertAmountFromYearlyToPayPeriod(
    payPeriod: PayPeriod
): Double {
    return when (payPeriod) {
        PayPeriod.WEEKLY -> this / 52
        PayPeriod.FOUR_WEEKLY -> this / 13
        PayPeriod.MONTHLY -> this / 12
        PayPeriod.YEARLY -> this
        else -> throw InvalidPayPeriod("$payPeriod is not supported")
    }
}
