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
import com.soywiz.klock.Month
import uk.gov.hmrc.calculator.services.mocks.MockDateTimeService
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DateServiceTests {

    private lateinit var sut: DateService

    @Test
    fun `WHEN date is 6th July 2022 THEN isIn2022JulyRevisedPeriod returns true`() {
        sut = DateServiceImpl(
            dateTimeService = MockDateTimeService(
                DateTime.invoke(
                    2022,
                    Month.July,
                    6
                )
            )
        )
        assertTrue(sut.isIn2022JulyRevisedPeriod)
    }

    @Test
    fun `WHEN date is after 6th July 2022 AND before 6th November THEN isIn2022JulyRevisedPeriod returns true`() {
        sut = DateServiceImpl(
            dateTimeService = MockDateTimeService(
                DateTime.invoke(
                    2022,
                    Month.August,
                    10
                )
            )
        )
        assertTrue(sut.isIn2022JulyRevisedPeriod)
    }

    @Test
    fun `WHEN date is after 6th July 2022 AND after 6th November THEN isIn2022JulyRevisedPeriod returns true`() {
        sut = DateServiceImpl(
            dateTimeService = MockDateTimeService(
                DateTime.invoke(
                    2022,
                    Month.December,
                    10
                )
            )
        )
        assertFalse(sut.isIn2022JulyRevisedPeriod)
    }

    @Test
    fun `WHEN date is after 6th July 2022 THEN isIn2022JulyRevisedPeriod returns true`() {
        sut = DateServiceImpl(
            dateTimeService = MockDateTimeService(
                DateTime.invoke(
                    2022,
                    Month.August,
                    10
                )
            )
        )
        assertTrue(sut.isIn2022JulyRevisedPeriod)
    }

    @Test
    fun `WHEN date is before 6th July 2022 THEN isIn2022JulyRevisedPeriod returns false`() {
        sut = DateServiceImpl(
            dateTimeService = MockDateTimeService(
                DateTime.invoke(
                    2022,
                    Month.July,
                    5
                )
            )
        )
        assertFalse(sut.isIn2022JulyRevisedPeriod)
    }

    @Test
    fun `WHEN date is before 6th November 2022 THEN isIn2022NovemberRevisedPeriod returns false`() {
        sut = DateServiceImpl(
            dateTimeService = MockDateTimeService(
                DateTime.invoke(
                    2022,
                    Month.November,
                    5
                )
            )
        )
        assertFalse(sut.isIn2022NovemberRevisedPeriod)
    }

    @Test
    fun `WHEN date is after 6th November 2022 THEN isIn2022NovemberRevisedPeriod returns false`() {
        sut = DateServiceImpl(
            dateTimeService = MockDateTimeService(
                DateTime.invoke(
                    2022,
                    Month.November,
                    15
                )
            )
        )
        assertTrue(sut.isIn2022NovemberRevisedPeriod)
    }
}
