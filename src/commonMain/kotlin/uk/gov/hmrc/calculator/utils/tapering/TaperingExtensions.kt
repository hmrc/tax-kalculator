/*
 * Copyright 2023 HM Revenue & Customs
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
package uk.gov.hmrc.calculator.utils.tapering

import uk.gov.hmrc.calculator.model.taxcodes.StandardTaxCode
import uk.gov.hmrc.calculator.model.taxcodes.TaxCode
import kotlin.jvm.JvmSynthetic

@JvmSynthetic
internal fun Double.deductTapering(yearlyWageAfterPension: Double): Double {
    val taperingAmountToDeduct = yearlyWageAfterPension.getTaperingAmount(this)
    val amountAfterTaperingDeduction = this - taperingAmountToDeduct

//    This if statement should be covered by the "maximum tapering check" in getTaperingAmount extension.
//    But added if statement to ensure amount can't go negative.
    return if (amountAfterTaperingDeduction <= 0) 0.0 else amountAfterTaperingDeduction
}

@JvmSynthetic
internal fun Double.getTaperingAmount(maximumTaperingAmount: Double): Double {
    val taperingAmount = (this - TAPERING_THRESHOLD) / 2.0
    return if (taperingAmount >= maximumTaperingAmount) maximumTaperingAmount else taperingAmount
}

@JvmSynthetic
internal fun Double.yearlyWageIsAboveHundredThousand() = this > TAPERING_THRESHOLD

@JvmSynthetic
internal fun Double.shouldApplyStandardTapering(taxCodeType: TaxCode, userSuppliedTaxCode: Boolean) =
    isStandardTaxCodeAndAboveHundredThousand(taxCodeType, this) && !userSuppliedTaxCode

@JvmSynthetic
internal fun Double.shouldApplyDefaultTaxCode(taxCode: String, userSuppliedTaxCode: Boolean) =
    taxCode.endsWith(TaxCode.getDefaultTaxCode(), ignoreCase = true) &&
        this.yearlyWageIsAboveHundredThousand() &&
        userSuppliedTaxCode

private fun isStandardTaxCodeAndAboveHundredThousand(taxCodeType: TaxCode, yearlyWageAfterPension: Double) =
    taxCodeType is StandardTaxCode && yearlyWageAfterPension.yearlyWageIsAboveHundredThousand()

private const val TAPERING_THRESHOLD = 100000.0
