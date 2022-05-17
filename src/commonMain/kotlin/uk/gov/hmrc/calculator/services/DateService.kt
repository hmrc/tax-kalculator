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
package uk.gov.hmrc.calculator.services

import com.soywiz.klock.DateTime
import com.soywiz.klock.DateTimeTz

interface DateService {
    val isIn2022RevisedPeriod: Boolean
    fun firstDayOfTaxYear(year: Int): DateTimeTz
}

class DateServiceImpl : DateService {

    override val isIn2022RevisedPeriod: Boolean =
        DateTime.now().local > DateTime(
            year = 2022,
            month = 7,
            day = 5
        ).local

    override fun firstDayOfTaxYear(year: Int): DateTimeTz =
        DateTime(
            year = year,
            month = 4,
            day = 6
        ).local

    companion object {
        fun firstDayOfTaxYear(year: Int): DateTimeTz =
            DateServiceImpl().firstDayOfTaxYear(year)

        val isIn2022RevisedPeriod: Boolean =
            DateServiceImpl().isIn2022RevisedPeriod
    }
}
