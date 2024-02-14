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
package uk.gov.hmrc.calculator

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.converter.ArgumentConverter
import org.junit.jupiter.params.converter.ConvertWith
import org.junit.jupiter.params.provider.CsvFileSource
import uk.gov.hmrc.calculator.model.CalculatorResponse
import uk.gov.hmrc.calculator.model.Country
import uk.gov.hmrc.calculator.model.PayPeriod
import uk.gov.hmrc.calculator.model.TaxYear
import uk.gov.hmrc.calculator.model.pension.AnnualPensionMethod
import uk.gov.hmrc.calculator.utils.prettyPrintDataClass

internal class ParameterizedCalculatorTests {

    @ParameterizedTest(name = "tax_code={0}, wages={1}, pay_period={2}, tax_year={3}, is_pension_age={4}")
    @CsvFileSource(resources = ["/data2021.csv"], numLinesToSkip = 1)
    fun `Tax calculations 2021`(
        inputTaxCode: String,
        inputWages: Double,
        @ConvertWith(PayPeriodConverter::class) inputPayPeriod: PayPeriod,
        @ConvertWith(TaxYearConverter::class) inputTaxYear: TaxYear,
        inputIsPensionAge: Boolean,
        @ConvertWith(CountryConverter::class) expectedCountry: Country,
        expectedYearlyNiEmployee: Double,
        expectedYearlyNiEmployer: Double,
        expectedYearlyIncomeTax: Double,
        expectedYearlyTotalDeduction: Double,
        expectedYearlyTakeHome: Double,
        expectedYearlyWages: Double,
        expectedYearlyTaxFreeAmount: Double,
        expectedYearlyKCodeAdjustment: Double?,
        expectedIsKCode: Boolean
    ) {
        val response = Calculator(
            taxCode = inputTaxCode,
            wages = inputWages,
            payPeriod = inputPayPeriod,
            taxYear = inputTaxYear,
            isPensionAge = inputIsPensionAge
        ).run()

        checkResults(
            expectedCountry,
            response,
            expectedIsKCode,
            inputTaxCode,
            expectedYearlyNiEmployer,
            expectedYearlyNiEmployee,
            expectedYearlyIncomeTax,
            expectedYearlyTotalDeduction,
            expectedYearlyTakeHome,
            expectedYearlyWages,
            expectedYearlyTaxFreeAmount,
            expectedYearlyKCodeAdjustment
        )
    }

    @ParameterizedTest(name = "tax_code={0}, wages={1}, pay_period={2}, tax_year={3}, is_pension_age={4}")
    @CsvFileSource(resources = ["/data2022.csv"], numLinesToSkip = 1)
    fun `Tax calculations 2022`(
        inputTaxCode: String,
        inputWages: Double,
        @ConvertWith(PayPeriodConverter::class) inputPayPeriod: PayPeriod,
        @ConvertWith(TaxYearConverter::class) inputTaxYear: TaxYear,
        inputIsPensionAge: Boolean,
        @ConvertWith(CountryConverter::class) expectedCountry: Country,
        expectedYearlyNiEmployee: Double,
        expectedYearlyNiEmployer: Double,
        expectedYearlyIncomeTax: Double,
        expectedYearlyTotalDeduction: Double,
        expectedYearlyTakeHome: Double,
        expectedYearlyWages: Double,
        expectedYearlyTaxFreeAmount: Double,
        expectedYearlyKCodeAdjustment: Double?,
        expectedIsKCode: Boolean
    ) {
        val response = Calculator(
            taxCode = inputTaxCode,
            wages = inputWages,
            payPeriod = inputPayPeriod,
            taxYear = inputTaxYear,
            isPensionAge = inputIsPensionAge
        ).run()

        checkResults(
            expectedCountry,
            response,
            expectedIsKCode,
            inputTaxCode,
            expectedYearlyNiEmployer,
            expectedYearlyNiEmployee,
            expectedYearlyIncomeTax,
            expectedYearlyTotalDeduction,
            expectedYearlyTakeHome,
            expectedYearlyWages,
            expectedYearlyTaxFreeAmount,
            expectedYearlyKCodeAdjustment
        )
    }

