import model.BandBreakdown
import model.CalculatorResponse
import model.CalculatorResponsePayPeriod
import model.PayPeriod
import model.PayPeriod.*
import model.bands.*
import model.taxcodes.*
import utils.*

class CalculatorHelper{
    fun getDefaultTaxCode(): String{
        val taxYear = TaxYear().currentTaxYear()
        return """${(getDefaultTaxAllowance(taxYear) / 10)}L"""
    }
}

class Calculator(
    private val taxCodeString: String,
    userEnteredWages: Double,
    payPeriod: PayPeriod,
    private val pensionAge: Boolean = false,
    hoursPerWeek: Double? = null,
    taxYear: Int = TaxYear().currentTaxYear()
) {
    private val bandBreakdown: MutableList<BandBreakdown> = mutableListOf()
    private val yearlyWages: Double = userEnteredWages.convertWageToYearly(payPeriod, hoursPerWeek)
    private val taxCode: TaxCode = taxCodeString.toTaxCode()
    private val bandAdjuster: Int =
        getDefaultTaxAllowance(taxYear, taxCode.country) //The full tax free amount e.g. 12509
    private val taxBands: List<Band> = TaxBands(taxCode.country, taxYear).bands
    private val employeeNIBands: List<EmployeeNIBand> = EmployeeNIBands(taxYear).bands
    private val employerNIBands: List<EmployerNIBand> = EmployerNIBands(taxYear).bands

    private fun taxToPay(): Double {
        return when (taxCode) {
            is StandardTaxCode -> getTotalFromBands(adjustTaxBands(taxBands), yearlyWages)
            is NoTaxTaxCode -> yearlyWages * taxCode.taxFreeAmount
            is SingleBandTax -> yearlyWages * taxBands[taxCode.taxAllAtBand].percentageAsDecimal
            is AdjustedTaxFreeTCode -> getTotalFromBands(adjustTaxBands(taxBands), yearlyWages)
            is EmergencyTaxCode -> getTotalFromBands(adjustTaxBands(taxBands), yearlyWages)
            is MarriageTaxCodes -> getTotalFromBands(adjustTaxBands(taxBands), yearlyWages)
            is KTaxCode -> getTotalFromBands(adjustTaxBands(taxBands), yearlyWages + taxCode.amountToAddToWages)
            else -> throw InvalidTaxCode("$this is an invalid tax code")
        }
    }

    private fun adjustTaxBands(taxBands: List<Band>): List<Band> {
        taxBands[0].upper = taxCode.taxFreeAmount
        taxBands[1].lower = taxCode.taxFreeAmount
        taxBands[1].upper = taxBands[1].upper + taxCode.taxFreeAmount - bandAdjuster

        for (bandNumber in 2 until taxBands.size) {
            taxBands[bandNumber].lower = taxBands[bandNumber].lower + taxCode.taxFreeAmount - bandAdjuster
            if (taxBands[bandNumber].upper != -1.0) {
                taxBands[bandNumber].upper = taxBands[bandNumber].upper + taxCode.taxFreeAmount - bandAdjuster
            }
        }
        return taxBands
    }

    private fun employerNIToPay(): Double {
        return if (pensionAge) 0.0 else getTotalFromBands(employerNIBands, yearlyWages)
    }

    private fun employeeNIToPay(): Double {
        return if (pensionAge) 0.0 else getTotalFromBands(employeeNIBands, yearlyWages)
    }

    private fun getTotalFromBands(bands: List<Band>, wages: Double): Double {
        var amount = 0.0
        bands.map { band: Band ->
            if (band.inBand(wages)) {
                val taxForBand = (wages - band.lower) * band.percentageAsDecimal
                if (shouldAddBand(band, band.percentageAsDecimal)) bandBreakdown.add(BandBreakdown(band.percentageAsDecimal, taxForBand))
                amount += taxForBand
                return amount
            } else {
                val taxForBand = (band.upper - band.lower) * band.percentageAsDecimal
                if (shouldAddBand(band, band.percentageAsDecimal)) bandBreakdown.add(BandBreakdown(band.percentageAsDecimal, taxForBand))
                amount += taxForBand
            }
        }
        throw ConfigurationError("No tax bands were found to be used for the calculation")
    }

    private fun shouldAddBand(band: Band, percentage: Double): Boolean{
        return band is TaxBand && percentage > 0.0
    }

    fun run(): CalculatorResponse {
        val taxPayable = taxToPay()
        val employeesNI = employeeNIToPay()
        val employersNI = employerNIToPay()

        return CalculatorResponse(
            taxCode = taxCodeString,
            country = taxCode.country,
            isKCode = taxCode is KTaxCode,
            weekly = CalculatorResponsePayPeriod(
                payPeriod = WEEKLY,
                taxToPay = taxPayable.convertAmountFromYearlyToPayPeriod(WEEKLY),
                employeesNI = employeesNI.convertAmountFromYearlyToPayPeriod(WEEKLY),
                employersNI = employersNI.convertAmountFromYearlyToPayPeriod(WEEKLY),
                wages = yearlyWages.convertAmountFromYearlyToPayPeriod(WEEKLY),
                taxBreakdown = bandBreakdown.convertListOfBandBreakdownForPayPeriod(WEEKLY),
                taxFree = adjustTaxBands(taxBands)[0].upper.convertAmountFromYearlyToPayPeriod(WEEKLY),
                kCodeAdjustment = if (taxCode is KTaxCode) taxCode.amountToAddToWages.convertAmountFromYearlyToPayPeriod(WEEKLY) else null
            ),
            fourWeekly = CalculatorResponsePayPeriod(
                payPeriod = FOUR_WEEKLY,
                taxToPay = taxPayable.convertAmountFromYearlyToPayPeriod(FOUR_WEEKLY),
                employeesNI = employeesNI.convertAmountFromYearlyToPayPeriod(FOUR_WEEKLY),
                employersNI = employersNI.convertAmountFromYearlyToPayPeriod(FOUR_WEEKLY),
                wages = yearlyWages.convertAmountFromYearlyToPayPeriod(FOUR_WEEKLY),
                taxBreakdown = bandBreakdown.convertListOfBandBreakdownForPayPeriod(FOUR_WEEKLY),
                taxFree = adjustTaxBands(taxBands)[0].upper.convertAmountFromYearlyToPayPeriod(FOUR_WEEKLY),
                kCodeAdjustment = if (taxCode is KTaxCode) taxCode.amountToAddToWages.convertAmountFromYearlyToPayPeriod(FOUR_WEEKLY) else null

            ),
            monthly = CalculatorResponsePayPeriod(
                payPeriod = MONTHLY,
                taxToPay = taxPayable.convertAmountFromYearlyToPayPeriod(MONTHLY),
                employeesNI = employeesNI.convertAmountFromYearlyToPayPeriod(MONTHLY),
                employersNI = employersNI.convertAmountFromYearlyToPayPeriod(MONTHLY),
                wages = yearlyWages.convertAmountFromYearlyToPayPeriod(MONTHLY),
                taxBreakdown = bandBreakdown.convertListOfBandBreakdownForPayPeriod(MONTHLY),
                taxFree = adjustTaxBands(taxBands)[0].upper.convertAmountFromYearlyToPayPeriod(MONTHLY),
                kCodeAdjustment = if (taxCode is KTaxCode) taxCode.amountToAddToWages.convertAmountFromYearlyToPayPeriod(MONTHLY) else null
            ),
            yearly = CalculatorResponsePayPeriod(
                payPeriod = YEARLY,
                taxToPay = taxPayable.convertAmountFromYearlyToPayPeriod(YEARLY),
                employeesNI = employeesNI.convertAmountFromYearlyToPayPeriod(YEARLY),
                employersNI = employersNI.convertAmountFromYearlyToPayPeriod(YEARLY),
                wages = yearlyWages.convertAmountFromYearlyToPayPeriod(YEARLY),
                taxBreakdown = bandBreakdown,
                taxFree = adjustTaxBands(taxBands)[0].upper,
                kCodeAdjustment = if (taxCode is KTaxCode) taxCode.amountToAddToWages else null
            )
        )
    }
}