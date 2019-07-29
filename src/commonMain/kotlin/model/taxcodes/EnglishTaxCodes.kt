package model.taxcodes

import model.Country
import model.Country.ENGLAND
import model.Country.NONE


internal interface EnglishTaxCode : TaxCode {
    override val country: Country
        get() = ENGLAND
}

internal class ZeroT : EnglishTaxCode, AdjustedTaxFreeTCode {
    override val taxFreeAmount: Double
        get() = 0.0
}

internal class BR : EnglishTaxCode, SingleBandTax {
    override val taxAllAtBand: Int
        get() = 1
}

internal class D0 : EnglishTaxCode, SingleBandTax {
    override val taxAllAtBand: Int
        get() = 2
}

internal class D1 : EnglishTaxCode, SingleBandTax {
    override val taxAllAtBand: Int
        get() = 3
}

internal class TCode(private val taxFreeAmountWithoutTrailingZero: Double) : EnglishTaxCode, AdjustedTaxFreeTCode {
    override val taxFreeAmount: Double
        get() = taxFreeAmountWithoutTrailingZero * 10 + 9
}

internal class LCode(private val taxFreeAmountWithoutTrailingZero: Double) : EnglishTaxCode, StandardTaxCode {
    override val taxFreeAmount: Double
        get() = taxFreeAmountWithoutTrailingZero * 10 + 9
}

internal class EnglishEmergencyCode(private val taxFreeAmountWithoutTrailingZero: Double) : EnglishTaxCode,
    EmergencyTaxCode {
    override val taxFreeAmount: Double
        get() = taxFreeAmountWithoutTrailingZero * 10 + 9
}

internal class NTCode : NoTaxTaxCode {
    override val country: Country
        get() = NONE
}

internal class EnglishMCode(private val taxFreeAmountWithoutTrailingZero: Double) : EnglishTaxCode, MarriageTaxCodes {
    override val increasedTaxAllowance: Boolean
        get() = true
    override val taxFreeAmount: Double
        get() = (taxFreeAmountWithoutTrailingZero * 10) + 9
}

internal class EnglishNCode(private val taxFreeAmountWithoutTrailingZero: Double) : EnglishTaxCode, MarriageTaxCodes {
    override val increasedTaxAllowance: Boolean
        get() = false
    override val taxFreeAmount: Double
        get() = (taxFreeAmountWithoutTrailingZero * 10) + 9
}

internal class KCode(private val amountToAddToWagesFromCode: Double) : EnglishTaxCode, KTaxCode {
    override val amountToAddToWages: Double
        get() = amountToAddToWagesFromCode * 10 + 9
    override val taxFreeAmount: Double
        get() = 0.0
}