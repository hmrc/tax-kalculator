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
package uk.gov.hmrc.calculator

import uk.gov.hmrc.calculator.exception.InvalidTaxYearException
import uk.gov.hmrc.calculator.model.DefaultTaxCodeResponse
import uk.gov.hmrc.calculator.model.TaxYear
import uk.gov.hmrc.calculator.model.taxcodes.TaxCode

object CalculatorUtils {

    @Throws(InvalidTaxYearException::class)
    fun defaultTaxCode(taxYear: Int = TaxYear.currentTaxYearInt): DefaultTaxCodeResponse =
        DefaultTaxCodeResponse(
            TaxCode.getDefaultTaxCode(TaxYear.fromInt(taxYear)),
            taxYear
        )

    fun currentTaxYear(): Int = TaxYear.currentTaxYearInt
}
