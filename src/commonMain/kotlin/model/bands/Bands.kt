package model.bands

internal interface Band {
    var lower: Double
    var upper: Double
    val percentageAsDecimal: Double

    fun inBand(amount: Double): Boolean {
        return if (upper == -1.0 && amount > lower) {
            true
        } else {
            amount > lower && amount <= upper
        }
    }
}

internal data class TaxBand(
    override var lower: Double,
    override var upper: Double,
    override val percentageAsDecimal: Double
) : Band

internal data class EmployerNIBand(
    override var lower: Double,
    override var upper: Double,
    override val percentageAsDecimal: Double
) : Band

internal data class EmployeeNIBand(
    override var lower: Double,
    override var upper: Double,
    override val percentageAsDecimal: Double
) : Band