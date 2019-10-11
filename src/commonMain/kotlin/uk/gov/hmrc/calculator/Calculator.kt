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
package uk.gov.hmrc.calculator

import uk.gov.hmrc.calculator.model.BandBreakdown
import uk.gov.hmrc.calculator.model.CalculatorResponse
import uk.gov.hmrc.calculator.model.CalculatorResponsePayPeriod
import uk.gov.hmrc.calculator.model.Country
import uk.gov.hmrc.calculator.model.Country.ENGLAND
import uk.gov.hmrc.calculator.model.PayPeriod
import uk.gov.hmrc.calculator.model.PayPeriod.FOUR_WEEKLY
import uk.gov.hmrc.calculator.model.PayPeriod.MONTHLY
import uk.gov.hmrc.calculator.model.PayPeriod.WEEKLY
import uk.gov.hmrc.calculator.model.PayPeriod.YEARLY
import uk.gov.hmrc.calculator.model.bands.Band
import uk.gov.hmrc.calculator.model.bands.EmployeeNIBands
import uk.gov.hmrc.calculator.model.bands.EmployerNIBands
import uk.gov.hmrc.calculator.model.bands.TaxBand
import uk.gov.hmrc.calculator.model.bands.TaxBands
import uk.gov.hmrc.calculator.model.taxcodes.AdjustedTaxFreeTCode
import uk.gov.hmrc.calculator.model.taxcodes.EmergencyTaxCode
import uk.gov.hmrc.calculator.model.taxcodes.KTaxCode
import uk.gov.hmrc.calculator.model.taxcodes.MarriageTaxCodes
import uk.gov.hmrc.calculator.model.taxcodes.NoTaxTaxCode
import uk.gov.hmrc.calculator.model.taxcodes.SingleBandTax
import uk.gov.hmrc.calculator.model.taxcodes.StandardTaxCode
import uk.gov.hmrc.calculator.exception.InvalidTaxBandException
import uk.gov.hmrc.calculator.exception.InvalidTaxCodeException
import uk.gov.hmrc.calculator.utils.TaxYear
import uk.gov.hmrc.calculator.utils.convertAmountFromYearlyToPayPeriod
import uk.gov.hmrc.calculator.utils.convertListOfBandBreakdownForPayPeriod
import uk.gov.hmrc.calculator.utils.convertWageToYearly
import uk.gov.hmrc.calculator.utils.toTaxCode

