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
package uk.gov.hmrc.calculator.utils.tapering

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TaperingExtensionTests {
    @Test
    fun `GIVEN tax free 12570 AND wage is above maximum tapering amount WHEN deductTapering THEN return zero`() {
        val taxFreeAmount = 12570.0
        val wage = 150000.0

        assertEquals(0.0, taxFreeAmount.deductTapering(wage))
    }

    @Test
    fun `GIVEN tax free 12570 AND wage is above tapering amount WHEN deductTapering THEN return amount`() {
        val taxFreeAmount = 12570.0
        val wage = 110000.0

        // tapering deduction is 5000, because (110000 - 100000) / 2
        // 12570 - 5000 = 7570
        assertEquals(7570.0, taxFreeAmount.deductTapering(wage))
    }

    @Test
    fun `GIVEN tapering amount is above maximumTaperingAmount WHEN getTaperingAmount THEN return maximumTaperingAmount`() {
        val wage = 150000.0
        val maximumTaperingAmount = 12570.0

        assertEquals(12570.0, wage.getTaperingAmount(maximumTaperingAmount))
    }

    @Test
    fun `GIVEN tapering amount is below maximumTaperingAmount WHEN getTaperingAmount THEN return tapering amount`() {
        val wage = 110000.0
        val maximumTaperingAmount = 12570.0

        assertEquals(5000.0, wage.getTaperingAmount(maximumTaperingAmount))
    }

    @Test
    fun `GIVEN wage is below tapering threshold WHEN shouldApplyTapering THEN return false`() {
        assertFalse(90000.0.shouldApplyTapering())
    }

    @Test
    fun `GIVEN wage is above tapering threshold WHEN shouldApplyTapering THEN return true`() {
        assertTrue(110000.0.shouldApplyTapering())
    }
}
