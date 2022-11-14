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

fun Any.prettyPrint(): String {
    val toString = toString()
    return toString.prettyPrintObjectString()
}

fun String.prettyPrintObjectString(
    indentWidth: Int = 4
): String {
    var indentLevel = 0
    val stringBuilder = StringBuilder(this.length)

    fun padding() = "".padStart(indentLevel * indentWidth)

    var i = 0
    while (i < this.length) {
        when (val char = this[i]) {
            '(', '[', '{' -> {
                indentLevel++
                stringBuilder
                    .appendLine(char)
                    .append(padding())
            }

            ')', ']', '}' -> {
                indentLevel--
                stringBuilder
                    .appendLine()
                    .append(padding())
                    .append(char)
            }

            ',' -> {
                stringBuilder
                    .appendLine(char)
                    .append(padding())
                val nextChar = this.getOrElse(i + 1) { char }
                if (nextChar == ' ') i++
            }

            else -> {
                stringBuilder.append(char)
            }
        }
        i++
    }

    return stringBuilder.toString()
}
