/*
 * Copyright 2022 HM Revenue & Customs
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

import uk.gov.hmrc.calculator.exception.InvalidHoursException
import uk.gov.hmrc.calculator.exception.InvalidPayPeriodException
import uk.gov.hmrc.calculator.exception.InvalidPensionException
import uk.gov.hmrc.calculator.exception.InvalidTaxBandException
import uk.gov.hmrc.calculator.exception.InvalidTaxCodeException
import uk.gov.hmrc.calculator.exception.InvalidTaxYearException
import uk.gov.hmrc.calculator.exception.InvalidWagesException
import uk.gov.hmrc.calculator.model.BandBreakdown
import uk.gov.hmrc.calculator.model.CalculatorResponse
import uk.gov.hmrc.calculator.model.CalculatorResponsePayPeriod
import uk.gov.hmrc.calculator.model.PayPeriod
import uk.gov.hmrc.calculator.model.PayPeriod.FOUR_WEEKLY
import uk.gov.hmrc.calculator.model.PayPeriod.MONTHLY
import uk.gov.hmrc.calculator.model.PayPeriod.WEEKLY
import uk.gov.hmrc.calculator.model.PayPeriod.YEARLY
import uk.gov.hmrc.calculator.model.StudentLoanAmountBreakdown
import uk.gov.hmrc.calculator.model.TaxYear
import uk.gov.hmrc.calculator.model.bands.Band
import uk.gov.hmrc.calculator.model.bands.EmployeeNIBands
import uk.gov.hmrc.calculator.model.bands.EmployerNIBands
import uk.gov.hmrc.calculator.model.bands.TaxBands
import uk.gov.hmrc.calculator.model.pension.PensionMethod
import uk.gov.hmrc.calculator.model.pension.calculateYearlyPension
import uk.gov.hmrc.calculator.model.studentloans.StudentLoanCalculation
import uk.gov.hmrc.calculator.model.taxcodes.AdjustedTaxFreeTCode
import uk.gov.hmrc.calculator.model.taxcodes.EmergencyTaxCode
import uk.gov.hmrc.calculator.model.taxcodes.KTaxCode
import uk.gov.hmrc.calculator.model.taxcodes.MarriageTaxCodes
import uk.gov.hmrc.calculator.model.taxcodes.NoTaxTaxCode
import uk.gov.hmrc.calculator.model.taxcodes.SingleBandTax
import uk.gov.hmrc.calculator.model.taxcodes.StandardTaxCode
import uk.gov.hmrc.calculator.model.taxcodes.TaxCode
import uk.gov.hmrc.calculator.utils.clarification.Clarification
import uk.gov.hmrc.calculator.utils.convertAmountFromYearlyToPayPeriod
import uk.gov.hmrc.calculator.utils.convertListOfBandBreakdownForPayPeriod
import uk.gov.hmrc.calculator.utils.convertWageToYearly
import uk.gov.hmrc.calculator.utils.studentloan.convertBreakdownForPayPeriod
import uk.gov.hmrc.calculator.utils.tapering.deductTapering
import uk.gov.hmrc.calculator.utils.tapering.getTaperingAmount
import uk.gov.hmrc.calculator.utils.tapering.shouldApplyStandardTapering
import uk.gov.hmrc.calculator.utils.tapering.yearlyWageIsAboveHundredThousand
import uk.gov.hmrc.calculator.utils.taxcode.getTaxCodeClarification
import uk.gov.hmrc.calculator.utils.taxcode.getTrueTaxFreeAmount
import uk.gov.hmrc.calculator.utils.taxcode.toTaxCode
import uk.gov.hmrc.calculator.utils.validation.PensionValidator
import uk.gov.hmrc.calculator.utils.validation.WageValidator
import kotlin.jvm.JvmOverloads

class Calculator @JvmOverloads constructor(
    private val taxCode: String,
    private val userPaysScottishTax: Boolean = false,
    private val userSuppliedTaxCode: Boolean = true,
    private val wages: Double,
    private val payPeriod: PayPeriod,
    private val isPensionAge: Boolean = false,
    private val howManyAWeek: Double? = null,
    private val taxYear: TaxYear = TaxYear.currentTaxYear,
    private val pensionContribution: PensionContribution? = null,
    private val studentLoanPlans: StudentLoanPlans? = null,
) {

    private val bandBreakdown: MutableList<BandBreakdown> = mutableListOf()

    private val listOfClarification: MutableList<Clarification> = mutableListOf()

    init {
        if (!userSuppliedTaxCode) listOfClarification.add(Clarification.NO_TAX_CODE_SUPPLIED)
        val pensionClarification = if (isPensionAge)
            Clarification.HAVE_STATE_PENSION else Clarification.HAVE_NO_STATE_PENSION
        listOfClarification.add(pensionClarification)
    }

    @Throws(
        InvalidTaxCodeException::class,
        InvalidTaxYearException::class,
        InvalidWagesException::class,
        InvalidPayPeriodException::class,
        InvalidHoursException::class,
        InvalidTaxBandException::class,
        InvalidPensionException::class,
    )

    fun run(): CalculatorResponse {
        if (!WageValidator.isAboveMinimumWages(wages) || !WageValidator.isBelowMaximumWages(wages)) {
            throw InvalidWagesException("Wages must be between 0 and 9999999.99")
        }

        val yearlyWages = wages.convertWageToYearly(payPeriod, howManyAWeek)

        val amountToAddToWages = if (taxCodeType is KTaxCode) {
            listOfClarification.add(Clarification.K_CODE)
            (taxCodeType as KTaxCode).amountToAddToWages
        } else null

        val yearlyPensionContribution = pensionContribution?.let { calculateYearlyPension(yearlyWages, it) }
        validatePensionContribution(yearlyPensionContribution, yearlyWages)
        val yearlyWageAfterPension = yearlyPensionContribution?.let { yearlyWages - it } ?: yearlyWages
        val (taxFreeAmount, taperingAmount) = getTaxFreeAndTaperingAmount(yearlyWageAfterPension)

        val studentLoan = StudentLoanCalculation(taxYear, yearlyWages, studentLoanPlans)
        studentLoan.studentLoanClarification?.let { listOfClarification.add(it) }

        taxCodeType.getTaxCodeClarification(userPaysScottishTax)?.let { listOfClarification.add(it) }

        return createResponse(
            taxCodeType,
            yearlyWages,
            taxFreeAmount,
            amountToAddToWages,
            yearlyPensionContribution,
            yearlyWageAfterPension,
            taperingAmount,
            studentLoan.listOfBreakdownResult,
            studentLoan.getStudentLoanDeduction(),
            studentLoan.getPostgraduateLoanDeduction(),
            listOfClarification,
        )
    }

    private fun getTaxFreeAndTaperingAmount(yearlyWageAfterPension: Double): Pair<Double, Double?> {
        return if (yearlyWageAfterPension.yearlyWageIsAboveHundredThousand()) {
            if (yearlyWageAfterPension.shouldApplyStandardTapering(taxCodeType, userSuppliedTaxCode)) {
                listOfClarification.add(Clarification.INCOME_OVER_100K_WITH_TAPERING)
                Pair(
                    taxCodeType.getTrueTaxFreeAmount().deductTapering(yearlyWageAfterPension),
                    // for calculation purpose, we use the taxFreeAmount (which include the £9) to calculate.
                    yearlyWageAfterPension.getTaperingAmount(taxCodeType.taxFreeAmount)
                )
            } else {
                listOfClarification.add(Clarification.INCOME_OVER_100K)
                Pair(taxCodeType.getTrueTaxFreeAmount(), null)
            }
        } else {
            Pair(taxCodeType.getTrueTaxFreeAmount(), null)
        }
    }

    private fun validatePensionContribution(yearlyPensionContribution: Double?, yearlyWages: Double) {
        yearlyPensionContribution?.let { yearlyPension ->
            if (!PensionValidator.isPensionLowerThenWage(yearlyPension, yearlyWages)) {
                listOfClarification.add(Clarification.PENSION_EXCEED_INCOME)
                throw InvalidPensionException("Pension must be lower then your yearly wage")
            }
            if (PensionValidator.isPensionAboveAnnualAllowance(yearlyPension, taxYear)) {
                listOfClarification.add(Clarification.PENSION_EXCEED_ANNUAL_ALLOWANCE)
            } else {
                listOfClarification.add(Clarification.PENSION_BELOW_ANNUAL_ALLOWANCE)
            }
        }
    }

    @Suppress("LongMethod", "LongParameterList")
    private fun createResponse(
        taxCode: TaxCode,
        yearlyWages: Double,
        taxFreeAmount: Double,
        amountToAddToWages: Double?,
        yearlyPensionContribution: Double?,
        yearlyWageAfterPensionDeduction: Double,
        taperingAmountDeduction: Double?,
        studentLoanBreakdown: MutableList<StudentLoanAmountBreakdown>,
        finalStudentLoanAmount: Double,
        finalPostgraduateLoanAmount: Double,
        listOfClarification: MutableList<Clarification>,
    ): CalculatorResponse {
        val taxPayable = taxToPay(
            yearlyWageAfterPensionDeduction,
            taxCode,
            taperingAmountDeduction,
        )
        val (employeesNI, employersNI) = if (isPensionAge) {
            Pair(0.0, 0.0)
        } else {
            Pair(
                EmployeeNIBands(taxYear).bands.getTotalFromNIBands(yearlyWages),
                EmployerNIBands(taxYear).bands.getTotalFromNIBands(yearlyWages)
            )
        }
        return CalculatorResponse(
            country = taxCode.country,
            isKCode = taxCode is KTaxCode,
            weekly = CalculatorResponsePayPeriod(
                payPeriod = WEEKLY,
                taxToPayForPayPeriod = taxPayable.convertAmountFromYearlyToPayPeriod(WEEKLY),
                employeesNIRaw = employeesNI.convertAmountFromYearlyToPayPeriod(WEEKLY),
                employersNIRaw = employersNI.convertAmountFromYearlyToPayPeriod(WEEKLY),
                wagesRaw = yearlyWages.convertAmountFromYearlyToPayPeriod(WEEKLY),
                taxBreakdownForPayPeriod = bandBreakdown.convertListOfBandBreakdownForPayPeriod(WEEKLY),
                taxFreeRaw = taxFreeAmount.convertAmountFromYearlyToPayPeriod(WEEKLY),
                kCodeAdjustmentRaw = amountToAddToWages?.convertAmountFromYearlyToPayPeriod(WEEKLY),
                pensionContributionRaw = yearlyPensionContribution?.convertAmountFromYearlyToPayPeriod(WEEKLY),
                wageAfterPensionDeductionRaw =
                yearlyWageAfterPensionDeduction.convertAmountFromYearlyToPayPeriod(WEEKLY),
                taperingAmountRaw = taperingAmountDeduction?.convertAmountFromYearlyToPayPeriod(WEEKLY),
                studentLoanBreakdownList = studentLoanBreakdown.convertBreakdownForPayPeriod(WEEKLY),
                finalStudentLoanAmountRaw = finalStudentLoanAmount.convertAmountFromYearlyToPayPeriod(WEEKLY),
                finalPostgraduateLoanAmountRaw = finalPostgraduateLoanAmount.convertAmountFromYearlyToPayPeriod(WEEKLY)
            ),
            fourWeekly = CalculatorResponsePayPeriod(
                payPeriod = FOUR_WEEKLY,
                taxToPayForPayPeriod = taxPayable.convertAmountFromYearlyToPayPeriod(FOUR_WEEKLY),
                employeesNIRaw = employeesNI.convertAmountFromYearlyToPayPeriod(FOUR_WEEKLY),
                employersNIRaw = employersNI.convertAmountFromYearlyToPayPeriod(FOUR_WEEKLY),
                wagesRaw = yearlyWages.convertAmountFromYearlyToPayPeriod(FOUR_WEEKLY),
                taxBreakdownForPayPeriod = bandBreakdown.convertListOfBandBreakdownForPayPeriod(FOUR_WEEKLY),
                taxFreeRaw = taxFreeAmount.convertAmountFromYearlyToPayPeriod(FOUR_WEEKLY),
                kCodeAdjustmentRaw = amountToAddToWages?.convertAmountFromYearlyToPayPeriod(FOUR_WEEKLY),
                pensionContributionRaw = yearlyPensionContribution?.convertAmountFromYearlyToPayPeriod(FOUR_WEEKLY),
                wageAfterPensionDeductionRaw =
                yearlyWageAfterPensionDeduction.convertAmountFromYearlyToPayPeriod(FOUR_WEEKLY),
                taperingAmountRaw = taperingAmountDeduction?.convertAmountFromYearlyToPayPeriod(FOUR_WEEKLY),
                studentLoanBreakdownList = studentLoanBreakdown.convertBreakdownForPayPeriod(FOUR_WEEKLY),
                finalStudentLoanAmountRaw = finalStudentLoanAmount.convertAmountFromYearlyToPayPeriod(FOUR_WEEKLY),
                finalPostgraduateLoanAmountRaw = finalPostgraduateLoanAmount.convertAmountFromYearlyToPayPeriod(
                    FOUR_WEEKLY
                ),
            ),
            monthly = CalculatorResponsePayPeriod(
                payPeriod = MONTHLY,
                taxToPayForPayPeriod = taxPayable.convertAmountFromYearlyToPayPeriod(MONTHLY),
                employeesNIRaw = employeesNI.convertAmountFromYearlyToPayPeriod(MONTHLY),
                employersNIRaw = employersNI.convertAmountFromYearlyToPayPeriod(MONTHLY),
                wagesRaw = yearlyWages.convertAmountFromYearlyToPayPeriod(MONTHLY),
                taxBreakdownForPayPeriod = bandBreakdown.convertListOfBandBreakdownForPayPeriod(MONTHLY),
                taxFreeRaw = taxFreeAmount.convertAmountFromYearlyToPayPeriod(MONTHLY),
                kCodeAdjustmentRaw = amountToAddToWages?.convertAmountFromYearlyToPayPeriod(MONTHLY),
                pensionContributionRaw = yearlyPensionContribution?.convertAmountFromYearlyToPayPeriod(MONTHLY),
                wageAfterPensionDeductionRaw =
                yearlyWageAfterPensionDeduction.convertAmountFromYearlyToPayPeriod(MONTHLY),
                taperingAmountRaw = taperingAmountDeduction?.convertAmountFromYearlyToPayPeriod(MONTHLY),
                studentLoanBreakdownList = studentLoanBreakdown.convertBreakdownForPayPeriod(MONTHLY),
                finalStudentLoanAmountRaw = finalStudentLoanAmount.convertAmountFromYearlyToPayPeriod(MONTHLY),
                finalPostgraduateLoanAmountRaw = finalPostgraduateLoanAmount
                    .convertAmountFromYearlyToPayPeriod(MONTHLY),
            ),
            yearly = CalculatorResponsePayPeriod(
                payPeriod = YEARLY,
                taxToPayForPayPeriod = taxPayable.convertAmountFromYearlyToPayPeriod(YEARLY),
                employeesNIRaw = employeesNI.convertAmountFromYearlyToPayPeriod(YEARLY),
                employersNIRaw = employersNI.convertAmountFromYearlyToPayPeriod(YEARLY),
                wagesRaw = yearlyWages.convertAmountFromYearlyToPayPeriod(YEARLY),
                taxBreakdownForPayPeriod = bandBreakdown,
                taxFreeRaw = taxFreeAmount,
                kCodeAdjustmentRaw = amountToAddToWages,
                pensionContributionRaw = yearlyPensionContribution?.convertAmountFromYearlyToPayPeriod(YEARLY),
                wageAfterPensionDeductionRaw =
                yearlyWageAfterPensionDeduction.convertAmountFromYearlyToPayPeriod(YEARLY),
                taperingAmountRaw = taperingAmountDeduction?.convertAmountFromYearlyToPayPeriod(YEARLY),
                studentLoanBreakdownList = studentLoanBreakdown.convertBreakdownForPayPeriod(YEARLY),
                finalStudentLoanAmountRaw = finalStudentLoanAmount.convertAmountFromYearlyToPayPeriod(YEARLY),
                finalPostgraduateLoanAmountRaw = finalPostgraduateLoanAmount.convertAmountFromYearlyToPayPeriod(YEARLY),
            ),
            listOfClarification = listOfClarification
        )
    }

    private fun taxToPay(
        yearlyWageAfterPension: Double,
        taxCode: TaxCode,
        taperingAmountDeduction: Double? = null,
    ): Double {

        return when (taxCode) {
            is StandardTaxCode, is AdjustedTaxFreeTCode, is EmergencyTaxCode, is MarriageTaxCodes -> {
                val taxBands = TaxBands.getBands(
                    taxYear,
                    taxCode.country
                )
                getTotalFromTaxBands(
                    taxBands,
                    yearlyWageAfterPension,
                    taperingAmountDeduction
                )
            }

            is NoTaxTaxCode -> getTotalFromSingleBand(
                yearlyWageAfterPension,
                taxCode.taxFreeAmount
            )

            is SingleBandTax -> {
                val taxBands = TaxBands.getBands(
                    taxYear,
                    taxCode.country
                )
                getTotalFromSingleBand(
                    yearlyWageAfterPension,
                    taxBands[taxCode.taxAllAtBand].percentageAsDecimal
                )
            }

            is KTaxCode -> {
                val taxBands = TaxBands.getBands(
                    taxYear,
                    taxCode.country
                )
                getTotalFromTaxBands(
                    taxBands,
                    yearlyWageAfterPension + taxCode.amountToAddToWages
                )
            }

            else -> throw InvalidTaxCodeException("$this is an invalid tax code")
        }
    }

    private fun getTotalFromSingleBand(
        yearlyWage: Double,
        percentageForSingleBand: Double,
    ): Double {
        val taxToPayForSingleBand: Double = yearlyWage * percentageForSingleBand
        bandBreakdown.add(
            BandBreakdown(
                percentageForSingleBand,
                taxToPayForSingleBand
            )
        )
        return taxToPayForSingleBand
    }

    private fun getTotalFromTaxBands(
        bands: List<Band>,
        wages: Double,
        taperingAmountDeduction: Double? = null,
    ): Double {
        var amount = 0.0
        val taxFreeAmount = if (taperingAmountDeduction != null) taxCodeType.taxFreeAmount - taperingAmountDeduction
        else taxCodeType.taxFreeAmount

        var remainingToTax = wages - taxFreeAmount
        bands.map { band: Band ->
            if (remainingToTax <= 0) return amount

            val toTax = if ((band.upper - band.lower) < remainingToTax && band.upper != -1.0) {
                band.upper - band.lower
            } else {
                remainingToTax
            }
            val taxForBand = toTax * band.percentageAsDecimal
            bandBreakdown.add(
                BandBreakdown(
                    band.percentageAsDecimal,
                    taxForBand
                )
            )

            remainingToTax -= toTax
            amount += taxForBand
        }
        return amount
    }

    private fun List<Band>.getTotalFromNIBands(wages: Double): Double {
        var amount = 0.0
        var remainingToTax = wages - this[0].lower
        this.map { band: Band ->
            if (remainingToTax <= 0) return amount

            val toTax = if ((band.upper - band.lower) < remainingToTax && band.upper != -1.0) {
                band.upper - band.lower // Bandwidth
            } else {
                remainingToTax
            }
            val taxForBand = toTax * band.percentageAsDecimal

            remainingToTax -= toTax
            amount += taxForBand
        }
        return amount
    }

    private val taxCodeType: TaxCode by lazy {
        this.taxCode.toTaxCode()
    }

    data class StudentLoanPlans(
        val hasPlanOne: Boolean = false,
        val hasPlanTwo: Boolean = false,
        val hasPlanFour: Boolean = false,
        val hasPostgraduatePlan: Boolean = false,
    )

    data class PensionContribution(
        val method: PensionMethod,
        val contributionAmount: Double,
    )
}
