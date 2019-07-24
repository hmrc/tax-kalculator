package model.bands

interface Band {
    var lower: Double
    var upper: Double
    val percentageAsDecimal: Double

    fun inBand(amount: Double): Boolean {
        return amount > lower && amount <= upper
    }
}

data class TaxBand(
    override var lower: Double,
    override var upper: Double,
    override val percentageAsDecimal: Double
) : Band

data class EmployerNIBand(
    override var lower: Double,
    override var upper: Double,
    override val percentageAsDecimal: Double
) : Band

data class EmployeeNIBand(
    override var lower: Double,
    override var upper: Double,
    override val percentageAsDecimal: Double
) : Band