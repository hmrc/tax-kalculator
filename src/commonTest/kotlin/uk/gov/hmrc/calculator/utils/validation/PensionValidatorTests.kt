/*
 * Copyright 2023 HM Revenue & Customs
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
package uk.gov.hmrc.calculator.utils.validation

import uk.gov.hmrc.calculator.model.TaxYear
import uk.gov.hmrc.calculator.model.pension.PensionMethod
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PensionValidatorTests {

    @Test
    fun `GIVEN pension is lower then wage WHEN isPensionLowerThenWage THEN return true`() {
        val wage = 20000.0
        val pension = 2000.0

        assertTrue(PensionValidator.isPensionLowerThenWage(pension, wage))
    }

    @Test
    fun `GIVEN pension is greater then wages WHEN isPensionLowerThenWage THEN return false`() {
        val wage = 20000.0
        val pension = 20001.0

        assertFalse(PensionValidator.isPensionLowerThenWage(pension, wage))
    }

    @Test
    fun `GIVEN pension is same as wage WHEN isPensionLowerThenWage THEN return true`() {
        val wage = 20000.0
        val pension = 20000.0

        assertTrue(PensionValidator.isPensionLowerThenWage(pension, wage))
    }

    @Test
    fun `GIVEN no error WHEN isValidYearlyPension THEN return empty list of error`() {
        val wage = 150000.0
        val pension = 50000.0
        val taxYear = TaxYear.TWENTY_TWENTY_FOUR

        val listOfError = emptyList<PensionValidator.PensionError>()

        assertEquals(listOfError, PensionValidator.isValidYearlyPension(pension, wage, taxYear))
    }

    @Test
    fun `GIVEN pension is invalid format WHEN isValidYearlyPension THEN return list of error`() {
        val wage = 150000.0
        val pension = 50000.123
        val taxYear = TaxYear.TWENTY_TWENTY_FOUR

        val listOfError = mutableListOf(PensionValidator.PensionError.INVALID_FORMAT)

        assertEquals(listOfError, PensionValidator.isValidYearlyPension(pension, wage, taxYear))
    }

    @Test
    fun `GIVEN pension is zero WHEN isValidYearlyPension THEN return list of error`() {
        val wage = 150000.0
        val pension = 0.0
        val taxYear = TaxYear.TWENTY_TWENTY_FOUR

        val listOfError = mutableListOf(PensionValidator.PensionError.BELOW_ZERO)

        assertEquals(listOfError, PensionValidator.isValidYearlyPension(pension, wage, taxYear))
    }

    @Test
    fun `GIVEN pension above wage WHEN isValidYearlyPension THEN return list of error`() {
        val wage = 40000.0
        val pension = 50000.0
        val taxYear = TaxYear.TWENTY_TWENTY_FOUR

        val listOfError = mutableListOf(PensionValidator.PensionError.ABOVE_WAGE)

        assertEquals(listOfError, PensionValidator.isValidYearlyPension(pension, wage, taxYear))
    }

    @Test
    fun `GIVEN pension is greater then annualAllowance WHEN isValidYearlyPension THEN return list of error`() {
        val wage = 150000.0
        val pension = 61000.0
        val taxYear = TaxYear.TWENTY_TWENTY_FOUR

        val listOfError = mutableListOf(PensionValidator.PensionError.ABOVE_ANNUAL_ALLOWANCE)

        assertEquals(listOfError, PensionValidator.isValidYearlyPension(pension, wage, taxYear))
    }

    @Test
    fun `GIVEN multiple error WHEN isValidYearlyPension THEN return list of error in order`() {
        val wage = 50000.0
        val pension = 61000.1234
        val taxYear = TaxYear.TWENTY_TWENTY_FOUR

        val listOfError = mutableListOf(
            PensionValidator.PensionError.INVALID_FORMAT,
            PensionValidator.PensionError.ABOVE_WAGE,
            PensionValidator.PensionError.ABOVE_ANNUAL_ALLOWANCE,
        )

        assertEquals(listOfError, PensionValidator.isValidYearlyPension(pension, wage, taxYear))
    }

    @Test
    fun `GIVEN no error WHEN isValidMonthlyPension THEN return empty list of error`() {
        val monthlyWage = 12500.0
        val monthlyPension = 4166.67
        val taxYear = TaxYear.TWENTY_TWENTY_FOUR

        val listOfError = emptyList<PensionValidator.PensionError>()

        assertEquals(listOfError, PensionValidator.isValidMonthlyPension(monthlyPension, monthlyWage, taxYear))
    }

    @Test
    fun `GIVEN pension is invalid format WHEN isValidMonthlyPension THEN return list of error`() {
        val monthlyWage = 12500.0
        val monthlyPension = 4166.123
        val taxYear = TaxYear.TWENTY_TWENTY_FOUR

        val listOfError = mutableListOf(PensionValidator.PensionError.INVALID_FORMAT)

        assertEquals(listOfError, PensionValidator.isValidMonthlyPension(monthlyPension, monthlyWage, taxYear))
    }

    @Test
    fun `GIVEN pension is zero WHEN isValidMonthlyPension THEN return list of error`() {
        val monthlyWage = 12500.0
        val monthlyPension = 0.0
        val taxYear = TaxYear.TWENTY_TWENTY_FOUR

        val listOfError = mutableListOf(PensionValidator.PensionError.BELOW_ZERO)

        assertEquals(listOfError, PensionValidator.isValidMonthlyPension(monthlyPension, monthlyWage, taxYear))
    }

    @Test
    fun `GIVEN pension above wage WHEN isValidMonthlyPension THEN return list of error`() {
        val monthlyWage = 3333.33
        val monthlyPension = 4166.67
        val taxYear = TaxYear.TWENTY_TWENTY_FOUR

        val listOfError = mutableListOf(PensionValidator.PensionError.ABOVE_WAGE)

        assertEquals(listOfError, PensionValidator.isValidMonthlyPension(monthlyPension, monthlyWage, taxYear))
    }

    @Test
    fun `GIVEN pension is greater then annualAllowance WHEN isValidMonthlyPension THEN return list of error`() {
        val monthlyWage = 12500.0
        val monthlyPension = 5083.33
        val taxYear = TaxYear.TWENTY_TWENTY_FOUR

        val listOfError = mutableListOf(PensionValidator.PensionError.ABOVE_ANNUAL_ALLOWANCE)

        assertEquals(listOfError, PensionValidator.isValidMonthlyPension(monthlyPension, monthlyWage, taxYear))
    }

    @Test
    fun `GIVEN multiple error WHEN isValidMonthlyPension THEN return list of error in order`() {
        val monthlyWage = 4166.67
        val monthlyPension = 5083.1234
        val taxYear = TaxYear.TWENTY_TWENTY_FOUR

        val listOfError = mutableListOf(
            PensionValidator.PensionError.INVALID_FORMAT,
            PensionValidator.PensionError.ABOVE_WAGE,
            PensionValidator.PensionError.ABOVE_ANNUAL_ALLOWANCE,
        )

        assertEquals(listOfError, PensionValidator.isValidMonthlyPension(monthlyPension, monthlyWage, taxYear))
    }

    @Test
    fun `GIVEN amount is less then zero WHEN validateValidInputPensionInput THEN return BELOW_ZERO`() {
        val monthlyPension = -10.0
        val pensionMethod = PensionMethod.MONTHLY_AMOUNT_IN_POUNDS

        val listOfError = mutableListOf(PensionValidator.PensionError.BELOW_ZERO)

        assertEquals(listOfError, PensionValidator.validateValidInputPensionInput(monthlyPension, pensionMethod))
    }

    @Test
    fun `GIVEN amount is above 100 AND pension method is percentage WHEN validateValidInputPensionInput THEN return ABOVE_HUNDRED_PERCENT`() {
        val monthlyPension = 1000.0
        val pensionMethod = PensionMethod.PERCENTAGE

        val listOfError = mutableListOf(PensionValidator.PensionError.ABOVE_HUNDRED_PERCENT)

        assertEquals(listOfError, PensionValidator.validateValidInputPensionInput(monthlyPension, pensionMethod))
    }

    @Test
    fun `GIVEN amount has more then two decimals AND pension method is percentage WHEN validateValidInputPensionInput THEN return INVALID_PERCENTAGE_DECIMAL`() {
        val monthlyPension = 30.123
        val pensionMethod = PensionMethod.PERCENTAGE

        val listOfError = mutableListOf(PensionValidator.PensionError.INVALID_PERCENTAGE_DECIMAL)

        assertEquals(listOfError, PensionValidator.validateValidInputPensionInput(monthlyPension, pensionMethod))
    }

    @Test
    fun `GIVEN amount has more then two decimals AND pension method is month amount in pounds WHEN validateValidInputPensionInput THEN return INVALID_AMOUNT_DECIMAL`() {
        val monthlyPension = 1000.123
        val pensionMethod = PensionMethod.MONTHLY_AMOUNT_IN_POUNDS

        val listOfError = mutableListOf(PensionValidator.PensionError.INVALID_AMOUNT_DECIMAL)

        assertEquals(listOfError, PensionValidator.validateValidInputPensionInput(monthlyPension, pensionMethod))
    }
}
