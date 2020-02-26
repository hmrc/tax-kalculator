/*
 * Copyright 2020 HM Revenue & Customs
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
package uk.gov.hmrc.calculator.utils.taxcode

import uk.gov.hmrc.calculator.exception.InvalidTaxCodeException
import uk.gov.hmrc.calculator.model.taxcodes.S0T
import uk.gov.hmrc.calculator.model.taxcodes.SBR
import uk.gov.hmrc.calculator.model.taxcodes.SD0
import uk.gov.hmrc.calculator.model.taxcodes.SD1
import uk.gov.hmrc.calculator.model.taxcodes.SD2
import uk.gov.hmrc.calculator.model.taxcodes.SKCode
import uk.gov.hmrc.calculator.model.taxcodes.SLCode
import uk.gov.hmrc.calculator.model.taxcodes.STCode
import uk.gov.hmrc.calculator.model.taxcodes.ScottishEmergencyCode
import uk.gov.hmrc.calculator.model.taxcodes.ScottishMCode
import uk.gov.hmrc.calculator.model.taxcodes.ScottishNCode
import uk.gov.hmrc.calculator.model.taxcodes.ScottishTaxCode

internal fun String.matchScottishTaxCode(): ScottishTaxCode {
    return when (this) {
        "S0T" -> S0T()
        "SBR" -> SBR()
        "SD0" -> SD0()
        "SD1" -> SD1()
        "SD2" -> SD2()
        else -> matchOtherScottishTaxCode()
    }
}

internal fun String.matchOtherScottishTaxCode(): ScottishTaxCode {
    return when {
        "^S[0-9]{1,4}T".toRegex().matches(this) ->
            STCode(removePrefix("S").removeSuffix("T").toDouble())
        "^S[0-9]{1,4}L".toRegex().matches(this) ->
            SLCode(removePrefix("S").removeSuffix("L").toDouble())
        "^S[0-9]{1,4}[LT]?(W1|M1|X)".toRegex().matches(this) -> {
            val strippedValue = removePrefix("S").extractDoubleFromEmergencyTaxCode()
            ScottishEmergencyCode(strippedValue)
        }
        "S[0-9]{1,4}([MN])".toRegex().matches(this) -> matchScottishMNCode()
        "SK[0-9]{1,4}(W1|M1|X)?".toRegex().matches(this) -> SKCode(extractDoubleFromEmergencyTaxCode())
        else -> throw InvalidTaxCodeException("$this is an invalid Scottish tax code")
    }
}

private fun String.matchScottishMNCode(): ScottishTaxCode {
    val strippedValue = removePrefix("S").removeSuffix("M").removeSuffix("N").toDouble()
    return when {
        endsWith("N") -> ScottishNCode(strippedValue)
        endsWith("M") -> ScottishMCode(strippedValue)
        else -> throw InvalidTaxCodeException("$this is an invalid scottish marriage tax code")
    }
}