    @ParameterizedTest(name = "tax_code={0}, wages={1}, pay_period={2}, tax_year={3}, is_pension_age={4}")
    @CsvFileSource(resources = ["/data2022_Nov_Revised.csv"], numLinesToSkip = 1)
    fun `Tax calculations 2022 Nov revised`(
        inputTaxCode: String,
        inputWages: Double,
        @ConvertWith(PayPeriodConverter::class) inputPayPeriod: PayPeriod,
        @ConvertWith(TaxYearConverter::class) inputTaxYear: TaxYear,
        inputIsPensionAge: Boolean,
        @ConvertWith(CountryConverter::class) expectedCountry: Country,
        expectedYearlyNiEmployee: Double,
        expectedYearlyNiEmployer: Double,
        expectedYearlyIncomeTax: Double,
        expectedYearlyTotalDeduction: Double,
        expectedYearlyTakeHome: Double,
        expectedYearlyWages: Double,
        expectedYearlyTaxFreeAmount: Double,
        expectedYearlyKCodeAdjustment: Double?,
        expectedIsKCode: Boolean
    ) {
        val response = Calculator(
            taxCode = inputTaxCode,
            wages = inputWages,
            payPeriod = inputPayPeriod,
            taxYear = inputTaxYear,
            isPensionAge = inputIsPensionAge
        ).run()

        checkResults(
            expectedCountry,
            response,
            expectedIsKCode,
            inputTaxCode,
            expectedYearlyNiEmployer,
            expectedYearlyNiEmployee,
            expectedYearlyIncomeTax,
            expectedYearlyTotalDeduction,
            expectedYearlyTakeHome,
            expectedYearlyWages,
            expectedYearlyTaxFreeAmount,
            expectedYearlyKCodeAdjustment
        )
    }

    @ParameterizedTest(name = "tax_code={0}, wages={1}, pay_period={2}, tax_year={3}, is_pension_age={4}")
    @CsvFileSource(resources = ["/data2023.csv"], numLinesToSkip = 1)
    fun `Tax calculations 2023`(
        inputTaxCode: String,
        inputWages: Double,
        @ConvertWith(PayPeriodConverter::class) inputPayPeriod: PayPeriod,
        @ConvertWith(TaxYearConverter::class) inputTaxYear: TaxYear,
        inputIsPensionAge: Boolean,
        @ConvertWith(CountryConverter::class) expectedCountry: Country,
        expectedYearlyNiEmployee: Double,
        expectedYearlyNiEmployer: Double,
        expectedYearlyIncomeTax: Double,
        expectedYearlyTotalDeduction: Double,
        expectedYearlyTakeHome: Double,
        expectedYearlyWages: Double,
        expectedYearlyTaxFreeAmount: Double,
        expectedYearlyKCodeAdjustment: Double?,
        expectedIsKCode: Boolean
    ) {
        val response = Calculator(
            taxCode = inputTaxCode,
            wages = inputWages,
            payPeriod = inputPayPeriod,
            taxYear = inputTaxYear,
            isPensionAge = inputIsPensionAge
        ).run()

        checkResults(
            expectedCountry,
            response,
            expectedIsKCode,
            inputTaxCode,
            expectedYearlyNiEmployer,
            expectedYearlyNiEmployee,
            expectedYearlyIncomeTax,
            expectedYearlyTotalDeduction,
            expectedYearlyTakeHome,
            expectedYearlyWages,
            expectedYearlyTaxFreeAmount,
            expectedYearlyKCodeAdjustment
        )
    }

