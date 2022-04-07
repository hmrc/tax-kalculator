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
package uk.gov.hmrc.calculator.utils.validation

import uk.gov.hmrc.calculator.model.ValidationError
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TaxCodeValidatorTests {
    @Test
    fun `Validate valid 1250L tax code`() {
        assertTrue(TaxCodeValidator.isValidTaxCode("1250L").isValid)
    }

    @Test
    fun `Validate valid K100 tax code`() {
        assertTrue(TaxCodeValidator.isValidTaxCode("K100").isValid)
    }
    @Test
    fun `Validate valid K100X tax code`() {
        assertTrue(TaxCodeValidator.isValidTaxCode("K100X").isValid)
    }

    @Test
    fun `Validate invalid tax code WrongTaxCodeNumber`() {
        assertFalse(TaxCodeValidator.isValidTaxCode("HELLO").isValid)
        assertEquals(ValidationError.WrongTaxCodeNumber, TaxCodeValidator.isValidTaxCode("HELLO").errorType)
    }

    @Test
    fun `Validate invalid tax code Other`() {
        assertFalse(TaxCodeValidator.isValidTaxCode("110").isValid)
        assertEquals(ValidationError.Other, TaxCodeValidator.isValidTaxCode("110").errorType)
    }

    @Test
    fun `Validate invalid tax code Prefix`() {
        assertFalse(TaxCodeValidator.isValidTaxCode("OO9999").isValid)
        assertEquals(ValidationError.WrongTaxCodePrefix, TaxCodeValidator.isValidTaxCode("OO9999").errorType)
    }
    @Test
    fun `Validate invalid tax code Suffix`() {
        assertFalse(TaxCodeValidator.isValidTaxCode("9999R").isValid)
        assertEquals(ValidationError.WrongTaxCodeSuffix, TaxCodeValidator.isValidTaxCode("9999R").errorType)
    }
}
