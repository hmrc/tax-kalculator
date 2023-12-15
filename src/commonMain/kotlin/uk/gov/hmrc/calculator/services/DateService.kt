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
    val isIn2022JulyRevisedPeriod: Boolean
    val isIn2022NovemberRevisedPeriod: Boolean
    val isIn2023JanuaryRevisedPeriod: Boolean
    fun firstDayOfTaxYear(year: Int): DateTimeTz

    companion object {
        fun firstDayOfTaxYear(year: Int): DateTimeTz =
            DateServiceImpl().firstDayOfTaxYear(year)

        val isIn2022JulyRevisedPeriod: Boolean =
            DateServiceImpl().isIn2022JulyRevisedPeriod

        val isIn2022NovemberRevisedPeriod: Boolean =
            DateServiceImpl().isIn2022NovemberRevisedPeriod

        val isIn2023JanuaryRevisedPeriod: Boolean =
            DateServiceImpl().isIn2023JanuaryRevisedPeriod
    }
}

class DateServiceImpl(
    private val dateTimeService: DateTimeService = DateTimeServiceImpl()
) : DateService {

    private val julyRevisedStartDate: DateTimeTz =
        DateTime(
            year = 2022,
            month = 7,
            day = 6
        ).local

    private val novemberRevisedStartDate: DateTimeTz =
        DateTime(
            year = 2022,
            month = 11,
            day = 6
        ).local

    private val january2023RevisedStartDate: DateTimeTz =
        DateTime(
            year = 2024,
            month = 1,
            day = 6
        ).local

    override val isIn2022JulyRevisedPeriod: Boolean =
        now() < novemberRevisedStartDate &&
            now() >= julyRevisedStartDate

    override val isIn2022NovemberRevisedPeriod: Boolean =
        now() >= novemberRevisedStartDate

    override val isIn2023JanuaryRevisedPeriod: Boolean =
        now() >= january2023RevisedStartDate

    override fun firstDayOfTaxYear(year: Int): DateTimeTz =
        DateTime(
            year = year,
            month = 4,
            day = 6
        ).local

    private fun now(): DateTimeTz = dateTimeService.now().local
}
