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
import kotlin.test.assertEquals

class DateServiceTests {

    private lateinit var sut: DateService

    @Test
    fun `WHEN date is 6th July 2022, THEN isIn2022RevisedPeriod returns true`() {
        sut = DateServiceImpl(dateTimeService = MockDateTimeService(DateTime.invoke(2022, Month.July, 6)))
        assertEquals(true, sut.isIn2022RevisedPeriod)
    }
    @Test
    fun `WHEN date is after 6th July 2022, THEN isIn2022RevisedPeriod returns true`() {
        sut = DateServiceImpl(dateTimeService = MockDateTimeService(DateTime.invoke(2022, Month.August, 10)))
        assertEquals(true, sut.isIn2022RevisedPeriod)
    }
    @Test
    fun `WHEN date is before 6th July 2022, THEN isIn2022RevisedPeriod returns false`() {
        sut = DateServiceImpl(dateTimeService = MockDateTimeService(DateTime.invoke(2022, Month.July, 5)))
        assertEquals(false, sut.isIn2022RevisedPeriod)
    }
}
