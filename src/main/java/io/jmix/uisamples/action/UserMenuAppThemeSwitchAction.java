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
import com.vaadin.flow.component.page.WebStorage;
import io.jmix.core.Messages;
import io.jmix.flowui.action.ActionType;
import io.jmix.flowui.action.usermenu.UserMenuAction;
import io.jmix.flowui.component.usermenu.UserMenu;
import io.jmix.flowui.icon.Icons;
import io.jmix.flowui.kit.component.usermenu.TextUserMenuItem;
import io.jmix.flowui.kit.component.usermenu.UserMenuItem;
import io.jmix.flowui.kit.component.usermenu.UserMenuItem.HasClickListener.ClickEvent;
import io.jmix.flowui.kit.component.usermenu.UserMenuItem.HasSubMenu;
import io.jmix.uisamples.icon.UiSamplesIcon;
import io.jmix.uisamples.theme.AppTheme;
import io.jmix.uisamples.theme.ThemeManager;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Switches the application's visual theme (Lumo or Aura) at runtime.
 * Stylesheets are added/removed via {@link com.vaadin.flow.component.page.Page#addStyleSheet}
 * and tracked by {@link ThemeManager}. The chosen theme is persisted in
 * {@code localStorage} under {@link ThemeManager#THEME_STORAGE_KEY}.
 */
@ActionType(UserMenuAppThemeSwitchAction.ID)
public class UserMenuAppThemeSwitchAction extends UserMenuAction<UserMenuAppThemeSwitchAction, UserMenu> {

    public static final String ID = "uisamples_userMenu_appThemeSwitch";

    protected static final String MESSAGE_KEY = "actions.userMenu.AppThemeSwitch";

    protected Messages messages;
    protected Icons icons;
    protected ThemeManager themeManager;

    protected final Map<AppTheme, UserMenuItem> menuItems = new EnumMap<>(AppTheme.class);
    protected HasSubMenu.SubMenu subMenu;

    public UserMenuAppThemeSwitchAction() {
        this(ID);
    }

    public UserMenuAppThemeSwitchAction(String id) {
        super(id);
    }

    @Autowired
    public void setIcons(Icons icons) {
        this.icons = icons;
        this.icon = createIcon(AppTheme.getDefault());
    }

    @Autowired
    public void setMessages(Messages messages) {
        this.messages = messages;
        this.text = messages.getMessage(MESSAGE_KEY);
    }

    @Autowired
    protected void setThemeManager(ThemeManager themeManager) {
        this.themeManager = themeManager;
    }

    @Override
    protected void setMenuItemInternal(@Nullable UserMenuItem menuItem) {
        super.setMenuItemInternal(menuItem);

        if (subMenu != null) {
            subMenu.removeAll();
            subMenu = null;
        }

        if (menuItem == null) {
            return;
        }

        if (!(menuItem instanceof HasSubMenu hasSubMenu)) {
            throw new IllegalStateException("%s does not implement %s"
                    .formatted(menuItem, HasSubMenu.class.getSimpleName()));
        }

        subMenu = hasSubMenu.getSubMenu();
        initItems(subMenu);

        WebStorage.getItem(WebStorage.Storage.LOCAL_STORAGE,
                ThemeManager.THEME_STORAGE_KEY,
                stored -> updateState(AppTheme.fromId(stored)));
    }

    protected void initItems(HasSubMenu.SubMenu subMenu) {
        for (AppTheme theme : AppTheme.values()) {
            menuItems.put(theme, createItem(subMenu, theme));
        }
    }

    protected UserMenuItem createItem(HasSubMenu.SubMenu subMenu, AppTheme theme) {
        String itemId = "%s_%sUserMenuItem".formatted(ID, theme.getId());
        Consumer<ClickEvent<TextUserMenuItem>> listener = event -> selectTheme(theme);
        UserMenuItem menuItem = subMenu.addTextItem(itemId,
                messages.getMessage("%s.%s".formatted(MESSAGE_KEY, theme.getId())),
                createIcon(theme),
                listener);
        menuItem.setCheckable(true);
        return menuItem;
    }

    protected Component createIcon(AppTheme theme) {
        return switch (theme) {
            case LUMO -> icons.get(UiSamplesIcon.LUMO);
            case AURA -> icons.get(UiSamplesIcon.AURA);
        };
    }

    protected void selectTheme(AppTheme theme) {
        themeManager.applyTheme(theme);
        updateState(theme);
    }

    protected void updateState(AppTheme theme) {
        menuItems.forEach((key, item) -> item.setChecked(key == theme));
        setIcon(createIcon(theme));
    }

    @Override
    public void execute() {
        // do nothing — clicking the parent menu item opens the sub-menu
    }
}
