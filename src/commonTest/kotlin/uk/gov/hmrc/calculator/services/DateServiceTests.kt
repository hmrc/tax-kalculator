package uk.gov.hmrc.calculator.services

import com.soywiz.klock.DateTime
import com.soywiz.klock.Month
import uk.gov.hmrc.calculator.services.mocks.MockDateTimeService
import kotlin.test.Test

class DateServiceTests {

    private lateinit var sut: DateService

    @Test
    fun `GIVEN I am a superhero THEN i can do awesome things`() {
        sut = DateServiceImpl(dateTimeService = MockDateTimeService(DateTime.invoke(2022, Month.April, 12)))
    }

}