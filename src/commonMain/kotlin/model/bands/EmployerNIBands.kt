package model.bands

import utils.UnsupportedTaxYear

internal class EmployerNIBands(taxYear: Int) {

    private val employerNIBands2019: List<EmployerNIBand> = listOf(
        EmployerNIBand(0.0, 6136.00, 0.0),
        EmployerNIBand(6136.0, 8632.00, 0.0),
        EmployerNIBand(8632.0, 50000.00, 0.138),
        EmployerNIBand(50000.0, -1.0, 0.138)
    )

    internal val bands: List<EmployerNIBand> = when (taxYear) {
        2019 -> employerNIBands2019
        else -> throw UnsupportedTaxYear("$taxYear")
    }
}
