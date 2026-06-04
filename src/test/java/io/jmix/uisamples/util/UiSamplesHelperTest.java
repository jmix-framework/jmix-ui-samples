package io.jmix.uisamples.util;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for {@link UiSamplesHelper#stripForDisplay} — the theme/plumbing marker processing
 * applied to source shown in sample code tabs. Pure function: no Spring context needed.
 */
class UiSamplesHelperTest {

    private final UiSamplesHelper helper = new UiSamplesHelper(null, null, null);

    @Test
    void contentWithoutMarkersIsUnchanged() {
        String src = "line A\nline B\nline C";
        assertEquals(src, helper.stripForDisplay(src, "aura"));
        assertEquals(src, helper.stripForDisplay(src, "lumo"));
    }

    @Test
    void themeOnlyBlockKeptUnderMatchingThemeMarkersRemoved() {
        String src = "line A\n// theme-only:lumo\nline B\n// theme-only:lumo:end\nline C";
        assertEquals("line A\nline B\nline C", helper.stripForDisplay(src, "lumo"));
    }

    @Test
    void themeOnlyBlockDroppedUnderOtherTheme() {
        String src = "line A\n// theme-only:lumo\nline B\n// theme-only:lumo:end\nline C";
        assertEquals("line A\nline C", helper.stripForDisplay(src, "aura"));
    }

    @Test
    void xmlThemeOnlyBlockHandled() {
        String src = "<a/>\n<!-- theme-only:lumo -->\n<b id=\"x\"/>\n<!-- theme-only:lumo:end -->\n<c/>";
        assertEquals("<a/>\n<b id=\"x\"/>\n<c/>", helper.stripForDisplay(src, "lumo"));
        assertEquals("<a/>\n<c/>", helper.stripForDisplay(src, "aura"));
    }

    @Test
    void sampleHideInlineDropsTheLineUnderAnyTheme() {
        String src = "keep1\ndrop me // sample-hide\nkeep2";
        assertEquals("keep1\nkeep2", helper.stripForDisplay(src, "aura"));
        assertEquals("keep1\nkeep2", helper.stripForDisplay(src, "lumo"));
    }

    @Test
    void sampleHideBlockDropsTheSpan() {
        String src = "keep1\n// sample-hide:start\nx\ny\n// sample-hide:end\nkeep2";
        assertEquals("keep1\nkeep2", helper.stripForDisplay(src, "aura"));
    }

    @Test
    void sampleHideGuardAroundThemeOnlyBlock() {
        // Mirrors ButtonThemeVariantSample#getSettingsCheckboxGroupItems
        String src = """
                List<String> items = base;
                if (lumo) {            // sample-hide
                // theme-only:lumo
                items.addAll(extra);
                // theme-only:lumo:end
                }                      // sample-hide
                return items;""";

        assertEquals("List<String> items = base;\nreturn items;",
                helper.stripForDisplay(src, "aura"));
        assertEquals("List<String> items = base;\nitems.addAll(extra);\nreturn items;",
                helper.stripForDisplay(src, "lumo"));
    }

    // --- themeHiddenComponentIds (dom4j walk over theme-only comment blocks) ---

    private static final String PROGRESS_BAR_XML = """
            <view xmlns="http://jmix.io/schema/flowui/view">
                <layout>
                    <h4 text="Error"/>
                    <progressBar id="errorProgressBar" themeNames="error"/>
                    <!-- theme-only:lumo -->
                    <h4 id="contrastLabel" text="Contrast"/>
                    <progressBar id="contrastProgressBar" themeNames="contrast"/>
                    <!-- theme-only:lumo:end -->
                </layout>
            </view>""";

    private static final String BUTTON_XML = """
            <view xmlns="http://jmix.io/schema/flowui/view">
                <layout>
                    <hbox>
                        <button id="primaryButton" themeNames="primary"/>
                        <!-- theme-only:lumo -->
                        <button id="contrastButton" themeNames="contrast"/>
                        <!-- theme-only:lumo:end -->
                    </hbox>
                    <hbox>
                        <button id="tertiaryButton" themeNames="tertiary"/>
                        <!-- theme-only:lumo -->
                        <button id="tertiaryInlineButton" themeNames="tertiary-inline"/>
                        <!-- theme-only:lumo:end -->
                    </hbox>
                </layout>
            </view>""";

    @Test
    void foreignThemeIdsCollectedUnderActiveAura() {
        assertEquals(List.of("contrastLabel", "contrastProgressBar"),
                helper.themeHiddenComponentIds(PROGRESS_BAR_XML, "aura"));
    }

    @Test
    void noIdsHiddenUnderTheBlocksOwnTheme() {
        assertTrue(helper.themeHiddenComponentIds(PROGRESS_BAR_XML, "lumo").isEmpty());
    }

    @Test
    void foreignThemeIdsCollectedFromNestedBlocks() {
        assertEquals(List.of("contrastButton", "tertiaryInlineButton"),
                helper.themeHiddenComponentIds(BUTTON_XML, "aura"));
        assertTrue(helper.themeHiddenComponentIds(BUTTON_XML, "lumo").isEmpty());
    }
}
