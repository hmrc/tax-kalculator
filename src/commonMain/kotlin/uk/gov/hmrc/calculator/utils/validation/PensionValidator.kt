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

import uk.gov.hmrc.calculator.model.TaxYear
import uk.gov.hmrc.calculator.model.pension.PensionAllowances.getPensionAllowances
import uk.gov.hmrc.calculator.utils.validation.HoursDaysValidator.isTwoDecimalPlacesOrFewer
import kotlin.jvm.JvmSynthetic

object PensionValidator {

    fun isValidYearlyPension(yearlyPension: Double, wage: Double, taxYear: TaxYear): MutableList<PensionError> {
        val listOfError = mutableListOf<PensionError>()

        if (!yearlyPension.isTwoDecimalPlacesOrFewer() || !isPensionValidFormat(yearlyPension)) {
            listOfError.add(PensionError.INVALID_FORMAT)
        }
        if (isPensionBelowZero(yearlyPension)) listOfError.add(PensionError.BELOW_ZERO)
        if (!isPensionLowerThenWage(yearlyPension, wage)) listOfError.add(PensionError.ABOVE_HUNDRED_PERCENT)
        if (isAboveAnnualAllowance(yearlyPension, taxYear)) listOfError.add(PensionError.ABOVE_ANNUAL_ALLOWANCE)

        listOfError.sortBy { it.priority }
        return listOfError
    }

    private fun isPensionValidFormat(yearlyPension: Double): Boolean {
        return "([0-9])+(\\.\\d{1,2})".toRegex().matches(yearlyPension.toString())
    }

    private fun isPensionBelowZero(yearlyPension: Double): Boolean {
        return yearlyPension <= 0.0
    }

    @JvmSynthetic
    internal fun isPensionLowerThenWage(yearlyPension: Double, yearlyWage: Double): Boolean {
        return yearlyPension <= yearlyWage
    }

    @JvmSynthetic
    internal fun isAboveAnnualAllowance(yearlyPension: Double, taxYear: TaxYear): Boolean {
        return yearlyPension > getPensionAllowances(taxYear).annualAllowance
    }

    enum class PensionError(val priority: Int) {
        INVALID_FORMAT(1),
        BELOW_ZERO(2),
        ABOVE_HUNDRED_PERCENT(3),
        ABOVE_ANNUAL_ALLOWANCE(4),
    }
}
