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
package uk.gov.hmrc.calculator.model.bands

internal interface Band {
    var lower: Double
    var upper: Double
    val percentageAsDecimal: Double

    fun inBand(amount: Double): Boolean {
        return if (upper == -1.0 && amount > lower) {
            true
        } else {
            amount > lower && amount <= upper
        }
    }
}

internal data class TaxBand(
    override var lower: Double,
    override var upper: Double,
    override val percentageAsDecimal: Double
) : Band

internal data class EmployerNIBand(
    override var lower: Double,
    override var upper: Double,
    override val percentageAsDecimal: Double
) : Band

internal data class EmployeeNIBand(
    override var lower: Double,
    override var upper: Double,
    override val percentageAsDecimal: Double
) : Band
