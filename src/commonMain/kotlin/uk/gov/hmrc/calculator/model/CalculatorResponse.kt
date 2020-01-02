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
package uk.gov.hmrc.calculator.model

data class BandBreakdown(val percentage: Double, val amount: Double)

class CalculatorResponsePayPeriod(
    val payPeriod: PayPeriod,
    taxToPayForPayPeriod: Double,
    val employeesNI: Double,
    val employersNI: Double,
    val wages: Double,
    taxBreakdownForPayPeriod: List<BandBreakdown>?,
    val taxFree: Double,
    val kCodeAdjustment: Double? = null
) {
    private val maxTaxAmount = wages / 2
    val taxToPay = if (taxToPayForPayPeriod > maxTaxAmount) maxTaxAmount else taxToPayForPayPeriod
    val maxTaxAmountExceeded = (taxToPayForPayPeriod > maxTaxAmount)
    val totalDeductions = taxToPay + employeesNI
    val takeHome = wages - totalDeductions
    val taxBreakdown = if (maxTaxAmountExceeded) null else taxBreakdownForPayPeriod
}

data class CalculatorResponse(
    val taxCode: String,
    val country: Country,
    val isKCode: Boolean,
    val weekly: CalculatorResponsePayPeriod,
    val fourWeekly: CalculatorResponsePayPeriod,
    val monthly: CalculatorResponsePayPeriod,
    val yearly: CalculatorResponsePayPeriod
)
