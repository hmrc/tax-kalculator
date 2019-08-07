/*
 * Copyright 2019 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package calculator

import calculator.model.BandBreakdown
import calculator.model.CalculatorResponse
import calculator.model.CalculatorResponsePayPeriod
import calculator.model.PayPeriod
import calculator.model.PayPeriod.MONTHLY
import calculator.model.bands.Band
import calculator.model.bands.EmployeeNIBand
import calculator.model.bands.EmployeeNIBands
import calculator.model.bands.EmployerNIBand
import calculator.model.bands.EmployerNIBands
import calculator.model.bands.TaxBand
import calculator.model.bands.TaxBands
import calculator.model.taxcodes.AdjustedTaxFreeTCode
import calculator.model.taxcodes.EmergencyTaxCode
import calculator.model.taxcodes.KTaxCode
import calculator.model.taxcodes.MarriageTaxCodes
import calculator.model.taxcodes.NoTaxTaxCode
import calculator.model.taxcodes.SingleBandTax
import calculator.model.taxcodes.StandardTaxCode
import calculator.model.taxcodes.TaxCode
import calculator.utils.ConfigurationError
import calculator.utils.InvalidTaxCode
import calculator.utils.TaxYear
import calculator.utils.convertAmountFromYearlyToPayPeriod
import calculator.utils.convertListOfBandBreakdownForPayPeriod
import calculator.utils.convertWageToYearly
import calculator.utils.getDefaultTaxAllowance
import calculator.utils.toTaxCode

class CalculatorHelper {
    fun getDefaultTaxCode(): String {
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
        getDefaultTaxAllowance(taxYear, taxCode.country) // The full tax free amount e.g. 12509
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
                if (shouldAddBand(band, band.percentageAsDecimal))
                    bandBreakdown.add(BandBreakdown(band.percentageAsDecimal, taxForBand))
                amount += taxForBand
                return amount
            } else {
                val taxForBand = (band.upper - band.lower) * band.percentageAsDecimal
                if (shouldAddBand(band, band.percentageAsDecimal))
                    bandBreakdown.add(BandBreakdown(band.percentageAsDecimal, taxForBand))
                amount += taxForBand
            }
        }
        throw ConfigurationError("No tax bands were found to be used for the calculation")
    }

    private fun shouldAddBand(band: Band, percentage: Double): Boolean {
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
                payPeriod = PayPeriod.WEEKLY,
                taxToPay = taxPayable.convertAmountFromYearlyToPayPeriod(PayPeriod.WEEKLY),
                employeesNI = employeesNI.convertAmountFromYearlyToPayPeriod(PayPeriod.WEEKLY),
                employersNI = employersNI.convertAmountFromYearlyToPayPeriod(PayPeriod.WEEKLY),
                wages = yearlyWages.convertAmountFromYearlyToPayPeriod(PayPeriod.WEEKLY),
                taxBreakdown = bandBreakdown.convertListOfBandBreakdownForPayPeriod(PayPeriod.WEEKLY),
                taxFree = adjustTaxBands(taxBands)[0].upper.convertAmountFromYearlyToPayPeriod(PayPeriod.WEEKLY),
                kCodeAdjustment = if (taxCode is KTaxCode)
                    taxCode.amountToAddToWages.convertAmountFromYearlyToPayPeriod(PayPeriod.WEEKLY)
                else null
            ),
            fourWeekly = CalculatorResponsePayPeriod(
                payPeriod = PayPeriod.FOUR_WEEKLY,
                taxToPay = taxPayable.convertAmountFromYearlyToPayPeriod(PayPeriod.FOUR_WEEKLY),
                employeesNI = employeesNI.convertAmountFromYearlyToPayPeriod(PayPeriod.FOUR_WEEKLY),
                employersNI = employersNI.convertAmountFromYearlyToPayPeriod(PayPeriod.FOUR_WEEKLY),
                wages = yearlyWages.convertAmountFromYearlyToPayPeriod(PayPeriod.FOUR_WEEKLY),
                taxBreakdown = bandBreakdown.convertListOfBandBreakdownForPayPeriod(PayPeriod.FOUR_WEEKLY),
                taxFree = adjustTaxBands(taxBands)[0].upper.convertAmountFromYearlyToPayPeriod(PayPeriod.FOUR_WEEKLY),
                kCodeAdjustment = if (taxCode is KTaxCode)
                    taxCode.amountToAddToWages.convertAmountFromYearlyToPayPeriod(PayPeriod.FOUR_WEEKLY)
                else null

            ),
            monthly = CalculatorResponsePayPeriod(
                payPeriod = MONTHLY,
                taxToPay = taxPayable.convertAmountFromYearlyToPayPeriod(MONTHLY),
                employeesNI = employeesNI.convertAmountFromYearlyToPayPeriod(MONTHLY),
                employersNI = employersNI.convertAmountFromYearlyToPayPeriod(MONTHLY),
                wages = yearlyWages.convertAmountFromYearlyToPayPeriod(MONTHLY),
                taxBreakdown = bandBreakdown.convertListOfBandBreakdownForPayPeriod(MONTHLY),
                taxFree = adjustTaxBands(taxBands)[0].upper.convertAmountFromYearlyToPayPeriod(MONTHLY),
                kCodeAdjustment = if (taxCode is KTaxCode)
                    taxCode.amountToAddToWages.convertAmountFromYearlyToPayPeriod(MONTHLY)
                else null
            ),
            yearly = CalculatorResponsePayPeriod(
                payPeriod = PayPeriod.YEARLY,
                taxToPay = taxPayable.convertAmountFromYearlyToPayPeriod(PayPeriod.YEARLY),
                employeesNI = employeesNI.convertAmountFromYearlyToPayPeriod(PayPeriod.YEARLY),
                employersNI = employersNI.convertAmountFromYearlyToPayPeriod(PayPeriod.YEARLY),
                wages = yearlyWages.convertAmountFromYearlyToPayPeriod(PayPeriod.YEARLY),
                taxBreakdown = bandBreakdown,
                taxFree = adjustTaxBands(taxBands)[0].upper,
                kCodeAdjustment = if (taxCode is KTaxCode) taxCode.amountToAddToWages else null
            )
        )
    }
}
