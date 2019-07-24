package utils

import model.PayPeriod

class WageConverter {

    fun convertWageToYearly(
        payPeriod: PayPeriod,
        hoursPerWeek: Double,
        userEnteredWages: Double
    ): Double {
        return when (payPeriod) {
            PayPeriod.HOURLY -> if (hoursPerWeek > 0) userEnteredWages * hoursPerWeek * 52 else throw IllegalArgumentException("Hours must be greater than 0 for PayPeriod = HOURLY")
            PayPeriod.WEEKLY -> userEnteredWages * 52
            PayPeriod.FOUR_WEEKLY -> userEnteredWages * 13
            PayPeriod.MONTHLY -> userEnteredWages * 12
            PayPeriod.YEARLY -> userEnteredWages
        }
    }


    fun convertAmountFromYearlyToPayPeriod(
        payPeriod: PayPeriod,
        amount: Double
    ): Double {
        return when (payPeriod) {
            PayPeriod.WEEKLY -> amount / 52
            PayPeriod.FOUR_WEEKLY -> amount / 13
            PayPeriod.MONTHLY -> amount / 12
            PayPeriod.YEARLY -> amount
            PayPeriod.HOURLY -> throw IllegalArgumentException("Amounts are not displayed hour by hour")
        }
    }

}