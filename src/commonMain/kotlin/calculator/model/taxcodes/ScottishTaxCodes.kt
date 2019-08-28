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
package calculator.model.taxcodes

import calculator.model.Country
import calculator.model.Country.SCOTLAND

internal interface ScottishTaxCode : TaxCode {
    override val country: Country
        get() = SCOTLAND
}

internal class S0T : ScottishTaxCode, AdjustedTaxFreeTCode {
    override val taxFreeAmount: Double
        get() = 0.0
}

internal class SBR : ScottishTaxCode, SingleBandTax {
    override val taxFreeAmount: Double
        get() = 0.0
    override val taxAllAtBand: Int
        get() = 2
}

internal class SD0 : ScottishTaxCode, SingleBandTax {
    override val taxAllAtBand: Int
        get() = 3
}

internal class SD1 : ScottishTaxCode, SingleBandTax {
    override val taxAllAtBand: Int
        get() = 4
}

internal class SD2 : ScottishTaxCode, SingleBandTax {
    override val taxAllAtBand: Int
        get() = 5
}

internal class STCode(private val taxFreeAmountWithoutTrailingZero: Double) : ScottishTaxCode, AdjustedTaxFreeTCode {
    override val taxFreeAmount: Double
        get() = taxFreeAmountWithoutTrailingZero * 10 + 9
}

internal class SLCode(private val taxFreeAmountWithoutTrailingZero: Double) : ScottishTaxCode, StandardTaxCode {
    override val taxFreeAmount: Double
        get() = taxFreeAmountWithoutTrailingZero * 10 + 9
}

internal class ScottishEmergencyCode(private val taxFreeAmountWithoutTrailingZero: Double) : ScottishTaxCode,
    EmergencyTaxCode {
    override val taxFreeAmount: Double
        get() = taxFreeAmountWithoutTrailingZero * 10 + 9
}

internal class ScottishMCode(private val taxFreeAmountWithoutTrailingZero: Double) : ScottishTaxCode, MarriageTaxCodes {
    override val increasedTaxAllowance: Boolean
        get() = true
    override val taxFreeAmount: Double
        get() = (taxFreeAmountWithoutTrailingZero * 10) + 9
}

internal class ScottishNCode(private val taxFreeAmountWithoutTrailingZero: Double) : ScottishTaxCode, MarriageTaxCodes {
    override val increasedTaxAllowance: Boolean
        get() = false
    override val taxFreeAmount: Double
        get() = (taxFreeAmountWithoutTrailingZero * 10) + 9
}

internal class SKCode(private val amountToAddToWagesFromCode: Double) : ScottishTaxCode, KTaxCode {
    override val amountToAddToWages: Double
        get() = amountToAddToWagesFromCode * 10 + 9
    override val taxFreeAmount: Double
        get() = 0.0
}