    @ParameterizedTest(name = "tax_code={0}, wages={1}, pay_period={2}, tax_year={3}, is_pension_age={4}")
    @CsvFileSource(resources = ["/data2023_with_pension.csv"], numLinesToSkip = 1)
    fun `Tax calculations 2023 with 10 percent Pension contribution`(
        inputTaxCode: String,
        inputWages: Double,
        @ConvertWith(PayPeriodConverter::class) inputPayPeriod: PayPeriod,
        @ConvertWith(TaxYearConverter::class) inputTaxYear: TaxYear,
        inputIsPensionAge: Boolean,
        @ConvertWith(AnnualPensionMethodConverter::class) inputPensionMethod: AnnualPensionMethod,
        inputPensionAmount: Double?,
        inputPensionPercentage: Double?,
        @ConvertWith(CountryConverter::class) expectedCountry: Country,
        expectedYearlyNiEmployee: Double,
        expectedYearlyNiEmployer: Double,
        expectedYearlyIncomeTax: Double,
        expectedYearlyTotalDeduction: Double,
        expectedYearlyTakeHome: Double,
        expectedYearlyWages: Double,
        expectedYearlyTaxFreeAmount: Double,
        expectedYearlyKCodeAdjustment: Double?,
        expectedIsKCode: Boolean
    ) {
        val response = Calculator(
            taxCode = inputTaxCode,
            wages = inputWages,
            payPeriod = inputPayPeriod,
            taxYear = inputTaxYear,
            pensionMethod = inputPensionMethod,
            pensionYearlyAmount = inputPensionAmount,
            pensionPercentage = inputPensionPercentage,
            isPensionAge = inputIsPensionAge
        ).run()

        checkResults(
            expectedCountry,
            response,
            expectedIsKCode,
            inputTaxCode,
            expectedYearlyNiEmployer,
            expectedYearlyNiEmployee,
            expectedYearlyIncomeTax,
            expectedYearlyTotalDeduction,
            expectedYearlyTakeHome,
            expectedYearlyWages,
            expectedYearlyTaxFreeAmount,
            expectedYearlyKCodeAdjustment
        )
    }

    @ParameterizedTest(name = "tax_code={0}, wages={1}, pay_period={2}, tax_year={3}, is_pension_age={4}")
    @CsvFileSource(resources = ["/data2023_with_student_loan.csv"], numLinesToSkip = 1)
    fun `Tax calculations 2023 with student loan`(
        inputTaxCode: String,
        inputWages: Double,
        @ConvertWith(PayPeriodConverter::class) inputPayPeriod: PayPeriod,
        @ConvertWith(TaxYearConverter::class) inputTaxYear: TaxYear,
        inputIsPensionAge: Boolean,
        inputHasPlanOne: Boolean,
        inputHasPlanTwo: Boolean,
        inputHasPlanFour: Boolean,
        inputHasPostgraduatePlan: Boolean,
        @ConvertWith(CountryConverter::class) expectedCountry: Country,
        expectedYearlyNiEmployee: Double,
        expectedYearlyNiEmployer: Double,
        expectedYearlyIncomeTax: Double,
        expectedYearlyTotalDeduction: Double,
        expectedYearlyTakeHome: Double,
        expectedYearlyWages: Double,
        expectedYearlyTaxFreeAmount: Double,
        expectedYearlyKCodeAdjustment: Double?,
        expectedIsKCode: Boolean
    ) {
        val response = Calculator(
            taxCode = inputTaxCode,
            wages = inputWages,
            payPeriod = inputPayPeriod,
            taxYear = inputTaxYear,
            isPensionAge = inputIsPensionAge,
            hasStudentLoanPlanOne = inputHasPlanOne,
            hasStudentLoanPlanTwo = inputHasPlanTwo,
            hasStudentLoanPlanFour = inputHasPlanFour,
            hasStudentLoanPostgraduatePlan = inputHasPostgraduatePlan,
        ).run()

        checkResults(
            expectedCountry,
            response,
            expectedIsKCode,
            inputTaxCode,
            expectedYearlyNiEmployer,
            expectedYearlyNiEmployee,
            expectedYearlyIncomeTax,
            expectedYearlyTotalDeduction,
            expectedYearlyTakeHome,
            expectedYearlyWages,
            expectedYearlyTaxFreeAmount,
            expectedYearlyKCodeAdjustment
        )
    }

