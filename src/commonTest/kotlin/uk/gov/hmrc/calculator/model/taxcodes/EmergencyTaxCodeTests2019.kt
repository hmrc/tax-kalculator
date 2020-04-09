/*
 * Copyright 2020 HM Revenue & Customs
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
package uk.gov.hmrc.calculator.model.taxcodes

import kotlin.test.Test
import kotlin.test.assertEquals
import uk.gov.hmrc.calculator.Calculator
import uk.gov.hmrc.calculator.model.PayPeriod.YEARLY

class EmergencyTaxCodeTests2019 {

    @Test
    fun `Wales C1250X 20k`() {
        val taxCode = "C1250X"
        val wages = 20000.00
        val response = Calculator(taxCode, wages, payPeriod = YEARLY, taxYear = 2019).run()
        assertEquals(1498.2, response.yearly.taxToPay)
        assertEquals(1568.784, response.yearly.employersNI)
        assertEquals(1364.1599999999999, response.yearly.employeesNI)
        assertEquals(2862.3599999999997, response.yearly.totalDeductions)
    }

    @Test
    fun `Wales C1250M1 20k`() {
        val taxCode = "C1250M1"
        val wages = 20000.00
        val response = Calculator(taxCode, wages, payPeriod = YEARLY, taxYear = 2019).run()
        assertEquals(1498.2, response.yearly.taxToPay)
        assertEquals(1568.784, response.yearly.employersNI)
        assertEquals(1364.1599999999999, response.yearly.employeesNI)
        assertEquals(2862.3599999999997, response.yearly.totalDeductions)
    }

    @Test
    fun `Wales C1250W1 20k`() {
        val taxCode = "C1250W1"
        val wages = 20000.00
        val response = Calculator(taxCode, wages, payPeriod = YEARLY, taxYear = 2019).run()
        assertEquals(1498.2, response.yearly.taxToPay)
        assertEquals(1568.784, response.yearly.employersNI)
        assertEquals(1364.1599999999999, response.yearly.employeesNI)
        assertEquals(2862.3599999999997, response.yearly.totalDeductions)
    }

    @Test
    fun `Scotland S1250X 20k`() {
        val taxCode = "S1250X"
        val wages = 20000.00
        val response = Calculator(taxCode, wages, payPeriod = YEARLY, taxYear = 2019).run()
        assertEquals(1477.8000000000002, response.yearly.taxToPay)
        assertEquals(1568.784, response.yearly.employersNI)
        assertEquals(1364.1599999999999, response.yearly.employeesNI)
        assertEquals(2841.96, response.yearly.totalDeductions)
    }

    @Test
    fun `Scotland S1250M1 20k`() {
        val taxCode = "S1250M1"
        val wages = 20000.00
        val response = Calculator(taxCode, wages, payPeriod = YEARLY, taxYear = 2019).run()
        assertEquals(1477.8000000000002, response.yearly.taxToPay)
        assertEquals(1568.784, response.yearly.employersNI)
        assertEquals(1364.1599999999999, response.yearly.employeesNI)
        assertEquals(2841.96, response.yearly.totalDeductions)
    }

    @Test
    fun `Scotland S1250W1 20k`() {
        val taxCode = "S1250W1"
        val wages = 20000.00
        val response = Calculator(taxCode, wages, payPeriod = YEARLY, taxYear = 2019).run()
        assertEquals(1477.8000000000002, response.yearly.taxToPay)
        assertEquals(1568.784, response.yearly.employersNI)
        assertEquals(1364.1599999999999, response.yearly.employeesNI)
        assertEquals(2841.96, response.yearly.totalDeductions)
    }

    @Test
    fun `England 1250X 20k`() {
        val taxCode = "1250X"
        val wages = 20000.00
        val response = Calculator(taxCode, wages, payPeriod = YEARLY, taxYear = 2019).run()
        assertEquals(1498.2, response.yearly.taxToPay)
        assertEquals(1568.784, response.yearly.employersNI)
        assertEquals(1364.1599999999999, response.yearly.employeesNI)
        assertEquals(2862.3599999999997, response.yearly.totalDeductions)
    }

    @Test
    fun `England 1250M1 20k`() {
        val taxCode = "1250M1"
        val wages = 20000.00
        val response = Calculator(taxCode, wages, payPeriod = YEARLY, taxYear = 2019).run()
        assertEquals(1498.2, response.yearly.taxToPay)
        assertEquals(1568.784, response.yearly.employersNI)
        assertEquals(1364.1599999999999, response.yearly.employeesNI)
        assertEquals(2862.3599999999997, response.yearly.totalDeductions)
    }

    @Test
    fun `England 1250W1 20k`() {
        val taxCode = "1250W1"
        val wages = 20000.00
        val response = Calculator(taxCode, wages, payPeriod = YEARLY, taxYear = 2019).run()
        assertEquals(1498.2, response.yearly.taxToPay)
        assertEquals(1568.784, response.yearly.employersNI)
        assertEquals(1364.1599999999999, response.yearly.employeesNI)
        assertEquals(2862.3599999999997, response.yearly.totalDeductions)
    }
}
