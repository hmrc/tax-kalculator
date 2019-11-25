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

import uk.gov.hmrc.calculator.exception.InvalidTaxCodeException
import uk.gov.hmrc.calculator.model.TaxCodeValidationResponse
import uk.gov.hmrc.calculator.model.ValidationError
import uk.gov.hmrc.calculator.utils.toTaxCode

object Validator {
    fun isValidTaxCode(taxCode: String): TaxCodeValidationResponse {
        return try {
            taxCode.toTaxCode()
            TaxCodeValidationResponse(true)
        } catch (e: InvalidTaxCodeException) {
            taxCode.invalidTaxCodeErrorGeneration()
        }
    }

    private fun String.invalidTaxCodeErrorGeneration(): TaxCodeValidationResponse {
        return if (!this.replace("[^\\d.]".toRegex(), "").matches("^[0-9]{1,4}".toRegex()))
            TaxCodeValidationResponse(false, ValidationError.WrongTaxCodeNumber)
        else if (this.replace("([0-9])+([A-Z]?)+".toRegex(), "").matches("[A-JL-RT-Z]{1,2}".toRegex()))
            TaxCodeValidationResponse(false, ValidationError.WrongTaxCodePrefix)
        else if (this.replace("^([A-Z]?)+([0-9]?)+".toRegex(), "").matches("[A-KO-SU-Z]".toRegex()))
            TaxCodeValidationResponse(false, ValidationError.WrongTaxCodeSuffix)
        else
            TaxCodeValidationResponse(false, ValidationError.Other)
    }

    fun isValidWages(wages: Double) =
        isAboveMinimumWages(wages) && isBelowMaximumWages(wages) && wages.isTwoDecimalPlacesOrFewer()

    fun isAboveMinimumWages(wages: Double) = wages > 0

    fun isBelowMaximumWages(wages: Double) = wages < 9999999.99

    fun isValidHoursPerWeek(hours: Double) =
        isAboveMinimumHoursPerWeek(hours) && isBelowMaximumHoursPerWeek(hours) && hours.isTwoDecimalPlacesOrFewer()

    fun isAboveMinimumHoursPerWeek(hours: Double) = hours >= 1

    fun isBelowMaximumHoursPerWeek(hours: Double) = hours <= 168

    fun isValidDaysPerWeek(days: Double) =
        isAboveMinimumDaysPerWeek(days) && isBelowMaximumDaysPerWeek(days) && days.isTwoDecimalPlacesOrFewer()

    fun isAboveMinimumDaysPerWeek(days: Double) = days >= 1

    fun isBelowMaximumDaysPerWeek(days: Double) = days <= 7

    private fun Double.isTwoDecimalPlacesOrFewer(): Boolean {
        val splitByDecimalPoint = toString().split(".")
        return splitByDecimalPoint.size == 2 && splitByDecimalPoint.last().length <= 2
    }
}
