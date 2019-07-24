package utils

import model.Country
import model.Country.*
import model.bands.Band
import model.bands.TaxBands
import model.taxcodes.*

fun String.toTaxCode(): TaxCode {
    //TODO Remove spaces from Tax Codes

    return when (this.country()) {
        SCOTLAND -> this.matchScottishTaxCode()
        WALES -> this.matchWelshTaxCode()
        ENGLAND -> this.matchEnglishTaxCode()
        NONE -> this.matchGeneralTaxCodes()
    }
}

fun String.country(): Country {
    return when (this) {
        "NT" -> NONE
        else -> when (this.first().toString()) {
            "S" -> SCOTLAND
            "C" -> WALES
            else -> ENGLAND
        }
    }
}

private fun String.matchGeneralTaxCodes(): TaxCode {
    return when (this) {
        "NT" -> NTCode()
        else -> throw IllegalArgumentException("Invalid General Tax Code")
    }
}

private fun String.matchScottishTaxCode(): ScottishTaxCode {
    return when (this) {
        "S0T" -> S0T()
        "SBR" -> SBR()
        "SD0" -> SD0()
        "SD1" -> SD1()
        "SD2" -> SD2()
        else -> {
            if ("^S[0-9]{1,4}T".toRegex().containsMatchIn(this)) {
                return STCode(this.removePrefix("S").removeSuffix("T").toDouble())
            }
            if ("^S[0-9]{1,4}L".toRegex().containsMatchIn(this)) {
                return SLCode(this.removePrefix("S").removeSuffix("L").toDouble())
            }
            if ("^S[0-9]{1,4}(W1|M1|X)".toRegex().containsMatchIn(this)) {
                val strippedValue = this.removePrefix("S").removeSuffix("W1").removeSuffix("M1").removeSuffix("X").toDouble()
                return ScottishEmergencyCode(strippedValue)
            }
            if ("^S[0-9]{1,4}([MN])".toRegex().containsMatchIn(this)) {
                val strippedValue = this.removePrefix("S").removeSuffix("M").removeSuffix("N").toDouble()
                return when {
                    this.endsWith("N") -> ScottishNCode(strippedValue)
                    this.endsWith("M") -> ScottishMCode(strippedValue)
                    else -> throw IllegalStateException("Invalid welsh marriage tax code")
                }
            }
            if ("^SK[0-9]{1,4}".toRegex().containsMatchIn(this)) {
                return SKCode(this.removePrefix("SK").toDouble())
            }
            throw IllegalArgumentException("Invalid Scottish Tax Code")
        }
    }
}

private fun String.matchWelshTaxCode(): WelshTaxCode {
    return when (this) {
        "C0T" -> C0T()
        "CBR" -> CBR()
        "CD0" -> CD0()
        "CD1" -> CD1()
        else -> {
            if ("^C[0-9]{1,4}T".toRegex().containsMatchIn(this)) {
                return CTCode(this.removePrefix("C").removeSuffix("T").toDouble())
            }
            if ("^C[0-9]{1,4}L".toRegex().containsMatchIn(this)) {
                return CLCode(this.removePrefix("C").removeSuffix("L").toDouble())
            }
            if ("^C[0-9]{1,4}(W1|M1|X)".toRegex().containsMatchIn(this)) {
                val strippedValue = this.removePrefix("C").removeSuffix("W1").removeSuffix("M1").removeSuffix("X").toDouble()
                return WelshEmergencyCode(strippedValue)
            }
            if ("^C[0-9]{1,4}([MN])".toRegex().containsMatchIn(this)) {
                val strippedValue = this.removePrefix("C").removeSuffix("M").removeSuffix("N").toDouble()
                return when {
                    this.endsWith("N") -> WelshNCode(strippedValue)
                    this.endsWith("M") -> WelshMCode(strippedValue)
                    else -> throw IllegalStateException("Invalid welsh marriage tax code")
                }
            }
            if ("^CK[0-9]{1,4}".toRegex().containsMatchIn(this)) {
                return CKCode(this.removePrefix("CK").toDouble())
            }
            throw IllegalArgumentException("Invalid Welsh Tax Code")
        }
    }
}

private fun String.matchEnglishTaxCode(): EnglishTaxCode {
    return when (this) {
        "0T" -> ZeroT()
        "BR" -> BR()
        "D0" -> D0()
        "D1" -> D1()
        else -> {
            if ("^[0-9]{1,4}T".toRegex().containsMatchIn(this)) {
                return TCode(this.removeSuffix("T").toDouble())
            }
            if ("^[0-9]{1,4}L".toRegex().containsMatchIn(this)) {
                return LCode(this.removeSuffix("L").toDouble())
            }
            if ("^[0-9]{1,4}(W1|M1|X)".toRegex().containsMatchIn(this)) {
                val strippedValue = this.removeSuffix("W1").removeSuffix("M1").removeSuffix("X").toDouble()
                return EnglishEmergencyCode(strippedValue)
            }
            if ("^[0-9]{1,4}([MN])".toRegex().containsMatchIn(this)) {
                val strippedValue = this.removeSuffix("M").removeSuffix("N").toDouble()
                return when {
                    this.endsWith("N") -> EnglishNCode(strippedValue)
                    this.endsWith("M") -> EnglishMCode(strippedValue)
                    else -> throw IllegalStateException("Invalid english marriage tax code")
                }
            }
            if ("^K[0-9]{1,4}".toRegex().containsMatchIn(this)) {
                return KCode(this.removePrefix("K").toDouble())
            }
            throw IllegalArgumentException("Invalid English Tax Code")
        }
    }

}

fun List<Band>.whichBandContains(wages: Double): Int {
    for (i in 0 until this.size) if (this[i].inBand(wages)) {
        return i
    }
    throw IllegalArgumentException("$wages are not in any band!")
}


fun getDefaultTaxAllowance(taxYear: Int, country: Country): Int {
    return TaxBands(country, taxYear).bands[0].upper.toInt()
}



