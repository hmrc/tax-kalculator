package utils

import model.BandBreakdown
import model.PayPeriod

fun Double.convertWageToYearly(
    payPeriod: PayPeriod,
    hoursPerWeek: Double = 0.0
): Double {
    return when (payPeriod) {
        PayPeriod.HOURLY -> if (hoursPerWeek > 0) this * hoursPerWeek * 52 else throw IllegalArgumentException("Hours must be greater than 0 for PayPeriod = HOURLY")
        PayPeriod.WEEKLY -> this * 52
        PayPeriod.FOUR_WEEKLY -> this * 13
        PayPeriod.MONTHLY -> this * 12
        PayPeriod.YEARLY -> this
    }
}

fun List<BandBreakdown>.convertListOfBandBreakdownForPayPeriod(payPeriod: PayPeriod): List<BandBreakdown> =
    this.map { bandBreakdown ->
        BandBreakdown(
            percentage = bandBreakdown.percentage,
            amount = bandBreakdown.amount.convertAmountFromYearlyToPayPeriod(payPeriod)
        )
    }


fun Double.convertAmountFromYearlyToPayPeriod(
    payPeriod: PayPeriod
): Double {
    return when (payPeriod) {
        PayPeriod.WEEKLY -> this / 52
        PayPeriod.FOUR_WEEKLY -> this / 13
        PayPeriod.MONTHLY -> this / 12
        PayPeriod.YEARLY -> this
        PayPeriod.HOURLY -> throw IllegalArgumentException("Amounts are not displayed hour by hour")
    }
}
