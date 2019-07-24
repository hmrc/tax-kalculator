package model.taxcodes

import model.Country
import model.Country.WALES


interface WelshTaxCode : TaxCode {
    override val country: Country
        get() = WALES
}

class C0T : WelshTaxCode, AdjustedTaxFreeTCode {
    override val taxFreeAmount: Double
        get() = 0.0
}

class CBR : WelshTaxCode, SingleBandTax {
    override val taxAllAtBand: Int
        get() = 1
}

class CD0 : WelshTaxCode, SingleBandTax {
    override val taxAllAtBand: Int
        get() = 2
}

class CD1 : WelshTaxCode, SingleBandTax {
    override val taxAllAtBand: Int
        get() = 3
}

class CTCode(private val taxFreeAmountWithoutTrailingZero: Double) : WelshTaxCode, AdjustedTaxFreeTCode {
    override val taxFreeAmount: Double
        get() = taxFreeAmountWithoutTrailingZero * 10 + 9
}

class CLCode(private val taxFreeAmountWithoutTrailingZero: Double) : WelshTaxCode, StandardTaxCode {
    override val taxFreeAmount: Double
        get() = taxFreeAmountWithoutTrailingZero * 10 + 9
}


class WelshEmergencyCode(private val taxFreeAmountWithoutTrailingZero: Double) : WelshTaxCode,
    EmergencyTaxCode {
    override val taxFreeAmount: Double
        get() = taxFreeAmountWithoutTrailingZero * 10 + 9
}

class WelshMCode(private val taxFreeAmountWithoutTrailingZero: Double) : WelshTaxCode, MarriageTaxCodes {
    override val increasedTaxAllowance: Boolean
        get() = true
    override val taxFreeAmount: Double
        get() = (taxFreeAmountWithoutTrailingZero * 10) + 9
}

class WelshNCode(private val taxFreeAmountWithoutTrailingZero: Double) : WelshTaxCode, MarriageTaxCodes {
    override val increasedTaxAllowance: Boolean
        get() = false
    override val taxFreeAmount: Double
        get() = (taxFreeAmountWithoutTrailingZero * 10) + 9
}

class CKCode(private val amountToAddToWagesFromCode: Double) : WelshTaxCode, KTaxCode {
    override val amountToAddToWages: Double
        get() = amountToAddToWagesFromCode * 10 + 9
    override val taxFreeAmount: Double
        get() = 0.0
}
