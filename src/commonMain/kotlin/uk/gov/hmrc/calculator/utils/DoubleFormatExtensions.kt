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
package uk.gov.hmrc.calculator.utils

import kotlin.math.floor
import kotlin.math.round

fun Double.formatMoney(): Double {
    return this.decimalRound(2)
}

fun Double.decimalRound(places: Int): Double {
    var multiplier = 1.0
    repeat(places) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}

fun Double.roundDownToWholeNumber(): Double {
    return floor(this)
}
