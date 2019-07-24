//package taxyear
//
//import com.soywiz.klock.DateTime
//import com.soywiz.klock.DateTimeTz
//import com.soywiz.klock.TimezoneOffset
//
//
//interface CurrentTaxYear {
//
//    val ukTime: DateTimeTz = DateTimeTz(("Europe/London")
//
//    private val startOfTaxYear = MonthDay(4, 6)
//
//    fun now() =  DateTime.now()
//
//    fun firstDayOfTaxYear(year: Int) = startOfTaxYear.toLocalDate(year)
//
//    fun today() = new LocalDate(now, ukTime)
//
//    fun taxYearFor(date: LocalDate): TaxYear {
//        if (date isBefore firstDayOfTaxYear(date.getYear))
//            TaxYear(startYear = date.getYear - 1)
//        else
//            TaxYear(startYear = date.getYear)
//    }
//
//    fun current: TaxYear = taxYearFor(today)
//}
