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
package uk.gov.hmrc.calculator.utils.taxcode

import uk.gov.hmrc.calculator.exception.InvalidTaxCodeException
import uk.gov.hmrc.calculator.model.Country
import uk.gov.hmrc.calculator.model.taxcodes.NTCode
import uk.gov.hmrc.calculator.model.taxcodes.ScottishTaxCode
import uk.gov.hmrc.calculator.model.taxcodes.TaxCode
import uk.gov.hmrc.calculator.utils.clarification.Clarification
import uk.gov.hmrc.calculator.utils.toCountry
import kotlin.jvm.JvmSynthetic

@JvmSynthetic
internal fun String.toTaxCode(): TaxCode {
    if (isBlank()) throw InvalidTaxCodeException("Tax code cannot be empty")

    val formattedTaxCode = this.replace("\\s".toRegex(), "").uppercase()

    return when (formattedTaxCode.toCountry()) {
        Country.SCOTLAND -> formattedTaxCode.matchScottishTaxCode()
        Country.WALES -> formattedTaxCode.matchWelshTaxCode()
        Country.ENGLAND -> formattedTaxCode.matchEnglishTaxCode()
        Country.NONE -> NTCode()
    }
}

@JvmSynthetic
internal fun String.extractDoubleFromEmergencyTaxCode(): Double =
    this
        .removePrefix("S")
        .removePrefix("C")
        .removePrefix("K")
        .removeSuffix("W1")
        .removeSuffix("M1")
        .removeSuffix("X")
        .removeSuffix("L")
        .removeSuffix("T")
        .toDouble()

/*
    Tax-free amount without the "£9"
*/
@JvmSynthetic
internal fun TaxCode.getTrueTaxFreeAmount(): Double {
    val amount = this.taxFreeAmount
    return if (amount > 0) amount - 9 else 0.0
}

@Suppress("ComplexMethod")
@JvmSynthetic
internal fun TaxCode.getTaxCodeClarification(userPaysScottishTax: Boolean): Clarification? {
    val clarification = when {
        (this is ScottishTaxCode) && userPaysScottishTax -> Clarification.SCOTTISH_INCOME_APPLIED
        (this is ScottishTaxCode) && !userPaysScottishTax -> Clarification.SCOTTISH_CODE_BUT_OTHER_RATE
        (this !is ScottishTaxCode) && userPaysScottishTax -> Clarification.NON_SCOTTISH_CODE_BUT_SCOTTISH_RATE
        else -> null
    }

    return clarification
}
