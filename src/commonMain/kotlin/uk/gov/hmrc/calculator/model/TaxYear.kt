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
package uk.gov.hmrc.calculator.model

import com.soywiz.klock.DateTime
import uk.gov.hmrc.calculator.annotations.Throws
import uk.gov.hmrc.calculator.exception.InvalidTaxYearException
import uk.gov.hmrc.calculator.services.DateService

enum class TaxYear(private val value: Int) {
    TWENTY_TWENTY(2020),
    TWENTY_TWENTY_ONE(2021),
    TWENTY_TWENTY_TWO(2022),
    TWENTY_TWENTY_TWO_JULY_REVISED(2022),
    TWENTY_TWENTY_TWO_NOVEMBER_REVISED(2022);

    companion object {
        @Throws(InvalidTaxYearException::class)
        fun fromInt(value: Int): TaxYear =
            values()
                .firstOrNull { it.value == value }
                ?: throw InvalidTaxYearException("$value")

        val currentTaxYearInt: Int =
            DateTime
                .nowLocal()
                .let {
                    if (it < DateService.firstDayOfTaxYear(it.yearInt)) it.yearInt - 1 else it.yearInt
                }

        val currentTaxYear: TaxYear
            get() {
                val year = values().first { it.value == currentTaxYearInt }
                return if (year == TWENTY_TWENTY_TWO && DateService.isIn2022NovemberRevisedPeriod) {
                    TWENTY_TWENTY_TWO_NOVEMBER_REVISED
                } else if (year == TWENTY_TWENTY_TWO && DateService.isIn2022JulyRevisedPeriod) {
                    TWENTY_TWENTY_TWO_JULY_REVISED
                } else year
            }
    }
}
