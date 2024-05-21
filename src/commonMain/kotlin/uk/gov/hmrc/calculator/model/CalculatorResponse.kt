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
package uk.gov.hmrc.calculator.model

import uk.gov.hmrc.calculator.utils.clarification.Clarification
import uk.gov.hmrc.calculator.utils.formatMoney
import uk.gov.hmrc.calculator.utils.roundDownToWholeNumber

data class BandBreakdown(
    val percentage: Double,
    val amount: Double,
)

data class StudentLoanAmountBreakdown(
    val plan: String,
    var amount: Double,
)

class CalculatorResponsePayPeriod(
    val payPeriod: PayPeriod,
    val taxToPayForPayPeriod: Double,
    private var employeesNIRaw: Double,
    private var employersNIRaw: Double,
    private var wagesRaw: Double,
    val taxBreakdownForPayPeriod: List<BandBreakdown>? = null,
    private var taxFreeRaw: Double,
    private var kCodeAdjustmentRaw: Double? = null,
    private val pensionContributionRaw: Double? = null,
    private var wageAfterPensionDeductionRaw: Double,
    private var taperingAmountRaw: Double? = null,
    val studentLoanBreakdownList: List<StudentLoanAmountBreakdown>,
    finalStudentLoanAmountRaw: Double,
    finalPostgraduateLoanAmountRaw: Double,
) {
    private val maxTaxAmount = (wagesRaw / 2).formatMoney()
    val taxToPay = if (taxToPayForPayPeriod > maxTaxAmount) maxTaxAmount else taxToPayForPayPeriod.formatMoney()
    val maxTaxAmountExceeded = (taxToPayForPayPeriod > maxTaxAmount)
    val pensionContribution = pensionContributionRaw?.formatMoney() ?: 0.0.formatMoney()
    val finalStudentLoanAmount = finalStudentLoanAmountRaw.roundDownToWholeNumber()
    val finalPostgraduateLoanAmount = finalPostgraduateLoanAmountRaw.roundDownToWholeNumber()
    val otherAmount = pensionContribution + finalStudentLoanAmount + finalPostgraduateLoanAmount
    val totalDeductions = (taxToPay + employeesNIRaw + otherAmount).formatMoney()
    val takeHome = (wagesRaw - totalDeductions).formatMoney()
    val taxBreakdown = if (maxTaxAmountExceeded) null else taxBreakdownForPayPeriod
    val wageAfterPensionDeduction = wageAfterPensionDeductionRaw.formatMoney()
    val taperingAmountDeduction = taperingAmountRaw?.formatMoney() ?: 0.0.formatMoney()
    val studentLoanBreakdown = if (finalStudentLoanAmount > 0 || finalPostgraduateLoanAmount > 0) {
        studentLoanBreakdownList
    } else null

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

    override fun toString(): String {
        return "CalculatorResponsePayPeriod(payPeriod=$payPeriod," +
                "taxToPayForPayPeriod=$taxToPayForPayPeriod," +
                "employeesNIRaw=$employeesNIRaw," +
                "employersNIRaw=$employersNIRaw," +
                "wagesRaw=$wagesRaw," +
                "taxBreakdownForPayPeriod=$taxBreakdownForPayPeriod," +
                "taxFreeRaw=$taxFreeRaw," +
                "takeHome=$takeHome," +
                "totalDeductions=$totalDeductions," +
                "kCodeAdjustmentRaw=$kCodeAdjustmentRaw," +
                "pensionContributionRaw=$pensionContributionRaw," +
                "wageAfterPensionDeductionRaw=$wageAfterPensionDeductionRaw," +
                "taperingAmountDeductionRaw=$taperingAmountRaw," +
                "studentLoanBreakdown=$studentLoanBreakdown," +
                "finalStudentLoanAmount=$finalStudentLoanAmount," +
                "finalPostgraduateLoanAmount=$finalPostgraduateLoanAmount," +
                "otherAmount=$otherAmount)"
    }
}

data class CalculatorResponse(
    val country: Country,
    val isKCode: Boolean,
    val weekly: CalculatorResponsePayPeriod,
    val fourWeekly: CalculatorResponsePayPeriod,
    val monthly: CalculatorResponsePayPeriod,
    val yearly: CalculatorResponsePayPeriod,
    val listOfClarification: MutableList<Clarification>,
)
