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
package calculator.utils

import com.soywiz.klock.DateTime

internal class TaxYear {
    fun currentTaxYear(): Int {
        val date = DateTime.nowLocal()
        return if (date < firstDayOfTaxYear(date.yearInt)) date.yearInt - 1 else date.yearInt
    }

    private fun firstDayOfTaxYear(year: Int) = DateTime(year = year, month = 4, day = 6).local
}
