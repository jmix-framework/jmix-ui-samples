package io.jmix.uisamples.view.flowui.components.tabs.themevariant;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.checkboxgroup.JmixCheckboxGroup;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.theme.StyleUtility;
import io.jmix.flowui.view.*;
// sample-hide:start
import io.jmix.uisamples.theme.AppTheme;
import io.jmix.uisamples.theme.ThemeManager;
// sample-hide:end
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ViewController("tabs-theme-variant")
@ViewDescriptor("tabs-theme-variant.xml")
public class TabsThemeVariantSample extends StandardView {

    @ViewComponent
    private Tabs tabs;
    @ViewComponent
    private JmixCheckboxGroup<String> tabsSettingsCheckboxGroup;
    @ViewComponent
    private HorizontalLayout controlButtonPlaceholder;

    @Autowired
    private UiComponents uiComponents;
    // sample-hide:start
    @Autowired
    private ThemeManager themeManager;
    // sample-hide:end

    @Subscribe
    public void onInit(InitEvent event) {
        tabsSettingsCheckboxGroup.setItems(getTabsThemes());
        controlButtonPlaceholder.add(createRemoveTabButton(), createAddTabButton());
    }

    @Subscribe("tabsSettingsCheckboxGroup")
    public void onTabsSettingsCheckboxGroupValueChange(
            AbstractField.ComponentValueChangeEvent<JmixCheckboxGroup<String>, Collection<String>> event) {
        if (event.getValue() == null) {
            return;
        }

        applyTabsThemes(event.getValue());
    }

    private void applyTabsThemes(Collection<String> themes) {
        tabs.getElement().getThemeList().clear();

        themes.stream()
                .map(String::toLowerCase)
                .forEach(tabs::addThemeName);
    }

    private Component createAddTabButton() {
        JmixButton button = uiComponents.create(JmixButton.class);
        button.setIcon(VaadinIcon.PLUS.create());
        button.addThemeVariants(ButtonVariant.LUMO_ICON);
        button.addClassName(StyleUtility.Button.LINK_BUTTON);

        button.addClickListener(event -> {
            long newTabIndex = tabs.getChildren().count() + 1;
            String tabLabel = "Tab " + newTabIndex;
            tabs.add(new Tab(tabLabel));
        });

        return button;
    }

    private Component createRemoveTabButton() {
        JmixButton button = uiComponents.create(JmixButton.class);
        button.setIcon(VaadinIcon.MINUS.create());
        button.addThemeVariants(ButtonVariant.LUMO_ICON);
        button.addClassName(StyleUtility.Button.LINK_BUTTON);

        button.addClickListener(event -> {
            long currentTabsSize = tabs.getChildren().count();
            if (currentTabsSize > 1) {
                Tab tabAt = tabs.getTabAt((int) (currentTabsSize - 1));
                tabs.remove(tabAt);
            }
        });

        return button;
    }

    private List<String> getTabsThemes() {
        if (themeManager.getCurrentTheme() == AppTheme.LUMO) {  // sample-hide
            // theme-only:lumo
        return List.of("Centered", "Small", "Minimal", "Hide-scroll-buttons", "Equal-width-tabs");
            // theme-only:lumo:end
        } else { // sample-hide
            // theme-only:aura
        return List.of("Small", "Hide-scroll-buttons");
            // theme-only:aura:end
        } // sample-hide
    }
}
