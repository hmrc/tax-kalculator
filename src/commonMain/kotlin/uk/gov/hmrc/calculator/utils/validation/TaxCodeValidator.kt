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
package uk.gov.hmrc.calculator.utils.validation

import uk.gov.hmrc.calculator.exception.InvalidTaxCodeException
import uk.gov.hmrc.calculator.model.TaxCodeValidationResponse
import uk.gov.hmrc.calculator.model.ValidationError
import uk.gov.hmrc.calculator.utils.clarification.Clarification
import uk.gov.hmrc.calculator.utils.taxcode.getTaxCodeClarification
import uk.gov.hmrc.calculator.utils.taxcode.invalidTaxCodeErrorGeneration
import uk.gov.hmrc.calculator.utils.taxcode.toTaxCode

object TaxCodeValidator {
    fun isValidTaxCode(taxCode: String): TaxCodeValidationResponse {
        return try {
            taxCode.toTaxCode()
            TaxCodeValidationResponse(true)
        } catch (e: InvalidTaxCodeException) {
            taxCode.invalidTaxCodeErrorGeneration()
        }
    }

    fun isTaxCodeMatchingRate(taxCode: String, isPayingScottishRate: Boolean): TaxCodeValidationResponse? {
        return try {
            return when (taxCode.toTaxCode().getTaxCodeClarification(isPayingScottishRate)) {
                Clarification.SCOTTISH_CODE_BUT_OTHER_RATE ->
                    TaxCodeValidationResponse(true, ValidationError.ScottishCodeButOtherRate)
                Clarification.NON_SCOTTISH_CODE_BUT_SCOTTISH_RATE ->
                    TaxCodeValidationResponse(true, ValidationError.NonScottishCodeButScottishRate)
                else -> null
            }
        } catch (e: InvalidTaxCodeException) {
            taxCode.invalidTaxCodeErrorGeneration()
        }
    }
}

