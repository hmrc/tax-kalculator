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
package uk.gov.hmrc.calculator.exception

class InvalidTaxCodeException(message: String) : Exception(message)

class InvalidTaxYearException(message: String) : Exception(message)

class InvalidWagesException(message: String) : Exception(message)

class InvalidPayPeriodException(message: String) : Exception(message)

class InvalidHoursException(message: String) : Exception(message)

class InvalidDaysException(message: String) : Exception(message)

class InvalidTaxBandException(message: String) : Exception(message)

class InvalidPensionException(message: String) : Exception(message)
