/*
 * Copyright 2021 HM Revenue & Customs
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

import uk.gov.hmrc.calculator.utils.formatMoney

data class BandBreakdown(val percentage: Double, val amount: Double) {
    override fun toString() = "BandBreakdown(percentage=$percentage \n, amount=$amount) \n"
}

class CalculatorResponsePayPeriod(
    val payPeriod: PayPeriod,
    private val taxToPayForPayPeriod: Double,
    private var employeesNIRaw: Double,
    private var employersNIRaw: Double,
    private var wagesRaw: Double,
    val taxBreakdownForPayPeriod: List<BandBreakdown>?,
    private var taxFreeRaw: Double,
    private var kCodeAdjustmentRaw: Double? = null
) {
    private val maxTaxAmount = (wagesRaw / 2).formatMoney()
    val taxToPay = if (taxToPayForPayPeriod > maxTaxAmount) maxTaxAmount else taxToPayForPayPeriod.formatMoney()
    val maxTaxAmountExceeded = (taxToPayForPayPeriod > maxTaxAmount)
    val totalDeductions = (taxToPay + employeesNIRaw).formatMoney()
    val takeHome = (wagesRaw - totalDeductions).formatMoney()

    var employeesNI
        get() = employeesNIRaw.formatMoney()
        set(value) { employeesNIRaw = value }

    var employersNI
        get() = employersNIRaw.formatMoney()
        set(value) { employersNIRaw = value }

    var wages
        get() = wagesRaw.formatMoney()
        set(value) { wagesRaw = value }

    var taxFree
        get() = taxFreeRaw.formatMoney()
        set(value) { taxFreeRaw = value }

    var kCodeAdjustment
        get() = kCodeAdjustmentRaw?.formatMoney()
        set(value) { kCodeAdjustmentRaw = value }

    override fun toString() = "CalculatorResponsePayPeriod(" +
            "payPeriod=$payPeriod \n" +
            "taxToPayForPayPeriod=$taxToPayForPayPeriod \n" +
            "employeesNI=$employeesNI \n" +
            "employersNI=$employersNI \n" +
            "wages=$wages \n" +
            "taxBreakdownForPayPeriod=$taxBreakdownForPayPeriod \n" +
            "taxFree=$taxFree \n" +
            "kCodeAdjustment=$kCodeAdjustment \n" +
            ")"
}

data class CalculatorResponse(
    val country: Country,
    val isKCode: Boolean,
    val weekly: CalculatorResponsePayPeriod,
    val fourWeekly: CalculatorResponsePayPeriod,
    val monthly: CalculatorResponsePayPeriod,
    val yearly: CalculatorResponsePayPeriod
) {
    override fun toString() = "CalculatorResponse(" +
            "country=$country \n" +
            "isKCode=$isKCode \n" +
            "weekly=$weekly \n" +
            "fourWeekly=$fourWeekly \n" +
            "monthly=$monthly \n" +
            "yearly=$yearly \n" +
            ")"
}
