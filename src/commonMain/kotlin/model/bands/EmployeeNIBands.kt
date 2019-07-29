package model.bands


internal class EmployeeNIBands(taxYear: Int) {

    private val employeeNIBands2019: List<EmployeeNIBand> = listOf(
        EmployeeNIBand(0.0, 6136.00, 0.0),
        EmployeeNIBand(6136.0, 8632.00, 0.0),
        EmployeeNIBand(8632.0, 50000.00, 0.12),
        EmployeeNIBand(50000.0, -1.0, 0.02)
    )

    internal val bands: List<EmployeeNIBand> = when (taxYear) {
        2019 -> employeeNIBands2019
        else -> throw IllegalArgumentException("Unsupported Year")
    }
}
