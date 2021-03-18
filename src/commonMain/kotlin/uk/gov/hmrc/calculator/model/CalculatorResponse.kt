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

data class BandBreakdown(val percentage: Double, val amount: Double)

class CalculatorResponsePayPeriod(
    val payPeriod: PayPeriod,
    taxToPayForPayPeriod: Double,
    private var employeesNIRaw: Double,
    private var employersNIRaw: Double,
    private var wagesRaw: Double,
    private var taxFreeRaw: Double,
    private var kCodeAdjustmentRaw: Double? = null
) {
    private val maxTaxAmount = (wagesRaw / 2).formatMoney()
    val taxToPay = if (taxToPayForPayPeriod > maxTaxAmount) maxTaxAmount else taxToPayForPayPeriod.formatMoney()
    val maxTaxAmountExceeded = (taxToPayForPayPeriod > maxTaxAmount)
    val totalDeductions = (taxToPay + employeesNIRaw).formatMoney()
    val takeHome = (wagesRaw - totalDeductions).formatMoney()

    val employeesNI: Double by lazy {
        employeesNIRaw.formatMoney()
    }

    val employersNI: Double by lazy {
        employersNIRaw.formatMoney()
    }

    val wages: Double by lazy {
        wagesRaw.formatMoney()
    }

    val taxFree: Double by lazy {
        taxFreeRaw.formatMoney()
    }

    val kCodeAdjustment: Double? by lazy {
        kCodeAdjustmentRaw?.formatMoney()
    }
}

data class CalculatorResponse(
    val country: Country,
    val isKCode: Boolean,
    val weekly: CalculatorResponsePayPeriod,
    val fourWeekly: CalculatorResponsePayPeriod,
    val monthly: CalculatorResponsePayPeriod,
    val yearly: CalculatorResponsePayPeriod
)
