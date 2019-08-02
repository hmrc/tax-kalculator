package calculator

import Calculator
import model.PayPeriod.HOURLY
import kotlin.test.Test

import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import CalculatorHelper
import utils.InvalidHours


class CalculatorTest {

    @Test
    fun `Error When Hours=0 And PayPeriod Is HOURLY`() {
        assertFailsWith<InvalidHours> {
            Calculator("1250L", 20.0, payPeriod = HOURLY, hoursPerWeek = 0.0, taxYear = 2019)
        }
    }
    @Test
    fun `Error When Hours=null And PayPeriod Is HOURLY`() {
        assertFailsWith<InvalidHours> {
            Calculator("1250L", 20.0, payPeriod = HOURLY, taxYear = 2019)
        }
    }
}

class CalculatorHelperTest{

    @Test
    fun `Get Default Tax code for year`(){
        assertEquals(CalculatorHelper().getDefaultTaxCode(), "1250L")
    }
}