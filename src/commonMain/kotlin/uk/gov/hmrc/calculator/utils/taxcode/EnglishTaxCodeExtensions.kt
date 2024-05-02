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
package uk.gov.hmrc.calculator.utils.taxcode

import uk.gov.hmrc.calculator.exception.InvalidTaxCodeException
import uk.gov.hmrc.calculator.model.taxcodes.BR
import uk.gov.hmrc.calculator.model.taxcodes.D0
import uk.gov.hmrc.calculator.model.taxcodes.D1
import uk.gov.hmrc.calculator.model.taxcodes.EnglishEmergencyCode
import uk.gov.hmrc.calculator.model.taxcodes.EnglishMCode
import uk.gov.hmrc.calculator.model.taxcodes.EnglishNCode
import uk.gov.hmrc.calculator.model.taxcodes.EnglishTaxCode
import uk.gov.hmrc.calculator.model.taxcodes.KCode
import uk.gov.hmrc.calculator.model.taxcodes.LCode
import uk.gov.hmrc.calculator.model.taxcodes.TCode
import uk.gov.hmrc.calculator.model.taxcodes.ZeroT
import kotlin.jvm.JvmSynthetic

@JvmSynthetic
internal fun String.matchEnglishTaxCode(): EnglishTaxCode {
    return when (this.take(2)) {
        "0T" -> ZeroT()
        "BR" -> BR()
        "D0" -> D0()
        "D1" -> D1()
        else -> this.matchOtherEnglishTaxCode()
    }
}

private fun String.matchOtherEnglishTaxCode(): EnglishTaxCode {
    return when {
        "[0-9]{1,4}T".toRegex().matches(this) -> TCode(removeSuffix("T").toDouble())
        "[0-9]{1,4}L".toRegex().matches(this) -> LCode(removeSuffix("L").toDouble())
        "[0-9]{1,4}[LT]?(W1|M1|X)".toRegex().matches(this) -> {
            val strippedValue = extractDoubleFromEmergencyTaxCode()
            EnglishEmergencyCode(strippedValue)
        }
        "[0-9]{1,4}([MN])".toRegex().matches(this) -> matchEnglishMNCode()
        "K[0-9]{1,4}(W1|M1|X)?".toRegex().matches(this) -> KCode(extractDoubleFromEmergencyTaxCode())
        else -> throw InvalidTaxCodeException("$this is an invalid English tax code")
    }
}

private fun String.matchEnglishMNCode(): EnglishTaxCode {
    val strippedValue = removeSuffix("M").removeSuffix("N").toDouble()
    return when {
        endsWith("N") -> EnglishNCode(strippedValue)
        endsWith("M") -> EnglishMCode(strippedValue)
        else -> throw InvalidTaxCodeException("$this is an invalid England marriage tax code")
    }
}
