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
package uk.gov.hmrc.calculator.model.pension

import uk.gov.hmrc.calculator.Calculator
import uk.gov.hmrc.calculator.model.PayPeriod
import uk.gov.hmrc.calculator.utils.convertWageToYearly
import kotlin.jvm.JvmSynthetic

@JvmSynthetic
internal fun calculateYearlyPension(
    yearlyWage: Double,
    pensionContribution: Calculator.PensionContribution,
): Double {
    return when (pensionContribution.method) {
        PensionMethod.MONTHLY_AMOUNT_IN_POUNDS -> {
            pensionContribution.contributionAmount.convertWageToYearly(PayPeriod.MONTHLY)
        }
        PensionMethod.PERCENTAGE -> {
            pensionContribution.contributionAmount.let { percentage ->
                yearlyWage * (percentage.div(100))
            }
        }
    }
}
