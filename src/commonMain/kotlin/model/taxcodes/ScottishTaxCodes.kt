package model.taxcodes

import model.Country
import model.Country.SCOTLAND

interface ScottishTaxCode : TaxCode {
    override val country: Country
        get() = SCOTLAND
}

class S0T : ScottishTaxCode, AdjustedTaxFreeTCode {
    override val taxFreeAmount: Double
        get() = 0.0
}


class SBR : ScottishTaxCode, SingleBandTax {
    override val taxFreeAmount: Double
        get() = 0.0
    override val taxAllAtBand: Int
        get() = 2
}

class SD0 : ScottishTaxCode, SingleBandTax {
    override val taxAllAtBand: Int
        get() = 3
}

class SD1 : ScottishTaxCode, SingleBandTax {
    override val taxAllAtBand: Int
        get() = 4
}

class SD2 : ScottishTaxCode, SingleBandTax {
    override val taxAllAtBand: Int
        get() = 5
}

class STCode(private val taxFreeAmountWithoutTrailingZero: Double) : ScottishTaxCode, AdjustedTaxFreeTCode {
    override val taxFreeAmount: Double
        get() = taxFreeAmountWithoutTrailingZero * 10 + 9
}

class SLCode(private val taxFreeAmountWithoutTrailingZero: Double) : ScottishTaxCode, StandardTaxCode {
    override val taxFreeAmount: Double
        get() = taxFreeAmountWithoutTrailingZero * 10 + 9
}

class ScottishEmergencyCode(private val taxFreeAmountWithoutTrailingZero: Double) : ScottishTaxCode,
    EmergencyTaxCode {
    override val taxFreeAmount: Double
        get() = taxFreeAmountWithoutTrailingZero * 10 + 9
}

class ScottishMCode(private val taxFreeAmountWithoutTrailingZero: Double) : ScottishTaxCode, MarriageTaxCodes {
    override val increasedTaxAllowance: Boolean
        get() = true
    override val taxFreeAmount: Double
        get() = (taxFreeAmountWithoutTrailingZero * 10) + 9
}

class ScottishNCode(private val taxFreeAmountWithoutTrailingZero: Double) : ScottishTaxCode, MarriageTaxCodes {
    override val increasedTaxAllowance: Boolean
        get() = false
    override val taxFreeAmount: Double
        get() = (taxFreeAmountWithoutTrailingZero * 10) + 9
}

class SKCode(private val amountToAddToWagesFromCode: Double) : ScottishTaxCode, KTaxCode {
    override val amountToAddToWages: Double
        get() = amountToAddToWagesFromCode * 10 + 9
    override val taxFreeAmount: Double
        get() = 0.0
}