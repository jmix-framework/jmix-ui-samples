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
import io.jmix.flowui.action.ActionType;
import io.jmix.flowui.action.usermenu.UserMenuThemeSwitchAction;
import io.jmix.uisamples.icon.UiSamplesIcon;

/**
 * Application override of {@link UserMenuThemeSwitchAction} that replaces the
 * default font icons with project SVG sprite icons (sun / moon-star / sun-moon
 * from {@link UiSamplesIcon}).
 *
 * <p>Registered under the same {@code @ActionType} id as the base action
 * ({@value UserMenuThemeSwitchAction#ID}). Jmix's {@code ActionsImpl} merges
 * {@link io.jmix.flowui.sys.ActionsConfiguration} beans in module order with
 * the application coming last, so this class transparently replaces the
 * built-in action for the existing {@code type="userMenu_themeSwitch"}
 * reference in views — no view-XML change required.
 *
 * <p>All behavior (localStorage key {@code jmix.app.theme},
 * {@code color-scheme-switching-support.js} integration, three-state submenu,
 * etc.) is inherited from the parent.
 */
@ActionType(UserMenuThemeSwitchAction.ID)
public class UiSamplesUserMenuThemeSwitchAction extends UserMenuThemeSwitchAction {

    public UiSamplesUserMenuThemeSwitchAction() {
        super();
    }

    public UiSamplesUserMenuThemeSwitchAction(String id) {
        super(id);
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