class Calculator(
    private val taxCodeString: String,
    userEnteredWages: Double,
    payPeriod: PayPeriod,
    private val pensionAge: Boolean = false,
    hoursPerWeek: Double? = null,
    taxYear: Int = TaxYear().currentTaxYear()
) {
    private val bandBreakdown: MutableList<BandBreakdown> = mutableListOf()

    private val yearlyWages = userEnteredWages.convertWageToYearly(payPeriod, hoursPerWeek)

    private val taxCode = taxCodeString.toTaxCode()

    // The full tax free amount e.g. 12509
    private val bandAdjuster = getDefaultTaxAllowance(taxYear, taxCode.country)

    private val taxBands = TaxBands(taxCode.country, taxYear).bands

    private val employeeNIBands = EmployeeNIBands(taxYear).bands

    private val employerNIBands = EmployerNIBands(taxYear).bands

    private fun taxToPay(): Double {
        return when (taxCode) {
            is StandardTaxCode -> getTotalFromBands(adjustTaxBands(taxBands), yearlyWages)
            is NoTaxTaxCode -> yearlyWages * taxCode.taxFreeAmount
            is SingleBandTax -> yearlyWages * taxBands[taxCode.taxAllAtBand].percentageAsDecimal
            is AdjustedTaxFreeTCode -> getTotalFromBands(adjustTaxBands(taxBands), yearlyWages)
            is EmergencyTaxCode -> getTotalFromBands(adjustTaxBands(taxBands), yearlyWages)
            is MarriageTaxCodes -> getTotalFromBands(adjustTaxBands(taxBands), yearlyWages)
            is KTaxCode -> getTotalFromBands(adjustTaxBands(taxBands), yearlyWages + taxCode.amountToAddToWages)
            else -> throw InvalidTaxCodeException("$this is an invalid tax code")
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

    private fun employerNIToPay() = if (pensionAge) 0.0 else getTotalFromBands(employerNIBands, yearlyWages)

    private fun employeeNIToPay() = if (pensionAge) 0.0 else getTotalFromBands(employeeNIBands, yearlyWages)

    private fun getTotalFromBands(bands: List<Band>, wages: Double): Double {
        var amount = 0.0
        bands.map { band: Band ->
            if (band.inBand(wages)) {
                val taxForBand = (wages - band.lower) * band.percentageAsDecimal
                if (shouldAddBand(band, band.percentageAsDecimal)) {
                    bandBreakdown.add(BandBreakdown(band.percentageAsDecimal, taxForBand))
                }
                amount += taxForBand
                return amount
            } else {
                val taxForBand = (band.upper - band.lower) * band.percentageAsDecimal
                if (shouldAddBand(band, band.percentageAsDecimal)) {
                    bandBreakdown.add(BandBreakdown(band.percentageAsDecimal, taxForBand))
                }
                amount += taxForBand
            }
        }
        throw InvalidTaxBandException("No tax bands were found to be used for the calculation")
    }

    private fun shouldAddBand(band: Band, percentage: Double) = band is TaxBand && percentage > 0.0

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
                kCodeAdjustment = if (taxCode is KTaxCode) {
                    taxCode.amountToAddToWages.convertAmountFromYearlyToPayPeriod(WEEKLY)
                } else null
            ),
            fourWeekly = CalculatorResponsePayPeriod(
                payPeriod = FOUR_WEEKLY,
                taxToPay = taxPayable.convertAmountFromYearlyToPayPeriod(FOUR_WEEKLY),
                employeesNI = employeesNI.convertAmountFromYearlyToPayPeriod(FOUR_WEEKLY),
                employersNI = employersNI.convertAmountFromYearlyToPayPeriod(FOUR_WEEKLY),
                wages = yearlyWages.convertAmountFromYearlyToPayPeriod(FOUR_WEEKLY),
                taxBreakdown = bandBreakdown.convertListOfBandBreakdownForPayPeriod(FOUR_WEEKLY),
                taxFree = adjustTaxBands(taxBands)[0].upper.convertAmountFromYearlyToPayPeriod(FOUR_WEEKLY),
                kCodeAdjustment = if (taxCode is KTaxCode) {
                    taxCode.amountToAddToWages.convertAmountFromYearlyToPayPeriod(FOUR_WEEKLY)
                } else null
            ),
            monthly = CalculatorResponsePayPeriod(
                payPeriod = MONTHLY,
                taxToPay = taxPayable.convertAmountFromYearlyToPayPeriod(MONTHLY),
                employeesNI = employeesNI.convertAmountFromYearlyToPayPeriod(MONTHLY),
                employersNI = employersNI.convertAmountFromYearlyToPayPeriod(MONTHLY),
                wages = yearlyWages.convertAmountFromYearlyToPayPeriod(MONTHLY),
                taxBreakdown = bandBreakdown.convertListOfBandBreakdownForPayPeriod(MONTHLY),
                taxFree = adjustTaxBands(taxBands)[0].upper.convertAmountFromYearlyToPayPeriod(MONTHLY),
                kCodeAdjustment = if (taxCode is KTaxCode) {
                    taxCode.amountToAddToWages.convertAmountFromYearlyToPayPeriod(MONTHLY)
                } else null
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

    companion object {
        fun getDefaultTaxCode() = "${(getDefaultTaxAllowance(TaxYear().currentTaxYear()) / 10)}L"

        internal fun getDefaultTaxAllowance(taxYear: Int, country: Country = ENGLAND) =
            TaxBands(country, taxYear).bands[0].upper.toInt()
    }
}
