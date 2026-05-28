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

package io.jmix.uisamples.bean;

import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.UIInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import io.jmix.uisamples.theme.AppTheme;
import io.jmix.uisamples.theme.ThemeManager;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

/**
 * Applies the application theme on every new {@link com.vaadin.flow.component.UI}
 * so the first paint is rendered with the correct stylesheets, then restores
 * the user's previous selection from browser {@code localStorage}.
 *
 * <p>The flow per UI init is:
 * <ol>
 *   <li>{@link ThemeManager#applyInitial(AppTheme)} is called synchronously
 *       with {@link AppTheme#getDefault() the default theme}, so its stylesheets
 *       are included in the initial bootstrap response and the first paint is
 *       fully styled (no flash of unstyled content).</li>
 *   <li>An asynchronous {@code executeJs} call reads
 *       {@link ThemeManager#THEME_STORAGE_KEY} from {@code localStorage}; if
 *       the stored value differs from the current theme,
 *       {@link ThemeManager#applyInitial(AppTheme) applyInitial} is invoked
 *       again to swap stylesheets.</li>
 * </ol>
 *
 * <p><b>Trade-off:</b> users whose stored theme differs from the default will
 * see a one-time swap from the default to the stored theme on the first paint
 * — the {@code localStorage} read is asynchronous and only completes after the
 * initial render. Users on the default theme experience no swap. Eliminating
 * this swap would require either a synchronously-readable persistence channel
 * (e.g. a cookie sent with the request) or a client-side bootstrap JS module
 * that injects stylesheets before render (the pattern used by
 * {@code color-scheme-switching-support.js} for the light/dark axis).
 */
@Component
public class ThemeUiInitListener implements VaadinServiceInitListener {

    private final ObjectProvider<ThemeManager> themeManagerProvider;

    public ThemeUiInitListener(ObjectProvider<ThemeManager> themeManagerProvider) {
        this.themeManagerProvider = themeManagerProvider;
    }

    @Override
    public void serviceInit(ServiceInitEvent event) {
        event.getSource().addUIInitListener(this::uiInit);
    }

    private void uiInit(UIInitEvent uiInit) {
        ThemeManager themeManager = themeManagerProvider.getObject();

        themeManager.applyInitial(AppTheme.getDefault());
        uiInit.getUI().getPage()
                .executeJs("return localStorage.getItem($0)", ThemeManager.THEME_STORAGE_KEY)
                .then(String.class, storedId -> {
                    AppTheme stored = AppTheme.fromId(storedId);
                    if (stored != themeManager.getCurrentTheme()) {
                        themeManager.applyInitial(stored);
                    }
                });
    }
}
