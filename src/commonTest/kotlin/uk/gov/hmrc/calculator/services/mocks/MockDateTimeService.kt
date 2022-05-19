package uk.gov.hmrc.calculator.services.mocks

import com.soywiz.klock.DateTime
import uk.gov.hmrc.calculator.services.DateTimeService

class MockDateTimeService(val dateTime: DateTime) : DateTimeService {
    override fun now(): DateTime = dateTime
}