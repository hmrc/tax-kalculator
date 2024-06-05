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
package uk.gov.hmrc.calculator.model.pension

import uk.gov.hmrc.calculator.model.TaxYear
import kotlin.jvm.JvmSynthetic

internal object PensionAllowances {

    @JvmSynthetic
    internal fun getPensionAllowances(taxYear: TaxYear) = when (taxYear) {
        TaxYear.TWENTY_TWENTY -> pensionAllowance2020()
        TaxYear.TWENTY_TWENTY_ONE -> pensionAllowance2021()
        TaxYear.TWENTY_TWENTY_TWO,
        TaxYear.TWENTY_TWENTY_TWO_JULY_REVISED,
        TaxYear.TWENTY_TWENTY_TWO_NOVEMBER_REVISED -> pensionAllowance2022()
        TaxYear.TWENTY_TWENTY_THREE -> pensionAllowance2023()
        TaxYear.TWENTY_TWENTY_THREE_JANUARY_REVISED -> pensionAllowance2023JanuaryRevised()
        TaxYear.TWENTY_TWENTY_FOUR -> pensionAllowance2024()
    }

    private fun pensionAllowance2020() = PensionAllowance(10000.0)

    private fun pensionAllowance2021() = PensionAllowance(10000.0)

    private fun pensionAllowance2022() = PensionAllowance(10000.0)

    private fun pensionAllowance2023() = PensionAllowance(10000.0)

    private fun pensionAllowance2023JanuaryRevised() = PensionAllowance(10000.0)

    private fun pensionAllowance2024() = PensionAllowance(10000.0)

    internal data class PensionAllowance(val annualAllowance: Double)
}