    @ParameterizedTest(name = "tax_code={0}, wages={1}, pay_period={2}, tax_year={3}, is_pension_age={4}")
    @CsvFileSource(resources = ["/data2023_Jan_Revised.csv"], numLinesToSkip = 1)
    fun `Tax calculations 2023 January Revised`(
        inputTaxCode: String,
        inputWages: Double,
        @ConvertWith(PayPeriodConverter::class) inputPayPeriod: PayPeriod,
        @ConvertWith(TaxYearConverter::class) inputTaxYear: TaxYear,
        inputIsPensionAge: Boolean,
        @ConvertWith(CountryConverter::class) expectedCountry: Country,
        expectedYearlyNiEmployee: Double,
        expectedYearlyNiEmployer: Double,
        expectedYearlyIncomeTax: Double,
        expectedYearlyTotalDeduction: Double,
        expectedYearlyTakeHome: Double,
        expectedYearlyWages: Double,
        expectedYearlyTaxFreeAmount: Double,
        expectedYearlyKCodeAdjustment: Double?,
        expectedIsKCode: Boolean
    ) {
        val response = Calculator(
            taxCode = inputTaxCode,
            wages = inputWages,
            payPeriod = inputPayPeriod,
            taxYear = inputTaxYear,
            isPensionAge = inputIsPensionAge
        ).run()

        checkResults(
            expectedCountry,
            response,
            expectedIsKCode,
            inputTaxCode,
            expectedYearlyNiEmployer,
            expectedYearlyNiEmployee,
            expectedYearlyIncomeTax,
            expectedYearlyTotalDeduction,
            expectedYearlyTakeHome,
            expectedYearlyWages,
            expectedYearlyTaxFreeAmount,
            expectedYearlyKCodeAdjustment
        )
    }

    @ParameterizedTest(name = "tax_code={0}, wages={1}, pay_period={2}, tax_year={3}, is_pension_age={4}")
    @CsvFileSource(resources = ["/data2024.csv"], numLinesToSkip = 1)
    fun `Tax calculations 2024`(
        inputTaxCode: String,
        inputWages: Double,
        @ConvertWith(PayPeriodConverter::class) inputPayPeriod: PayPeriod,
        @ConvertWith(TaxYearConverter::class) inputTaxYear: TaxYear,
        inputIsPensionAge: Boolean,
        @ConvertWith(CountryConverter::class) expectedCountry: Country,
        expectedYearlyNiEmployee: Double,
        expectedYearlyNiEmployer: Double,
        expectedYearlyIncomeTax: Double,
        expectedYearlyTotalDeduction: Double,
        expectedYearlyTakeHome: Double,
        expectedYearlyWages: Double,
        expectedYearlyTaxFreeAmount: Double,
        expectedYearlyKCodeAdjustment: Double?,
        expectedIsKCode: Boolean
    ) {
        val response = Calculator(
            taxCode = inputTaxCode,
            wages = inputWages,
            payPeriod = inputPayPeriod,
            taxYear = inputTaxYear,
            isPensionAge = inputIsPensionAge
        ).run()

        checkResults(
            expectedCountry,
            response,
            expectedIsKCode,
            inputTaxCode,
            expectedYearlyNiEmployer,
            expectedYearlyNiEmployee,
            expectedYearlyIncomeTax,
            expectedYearlyTotalDeduction,
            expectedYearlyTakeHome,
            expectedYearlyWages,
            expectedYearlyTaxFreeAmount,
            expectedYearlyKCodeAdjustment
        )
    }

