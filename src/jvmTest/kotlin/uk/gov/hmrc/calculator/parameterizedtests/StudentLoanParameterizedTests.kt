/*
 * Copyright 2024 HM Revenue & Customs
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
package uk.gov.hmrc.calculator.parameterizedtests

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import uk.gov.hmrc.calculator.Calculator
import uk.gov.hmrc.calculator.model.PayPeriod
import uk.gov.hmrc.calculator.model.TaxYear
import uk.gov.hmrc.calculator.utils.prettyPrintDataClass
import kotlin.test.assertEquals

@Suppress("LongParameterList")
class StudentLoanParameterizedTests {

    @ParameterizedTest(name = "wages={0}")
    @CsvFileSource(resources = ["/studentloan/data2024_student_loan.csv"], numLinesToSkip = 1)
    fun `Student Loan calculation 2024`(
        inputWage: Double,
        inputHasPlanOne: Boolean,
        inputHasPlanTwo: Boolean,
        inputHasPlanFour: Boolean,
        inputHasPostgraduatePlan: Boolean,
        expectedYearlyStudentLoan: Double,
        expectedMonthlyStudentLoan: Double,
        expectedFourWeeklyStudentLoan: Double,
        expectedWeeklyStudentLoan: Double,
        expectedYearlyPostgraduateLoan: Double,
        expectedMonthlyPostgraduateLoan: Double,
        expectedFourWeeklyPostgraduateLoan: Double,
        expectedWeeklyPostgraduateLoan: Double,
    ) {
        val response = Calculator(
            taxCode = "1257L",
            wages = inputWage,
            payPeriod = PayPeriod.YEARLY,
            taxYear = TaxYear.TWENTY_TWENTY_FOUR,
            studentLoanPlans = Calculator.StudentLoanPlans(
                inputHasPlanOne,
                inputHasPlanTwo,
                inputHasPlanFour,
                inputHasPostgraduatePlan,
            )
        ).run()

        val yearlyPeriod = response.yearly
        val monthly = response.monthly
        val fourWeekly = response.fourWeekly
        val weekly = response.weekly
        println(yearlyPeriod.prettyPrintDataClass())
        println(monthly.prettyPrintDataClass())
        println(fourWeekly.prettyPrintDataClass())
        println(weekly.prettyPrintDataClass())
        assertEquals(expectedYearlyStudentLoan, yearlyPeriod.finalStudentLoanAmount)
        assertEquals(expectedMonthlyStudentLoan, monthly.finalStudentLoanAmount)
        assertEquals(expectedFourWeeklyStudentLoan, fourWeekly.finalStudentLoanAmount)
        assertEquals(expectedWeeklyStudentLoan, weekly.finalStudentLoanAmount)
        assertEquals(expectedYearlyPostgraduateLoan, yearlyPeriod.finalPostgraduateLoanAmount)
        assertEquals(expectedMonthlyPostgraduateLoan, monthly.finalPostgraduateLoanAmount)
        assertEquals(expectedFourWeeklyPostgraduateLoan, fourWeekly.finalPostgraduateLoanAmount)
        assertEquals(expectedWeeklyPostgraduateLoan, weekly.finalPostgraduateLoanAmount)
    }

    @ParameterizedTest(name = "wages={0}")
    @CsvFileSource(resources = ["/studentloan/data2025_student_loan.csv"], numLinesToSkip = 1)
    fun `Student Loan calculation 2025`(
        inputWage: Double,
        inputHasPlanOne: Boolean,
        inputHasPlanTwo: Boolean,
        inputHasPlanFour: Boolean,
        inputHasPostgraduatePlan: Boolean,
        expectedYearlyStudentLoan: Double,
        expectedMonthlyStudentLoan: Double,
        expectedFourWeeklyStudentLoan: Double,
        expectedWeeklyStudentLoan: Double,
        expectedYearlyPostgraduateLoan: Double,
        expectedMonthlyPostgraduateLoan: Double,
        expectedFourWeeklyPostgraduateLoan: Double,
        expectedWeeklyPostgraduateLoan: Double,
    ) {
        val response = Calculator(
            taxCode = "1257L",
            wages = inputWage,
            payPeriod = PayPeriod.YEARLY,
            taxYear = TaxYear.TWENTY_TWENTY_FIVE,
            studentLoanPlans = Calculator.StudentLoanPlans(
                inputHasPlanOne,
                inputHasPlanTwo,
                inputHasPlanFour,
                inputHasPostgraduatePlan,
            )
        ).run()

        val yearlyPeriod = response.yearly
        val monthly = response.monthly
        val fourWeekly = response.fourWeekly
        val weekly = response.weekly
        println(yearlyPeriod.prettyPrintDataClass())
        println(monthly.prettyPrintDataClass())
        println(fourWeekly.prettyPrintDataClass())
        println(weekly.prettyPrintDataClass())
        assertEquals(expectedYearlyStudentLoan, yearlyPeriod.finalStudentLoanAmount)
        assertEquals(expectedMonthlyStudentLoan, monthly.finalStudentLoanAmount)
        assertEquals(expectedFourWeeklyStudentLoan, fourWeekly.finalStudentLoanAmount)
        assertEquals(expectedWeeklyStudentLoan, weekly.finalStudentLoanAmount)
        assertEquals(expectedYearlyPostgraduateLoan, yearlyPeriod.finalPostgraduateLoanAmount)
        assertEquals(expectedMonthlyPostgraduateLoan, monthly.finalPostgraduateLoanAmount)
        assertEquals(expectedFourWeeklyPostgraduateLoan, fourWeekly.finalPostgraduateLoanAmount)
        assertEquals(expectedWeeklyPostgraduateLoan, weekly.finalPostgraduateLoanAmount)
    }

    @ParameterizedTest(name = "wages={0}")
    @CsvFileSource(resources = ["/studentloan/data2025_Oct_student_loan.csv"], numLinesToSkip = 1)
    fun `Student Loan calculation OCT 2025`(
        inputWage: Double,
        inputHasPlanOne: Boolean,
        inputHasPlanTwo: Boolean,
        inputHasPlanFour: Boolean,
        inputHasPlanFive: Boolean,
        inputHasPostgraduatePlan: Boolean,
        expectedYearlyStudentLoan: Double,
        expectedMonthlyStudentLoan: Double,
        expectedFourWeeklyStudentLoan: Double,
        expectedWeeklyStudentLoan: Double,
        expectedYearlyPostgraduateLoan: Double,
        expectedMonthlyPostgraduateLoan: Double,
        expectedFourWeeklyPostgraduateLoan: Double,
        expectedWeeklyPostgraduateLoan: Double,
    ) {
        val response = Calculator(
            taxCode = "1257L",
            wages = inputWage,
            payPeriod = PayPeriod.YEARLY,
            taxYear = TaxYear.TWENTY_TWENTY_FIVE,
            studentLoanPlans = Calculator.StudentLoanPlans(
                inputHasPlanOne,
                inputHasPlanTwo,
                inputHasPlanFour,
                inputHasPostgraduatePlan,
                inputHasPlanFive,
            )
        ).run()

        val yearlyPeriod = response.yearly
        val monthly = response.monthly
        val fourWeekly = response.fourWeekly
        val weekly = response.weekly
        println(yearlyPeriod.prettyPrintDataClass())
        println(monthly.prettyPrintDataClass())
        println(fourWeekly.prettyPrintDataClass())
        println(weekly.prettyPrintDataClass())
        assertEquals(expectedYearlyStudentLoan, yearlyPeriod.finalStudentLoanAmount)
        assertEquals(expectedMonthlyStudentLoan, monthly.finalStudentLoanAmount)
        assertEquals(expectedFourWeeklyStudentLoan, fourWeekly.finalStudentLoanAmount)
        assertEquals(expectedWeeklyStudentLoan, weekly.finalStudentLoanAmount)
        assertEquals(expectedYearlyPostgraduateLoan, yearlyPeriod.finalPostgraduateLoanAmount)
        assertEquals(expectedMonthlyPostgraduateLoan, monthly.finalPostgraduateLoanAmount)
        assertEquals(expectedFourWeeklyPostgraduateLoan, fourWeekly.finalPostgraduateLoanAmount)
        assertEquals(expectedWeeklyPostgraduateLoan, weekly.finalPostgraduateLoanAmount)
    }

    @ParameterizedTest(name = "wages={0}")
    @CsvFileSource(resources = ["/studentloan/data2026_student_loan.csv"], numLinesToSkip = 1)
    fun `Student Loan calculation 2026`(
        inputWage: Double,
        inputHasPlanOne: Boolean,
        inputHasPlanTwo: Boolean,
        inputHasPlanFour: Boolean,
        inputHasPlanFive: Boolean,
        inputHasPostgraduatePlan: Boolean,
        expectedYearlyStudentLoan: Double,
        expectedMonthlyStudentLoan: Double,
        expectedFourWeeklyStudentLoan: Double,
        expectedWeeklyStudentLoan: Double,
        expectedYearlyPostgraduateLoan: Double,
        expectedMonthlyPostgraduateLoan: Double,
        expectedFourWeeklyPostgraduateLoan: Double,
        expectedWeeklyPostgraduateLoan: Double,
    ) {
        val response = Calculator(
            taxCode = "1257L",
            wages = inputWage,
            payPeriod = PayPeriod.YEARLY,
            taxYear = TaxYear.TWENTY_TWENTY_SIX,
            studentLoanPlans = Calculator.StudentLoanPlans(
                inputHasPlanOne,
                inputHasPlanTwo,
                inputHasPlanFour,
                inputHasPostgraduatePlan,
                inputHasPlanFive,
            )
        ).run()

        val yearlyPeriod = response.yearly
        val monthly = response.monthly
        val fourWeekly = response.fourWeekly
        val weekly = response.weekly
        println(yearlyPeriod.prettyPrintDataClass())
        println(monthly.prettyPrintDataClass())
        println(fourWeekly.prettyPrintDataClass())
        println(weekly.prettyPrintDataClass())
        assertEquals(expectedYearlyStudentLoan, yearlyPeriod.finalStudentLoanAmount)
        assertEquals(expectedMonthlyStudentLoan, monthly.finalStudentLoanAmount)
        assertEquals(expectedFourWeeklyStudentLoan, fourWeekly.finalStudentLoanAmount)
        assertEquals(expectedWeeklyStudentLoan, weekly.finalStudentLoanAmount)
        assertEquals(expectedYearlyPostgraduateLoan, yearlyPeriod.finalPostgraduateLoanAmount)
        assertEquals(expectedMonthlyPostgraduateLoan, monthly.finalPostgraduateLoanAmount)
        assertEquals(expectedFourWeeklyPostgraduateLoan, fourWeekly.finalPostgraduateLoanAmount)
        assertEquals(expectedWeeklyPostgraduateLoan, weekly.finalPostgraduateLoanAmount)
    }
}
