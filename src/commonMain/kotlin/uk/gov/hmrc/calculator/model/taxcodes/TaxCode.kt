/*
 * Copyright 2020 HM Revenue & Customs
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
