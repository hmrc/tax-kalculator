import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.Assertions.assertEquals
import uk.gov.hmrc.calculator.Calculator
import uk.gov.hmrc.calculator.model.CalculatorResponse
import uk.gov.hmrc.calculator.model.Country
import uk.gov.hmrc.calculator.model.PayPeriod

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
        TestData(Country.ENGLAND, "Basic rate L", 43000.0, "1250L", 6098.2, 4020.0, 10118.2, 32881.8, 4721.26),
        TestData(Country.ENGLAND, "Higher rate L", 75500.0, "980L", 18778.2, 5370.0, 24148.2, 51351.8, 9206.26),
        TestData(Country.ENGLAND, "Marriage allowance M", 43000.0, "1500M", 5598.2, 4020.0, 9618.2, 33381.8, 4721.26),
        TestData(Country.ENGLAND, "Marriage allowance N", 43000.0, "1000N", 6598.2, 4020.0, 10618.2, 32381.8, 4721.26),
        TestData(Country.ENGLAND, "Emergency Rate W1", 20000.0, "1250 W1", 1498.2, 1260.0, 2758.2, 17241.8, 1547.26),
        TestData(Country.ENGLAND, "Emergency Rate M1", 20000.0, "1250 M1", 1498.2, 1260.0, 2758.2, 17241.8, 1547.26),
        TestData(Country.ENGLAND, "Emergency Rate X", 20000.0, "1250 X", 1498.2, 1260.0, 2758.2, 17241.8, 1547.26),
        TestData(Country.ENGLAND, "Emergency RateL X", 20000.0, "1250L X", 1498.2, 1260.0, 2758.2, 17241.8, 1547.26),
        TestData(Country.ENGLAND , "Higher rate (no PA)",119000.0,"1250L",35098.20,6240.00 ,41338.20 ,77661.80 ,15209.26),
        TestData(Country.ENGLAND, "K code", 45000.0, "K100", 10905.40, 4260.0, 15165.4, 29834.6, 4997.26),
        TestData(Country.ENGLAND, "NT code", 20000.0, "NT", 0.0, 1260.0, 1260.0, 18740.0, 1547.26),
        TestData(Country.ENGLAND, "0T code", 60000.0, "0T", 16501.80, 5060.0, 21561.8, 38438.2, 7067.26),
        TestData(Country.ENGLAND, "BR code", 10000.0, "BR", 2000.0, 60.0, 2060.0, 7940.0, 167.26),
        TestData(Country.ENGLAND, "D0 code", 12000.0, "D0", 4800.0, 300.0, 5100.0, 6900.0, 443.26),
        TestData(Country.ENGLAND, "D1 code", 12000.0, "D1", 5400.0, 300.0, 5700.0, 6300.0, 443.26),
        TestData(Country.WALES, "Basic rate L", 43000.0, "C1250L", 6098.2, 4020.0, 10118.2, 32881.80, 4721.26),
        TestData(Country.WALES, "Higher rate L", 75500.0, "C980L", 18778.2, 5370.0, 24148.2, 51351.8, 9206.26),
        TestData(Country.WALES, "Marriage allowance M", 43000.0, "C1500M", 5598.2, 4020.0, 9618.2, 33381.8, 4721.26),
        TestData(Country.WALES, "Marriage allowance N", 43000.0, "C1000N", 6598.2, 4020.0, 10618.2, 32381.8, 4721.26),
        TestData(Country.WALES, "Emergency Rate W1", 20000.0, "C1250 W1", 1498.2, 1260.0, 2758.2, 17241.8, 1547.26),
        TestData(Country.WALES, "Emergency Rate M1", 20000.0, "C1250 M1", 1498.2, 1260.0, 2758.2, 17241.8, 1547.26),
        TestData(Country.WALES, "Emergency Rate X", 20000.0, "C1250 X", 1498.2, 1260.0, 2758.2, 17241.8, 1547.26),
        TestData(Country.WALES, "Emergency RateL X", 20000.0, "C1250L X", 1498.2, 1260.0, 2758.2, 17241.8, 1547.26),
        TestData(Country.WALES, "K code", 45000.0, "CK100", 10905.4, 4260.0, 15165.4, 29834.6, 4997.26),
        TestData(Country.WALES, "NT code", 20000.0, "NT", 0.0, 1260.0, 1260.0, 18740.0, 1547.26),
        TestData(Country.WALES, "0T code", 60000.0, "C0T", 16501.8, 5060.0, 21561.8, 38438.2, 7067.26),
        TestData(Country.WALES, "BR code", 10000.0, "CBR", 2000.0, 60.0, 2060.0, 7940.0, 167.26),
        TestData(Country.WALES, "D0 code", 12000.0, "CD0", 4800.0, 300.0, 5100.0, 6900.0, 443.26),
        TestData(Country.WALES, "D1 code", 12000.0, "CD1", 5400.0, 300.0, 5700.0, 6300.0, 443.26),
        TestData(Country.SCOTLAND , "Basic rate L",43000.0,"S1250L",6258.36,4020.0,10278.36,32721.64,4721.26),
        TestData(Country.SCOTLAND , "Higher rate L",75500.0,"S980L",20604.36,5370.0,25974.36,49525.64,9206.26),
        TestData(Country.SCOTLAND, "Marriage allowance M", 20000.0, "S1375M", 1227.8, 1260.0, 2487.8, 17512.2, 1547.26),
        TestData(Country.SCOTLAND, "Marriage allowance N", 10000.0, "S1125N", 0.0, 60.0, 60.0, 9940.0, 167.26),
        TestData(Country.SCOTLAND, "Emergency Rate", 20000.0, "S1250 W1", 1477.8, 1260.0, 2737.8, 17262.2, 1547.26),
        TestData(Country.SCOTLAND, "Emergency Rate", 20000.0, "S1250 M1", 1477.8, 1260.0, 2737.8, 17262.2, 1547.26),
        TestData(Country.SCOTLAND, "Emergency Rate", 20000.0, "S1250 X", 1477.8, 1260.0, 2737.8, 17262.2, 1547.26),
        TestData(Country.SCOTLAND, "Emergency Rate", 20000.0, "S1250L X", 1477.8, 1260.0, 2737.8, 17262.2, 1547.26),
        TestData(Country.SCOTLAND, "K code", 20000.0, "SK100", 4267.14, 1260.0, 5527.14, 14472.86, 1547.26),
        TestData(Country.SCOTLAND, "NT code", 20000.0, "NT", 0.0, 1260.0, 1260.0, 18740.0, 1547.26),
        TestData(Country.SCOTLAND, "0T code", 60000.0, "S0T", 18271.05, 5060.0, 23331.05, 36668.95, 7067.26),
        TestData(Country.SCOTLAND, "BR code", 10000.0, "SBR", 2000.0, 60.0, 2060.0, 7940.0, 167.26),
        TestData(Country.SCOTLAND, "D0 code", 12000.0, "SD0", 2520.0, 300.0, 2820.0, 9180.0, 443.26),
        TestData(Country.SCOTLAND, "D1 code", 12000.0, "SD1", 4920.0, 300.0, 5220.0, 6780.0, 443.26),
        TestData(Country.SCOTLAND, "D2 code", 12000.0, "SD2", 5520.0, 300.0, 5820.0, 6180.0, 443.26)
        ).map {

        dynamicTest("${it.country} - ${it.scenario} - ${it.taxCode} - ${it.salary}") {

            val response: CalculatorResponse =
                Calculator(it.taxCode, it.salary, payPeriod = PayPeriod.YEARLY, taxYear = 2020).run()


//            print(response.yearly.taxBreakdown)

            assertEquals(it.niEmployee, response.yearly.employeesNI, 0.01)
            assertEquals(it.niEmployer, response.yearly.employersNI, 0.01)
            assertEquals(it.incomeTax, response.yearly.taxToPay, 0.01)
            assertEquals(it.totalDeduction, response.yearly.totalDeductions, 0.01)
            assertEquals(it.takeHome, response.yearly.takeHome, 0.01)
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