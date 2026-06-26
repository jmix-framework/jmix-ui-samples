/*
 * Copyright 2026 Haulmont.
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

package io.jmix.uisamples.theme;

import com.vaadin.flow.theme.aura.Aura;
import com.vaadin.flow.theme.lumo.Lumo;
import io.jmix.flowui.theme.aura.JmixAura;
import io.jmix.flowui.theme.lumo.JmixLumo;
import org.jspecify.annotations.Nullable;

import java.util.List;

/**
 * Application visual theme available for runtime switching.
 *
 * <p>Each value carries an ordered list of stylesheet URLs — the base Vaadin
 * theme stylesheet, the Jmix Flow UI theme stylesheet, and the application's
 * custom stylesheet. {@link ThemeManager} passes these URLs to
 * {@link com.vaadin.flow.component.page.Page#addStyleSheet(String)} when
 * applying the theme.
 *
 * <p>The {@link #getId() id} of each value is stable and used as the persisted
 * value in browser {@code localStorage}, so it is decoupled from the enum
 * constant name.
 *
 * @see ThemeManager
 */
public enum AppTheme {

    /**
     * Classic Vaadin Lumo theme combined with the Jmix Lumo extensions.
     */
    LUMO("lumo", List.of(
            Lumo.STYLESHEET,
            JmixLumo.STYLESHEET,
            "themes/lumo/styles.css")),

    /**
     * Vaadin Aura theme (introduced in Vaadin 25) combined with the Jmix Aura
     * extensions. Used as {@link #getDefault() the default theme}.
     */
    AURA("aura", List.of(
            Aura.STYLESHEET,
            JmixAura.STYLESHEET,
            "themes/aura/styles.css"));

    private final String id;
    private final List<String> stylesheetUrls;

    AppTheme(String id, List<String> stylesheetUrls) {
        this.id = id;
        this.stylesheetUrls = stylesheetUrls;
    }

    /**
     * @return the stable identifier of this theme (used in {@code localStorage}).
     */
    public String getId() {
        return id;
    }

    /**
     * @return the ordered list of stylesheet URLs that make up this theme.
     */
    public List<String> getStylesheetUrls() {
        return stylesheetUrls;
    }

    /**
     * Resolves a stored {@link #getId() id} (typically read from
     * {@code localStorage}) into an {@link AppTheme}. Falls back to
     * {@link #getDefault()} for {@code null} or unknown values, so an outdated
     * or tampered storage value never breaks UI initialization.
     */
    public static AppTheme fromId(@Nullable String id) {
        if (id != null) {
            for (AppTheme theme : values()) {
                if (theme.id.equalsIgnoreCase(id)) {
                    return theme;
                }
            }
        }

        return getDefault();
    }

    /**
     * @return the theme applied when the user has not selected one yet.
     */
    public static AppTheme getDefault() {
        return AURA;
    }
}
