/*
 * Copyright 2023 HM Revenue & Customs
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
package uk.gov.hmrc.calculator.utils.validation

import uk.gov.hmrc.calculator.Calculator
import uk.gov.hmrc.calculator.model.PayPeriod
import uk.gov.hmrc.calculator.model.TaxYear
import uk.gov.hmrc.calculator.model.pension.PensionAllowances.getPensionAllowances
import uk.gov.hmrc.calculator.model.pension.PensionMethod
import uk.gov.hmrc.calculator.utils.convertWageToMonthly
import uk.gov.hmrc.calculator.utils.convertWageToYearly
import uk.gov.hmrc.calculator.utils.formatMoney
import uk.gov.hmrc.calculator.utils.validation.HoursDaysValidator.isTwoDecimalPlacesOrFewer
import kotlin.jvm.JvmSynthetic

@Suppress("TooManyFunctions")
object PensionValidator {

    fun isValidMonthlyPension(
        monthlyPension: Double,
        monthlyWage: Double,
        taxYear: TaxYear,
    ): MutableList<PensionError> {
        val listOfError = mutableListOf<PensionError>()
        val yearlyPension = monthlyPension.convertWageToYearly(PayPeriod.MONTHLY)
        val yearlyWage = monthlyWage.convertWageToYearly(PayPeriod.MONTHLY)

        if (!monthlyPension.isTwoDecimalPlacesOrFewer() || !isPensionValidFormat(monthlyPension)) {
            listOfError.add(PensionError.INVALID_FORMAT)
        }
        listOfError.addAll(validatePensionWithinRange(yearlyPension, yearlyWage, taxYear))

        listOfError.sortBy { it.priority }
        return listOfError
    }

    fun isValidYearlyPension(yearlyPension: Double, yearlyWage: Double, taxYear: TaxYear): MutableList<PensionError> {
        val listOfError = mutableListOf<PensionError>()

        if (!yearlyPension.isTwoDecimalPlacesOrFewer() || !isPensionValidFormat(yearlyPension)) {
            listOfError.add(PensionError.INVALID_FORMAT)
        }
        listOfError.addAll(validatePensionWithinRange(yearlyPension, yearlyWage, taxYear))

        listOfError.sortBy { it.priority }
        return listOfError
    }

    fun validateValidInputPensionInput(
        monthlyPension: Double,
        pensionMethod: PensionMethod,
    ): MutableList<PensionError> {
        val listOfError = mutableListOf<PensionError>()
        val inputValue = if (pensionMethod == PensionMethod.MONTHLY_AMOUNT_IN_POUNDS) {
            monthlyPension.convertWageToYearly(PayPeriod.MONTHLY)
        } else monthlyPension

        if (isPensionBelowZero(inputValue)) listOfError.add(PensionError.BELOW_ZERO)
        if (isPensionPercentageAboveHundred(inputValue, pensionMethod))
            listOfError.add(PensionError.ABOVE_HUNDRED_PERCENT)
        if (isTenMillionOrAbove(monthlyPension)) {
            listOfError.add(PensionError.AMOUNT_REACHED_TEN_MILLIONS)
        } else {
            validatePensionInputValidDecimal(monthlyPension, pensionMethod)?.let { listOfError.add(it) }
        }

        listOfError.sortBy { it.priority }
        return listOfError
    }

    fun validatePensionBelowWage(
        wage: Double,
        payPeriod: PayPeriod,
        hoursOrDaysWorkedForPayPeriod: Double? = null,
        pensionContribution: Calculator.PensionContribution?,
    ): PensionErrorWithAmount? {
        if (pensionContribution == null || pensionContribution.method == PensionMethod.PERCENTAGE) {
            return null
        } else {
            val monthlyWage = wage.convertWageToMonthly(payPeriod, hoursOrDaysWorkedForPayPeriod)

            return if (pensionContribution.contributionAmount > monthlyWage) {
                PensionErrorWithAmount(PensionError.ABOVE_WAGE, monthlyWage.formatMoney())
            } else null
        }
    }

    private fun validatePensionWithinRange(
        yearlyPension: Double,
        yearlyWage: Double,
        taxYear: TaxYear,
    ): MutableList<PensionError> {
        val listOfError = mutableListOf<PensionError>()
        if (isPensionBelowZero(yearlyPension)) listOfError.add(PensionError.BELOW_ZERO)
        if (!isPensionLowerThenWage(yearlyPension, yearlyWage)) listOfError.add(PensionError.ABOVE_WAGE)
        if (isPensionAboveAnnualAllowance(yearlyPension, taxYear)) listOfError.add(PensionError.ABOVE_ANNUAL_ALLOWANCE)

        return listOfError
    }

    private fun isPensionValidFormat(yearlyPension: Double): Boolean {
        return "([0-9])+(\\.\\d{1,2})".toRegex().matches(yearlyPension.toString())
    }

    private fun isTenMillionOrAbove(monthlyPension: Double): Boolean {
        return monthlyPension >= 10000000.0
    }

    private fun isPensionBelowZero(yearlyPension: Double): Boolean {
        return yearlyPension <= 0.0
    }

    private fun isPensionPercentageAboveHundred(monthlyPension: Double, pensionMethod: PensionMethod): Boolean {
        return monthlyPension > 100.0 && pensionMethod == PensionMethod.PERCENTAGE
    }

    private fun validatePensionInputValidDecimal(monthlyPension: Double, pensionMethod: PensionMethod): PensionError? {
        return if (!monthlyPension.isTwoDecimalPlacesOrFewer()) {
            if (pensionMethod == PensionMethod.PERCENTAGE) PensionError.INVALID_PERCENTAGE_DECIMAL
            else PensionError.INVALID_AMOUNT_DECIMAL
        } else null
    }

    @JvmSynthetic
    internal fun isPensionLowerThenWage(yearlyPension: Double, yearlyWage: Double): Boolean {
        return yearlyPension <= yearlyWage
    }

    @JvmSynthetic
    internal fun isPensionAboveAnnualAllowance(yearlyPension: Double, taxYear: TaxYear): Boolean {
        return yearlyPension > getPensionAllowances(taxYear).annualAllowance
    }

    enum class PensionError(val priority: Int) {
        INVALID_FORMAT(1),
        AMOUNT_REACHED_TEN_MILLIONS(2),
        INVALID_AMOUNT_DECIMAL(3),
        INVALID_PERCENTAGE_DECIMAL(4),
        BELOW_ZERO(5),
        ABOVE_WAGE(6),
        ABOVE_HUNDRED_PERCENT(7),
        ABOVE_ANNUAL_ALLOWANCE(8),
    }

    data class PensionErrorWithAmount(val error: PensionError, val amountToShow: Double)
}
