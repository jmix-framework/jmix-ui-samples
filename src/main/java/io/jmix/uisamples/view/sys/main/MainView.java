/*
 * Copyright 2022 Haulmont.
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

package io.jmix.uisamples.view.sys.main;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.app.main.StandardMainView;
import io.jmix.flowui.component.main.JmixListMenu;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.kit.component.main.ListMenu;
import io.jmix.flowui.menu.MenuItem;
import io.jmix.flowui.view.*;
import io.jmix.uisamples.bean.MenuNavigationExpander;
import io.jmix.uisamples.bean.OverviewPageGenerator;
import io.jmix.uisamples.config.UiSamplesMenuConfig;
import io.jmix.uisamples.config.UiSamplesMenuItem;
import io.jmix.uisamples.view.sys.sampleview.SampleView;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Route("")
@ViewController("MainView")
@ViewDescriptor("main-view.xml")
@AnonymousAllowed
public class MainView extends StandardMainView {

    @ViewComponent
    protected MessageBundle messageBundle;
    @ViewComponent
    protected JmixListMenu menu;
    @ViewComponent
    protected TypedTextField<String> searchField;
    @ViewComponent
    protected Div applicationTitlePlaceholder;

    @Autowired
    protected UiSamplesMenuConfig menuConfig;
    @Autowired
    protected UiComponents uiComponents;
    @Autowired
    protected MenuNavigationExpander menuNavigationExpander;
    @Autowired
    protected OverviewPageGenerator overviewPageGenerator;

    protected List<JmixListMenu.MenuItem> foundItems = new ArrayList<>();
    protected List<String> parentListIdsToExpand = new ArrayList<>();
    protected boolean showNew;

    @Subscribe
    public void onInit(InitEvent event) {
        initSideMenu();
        initApplicationTitle();

        menuNavigationExpander.setExpandCallback(this::expandAllParentRecursively);
    }

    @Subscribe
    public void onReady(final ReadyEvent event) {
        createOverviewLayout();
    }

    private void createOverviewLayout() {
        if (getContent().getContent() == null) {
            getContent().setContent(overviewPageGenerator.generate(
                    "main",
                    "io/jmix/uisamples/view/sys/main/main-overview.xml"));
        }
    }

    protected void initApplicationTitle() {
        RouterLink link = uiComponents.create(RouterLink.class);
        link.setRoute(MainView.class);
        link.addClassNames("jmix-main-view-header-link");

        link.add(createApplicationImage(), createApplicationText());

        applicationTitlePlaceholder.addComponentAsFirst(link);
    }

    protected Component createApplicationImage() {
        Image image = uiComponents.create(Image.class);
        image.setSrc("icons/icon.png");

        image.setWidth("1.5em");
        image.setHeight("1.5em");
        return image;
    }

    protected Component createApplicationText() {
        H2 h2 = uiComponents.create(H2.class);
        h2.setText(messageBundle.getMessage("applicationTitle.text"));
        h2.setClassName("jmix-main-view-title");
        return h2;
    }

    protected void initSideMenu() {
        menu.removeAllMenuItems();
        initMenuItems();
        initSearchField();

        searchField.focus();
    }

    protected void initMenuItems() {
        List<UiSamplesMenuItem> menuItems = menuConfig.getRootItems();

        for (UiSamplesMenuItem item : menuItems) {
            ListMenu.MenuBarItem menuItem = new ListMenu.MenuBarItem(item.getId())
                    .withTitle(menuConfig.getMenuItemTitle(item.getId()));

            loadMenuItems(item, menuItem);

            menu.addMenuItem(menuItem);

            if (item.isVaadinCommercial() && item.isNew()) {
                appendSuffixLayout(menuItem);
            }

            if (item.isVaadinCommercial()) {
                appendVaadinCommercialBadge(menuItem);
            }

            if (item.isNew()) {
                appendNewBadge(menuItem);
            }
        }
        UI.getCurrent().addAfterNavigationListener(event -> updateTitle());
    }

    protected void loadMenuItems(UiSamplesMenuItem parentUiSamplesItem,
                                 ListMenu.MenuBarItem parentSideMenuItem) {
        for (UiSamplesMenuItem currentItem : parentUiSamplesItem.getChildren()) {
            String id = currentItem.getId();

            ListMenu.MenuItem child;

            if (currentItem.isMenu()) {
                child = new ListMenu.MenuBarItem(id)
                        .withTitle(menuConfig.getMenuItemTitle(currentItem.getId()));

                loadMenuItems(currentItem, (ListMenu.MenuBarItem) child);
                parentSideMenuItem.addChildItem(child);
            } else {
                MenuItem.MenuItemParameter sampleIdParam = new MenuItem.MenuItemParameter("sampleId", id);

                child = new JmixListMenu.ViewMenuItem(id)
                        .withTitle(menuConfig.getMenuItemTitle(currentItem.getId()))
                        .withControllerClass(SampleView.class)
                        .withUrlQueryParameters(currentItem.getUrlQueryParameters())
                        .withRouteParameters(Collections.singletonList(sampleIdParam));

                parentSideMenuItem.addChildItem(child);
            }

            if (currentItem.isVaadinCommercial() && currentItem.isNew()) {
                appendSuffixLayout(child);
            }

            if (currentItem.isVaadinCommercial()) {
                appendVaadinCommercialBadge(child);
            }

            if (currentItem.isNew()) {
                appendNewBadge(child);
            }
        }
    }

    protected void appendSuffixLayout(ListMenu.MenuItem child) {
        HorizontalLayout horizontalLayout = uiComponents.create(HorizontalLayout.class);
        horizontalLayout.setPadding(false);
        child.setSuffixComponent(horizontalLayout);
    }

    protected void appendNewBadge(ListMenu.MenuItem child) {
        Span newBadge = uiComponents.create(Span.class);
        newBadge.setText(messageBundle.getMessage("newBadge.text"));
        newBadge.getElement().getThemeList().add("badge pill small");

        if (child.getSuffixComponent() != null && child.getSuffixComponent() instanceof HorizontalLayout suffixLayout) {
            suffixLayout.add(newBadge);
        } else {
            child.setSuffixComponent(newBadge);
        }
    }

    protected void appendVaadinCommercialBadge(ListMenu.MenuItem child) {
        Span badgeText = uiComponents.create(Span.class);
        badgeText.setText(messageBundle.getMessage("vaadinCommercialBadge.text"));

        Icon icon = uiComponents.create(Icon.class);
        icon.setIcon(VaadinIcon.DOLLAR);
        icon.addClassName("badged-icon");

        Span vaadinCommercialBadge = uiComponents.create(Span.class);
        vaadinCommercialBadge.getElement().getThemeList().add("badge pill small paid");

        vaadinCommercialBadge.add(badgeText, icon);

        if (child.getSuffixComponent() != null && child.getSuffixComponent() instanceof HorizontalLayout suffixLayout) {
            suffixLayout.add(vaadinCommercialBadge);
        } else {
            child.setSuffixComponent(vaadinCommercialBadge);
        }
    }

    protected void initSearchField() {
        JmixButton searchButton = createSearchButton();

        searchField.setSuffixComponent(searchButton);
        searchField.addKeyPressListener(Key.ENTER, this::searchFieldEnterPressListener);
    }

    protected JmixButton createSearchButton() {
        JmixButton searchButton = uiComponents.create(JmixButton.class);
        searchButton.setIcon(VaadinIcon.SEARCH.create());
        searchButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_TERTIARY_INLINE);

        searchButton.addClickListener(this::searchButtonClickListener);

        return searchButton;
    }

    protected void searchFieldEnterPressListener(KeyPressEvent keyPressEvent) {
        search(searchField.getValue());
    }

    protected void searchButtonClickListener(ClickEvent<Button> buttonClickEvent) {
        search(searchField.getValue());
    }

    @SuppressWarnings("ConstantConditions")
    protected void search(@Nullable String searchValue) {
        foundItems.clear();
        menu.removeAllMenuItems();
        initMenuItems();

        if (!StringUtils.isEmpty(searchValue) || showNew) {
            findItemsRecursively(menu.getMenuItems(), searchValue);

            for (JmixListMenu.MenuItem item : foundItems) {
                if (menuConfig.getItemById(item.getId()).getParent() != null) {
                    expandAllParentRecursively(item.getId());
                }

                ListMenu.MenuItem menuItem = menu.getMenuItem(item.getId());
                if (menuItem.isMenu()
                        && menuItem instanceof ListMenu.MenuBarItem menuBarItem
                        && !menuBarItem.getChildItems().isEmpty()) {
                    expand(menuItem, true);
                }
            }

            removeNotRequestedItems(List.copyOf(menu.getMenuItems()), searchValue);
        }
    }

    protected void findItemsRecursively(List<JmixListMenu.MenuItem> items, @Nullable String searchValue) {
        for (JmixListMenu.MenuItem item : items) {
            if (hasNewBadgeIfRequired(item) && matchSearchValue(item, searchValue)) {
                foundItems.add(item);
            }
            if (item.isMenu()) {
                findItemsRecursively(((ListMenu.MenuBarItem) item).getChildItems(), searchValue);
            }
        }
    }

    protected boolean hasNewBadgeIfRequired(JmixListMenu.MenuItem item) {
        return !showNew || item.getSuffixComponent() != null;
    }

    protected boolean matchSearchValue(JmixListMenu.MenuItem item, @Nullable String searchValue) {
        return searchValue == null || Strings.CI.contains(item.getTitle(), searchValue);
    }

    @SuppressWarnings("ConstantConditions")
    protected void removeNotRequestedItems(List<JmixListMenu.MenuItem> list,
                                           String searchValue) {
        for (JmixListMenu.MenuItem item : list) {
            if (item.isMenu() && item instanceof ListMenu.MenuBarItem menuItem && menuItem.hasChildren()) {
                if (!menuItem.isOpened()) {
                    menu.removeMenuItem(item);
                } else if (!Strings.CI.contains(item.getTitle(), searchValue)
                        || showNew && item.getSuffixComponent() == null) {
                    removeNotRequestedItems(menuItem.getChildItems(), searchValue);
                }
            } else if (!Strings.CI.contains(item.getTitle(), searchValue)
                    || showNew && item.getSuffixComponent() == null) {
                menu.removeMenuItem(item);
            }
        }
    }

    @Subscribe("showNewBtn")
    public void onShowNewBtnClick(ClickEvent<Button> event) {
        showNew = !showNew;
        search(searchField.getValue());

        Button showNewBtn = event.getSource();
        if (showNew) {
            showNewBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        } else {
            showNewBtn.removeThemeVariants(ButtonVariant.LUMO_PRIMARY);
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Subscribe("collapseAllBtn")
    public void onCollapseAllBtnClick(ClickEvent<Button> event) {
        for (JmixListMenu.MenuItem item : menu.getMenuItems()) {
            expand(item, false);
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Subscribe("expandAllBtn")
    public void onExpandAllBtnClick(ClickEvent<Button> event) {
        for (JmixListMenu.MenuItem item : menu.getMenuItems()) {
            expand(item, true);
        }
    }

    protected void expand(JmixListMenu.MenuItem item, boolean isExpand) {
        if (item.isMenu()) {
            ((ListMenu.MenuBarItem) item).setOpened(isExpand);

            for (JmixListMenu.MenuItem menuItem : ((ListMenu.MenuBarItem) item).getChildItems()) {
                if (menuItem.isMenu()) {
                    expand(menuItem, isExpand);
                }
            }
        }
    }

    protected void expandAllParentRecursively(String id) {
        parentListIdsToExpand.clear();
        fillParentListToExpand(id);

        for (String parentId : parentListIdsToExpand) {
            ListMenu.MenuItem menuItem = menu.getMenuItem(parentId);

            if (menuItem instanceof ListMenu.MenuBarItem menuBarItem) {
                menuBarItem.setOpened(true);
            }
        }
    }

    protected void fillParentListToExpand(String id) {
        UiSamplesMenuItem itemToExpand = menuConfig.getItemById(id);
        if (itemToExpand.getParent() != null) {
            parentListIdsToExpand.add(itemToExpand.getParent().getId());
            fillParentListToExpand(itemToExpand.getParent().getId());
        }
    }
}
