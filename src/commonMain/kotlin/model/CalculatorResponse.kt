package model

data class BandBreakdown(val percentage: Double, val amount: Double)

data class CalculatorResponsePayPeriod(val taxToPay: Double, val employeesNI: Double, val employersNI: Double, val wages: Double, val taxBreakdown: List<BandBreakdown>,val taxFree: Double) {
    val totalDeductions: Double = taxToPay + employeesNI
    val takeHome: Double = wages - totalDeductions
}

data class CalculatorResponse(
    val weekly: CalculatorResponsePayPeriod,
    val fourWeekly: CalculatorResponsePayPeriod,
    val monthly: CalculatorResponsePayPeriod,
    val yearly: CalculatorResponsePayPeriod
)