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

package io.jmix.uisamples.icon;

import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.icon.Icon;
import io.jmix.flowui.kit.icon.IconFactory;

/**
 * Application-specific SVG sprite icons defined in
 * {@code src/main/frontend/icons/uisamples-icons.js} (Vaadin iconset
 * {@value #ICONSET}). The icons use {@code stroke="currentColor"} so they
 * inherit the surrounding text color and look native to the active theme.
 *
 * <p>Compatible with the standard Jmix
 * {@link io.jmix.flowui.icon.Icons#get(IconFactory)} API: {@link #name()}
 * returns the {@code "iconset:icon"} form that the icons service parses
 * (see {@code IconsImpl.createIconFromCollection}), so calls like
 * {@code icons.get(UiSamplesIcon.LUMO)} resolve to the correct sprite icon.
 *
 * <p>Implemented as a final class with static instances rather than an enum,
 * because {@link Enum#name()} is {@code final} and would return the enum
 * constant identifier (e.g. {@code "LUMO"}), which the icons service would try
 * to resolve against the built-in {@code JmixFontIcon} / {@code VaadinIcon}
 * registries and fail.
 */
@JsModule("./icons/uisamples-icons.js")
public final class UiSamplesIcon implements IconFactory<Icon> {

    /** Vaadin iconset name registered by {@code uisamples-icons.js}. */
    public static final String ICONSET = "uisamples-icons";

    /** Sparkles icon — represents the Lumo theme. */
    public static final UiSamplesIcon LUMO = new UiSamplesIcon("lumo");

    /** Eclipse icon — represents the Aura theme. */
    public static final UiSamplesIcon AURA = new UiSamplesIcon("aura");

    /** Palette icon — used as the user-menu trigger button. */
    public static final UiSamplesIcon PALETTE = new UiSamplesIcon("palette");

    /** Sun icon — light color scheme. */
    public static final UiSamplesIcon LIGHT = new UiSamplesIcon("light");

    /** Moon icon — dark color scheme. */
    public static final UiSamplesIcon DARK = new UiSamplesIcon("dark");

    /** Sun/moon icon — follow the system color scheme preference. */
    public static final UiSamplesIcon SYSTEM = new UiSamplesIcon("system");

    private final String iconName;

    private UiSamplesIcon(String iconName) {
        this.iconName = iconName;
    }

    @Override
    public String name() {
        return ICONSET + ":" + iconName;
    }

    @Override
    public Icon create() {
        return new Icon(ICONSET, iconName);
    }
}
