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
import uk.gov.hmrc.calculator.model.taxcodes.C0T
import uk.gov.hmrc.calculator.model.taxcodes.CBR
import uk.gov.hmrc.calculator.model.taxcodes.CD0
import uk.gov.hmrc.calculator.model.taxcodes.CD1
import uk.gov.hmrc.calculator.model.taxcodes.CKCode
import uk.gov.hmrc.calculator.model.taxcodes.CLCode
import uk.gov.hmrc.calculator.model.taxcodes.CTCode
import uk.gov.hmrc.calculator.model.taxcodes.WelshEmergencyCode
import uk.gov.hmrc.calculator.model.taxcodes.WelshMCode
import uk.gov.hmrc.calculator.model.taxcodes.WelshNCode
import uk.gov.hmrc.calculator.model.taxcodes.WelshTaxCode

internal fun String.matchWelshTaxCode(): WelshTaxCode {
    return when (this) {
        "C0T" -> C0T()
        "CBR" -> CBR()
        "CD0" -> CD0()
        "CD1" -> CD1()
        else -> matchOtherWelshTaxCode()
    }
}

private fun String.matchOtherWelshTaxCode(): WelshTaxCode {
    return when {
        "C[0-9]{1,4}T".toRegex().matches(this) ->
            CTCode(removePrefix("C").removeSuffix("T").toDouble())
        "C[0-9]{1,4}L".toRegex().matches(this) ->
            CLCode(removePrefix("C").removeSuffix("L").toDouble())
        "C[0-9]{1,4}[LT]?(W1|M1|X)".toRegex().matches(this) -> {
            val strippedValue =
                removePrefix("C").extractDoubleFromEmergencyTaxCode()
            WelshEmergencyCode(strippedValue)
        }
        "C[0-9]{1,4}([MN])".toRegex().matches(this) -> matchWelshMNCode()
        "CK[0-9]{1,4}".toRegex().matches(this) -> CKCode(
            removePrefix("CK").toDouble()
        )
        else -> throw InvalidTaxCodeException("$this is an invalid Welsh tax code")
    }
}

private fun String.matchWelshMNCode(): WelshTaxCode {
    val strippedValue = removePrefix("C").removeSuffix("M").removeSuffix("N").toDouble()
    return when {
        endsWith("N") -> WelshNCode(strippedValue)
        endsWith("M") -> WelshMCode(strippedValue)
        else -> throw InvalidTaxCodeException("$this is an invalid scottish marriage tax code")
    }
}
