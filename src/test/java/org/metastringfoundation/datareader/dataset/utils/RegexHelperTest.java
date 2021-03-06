/*
 *    Copyright 2020 Metastring Foundation
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.metastringfoundation.datareader.dataset.utils;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class RegexHelperTest {

    @Test
    void getFirstMatchOrAll() {
        Pattern pattern = Pattern.compile("(.*) - .*");
        String raw = "Karnataka - Bangalore";
        String actual = RegexHelper.getFirstMatchOrAll(raw, pattern);
        assertEquals("Karnataka", actual);
    }

    @Test
    void ifCapturingGroupOptionalAtTheEnd() {
        Pattern pattern = Pattern.compile(".* - .* - (.*)");
        String raw = "Karnataka - Bangalore - NFHS";
        String actual = RegexHelper.getFirstMatchOrAll(raw, pattern);
        assertEquals("NFHS", actual);
    }

    @Test
    void ifCapturingGroupOptionalAndNotPresentInRawText() {
        Pattern pattern = Pattern.compile(".* - .* - (.*)");
        String raw = "Karnataka - Bangalore";
        String actual = RegexHelper.getFirstMatchOrNull(raw, pattern);
        assertNull(actual);
    }

    @Test
    void ifOptionalGroupAtEndAndCaptuingGroupInMiddle() {
        Pattern pattern = Pattern.compile(".* - (.*) - .*");
        String raw = "Karnataka - Bangalore - NFHS";
        String actual = RegexHelper.getFirstMatchOrAll(raw, pattern);
        assertEquals("Bangalore", actual);
    }
}