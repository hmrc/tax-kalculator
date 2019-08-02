package model

data class BandBreakdown(val percentage: Double, val amount: Double){
    //TODO better way if percentage is a decimal. Awaiting Kotlin native library for MPP
    private val percentageFormatted = (percentage*100).toInt()
    val bandDescription: String = "Income taxed at $percentageFormatted%"
}

data class CalculatorResponsePayPeriod(
    val payPeriod: PayPeriod,
    val taxToPay: Double,
    val employeesNI: Double,
    val employersNI: Double,
    val wages: Double,
    val taxBreakdown: List<BandBreakdown>,
    val taxFree: Double,
    val kCodeAdjustment: Double? = null
) {
    val totalDeductions: Double = taxToPay + employeesNI
    val takeHome: Double = wages - totalDeductions
}

data class CalculatorResponse(
    val taxCode: String,
    val country: Country,
    val isKCode: Boolean,
    val weekly: CalculatorResponsePayPeriod,
    val fourWeekly: CalculatorResponsePayPeriod,
    val monthly: CalculatorResponsePayPeriod,
    val yearly: CalculatorResponsePayPeriod
)