    @ParameterizedTest(name = "tax_code={0}, wages={1}, pay_period={2}, tax_year={3}, is_pension_age={4}")
    @CsvFileSource(resources = ["/data2024_with_pension.csv"], numLinesToSkip = 1)
    fun `Tax calculations 2024 with 10 percent Pension contribution`(
        inputTaxCode: String,
        inputWages: Double,
        @ConvertWith(PayPeriodConverter::class) inputPayPeriod: PayPeriod,
        @ConvertWith(TaxYearConverter::class) inputTaxYear: TaxYear,
        inputIsPensionAge: Boolean,
        @ConvertWith(AnnualPensionMethodConverter::class) inputPensionMethod: AnnualPensionMethod,
        inputPensionAmount: Double?,
        inputPensionPercentage: Double?,
        @ConvertWith(CountryConverter::class) expectedCountry: Country,
        expectedYearlyNiEmployee: Double,
        expectedYearlyNiEmployer: Double,
        expectedYearlyIncomeTax: Double,
        expectedYearlyTotalDeduction: Double,
        expectedYearlyTakeHome: Double,
        expectedYearlyWages: Double,
        expectedYearlyTaxFreeAmount: Double,
        expectedYearlyKCodeAdjustment: Double?,
        expectedIsKCode: Boolean
    ) {
        val response = Calculator(
            taxCode = inputTaxCode,
            wages = inputWages,
            payPeriod = inputPayPeriod,
            taxYear = inputTaxYear,
            pensionMethod = inputPensionMethod,
            pensionYearlyAmount = inputPensionAmount,
            pensionPercentage = inputPensionPercentage,
            isPensionAge = inputIsPensionAge
        ).run()

        checkResults(
            expectedCountry,
            response,
            expectedIsKCode,
            inputTaxCode,
            expectedYearlyNiEmployer,
            expectedYearlyNiEmployee,
            expectedYearlyIncomeTax,
            expectedYearlyTotalDeduction,
            expectedYearlyTakeHome,
            expectedYearlyWages,
            expectedYearlyTaxFreeAmount,
            expectedYearlyKCodeAdjustment
        )
    }

    @ParameterizedTest(name = "tax_code={0}, wages={1}, pay_period={2}, tax_year={3}, is_pension_age={4}")
    @CsvFileSource(resources = ["/data2024_with_student_loan.csv"], numLinesToSkip = 1)
    fun `Tax calculations 2024 with student loan`(
        inputTaxCode: String,
        inputWages: Double,
        @ConvertWith(PayPeriodConverter::class) inputPayPeriod: PayPeriod,
        @ConvertWith(TaxYearConverter::class) inputTaxYear: TaxYear,
        inputIsPensionAge: Boolean,
        inputHasPlanOne: Boolean,
        inputHasPlanTwo: Boolean,
        inputHasPlanFour: Boolean,
        inputHasPostgraduatePlan: Boolean,
        @ConvertWith(CountryConverter::class) expectedCountry: Country,
        expectedYearlyNiEmployee: Double,
        expectedYearlyNiEmployer: Double,
        expectedYearlyIncomeTax: Double,
        expectedYearlyTotalDeduction: Double,
        expectedYearlyTakeHome: Double,
        expectedYearlyWages: Double,
        expectedYearlyTaxFreeAmount: Double,
        expectedYearlyKCodeAdjustment: Double?,
        expectedIsKCode: Boolean
    ) {
        val response = Calculator(
            taxCode = inputTaxCode,
            wages = inputWages,
            payPeriod = inputPayPeriod,
            taxYear = inputTaxYear,
            isPensionAge = inputIsPensionAge,
            hasStudentLoanPlanOne = inputHasPlanOne,
            hasStudentLoanPlanTwo = inputHasPlanTwo,
            hasStudentLoanPlanFour = inputHasPlanFour,
            hasStudentLoanPostgraduatePlan = inputHasPostgraduatePlan,
        ).run()

        checkResults(
            expectedCountry,
            response,
            expectedIsKCode,
            inputTaxCode,
            expectedYearlyNiEmployer,
            expectedYearlyNiEmployee,
            expectedYearlyIncomeTax,
            expectedYearlyTotalDeduction,
            expectedYearlyTakeHome,
            expectedYearlyWages,
            expectedYearlyTaxFreeAmount,
            expectedYearlyKCodeAdjustment
        )
    }

