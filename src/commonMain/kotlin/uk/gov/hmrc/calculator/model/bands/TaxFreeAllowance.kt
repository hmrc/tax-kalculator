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
package uk.gov.hmrc.calculator.model.bands

import uk.gov.hmrc.calculator.model.TaxYear

internal object TaxFreeAllowance {

    fun getAllowance(taxYear: TaxYear): Double =
        when (taxYear) {
            TaxYear.TWENTY_TWENTY -> 12500.00
            TaxYear.TWENTY_TWENTY_ONE, TaxYear.TWENTY_TWENTY_TWO -> 12570.00
        }
}
