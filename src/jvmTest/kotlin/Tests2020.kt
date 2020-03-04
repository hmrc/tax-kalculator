import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory
import uk.gov.hmrc.calculator.Calculator
import uk.gov.hmrc.calculator.model.CalculatorResponse
import uk.gov.hmrc.calculator.model.Country
import uk.gov.hmrc.calculator.model.PayPeriod
import kotlin.test.assertEquals

data class TestData(
    val country: Country,
    val scenario: String,
    val salary: Double,
    val taxCode: String,
    val incomeTax: Double,
    val niEmployee: Double,
    val totalDeduction: Double,
    val takeHome: Double,
    val niEmployer: Double)

internal class Tests2020 {

    @TestFactory
    fun `2020 Tests`() = listOf(
        TestData(
            country = Country.ENGLAND,
            scenario = "Basic rate L",
            salary = 43000.00,
            taxCode = "1250L",
            incomeTax = 6098.200000000001,
            niEmployee = 4020.00,
            totalDeduction = 10118.20,
            takeHome = 32881.80,
            niEmployer = 4721.256)).map {
        dynamicTest("${it.country} - ${it.scenario} - ${it.taxCode} - ${it.salary}") {

            val response: CalculatorResponse =
                Calculator(it.taxCode, it.salary, payPeriod = PayPeriod.YEARLY, taxYear = 2020).run()

            assertEquals(response.yearly.employeesNI, it.niEmployee)
            assertEquals(response.yearly.takeHome, it.takeHome)
            assertEquals(response.yearly.taxToPay, it.incomeTax)
            assertEquals(response.yearly.totalDeductions, it.totalDeduction)
            assertEquals(response.yearly.employersNI, it.niEmployer)
        }
    }

}


//import kotlin.test.Test
//
//data class TestData(val title: String, val taxCode: String)
//
//class LoopTest {
//
//    val listOfTest = listOf(TestData("dasdas", "dasdas"), TestData("dasds", "Dasdas"))
//
//    @Test
//    fun `2020 Test Scenarios`() {
//
//        listOfTest.forEach { testData: TestData ->
//            @Test
//            fun `Title: ${testData.title} TaxCode: ${testData.taxCode}`(string: String) {
//
//            }
//        }
//    }
//}