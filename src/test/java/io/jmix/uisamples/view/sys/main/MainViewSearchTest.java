/*
 * Copyright 2022 Haulmont.
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

package io.jmix.uisamples.view.sys.main;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the menu search matching in {@link MainView} (token-AND over normalized titles).
 * Pure functions: no Spring/UI context needed.
 */
class MainViewSearchTest {

    @Test
    void singleTokenMatchesSubstringCaseInsensitively() {
        assertAll(
                () -> assertTrue(MainView.matchesQuery("Simple Button", "button")),
                () -> assertTrue(MainView.matchesQuery("Simple Button", "BUTTON")),
                () -> assertTrue(MainView.matchesQuery("Simple Button", "Simple")),
                () -> assertFalse(MainView.matchesQuery("Simple Badge", "button"))
        );
    }

    @Test
    void separatorDifferencesAreIgnoredBothWays() {
        // Title is concatenated ("TextField"); query with or without a space must match.
        assertAll(
                () -> assertTrue(MainView.matchesQuery("TextField", "textfield")),
                () -> assertTrue(MainView.matchesQuery("TextField", "text field")),
                () -> assertTrue(MainView.matchesQuery("ComboBox", "combo box")),
                () -> assertTrue(MainView.matchesQuery("ComboBox", "combo-box")),
                // Hyphen in the title is dropped, so a concatenated query still matches.
                () -> assertTrue(MainView.matchesQuery("Data-aware TextField", "dataaware"))
        );
    }

    @Test
    void allTokensMustMatchInAnyOrder() {
        assertAll(
                () -> assertTrue(MainView.matchesQuery("Button with Action", "button action")),
                () -> assertTrue(MainView.matchesQuery("Button with Action", "action button")),
                () -> assertTrue(MainView.matchesQuery("DataGrid with filter in header", "datagrid filter")),
                // "action" is absent from the title -> the whole query fails.
                () -> assertFalse(MainView.matchesQuery("Simple Button", "button action"))
        );
    }

    @Test
    void tokensAreSubstringsNotSubsequences() {
        // Documents the deliberate token-AND limitation (vs. fuzzy/subsequence):
        // a single concatenated token must occur contiguously, "with" in between breaks it,
        // and abbreviations are not expanded.
        assertAll(
                () -> assertFalse(MainView.matchesQuery("Button with Action", "buttonaction")),
                () -> assertFalse(MainView.matchesQuery("Button", "btn")),
                () -> assertFalse(MainView.matchesQuery("ComboBox", "cb"))
        );
    }

    @Test
    void blankOrNullQueryMatchesEverything() {
        assertAll(
                () -> assertTrue(MainView.matchesQuery("Anything", "")),
                () -> assertTrue(MainView.matchesQuery("Anything", "   ")),
                () -> assertTrue(MainView.matchesQuery("Anything", "--")),
                () -> assertTrue(MainView.matchesQuery("Anything", null))
        );
    }

    @Test
    void nullTitleMatchesOnlyAnEmptyQuery() {
        assertAll(
                () -> assertFalse(MainView.matchesQuery(null, "button")),
                () -> assertTrue(MainView.matchesQuery(null, ""))
        );
    }

    @Test
    void matchingKeepsNonAsciiLetters() {
        // Cyrillic "Кнопка" / "кнопка": normalization must keep Unicode letters (\p{IsAlphabetic})
        // and lower-case them, not behave like a [^a-z0-9] strip that would drop them.
        assertTrue(MainView.matchesQuery("Кнопка", "кнопка"));
    }

    @Test
    void normalizeForSearchLowercasesAndStripsNonAlphanumerics() {
        assertAll(
                () -> assertEquals("dataawaretextfield", MainView.normalizeForSearch("Data-aware TextField!")),
                () -> assertEquals("textfield2", MainView.normalizeForSearch("Text Field 2")),
                () -> assertEquals("", MainView.normalizeForSearch("  -- ")),
                () -> assertEquals("", MainView.normalizeForSearch(null))
        );
    }

    @Test
    void tokenizeSplitsOnWhitespaceAndDropsEmpties() {
        assertAll(
                () -> assertEquals(List.of("button", "action"), MainView.tokenize("  button   action ")),
                () -> assertEquals(List.of("textfield"), MainView.tokenize("text-field")),
                () -> assertEquals(List.of(), MainView.tokenize("")),
                () -> assertEquals(List.of(), MainView.tokenize("--")),
                () -> assertEquals(List.of(), MainView.tokenize(null))
        );
    }
}
