/*
 * Copyright 2019 Haulmont.
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

package io.jmix.sampler.screen.sys.main;

import com.google.common.base.Strings;
import com.vaadin.server.Page;
import io.jmix.core.CoreProperties;
import io.jmix.core.MessageTools;
import io.jmix.core.Messages;
import io.jmix.sampler.bean.SamplerApp;
import io.jmix.sampler.config.SamplerMenuConfig;
import io.jmix.sampler.config.SamplerMenuItem;
import io.jmix.sampler.screen.sys.maindashboard.DashboardItemClickEvent;
import io.jmix.sampler.screen.sys.maindashboard.MainDashboardFragment;
import io.jmix.sampler.util.SamplerHelper;
import io.jmix.ui.App;
import io.jmix.ui.AppUI;
import io.jmix.ui.Screens;
import io.jmix.ui.UiProperties;
import io.jmix.ui.component.*;
import io.jmix.ui.component.mainwindow.SideMenu;
import io.jmix.ui.navigation.RedirectHandler;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.navigation.UrlRouting;
import io.jmix.ui.screen.*;
import io.jmix.ui.settings.UserSettingsTools;
import io.jmix.ui.theme.ThemeVariantsManager;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Route(path = "main")
@UiDescriptor("main-screen.xml")
@UiController("sampler_MainScreen")
public class MainScreen extends Screen implements Window.HasWorkArea {

    protected final static String FOUND_ITEM_STYLE = "found-item";

    @Autowired
    protected SamplerMenuConfig menuConfig;
    @Autowired
    protected SamplerHelper helper;
    @Autowired
    protected Screens screens;
    @Autowired
    protected CoreProperties coreProperties;
    @Autowired
    private UiProperties uiProperties;
    @Autowired
    protected UrlRouting urlRouting;
    @Autowired
    protected UserSettingsTools userSettingsTools;
    @Autowired
    protected ApplicationContext applicationContext;
    @Autowired
    protected Messages messages;
    @Autowired
    private MessageTools messageTools;

    @Autowired
    protected TextField<String> searchField;
    @Autowired
    protected SideMenu sideMenu;
    @Autowired
    protected SplitPanel mainSplit;
    @Autowired
    protected ComboBox<Locale> localeSelector;
    @Autowired
    protected ComboBox<Theme> themeSelector;
    @Autowired
    protected MainDashboardFragment dashboardFragment;
    @Autowired
    protected ThemeVariantsManager themeVariantsManager;

    protected List<SideMenu.MenuItem> foundItems = new ArrayList<>();
    protected List<String> parentListIdsToExpand = new ArrayList<>();

    @Override
    public AppWorkArea getWorkArea() {
        return (AppWorkArea) getWindow().getComponent("workArea");
    }

    public void expandItemsFromDirectLink(String id) {
        sideMenu.removeAllMenuItems();
        initMenuItems();
        expandAllParentRecursively(id);
        SideMenu.MenuItem item = sideMenu.getMenuItemNN(id);
        item.setStyleName(FOUND_ITEM_STYLE);
        item.setExpanded(true);
    }

    public void initDashboardByRootItemId(String itemId) {
        dashboardFragment.clearDashboard();
        if (menuConfig.isRootItem(itemId)) {
            dashboardFragment.initDashboardMenu(itemId);
        } else {
            dashboardFragment.initDashboardMenu(MainDashboardFragment.MENU_ROOT_ITEM_ID);
        }
    }

    @Subscribe
    public void onInit(InitEvent event) {
        initSideMenu();
        initMainSplit();
        initLocales();
        initThemes();
        initDashboardFragment();
        initRedirectHandler();

        searchField.focus();
    }

    protected void initSideMenu() {
        sideMenu.removeAllMenuItems();
        initMenuItems();
    }

    protected void initMenuItems() {
        List<SamplerMenuItem> menuItems = menuConfig.getRootItems();
        for (SamplerMenuItem item : menuItems) {
            SideMenu.MenuItem menuItem = sideMenu.createMenuItem(item.getId());
            menuItem.setCaption(menuConfig.getMenuItemCaption(item.getId()));
            loadMenuItems(item, menuItem);
            sideMenu.addMenuItem(menuItem);
        }
    }

    protected void initMainSplit() {
        mainSplit.setDockable(true);
    }

    protected void initLocales() {
        AppUI ui = AppUI.getCurrent();
        if (ui == null) {
            return;
        }

        localeSelector.setOptionsMap(messageTools.getAvailableLocalesMap());
        localeSelector.setValue(ui.getLocale());

        localeSelector.setVisible(uiProperties.isLocaleSelectVisible());

        localeSelector.addValueChangeListener(e -> {
            Locale selectedLocale = e.getValue();
            if (selectedLocale != null) {
                updateLocale(ui, selectedLocale);
            }
        });

        localeSelector.setOptionStyleProvider(locale ->
                locale.equals(ui.getLocale()) ? "selected-locale" : null
        );
    }

    protected void updateLocale(AppUI ui, Locale locale) {
        RedirectHandler handler = ui.getUrlChangeHandler().getRedirectHandler();
        if (handler != null) {
            handler.schedule(urlRouting.getState());
        }

        ui.getApp().setLocale(locale);
        ui.getApp().addCookie(App.COOKIE_LOCALE, locale.toLanguageTag());
        ui.getApp().createTopLevelWindow();
    }

    protected void initThemes() {
        List<Theme> availableThemes = Arrays.asList(
                new Theme("demo-helium", "helium-light", "light"),
                new Theme("demo-helium", "helium-dark", "dark"),
                new Theme("demo-halo", "halo"),
                new Theme("demo-hover", "hover")
        );

        themeSelector.setOptionsList(availableThemes);

        String userTheme = userSettingsTools.loadTheme();
        String userThemeMode = getUserThemeMode();
        themeSelector.setValue(
                availableThemes.stream()
                        .filter(theme ->
                                theme.getTheme().equals(userTheme)
                                        && (theme.getMode() == null
                                        || theme.getMode().equals(userThemeMode)))
                        .findFirst()
                        .orElse(availableThemes.get(0))
        );

        themeSelector.addValueChangeListener(valueChangeEvent -> {
            AppUI ui = AppUI.getCurrent();
            Theme selectedTheme = valueChangeEvent.getValue();
            if (ui != null && selectedTheme != null) {
                RedirectHandler redirectHandler = ui.getUrlChangeHandler().getRedirectHandler();
                if (redirectHandler != null) {
                    redirectHandler.schedule(urlRouting.getState());
                }

                String themeName = selectedTheme.getTheme();
                userSettingsTools.saveAppWindowTheme(themeName);
                ui.setTheme(themeName);
                ((SamplerApp) ui.getApp()).applyUserTheme(themeName);

                String themeMode = selectedTheme.getMode();
                if (!Strings.isNullOrEmpty(themeMode)) {
                    themeVariantsManager.setThemeMode(themeMode);
                    Page.getCurrent().reload();
                }

                ui.getApp().createTopLevelWindow();
            }
        });

        themeSelector.setOptionStyleProvider(theme -> {
            if (theme.getTheme().equals(userSettingsTools.loadTheme())
                    && (theme.getMode() == null
                    || theme.getMode().equals(userThemeMode))) {
                return "selected-theme";
            }

            return null;
        });
    }

    @org.jetbrains.annotations.Nullable
    private String getUserThemeMode() {
        String userThemeMode = themeVariantsManager.getThemeModeUserSetting();
        if (userThemeMode == null) {
            userThemeMode = themeVariantsManager.getThemeModeCookieValue();
        }
        if (userThemeMode == null) {
            userThemeMode = themeVariantsManager.getDefaultThemeMode();
        }

        return userThemeMode;
    }

    protected void initDashboardFragment() {
        dashboardFragment.addDashboardItemClickListener(this::onDashboardItemClicked);
    }

    protected void onDashboardItemClicked(DashboardItemClickEvent event) {
        sideMenu.removeAllMenuItems();
        initMenuItems();

        SideMenu.MenuItem item = sideMenu.getMenuItem(event.getMenuItemId());
        if (item != null) {
            item.setStyleName(FOUND_ITEM_STYLE);
            item.setExpanded(true);
            expandAllParentRecursively(event.getMenuItemId());
        }
    }

    protected void initRedirectHandler() {
        AppUI ui = AppUI.getCurrent();
        if (ui == null) {
            return;
        }

        if (ui.getUrlChangeHandler().getRedirectHandler() == null) {
            RedirectHandler redirectHandler = applicationContext.getBean(RedirectHandler.class, ui);
            ui.getUrlChangeHandler().setRedirectHandler(redirectHandler);
        }
    }

    protected void loadMenuItems(SamplerMenuItem parentSamplerItem,
                                 SideMenu.MenuItem parentSideMenuItem) {
        for (SamplerMenuItem currentItem : parentSamplerItem.getChildren()) {
            SideMenu.MenuItem child;
            String id = currentItem.getId();
            if (currentItem.isMenu()) {
                child = sideMenu.createMenuItem(id);
                child.setCaption(menuConfig.getMenuItemCaption(currentItem.getId()));

                loadMenuItems(currentItem, child);
                parentSideMenuItem.addChildItem(child);
            } else {
                child = sideMenu.createMenuItem(id);
                child.setCaption(menuConfig.getMenuItemCaption(currentItem.getId()));
                child.setCommand(item -> {
                    ScreenOptions screenOptions = helper.getScreenOptions(menuConfig.getItemById(item.getId()));
                    removeStyleNameFromAll(FOUND_ITEM_STYLE, sideMenu.getMenuItems());
                    item.setStyleName(FOUND_ITEM_STYLE);
                    screens.create(helper.getSamplerBrowserId(), OpenMode.NEW_TAB, screenOptions)
                            .show();
                });
                parentSideMenuItem.addChildItem(child);
            }
        }
    }

    protected void removeStyleNameFromAll(String styleName, List<SideMenu.MenuItem> list) {
        for (SideMenu.MenuItem menuItem : list) {
            menuItem.removeStyleName(styleName);
            if (menuItem.hasChildren()) {
                removeStyleNameFromAll(styleName, menuItem.getChildren());
            }
        }
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        AppUI ui = AppUI.getCurrent();
        if (ui != null) {
            RedirectHandler redirectHandler = ui.getUrlChangeHandler().getRedirectHandler();
            if (redirectHandler != null
                    && redirectHandler.scheduled()) {
                redirectHandler.redirect();
            }
        }
    }

    @Subscribe("searchButton")
    protected void onSearchButtonClick(Button.ClickEvent event) {
        search(searchField.getValue());
    }

    @Subscribe("searchField")
    protected void onSearchFieldEnterPress(TextInputField.EnterPressEvent event) {
        search(searchField.getValue());
    }

    protected void search(String searchValue) {
        foundItems.clear();
        sideMenu.removeAllMenuItems();
        initMenuItems();
        if (!StringUtils.isEmpty(searchValue)) {
            findItemsRecursively(sideMenu.getMenuItems(), searchValue);

            for (SideMenu.MenuItem item : foundItems) {
                if (menuConfig.getItemById(item.getId()).getParent() != null) {
                    expandAllParentRecursively(item.getId());
                }
                if (CollectionUtils.isNotEmpty(item.getChildren())) {
                    expand(sideMenu.getMenuItemNN(item.getId()), true);
                }
            }

            removeNotRequestedItems(sideMenu.getMenuItems(), null, searchValue);
        }
    }

    protected void findItemsRecursively(List<SideMenu.MenuItem> items, String searchValue) {
        for (SideMenu.MenuItem item : items) {
            if (StringUtils.containsIgnoreCase(item.getCaption(), searchValue)) {
                item.setStyleName(FOUND_ITEM_STYLE);
                foundItems.add(item);
            }
            if (item.hasChildren()) {
                findItemsRecursively(item.getChildren(), searchValue);
            }
        }
    }

    protected void removeNotRequestedItems(List<SideMenu.MenuItem> list,
                                           SideMenu.MenuItem parentItem, String searchValue) {
        for (SideMenu.MenuItem item : list) {
            if (item.hasChildren()) {
                if (!item.isExpanded()) {
                    if (parentItem != null) {
                        parentItem.removeChildItem(item);
                    }
                    sideMenu.removeMenuItem(item);
                } else if (!StringUtils.containsIgnoreCase(item.getCaption(), searchValue)) {
                    removeNotRequestedItems(item.getChildren(), item, searchValue);
                }
            } else if (!StringUtils.containsIgnoreCase(item.getCaption(), searchValue)) {
                if (parentItem != null) {
                    parentItem.removeChildItem(item);
                }
                sideMenu.removeMenuItem(item);
            }
        }
    }

    @Subscribe("expandAllBtn")
    protected void onExpandAllBtnClick(Button.ClickEvent event) {
        for (SideMenu.MenuItem item : sideMenu.getMenuItems()) {
            expand(item, true);
        }
    }

    @Subscribe("collapseAllBtn")
    protected void onCollapseAllBtnClick(Button.ClickEvent event) {
        for (SideMenu.MenuItem item : sideMenu.getMenuItems()) {
            expand(item, false);
        }
    }

    protected void expand(SideMenu.MenuItem item, boolean isExpand) {
        if (item.hasChildren()) {
            item.setExpanded(isExpand);
            for (SideMenu.MenuItem menuItem : item.getChildren()) {
                if (menuItem.hasChildren()) {
                    expand(menuItem, isExpand);
                }
            }
        }
    }

    protected void expandAllParentRecursively(String id) {
        parentListIdsToExpand.clear();
        fillParentListToExpand(id);
        for (String s : parentListIdsToExpand) {
            SideMenu.MenuItem item = sideMenu.getMenuItem(s);
            if (item != null) {
                item.setExpanded(true);
            }
        }
    }

    protected void fillParentListToExpand(String id) {
        SamplerMenuItem itemToExpand = menuConfig.getItemById(id);
        if (itemToExpand.getParent() != null) {
            parentListIdsToExpand.add(itemToExpand.getParent().getId());
            fillParentListToExpand(itemToExpand.getParent().getId());
        }
    }

    private static class Theme {
        private String theme;
        private String displayName;
        private String mode;

        public Theme(String theme, String displayName) {
            this(theme, displayName, null);
        }

        public Theme(String theme, String displayName, String mode) {
            this.theme = theme;
            this.displayName = displayName;
            this.mode = mode;
        }

        public String getTheme() {
            return theme;
        }

        public String getDisplayName() {
            return displayName;
        }

        @Nullable
        public String getMode() {
            return mode;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }
}
