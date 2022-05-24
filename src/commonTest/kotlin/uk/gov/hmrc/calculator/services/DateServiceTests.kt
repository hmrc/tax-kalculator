package uk.gov.hmrc.calculator.services

import com.soywiz.klock.Date
import com.soywiz.klock.DateTime
import com.soywiz.klock.Month
import uk.gov.hmrc.calculator.Calculator
import uk.gov.hmrc.calculator.model.PayPeriod
import uk.gov.hmrc.calculator.model.TaxYear
import uk.gov.hmrc.calculator.model.bands.EmployeeNIBand
import uk.gov.hmrc.calculator.model.bands.EmployeeNIBands
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