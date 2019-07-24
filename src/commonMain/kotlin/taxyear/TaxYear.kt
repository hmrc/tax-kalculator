//package taxyear
//
//import com.soywiz.klock.DateTime
//
//data class TaxYear(val startYear: Int) {
//
//    private val finishYear: Int = startYear + 1
//    private val starts: DateTime = TaxYear.firstDayOfTaxYear(startYear)
//    private val startInstant: DateTime = starts.toDateTimeAtStartOfDay(TaxYear.ukTime)
//    private val finishes: DateTime = DateTime(year = finishYear, month = 4, day = 5)
////    fun finishInstant: DateTime = next.startInstant
//
//    private fun back(years: Int): TaxYear = TaxYear(startYear - years)
//    val previous: TaxYear = back(1)
//
//    val currentYear : Int = startYear
//
//    fun forwards(years: Int): TaxYear = TaxYear(startYear + years)
//    val next: TaxYear = forwards(1)
//
//    fun contains(date: DateTime) = !(date.isBefore(starts) || date.isAfter(finishes))
//
////    fun yearRange: Inclusive = startYear to finishYear
////    fun interval = Interval(startInstant, finishInstant)
//
//    override fun toString() = "$startYear to $finishYear"
//}
//
////companion object TaxYear:  CurrentTaxYear with (Int => TaxYear) {
////    override fun now = () => DateTime.now
////}