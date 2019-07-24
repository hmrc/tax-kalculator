package model.taxcodes

import model.Country

interface TaxCode {
    val country: Country
    val taxFreeAmount: Double
}

interface SingleBandTax : TaxCode {
    override val taxFreeAmount: Double
        get() = 0.0
    val taxAllAtBand: Int
}

interface EmergencyTaxCode

interface AdjustedTaxFreeTCode

interface StandardTaxCode

interface NoTaxTaxCode : TaxCode {
    override val taxFreeAmount: Double
        get() = 0.0
}

interface MarriageTaxCodes {
    val increasedTaxAllowance: Boolean
}

interface KTaxCode {
    val amountToAddToWages: Double
}

