package model

data class CalculatorResponsePayPeriod(val taxToPay: Double, val employeesNI: Double, val employersNI: Double) {
    val totalDeductions: Double = taxToPay + employeesNI
}

data class CalculatorResponse(
    val weekly: CalculatorResponsePayPeriod,
    val fourWeekly: CalculatorResponsePayPeriod,
    val monthly: CalculatorResponsePayPeriod,
    val yearly: CalculatorResponsePayPeriod
)

