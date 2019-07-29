package model.taxcodes

import model.Country
import model.Country.WALES


internal interface WelshTaxCode : TaxCode {
    override val country: Country
        get() = WALES
}

internal class C0T : WelshTaxCode, AdjustedTaxFreeTCode {
    override val taxFreeAmount: Double
        get() = 0.0
}

internal class CBR : WelshTaxCode, SingleBandTax {
    override val taxAllAtBand: Int
        get() = 1
}

internal class CD0 : WelshTaxCode, SingleBandTax {
    override val taxAllAtBand: Int
        get() = 2
}

internal class CD1 : WelshTaxCode, SingleBandTax {
    override val taxAllAtBand: Int
        get() = 3
}

internal class CTCode(private val taxFreeAmountWithoutTrailingZero: Double) : WelshTaxCode, AdjustedTaxFreeTCode {
    override val taxFreeAmount: Double
        get() = taxFreeAmountWithoutTrailingZero * 10 + 9
}

internal class CLCode(private val taxFreeAmountWithoutTrailingZero: Double) : WelshTaxCode, StandardTaxCode {
    override val taxFreeAmount: Double
        get() = taxFreeAmountWithoutTrailingZero * 10 + 9
}


internal class WelshEmergencyCode(private val taxFreeAmountWithoutTrailingZero: Double) : WelshTaxCode,
    EmergencyTaxCode {
    override val taxFreeAmount: Double
        get() = taxFreeAmountWithoutTrailingZero * 10 + 9
}

internal class WelshMCode(private val taxFreeAmountWithoutTrailingZero: Double) : WelshTaxCode, MarriageTaxCodes {
    override val increasedTaxAllowance: Boolean
        get() = true
    override val taxFreeAmount: Double
        get() = (taxFreeAmountWithoutTrailingZero * 10) + 9
}

internal class WelshNCode(private val taxFreeAmountWithoutTrailingZero: Double) : WelshTaxCode, MarriageTaxCodes {
    override val increasedTaxAllowance: Boolean
        get() = false
    override val taxFreeAmount: Double
        get() = (taxFreeAmountWithoutTrailingZero * 10) + 9
}

internal class CKCode(private val amountToAddToWagesFromCode: Double) : WelshTaxCode, KTaxCode {
    override val amountToAddToWages: Double
        get() = amountToAddToWagesFromCode * 10 + 9
    override val taxFreeAmount: Double
        get() = 0.0
}
