import com.soywiz.klock.*
import model.CalculatorResponse
import model.CalculatorResponsePayPeriod
import model.PayPeriod
import model.PayPeriod.*
import model.bands.*
import model.taxcodes.*
import utils.WageConverter
import utils.getDefaultTaxAllowance
import utils.toTaxCode

class TaxYear {
    fun currentTaxYear(): Int {
        val date = DateTime.nowLocal()
        return if (date < firstDayOfTaxYear(date.yearInt))
            date.yearInt - 1
        else
            date.yearInt
    }

    private fun firstDayOfTaxYear(year: Int): DateTimeTz {
        return DateTime(year = year, month = 4, day = 6).local //TODO check library for details about the `.local`
    }
}

class Calculator(
    taxCodeString: String,
    userEnteredWages: Double,
    payPeriod: PayPeriod,
    private val pensionAge: Boolean = false,
    hoursPerWeek: Double = 0.0,
    taxYear: Int = TaxYear().currentTaxYear()
) {
    private val originalWages: Double = WageConverter().convertWageToYearly(payPeriod, hoursPerWeek, userEnteredWages)
    private val taxCode: TaxCode = taxCodeString.toTaxCode()
    private val bandAdjuster: Int =
        getDefaultTaxAllowance(taxYear, taxCode.country) //The full tax free amount e.g. 12509
    private val taxBands: List<Band> = TaxBands(taxCode.country, taxYear).bands
    private val employeeNIBands: List<EmployeeNIBand> = EmployeeNIBands(taxYear).bands
    private val employerNIBands: List<EmployerNIBand> = EmployerNIBands(taxYear).bands

    private fun taxToPay(): Double {
        return when (taxCode) {
            is StandardTaxCode -> {
                getTotalFromBands(adjustTaxBands(taxBands), originalWages)
            }
            is NoTaxTaxCode -> {
                originalWages * taxCode.taxFreeAmount
            }
            is SingleBandTax -> {
                originalWages * taxBands[taxCode.taxAllAtBand].percentageAsDecimal
            }
            is AdjustedTaxFreeTCode -> {
                getTotalFromBands(adjustTaxBands(taxBands), originalWages)
            }
            is EmergencyTaxCode -> {
                getTotalFromBands(adjustTaxBands(taxBands), originalWages)
            }

            is MarriageTaxCodes -> {
                getTotalFromBands(adjustTaxBands(taxBands), originalWages)
            }
            is KTaxCode -> {
                getTotalFromBands(adjustTaxBands(taxBands), originalWages + taxCode.amountToAddToWages)
            }
            else -> throw IllegalStateException("Unknown tax code")

        }
    }

    private fun adjustTaxBands(taxBands: List<Band>): List<Band> {
        taxBands[0].upper = taxCode.taxFreeAmount
        taxBands[1].lower = taxCode.taxFreeAmount
        taxBands[1].upper = taxBands[1].upper + taxCode.taxFreeAmount - bandAdjuster

        for (bandNumber in 2 until taxBands.size) {
            taxBands[bandNumber].lower = taxBands[bandNumber].lower + taxCode.taxFreeAmount - bandAdjuster
            taxBands[bandNumber].upper = taxBands[bandNumber].upper + taxCode.taxFreeAmount - bandAdjuster
        }
        return taxBands
    }

    private fun employerNIToPay(): Double {
        return if (pensionAge) 0.0 else getTotalFromBands(employerNIBands, originalWages)
    }

    private fun employeeNIToPay(): Double {
        return if (pensionAge) 0.0 else getTotalFromBands(employeeNIBands, originalWages)
    }

    private fun getTotalFromBands(bands: List<Band>, wages: Double): Double {
        var amount = 0.0
        bands.map { band: Band ->
            if (band.inBand(wages)) {
                amount += (wages - band.lower) * band.percentageAsDecimal
                return amount
            } else {
                amount += (band.upper - band.lower) * band.percentageAsDecimal
            }
        }
        throw IllegalStateException("No tax bands were found to be used for the calculation")
    }

    fun run(): CalculatorResponse {
        val taxPayable = this.taxToPay()
        val employeesNI = this.employeeNIToPay()
        val employersNI = this.employerNIToPay()

        return CalculatorResponse(
            weekly = CalculatorResponsePayPeriod(
                taxToPay = WageConverter().convertAmountFromYearlyToPayPeriod(WEEKLY, amount = taxPayable),
                employeesNI = WageConverter().convertAmountFromYearlyToPayPeriod(WEEKLY, amount = employeesNI),
                employersNI = WageConverter().convertAmountFromYearlyToPayPeriod(WEEKLY, amount = employersNI)
            ),
            fourWeekly = CalculatorResponsePayPeriod(
                taxToPay = WageConverter().convertAmountFromYearlyToPayPeriod(FOUR_WEEKLY, amount = taxPayable),
                employeesNI = WageConverter().convertAmountFromYearlyToPayPeriod(FOUR_WEEKLY, amount = employeesNI),
                employersNI = WageConverter().convertAmountFromYearlyToPayPeriod(FOUR_WEEKLY, amount = employersNI)
            ),
            monthly = CalculatorResponsePayPeriod(
                taxToPay = WageConverter().convertAmountFromYearlyToPayPeriod(MONTHLY, amount = taxPayable),
                employeesNI = WageConverter().convertAmountFromYearlyToPayPeriod(MONTHLY, amount = employeesNI),
                employersNI = WageConverter().convertAmountFromYearlyToPayPeriod(MONTHLY, amount = employersNI)
            ),
            yearly = CalculatorResponsePayPeriod(
                taxToPay = WageConverter().convertAmountFromYearlyToPayPeriod(YEARLY, amount = taxPayable),
                employeesNI = WageConverter().convertAmountFromYearlyToPayPeriod(YEARLY, amount = employeesNI),
                employersNI = WageConverter().convertAmountFromYearlyToPayPeriod(YEARLY, amount = employersNI)
            )
        )
    }
}