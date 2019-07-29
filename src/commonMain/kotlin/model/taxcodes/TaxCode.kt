package model.taxcodes

import model.Country

internal interface TaxCode {
    val country: Country
    val taxFreeAmount: Double
}

internal interface SingleBandTax : TaxCode {
    override val taxFreeAmount: Double
        get() = 0.0
    val taxAllAtBand: Int
}

internal interface EmergencyTaxCode

internal interface AdjustedTaxFreeTCode

internal interface StandardTaxCode

internal interface NoTaxTaxCode : TaxCode {
    override val taxFreeAmount: Double
        get() = 0.0
}

internal interface MarriageTaxCodes {
    val increasedTaxAllowance: Boolean
}

internal interface KTaxCode {
    val amountToAddToWages: Double
}

