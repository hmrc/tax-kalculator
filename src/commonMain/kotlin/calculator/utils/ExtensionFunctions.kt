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
package calculator.utils

import calculator.model.Country
import calculator.model.bands.Band
import calculator.model.bands.TaxBands
import calculator.model.taxcodes.BR
import calculator.model.taxcodes.C0T
import calculator.model.taxcodes.CBR
import calculator.model.taxcodes.CD0
import calculator.model.taxcodes.CD1
import calculator.model.taxcodes.CKCode
import calculator.model.taxcodes.CLCode
import calculator.model.taxcodes.CTCode
import calculator.model.taxcodes.D0
import calculator.model.taxcodes.D1
import calculator.model.taxcodes.EnglishEmergencyCode
import calculator.model.taxcodes.EnglishMCode
import calculator.model.taxcodes.EnglishNCode
import calculator.model.taxcodes.EnglishTaxCode
import calculator.model.taxcodes.KCode
import calculator.model.taxcodes.LCode
import calculator.model.taxcodes.NTCode
import calculator.model.taxcodes.S0T
import calculator.model.taxcodes.SBR
import calculator.model.taxcodes.SD0
import calculator.model.taxcodes.SD1
import calculator.model.taxcodes.SD2
import calculator.model.taxcodes.SKCode
import calculator.model.taxcodes.SLCode
import calculator.model.taxcodes.STCode
import calculator.model.taxcodes.ScottishEmergencyCode
import calculator.model.taxcodes.ScottishMCode
import calculator.model.taxcodes.ScottishNCode
import calculator.model.taxcodes.ScottishTaxCode
import calculator.model.taxcodes.TCode
import calculator.model.taxcodes.TaxCode
import calculator.model.taxcodes.WelshEmergencyCode
import calculator.model.taxcodes.WelshMCode
import calculator.model.taxcodes.WelshNCode
import calculator.model.taxcodes.WelshTaxCode
import calculator.model.taxcodes.ZeroT

internal fun String.toTaxCode(): TaxCode {
    val noSpacesTaxCode = this.replace("\\s".toRegex(), "")

    return when (noSpacesTaxCode.country()) {
        Country.SCOTLAND -> noSpacesTaxCode.matchScottishTaxCode()
        Country.WALES -> noSpacesTaxCode.matchWelshTaxCode()
        Country.ENGLAND -> noSpacesTaxCode.matchEnglishTaxCode()
        Country.NONE -> noSpacesTaxCode.matchGeneralTaxCodes()
    }
}

internal fun String.country(): Country {
    return when (this) {
        "NT" -> Country.NONE
        else -> when (this.first().toString()) {
            "S" -> Country.SCOTLAND
            "C" -> Country.WALES
            else -> Country.ENGLAND
        }
    }
}

private fun String.matchGeneralTaxCodes(): TaxCode {
    return when (this) {
        "NT" -> NTCode()
        else -> throw InvalidTaxCode("$this is an invalid tax code")
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
        "^S[0-9]{1,4}([MN])".toRegex().containsMatchIn(this) -> {
            val strippedValue = removePrefix("S").removeSuffix("M").removeSuffix("N").toDouble()
            when {
                endsWith("N") -> ScottishNCode(strippedValue)
                endsWith("M") -> ScottishMCode(strippedValue)
                else -> throw InvalidTaxCode("$this is an invalid scottish marriage tax code")
            }
        }
        "^SK[0-9]{1,4}".toRegex().containsMatchIn(this) -> SKCode(removePrefix("SK").toDouble())
        else -> throw InvalidTaxCode("$this is an invalid Scottish tax code")
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
        "^C[0-9]{1,4}([MN])".toRegex().containsMatchIn(this) -> {
            val strippedValue = removePrefix("C").removeSuffix("M").removeSuffix("N").toDouble()
            when {
                endsWith("N") -> WelshNCode(strippedValue)
                endsWith("M") -> WelshMCode(strippedValue)
                else -> throw InvalidTaxCode("$this is an invalid scottish marriage tax code")
            }
        }
        "^CK[0-9]{1,4}".toRegex().containsMatchIn(this) -> CKCode(removePrefix("CK").toDouble())
        else -> throw InvalidTaxCode("$this is an invalid Welsh tax code")
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
        "^[0-9]{1,4}([MN])".toRegex().containsMatchIn(this) -> {
            val strippedValue = removeSuffix("M").removeSuffix("N").toDouble()
            when {
                endsWith("N") -> EnglishNCode(strippedValue)
                endsWith("M") -> EnglishMCode(strippedValue)
                else -> throw InvalidTaxCode("$this is an invalid England marriage tax code")
            }
        }
        "^K[0-9]{1,4}".toRegex().containsMatchIn(this) -> KCode(removePrefix("K").toDouble())
        else -> throw InvalidTaxCode("$this is an invalid Welsh tax code")
    }
}

internal fun List<Band>.whichBandContains(wages: Double): Int {
    for (i in 0 until this.size) if (this[i].inBand(wages)) {
        return i
    }
    throw ConfigurationError("$wages are not in any band!")
}

internal fun getDefaultTaxAllowance(taxYear: Int, country: Country = Country.ENGLAND): Int {
    return TaxBands(country, taxYear).bands[0].upper.toInt()
}
