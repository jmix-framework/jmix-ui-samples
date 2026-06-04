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
import java.util.EventObject;
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
 * <p>A {@link ThemeChangedEvent} is published to {@link #addThemeChangedListener
 * registered listeners} when the user switches the theme via
 * {@link #applyTheme(AppTheme)}, so views whose content depends on the active
 * theme (notably the sample view, which shows theme-specific source files and
 * demo components) can reload themselves without a full browser page refresh.
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
    private final List<ThemeChangedListener> themeChangedListeners = new ArrayList<>();

    /**
     * Switches the current UI to the given theme and persists the selection in
     * browser {@code localStorage} under {@link #THEME_STORAGE_KEY}. The
     * previously applied stylesheets are removed first, then the new theme's
     * stylesheets are injected. Intended to be called when the user picks a
     * theme via the UI.
     *
     * <p>When the theme actually changes, a {@link ThemeChangedEvent} is
     * published to {@link #addThemeChangedListener registered listeners} so they
     * can react (e.g. reload theme-dependent content). Selecting the already
     * active theme applies the stylesheets again but fires no event.
     *
     * @param theme the theme to apply (non-null)
     */
    public void applyTheme(AppTheme theme) {
        AppTheme previousTheme = currentTheme;

        applyInitial(theme);
        UI.getCurrent().getPage().executeJs("localStorage.setItem($0, $1)",
                THEME_STORAGE_KEY, theme.getId());

        if (previousTheme != theme) {
            fireThemeChangedEvent(new ThemeChangedEvent(this, previousTheme, theme));
        }
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

    /**
     * Registers a listener notified whenever the user switches the theme via
     * {@link #applyTheme(AppTheme)}. Typically used by views whose content
     * depends on the active theme (e.g. the sample view) to reload themselves
     * without a full browser page refresh.
     *
     * @param listener the listener to add (non-null)
     * @return a handle for removing the listener; call {@link Registration#remove()}
     *         when the listener is no longer needed (e.g. on view detach)
     */
    public Registration addThemeChangedListener(ThemeChangedListener listener) {
        themeChangedListeners.add(listener);
        return () -> themeChangedListeners.remove(listener);
    }

    protected void fireThemeChangedEvent(ThemeChangedEvent event) {
        // Iterate over a copy: a listener may trigger a view refresh that
        // re-registers/removes listeners while the event is being dispatched.
        for (ThemeChangedListener listener : List.copyOf(themeChangedListeners)) {
            listener.onThemeChanged(event);
        }
    }

    /**
     * Published by {@link ThemeManager} after the user switches the application
     * theme at runtime via {@link #applyTheme(AppTheme)}. It is <b>not</b>
     * published for the theme applied at UI start nor for the {@code localStorage}
     * restore (both go through {@link #applyInitial(AppTheme)}), so listeners
     * react only to deliberate user-initiated switches.
     *
     * @see #addThemeChangedListener(ThemeChangedListener)
     */
    public static class ThemeChangedEvent extends EventObject {

        private final AppTheme previousTheme;
        private final AppTheme theme;

        public ThemeChangedEvent(ThemeManager source, AppTheme previousTheme, AppTheme theme) {
            super(source);
            this.previousTheme = previousTheme;
            this.theme = theme;
        }

        @Override
        public ThemeManager getSource() {
            return (ThemeManager) super.getSource();
        }

        /** @return the theme that was active before the switch. */
        public AppTheme getPreviousTheme() {
            return previousTheme;
        }

        /** @return the theme that is now active. */
        public AppTheme getTheme() {
            return theme;
        }
    }

    /**
     * Listener notified when the user switches the application theme at runtime.
     *
     * @see #addThemeChangedListener(ThemeChangedListener)
     */
    @FunctionalInterface
    public interface ThemeChangedListener {

        void onThemeChanged(ThemeChangedEvent event);
    }
}
