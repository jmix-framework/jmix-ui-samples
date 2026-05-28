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

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * UI-scoped manager that switches the application's visual theme at runtime
 * by adding and removing CSS stylesheets via the Vaadin {@link Page} API.
 *
 * <p>The manager keeps the {@link Registration} handles returned by
 * {@link Page#addStyleSheet(String)} for the currently applied stylesheets so
 * they can be removed when switching to another theme. The user's choice is
 * persisted in browser {@code localStorage} under {@value #THEME_STORAGE_KEY}.
 *
 * <p>{@link UIScope}-scoped because {@code Registration}s are valid only for
 * the {@link Page} of the {@link UI} that produced them: a session-scoped
 * shared list would corrupt state across multiple browser tabs. Each {@link UI}
 * tracks its own active stylesheets and survives in-app navigation (the
 * application layout is not recreated).
 *
 * @see AppTheme
 * @see io.jmix.uisamples.bean.ThemeUiInitListener
 * @see io.jmix.uisamples.action.UserMenuAppThemeSwitchAction
 */
@Component("uisamples_ThemeManager")
@UIScope
public class ThemeManager {

    /** Browser {@code localStorage} key under which the selected theme id is persisted. */
    public static final String THEME_STORAGE_KEY = "jmix.uisamples.appTheme";

    private AppTheme currentTheme = AppTheme.getDefault();
    private final List<Registration> activeRegistrations = new ArrayList<>();

    /**
     * Switches the current UI to the given theme and persists the selection in
     * browser {@code localStorage} under {@link #THEME_STORAGE_KEY}. The
     * previously applied stylesheets are removed first, then the new theme's
     * stylesheets are injected. Intended to be called when the user picks a
     * theme via the UI.
     *
     * @param theme the theme to apply (non-null)
     */
    public void applyTheme(AppTheme theme) {
        applyInitial(theme);
        UI.getCurrent().getPage().executeJs("localStorage.setItem($0, $1)",
                THEME_STORAGE_KEY, theme.getId());
    }

    /**
     * Switches the current UI to the given theme <b>without</b> writing to
     * {@code localStorage}. Used both for applying the default theme at UI
     * initialization and for restoring a value that was just read from
     * {@code localStorage} — neither path should overwrite the user's own
     * persisted choice.
     *
     * @param theme the theme to apply (non-null)
     * @see io.jmix.uisamples.bean.ThemeUiInitListener
     */
    public void applyInitial(AppTheme theme) {
        Page page = UI.getCurrent().getPage();
        activeRegistrations.forEach(Registration::remove);
        activeRegistrations.clear();

        for (String url : theme.getStylesheetUrls()) {
            activeRegistrations.add(page.addStyleSheet(url));
        }
        this.currentTheme = theme;
    }

    /** @return the theme currently applied to this UI. */
    public AppTheme getCurrentTheme() {
        return currentTheme;
    }
}
