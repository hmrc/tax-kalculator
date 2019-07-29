package utils

import com.soywiz.klock.DateTime
import com.soywiz.klock.DateTimeTz

internal class TaxYear {
    fun currentTaxYear(): Int {
        val date = DateTime.nowLocal()
        return if (date < firstDayOfTaxYear(date.yearInt))
            date.yearInt - 1
        else
            date.yearInt
    }

    private fun firstDayOfTaxYear(year: Int): DateTimeTz {
        return DateTime(year = year, month = 4, day = 6).local
    }
}