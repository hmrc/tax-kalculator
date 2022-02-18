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
import uk.gov.hmrc.calculator.model.Country.WALES

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
        get() = 0
}

internal class CD0 : WelshTaxCode, SingleBandTax {
    override val taxAllAtBand: Int
        get() = 1
}

internal class CD1 : WelshTaxCode, SingleBandTax {
    override val taxAllAtBand: Int
        get() = 2
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
