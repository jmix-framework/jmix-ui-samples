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

package io.jmix.uisamples.action;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.dom.DomEvent;
import com.vaadin.flow.shared.Registration;
import io.jmix.flowui.action.ActionType;
import io.jmix.flowui.action.usermenu.UserMenuThemeSwitchAction;
import io.jmix.flowui.kit.component.usermenu.UserMenuItem;
import io.jmix.uisamples.component.themetoggle.ThemeToggle;
import io.jmix.uisamples.icon.UiSamplesIcon;
import org.jspecify.annotations.Nullable;

/**
 * Application override of {@link UserMenuThemeSwitchAction} that replaces the
 * default font icons with project SVG sprite icons (sun / moon-star / sun-moon
 * from {@link UiSamplesIcon}) and keeps the checked item in sync when the color
 * scheme is switched by a {@link ThemeToggle}.
 *
 * <p>Registered under the same {@code @ActionType} id as the base action
 * ({@value UserMenuThemeSwitchAction#ID}). Jmix's {@code ActionsImpl} merges
 * {@link io.jmix.flowui.sys.ActionsConfiguration} beans in module order with
 * the application coming last, so this class transparently replaces the
 * built-in action for the existing {@code type="userMenu_themeSwitch"}
 * reference in views — no view-XML change required.
 *
 * <p>All other behavior (localStorage key {@code jmix.app.theme},
 * {@code color-scheme-switching-support.js} integration, three-state submenu,
 * etc.) is inherited from the parent.
 */
@ActionType(UserMenuThemeSwitchAction.ID)
public class UiSamplesUserMenuThemeSwitchAction extends UserMenuThemeSwitchAction {

    protected static final String THEME_VALUE_EVENT_DATA = "event.detail.value";

    protected @Nullable Registration themeToggleRegistration;

    public UiSamplesUserMenuThemeSwitchAction() {
        super();
    }

    public UiSamplesUserMenuThemeSwitchAction(String id) {
        super(id);
    }

    @Override
    protected void setMenuItemInternal(@Nullable UserMenuItem menuItem) {
        super.setMenuItemInternal(menuItem);

        if (themeToggleRegistration != null) {
            themeToggleRegistration.remove();
            themeToggleRegistration = null;
        }

        if (menuItem == null) {
            return;
        }

        themeToggleRegistration = UI.getCurrent().getElement()
                .addEventListener(ThemeToggle.THEME_CHANGED_EVENT, this::onThemeToggleThemeChanged)
                .addEventData(THEME_VALUE_EVENT_DATA);
    }

    protected void onThemeToggleThemeChanged(DomEvent event) {
        updateState(event.getEventData().get(THEME_VALUE_EVENT_DATA).asString());
    }

    @Override
    protected Component createIcon(String theme) {
        return switch (theme) {
            case SYSTEM_THEME -> icons.get(UiSamplesIcon.SYSTEM);
            case LIGHT_THEME -> icons.get(UiSamplesIcon.LIGHT);
            case DARK_THEME -> icons.get(UiSamplesIcon.DARK);
            default -> throw new IllegalStateException("Unknown theme: " + theme);
        };
    }
}
