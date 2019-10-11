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
package uk.gov.hmrc.calculator.utils

import uk.gov.hmrc.calculator.exception.InvalidTaxCodeException
import uk.gov.hmrc.calculator.model.Country.ENGLAND
import uk.gov.hmrc.calculator.model.Country.NONE
import uk.gov.hmrc.calculator.model.Country.SCOTLAND
import uk.gov.hmrc.calculator.model.Country.WALES
import uk.gov.hmrc.calculator.model.taxcodes.BR
import uk.gov.hmrc.calculator.model.taxcodes.C0T
import uk.gov.hmrc.calculator.model.taxcodes.CBR
import uk.gov.hmrc.calculator.model.taxcodes.CD0
import uk.gov.hmrc.calculator.model.taxcodes.CD1
import uk.gov.hmrc.calculator.model.taxcodes.CKCode
import uk.gov.hmrc.calculator.model.taxcodes.CLCode
import uk.gov.hmrc.calculator.model.taxcodes.CTCode
import uk.gov.hmrc.calculator.model.taxcodes.D0
import uk.gov.hmrc.calculator.model.taxcodes.D1
import uk.gov.hmrc.calculator.model.taxcodes.EnglishEmergencyCode
import uk.gov.hmrc.calculator.model.taxcodes.EnglishMCode
import uk.gov.hmrc.calculator.model.taxcodes.EnglishNCode
import uk.gov.hmrc.calculator.model.taxcodes.EnglishTaxCode
import uk.gov.hmrc.calculator.model.taxcodes.KCode
import uk.gov.hmrc.calculator.model.taxcodes.LCode
import uk.gov.hmrc.calculator.model.taxcodes.NTCode
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
import uk.gov.hmrc.calculator.model.taxcodes.TCode
import uk.gov.hmrc.calculator.model.taxcodes.TaxCode
import uk.gov.hmrc.calculator.model.taxcodes.WelshEmergencyCode
import uk.gov.hmrc.calculator.model.taxcodes.WelshMCode
import uk.gov.hmrc.calculator.model.taxcodes.WelshNCode
import uk.gov.hmrc.calculator.model.taxcodes.WelshTaxCode
import uk.gov.hmrc.calculator.model.taxcodes.ZeroT

internal fun String.toTaxCode(): TaxCode {
    val noSpacesTaxCode = this.replace("\\s".toRegex(), "")

    return when (noSpacesTaxCode.toCountry()) {
        SCOTLAND -> noSpacesTaxCode.matchScottishTaxCode()
        WALES -> noSpacesTaxCode.matchWelshTaxCode()
        ENGLAND -> noSpacesTaxCode.matchEnglishTaxCode()
        NONE -> {
            when (this) {
                "NT" -> NTCode()
                else -> throw InvalidTaxCodeException("$this is an invalid tax code")
            }
        }
    }
}

private fun String.matchScottishTaxCode(): ScottishTaxCode {
    return when (this) {
        "S0T" -> S0T()
        "SBR" -> SBR()
        "SD0" -> SD0()
        "SD1" -> SD1()
        "SD2" -> SD2()
        else -> matchOtherScottishTaxCode()
    }
}

private fun String.matchOtherScottishTaxCode(): ScottishTaxCode {
    return when {
        "^S[0-9]{1,4}T".toRegex().containsMatchIn(this) ->
            STCode(removePrefix("S").removeSuffix("T").toDouble())
        "^S[0-9]{1,4}L".toRegex().containsMatchIn(this) ->
            SLCode(removePrefix("S").removeSuffix("L").toDouble())
        "^S[0-9]{1,4}(W1|M1|X)".toRegex().containsMatchIn(this) -> {
            val strippedValue = removePrefix("S").removeSuffix("W1").removeSuffix("M1")
                .removeSuffix("X").toDouble()
            ScottishEmergencyCode(strippedValue)
        }
        "^S[0-9]{1,4}([MN])".toRegex().containsMatchIn(this) -> matchScottishMNCode()
        "^SK[0-9]{1,4}".toRegex().containsMatchIn(this) -> SKCode(
            removePrefix("SK").toDouble()
        )
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

private fun String.matchWelshTaxCode(): WelshTaxCode {
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
        "^C[0-9]{1,4}T".toRegex().containsMatchIn(this) ->
            CTCode(removePrefix("C").removeSuffix("T").toDouble())
        "^C[0-9]{1,4}L".toRegex().containsMatchIn(this) ->
            CLCode(removePrefix("C").removeSuffix("L").toDouble())
        "^C[0-9]{1,4}(W1|M1|X)".toRegex().containsMatchIn(this) -> {
            val strippedValue =
                removePrefix("C").removeSuffix("W1").removeSuffix("M1")
                    .removeSuffix("X").toDouble()
            WelshEmergencyCode(strippedValue)
        }
        "^C[0-9]{1,4}([MN])".toRegex().containsMatchIn(this) -> matchWelshMNCode()
        "^CK[0-9]{1,4}".toRegex().containsMatchIn(this) -> CKCode(
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

private fun String.matchEnglishTaxCode(): EnglishTaxCode {
    return when (this) {
        "0T" -> ZeroT()
        "BR" -> BR()
        "D0" -> D0()
        "D1" -> D1()
        else -> matchOtherEnglishTaxCode()
    }
}

private fun String.matchOtherEnglishTaxCode(): EnglishTaxCode {
    return when {
        "^[0-9]{1,4}T".toRegex().containsMatchIn(this) -> TCode(removeSuffix("T").toDouble())
        "^[0-9]{1,4}L".toRegex().containsMatchIn(this) -> LCode(removeSuffix("L").toDouble())
        "^[0-9]{1,4}(W1|M1|X)".toRegex().containsMatchIn(this) -> {
            val strippedValue = removeSuffix("W1").removeSuffix("M1").removeSuffix("X").toDouble()
            EnglishEmergencyCode(strippedValue)
        }
        "^[0-9]{1,4}([MN])".toRegex().containsMatchIn(this) -> matchEnglishMNCode()
        "^K[0-9]{1,4}".toRegex().containsMatchIn(this) -> KCode(removePrefix("K").toDouble())
        else -> throw InvalidTaxCodeException("$this is an invalid Welsh tax code")
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
