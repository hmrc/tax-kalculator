package model.bands

class EmployerNIBands(taxYear: Int) {

    private val employerNIBands2019: List<EmployerNIBand> = listOf(
        EmployerNIBand(0.0, 6136.00, 0.0),
        EmployerNIBand(6136.0, 8632.00, 0.0),
        EmployerNIBand(8632.0, 50000.00, 0.138),
        EmployerNIBand(50000.0, 5000000.00, 0.138)//TODO Fix the 5million...
    )

    val bands: List<EmployerNIBand> = when (taxYear) {
        2019 -> employerNIBands2019
        else -> throw IllegalArgumentException("Unsupported Year")
    }
}
