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
import kotlin.test.assertNull
import kotlin.test.assertTrue

class TaxCodeValidatorTests {
    @Test
    fun `GIVEN valid 1250L tax code WHEN isValidTaxCode THEN isValid return true`() {
        assertTrue(TaxCodeValidator.isValidTaxCode("1250L").isValid)
    }

    @Test
    fun `GIVEN valid K100 tax code WHEN isValidTaxCode THEN isValid return true`() {
        assertTrue(TaxCodeValidator.isValidTaxCode("K100").isValid)
    }
    @Test
    fun `GIVEN valid K100X tax code WHEN isValidTaxCode THEN isValid return true`() {
        assertTrue(TaxCodeValidator.isValidTaxCode("K100X").isValid)
    }

    @Test
    fun `GIVEN wrong tax code number WHEN isValidTaxCode THEN return WrongTaxCodeNumber AND isValid return false`() {
        assertFalse(TaxCodeValidator.isValidTaxCode("HELLO").isValid)
        assertEquals(ValidationError.WrongTaxCodeNumber, TaxCodeValidator.isValidTaxCode("HELLO").errorType)
    }

    @Test
    fun `GIVEN other invalid tax code WHEN isValidTaxCode THEN return Other AND isValid return false`() {
        assertFalse(TaxCodeValidator.isValidTaxCode("110").isValid)
        assertEquals(ValidationError.Other, TaxCodeValidator.isValidTaxCode("110").errorType)
    }

    @Test
    fun `GIVEN invalid tax code prefix WHEN isValidTaxCode THEN return WrongTaxCodePrefix AND isValid return false`() {
        assertFalse(TaxCodeValidator.isValidTaxCode("OO9999").isValid)
        assertEquals(ValidationError.WrongTaxCodePrefix, TaxCodeValidator.isValidTaxCode("OO9999").errorType)
    }
    @Test
    fun `GIVEN invalid tax code suffix WHEN isValidTaxCode THEN return WrongTaxCodeSuffix AND isValid return false`() {
        assertFalse(TaxCodeValidator.isValidTaxCode("9999R").isValid)
        assertEquals(ValidationError.WrongTaxCodeSuffix, TaxCodeValidator.isValidTaxCode("9999R").errorType)
    }

    @Test
    fun `GIVEN english tax code AND isPayingScottishRate false WHEN validateTaxCodeMatchingRate THEN return null`() {
        val result = TaxCodeValidator.validateTaxCodeMatchingRate("1257L", false)
        assertNull(result)
    }

    @Test
    fun `GIVEN english tax code AND isPayingScottishRate true WHEN validateTaxCodeMatchingRate THEN return NonScottishCodeButScottishRate`() {
        val result = TaxCodeValidator.validateTaxCodeMatchingRate("1257L", true)
        assertEquals(ValidationError.NonScottishCodeButScottishRate, result!!.errorType)
    }

    @Test
    fun `GIVEN scottish tax code AND isPayingScottishRate false WHEN validateTaxCodeMatchingRate THEN return ScottishCodeButOtherRate`() {
        val result = TaxCodeValidator.validateTaxCodeMatchingRate("S1257L", false)
        assertEquals(ValidationError.ScottishCodeButOtherRate, result!!.errorType)
    }
}