    @ParameterizedTest(name = "tax_code={0}, wages={1}, pay_period={2}, tax_year={3}, is_pension_age={4}")
    @CsvFileSource(resources = ["/data2024_with_tapering.csv"], numLinesToSkip = 1)
    fun `Tax calculations 2024 with tapering`(
        inputTaxCode: String,
        inputWages: Double,
        @ConvertWith(PayPeriodConverter::class) inputPayPeriod: PayPeriod,
        @ConvertWith(TaxYearConverter::class) inputTaxYear: TaxYear,
        inputIsPensionAge: Boolean,
        @ConvertWith(CountryConverter::class) expectedCountry: Country,
        expectedYearlyNiEmployee: Double,
        expectedYearlyNiEmployer: Double,
        expectedYearlyIncomeTax: Double,
        expectedYearlyTotalDeduction: Double,
        expectedYearlyTakeHome: Double,
        expectedYearlyWages: Double,
        expectedYearlyTaxFreeAmount: Double,
        expectedYearlyKCodeAdjustment: Double?,
        expectedIsKCode: Boolean
    ) {
        val response = Calculator(
            taxCode = inputTaxCode,
            userSuppliedTaxCode = false,
            wages = inputWages,
            payPeriod = inputPayPeriod,
            taxYear = inputTaxYear,
            isPensionAge = inputIsPensionAge
        ).run()

        checkResults(
            expectedCountry,
            response,
            expectedIsKCode,
            inputTaxCode,
            expectedYearlyNiEmployer,
            expectedYearlyNiEmployee,
            expectedYearlyIncomeTax,
            expectedYearlyTotalDeduction,
            expectedYearlyTakeHome,
            expectedYearlyWages,
            expectedYearlyTaxFreeAmount,
            expectedYearlyKCodeAdjustment
        )
    }

    private fun checkResults(
        expectedCountry: Country,
        response: CalculatorResponse,
        expectedIsKCode: Boolean,
        inputTaxCode: String,
        expectedYearlyNiEmployer: Double,
        expectedYearlyNiEmployee: Double,
        expectedYearlyIncomeTax: Double,
        expectedYearlyTotalDeduction: Double,
        expectedYearlyTakeHome: Double,
        expectedYearlyWages: Double,
        expectedYearlyTaxFreeAmount: Double,
        expectedYearlyKCodeAdjustment: Double?
    ) {
        assertEquals(expectedCountry, response.country, "Country did not match")
        assertEquals(expectedIsKCode, response.isKCode)

        val yearlyPeriod = response.yearly
        println(inputTaxCode)
        println(yearlyPeriod.prettyPrintDataClass())
        println("expectedYearlyNiEmployer=$expectedYearlyNiEmployer, yearlyPeriod.employersNI=$yearlyPeriod.employersNI")
        assertEquals(PayPeriod.YEARLY, yearlyPeriod.payPeriod)
        assertEquals(expectedYearlyNiEmployee, yearlyPeriod.employeesNI, "Yearly employee NI did not match")
        assertEquals(expectedYearlyNiEmployer, yearlyPeriod.employersNI, "Yearly employer NI did not match")
        assertEquals(expectedYearlyIncomeTax, yearlyPeriod.taxToPay, "Yearly income tax did not match")
        assertEquals(expectedYearlyTotalDeduction, yearlyPeriod.totalDeductions, "Yearly total deductions did not match")
        assertEquals(expectedYearlyTakeHome, yearlyPeriod.takeHome, "Yearly take home did not match")
        assertEquals(expectedYearlyWages, yearlyPeriod.wages, "Yearly wages did not match")
        assertEquals(expectedYearlyTaxFreeAmount, yearlyPeriod.taxFree, "Yearly tax free amount did not match")
        assertEquals(expectedYearlyKCodeAdjustment, yearlyPeriod.kCodeAdjustment, "Yearly K code adjustment did not match")
        assertFalse(yearlyPeriod.maxTaxAmountExceeded)

        assertEquals(PayPeriod.MONTHLY, response.monthly.payPeriod)
        assertEquals(PayPeriod.FOUR_WEEKLY, response.fourWeekly.payPeriod)
        assertEquals(PayPeriod.WEEKLY, response.weekly.payPeriod)
    }

    class PayPeriodConverter : ArgumentConverter {
        override fun convert(source: Any, context: ParameterContext?) = PayPeriod.valueOf(source as String)
    }

    class CountryConverter : ArgumentConverter {
        override fun convert(source: Any, context: ParameterContext?) = Country.valueOf(source as String)
    }

    class TaxYearConverter : ArgumentConverter {
        override fun convert(source: Any, context: ParameterContext?) = TaxYear.valueOf(source as String)
    }

    class AnnualPensionMethodConverter : ArgumentConverter {
        override fun convert(source: Any?, context: ParameterContext?) = AnnualPensionMethod.valueOf(source as String)
    }
}
