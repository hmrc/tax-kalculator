/*
 * Copyright 2022 HM Revenue & Customs
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
package uk.gov.hmrc.calculator.model.taxcodes

import uk.gov.hmrc.calculator.model.Country
import uk.gov.hmrc.calculator.model.Country.ENGLAND
import uk.gov.hmrc.calculator.model.Country.NONE

internal interface EnglishTaxCode : TaxCode {
    override val country: Country
        get() = ENGLAND
}

internal class ZeroT : EnglishTaxCode,
    AdjustedTaxFreeTCode {
    override val taxFreeAmount: Double
        get() = 0.0
}

internal class BR : EnglishTaxCode,
    SingleBandTax {
    override val taxAllAtBand: Int
        get() = 0
}

internal class D0 : EnglishTaxCode,
    SingleBandTax {
    override val taxAllAtBand: Int
        get() = 1
}

internal class D1 : EnglishTaxCode,
    SingleBandTax {
    override val taxAllAtBand: Int
        get() = 2
}

internal class TCode(private val taxFreeAmountWithoutTrailingZero: Double) : EnglishTaxCode,
    AdjustedTaxFreeTCode {
    override val taxFreeAmount: Double
        get() = taxFreeAmountWithoutTrailingZero * 10 + 9
}

internal class LCode(private val taxFreeAmountWithoutTrailingZero: Double) : EnglishTaxCode,
    StandardTaxCode {
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

internal class EnglishMCode(private val taxFreeAmountWithoutTrailingZero: Double) : EnglishTaxCode,
    MarriageTaxCodes {
    override val increasedTaxAllowance: Boolean
        get() = true
    override val taxFreeAmount: Double
        get() = (taxFreeAmountWithoutTrailingZero * 10) + 9
}

internal class EnglishNCode(private val taxFreeAmountWithoutTrailingZero: Double) : EnglishTaxCode,
    MarriageTaxCodes {
    override val increasedTaxAllowance: Boolean
        get() = false
    override val taxFreeAmount: Double
        get() = (taxFreeAmountWithoutTrailingZero * 10) + 9
}

internal class KCode(private val amountToAddToWagesFromCode: Double) : EnglishTaxCode,
    KTaxCode {
    override val amountToAddToWages: Double
        get() = amountToAddToWagesFromCode * 10 + 9
    override val taxFreeAmount: Double
        get() = 0.0
}
