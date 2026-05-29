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
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

@ViewController("tabs-theme-variant")
@ViewDescriptor("tabs-theme-variant.xml")
public class TabsThemeVariantSample extends StandardView {

    @ViewComponent
    protected Tabs tabs;
    @ViewComponent
    protected JmixCheckboxGroup<String> tabsSettingsCheckboxGroup;
    @ViewComponent
    protected HorizontalLayout controlButtonPlaceholder;

    @Autowired
    protected UiComponents uiComponents;

    @Subscribe
    protected void onInit(InitEvent event) {
        List<String> tabsSettingsItems = List.of("Centered", "Small", "Minimal", "Hide-scroll-buttons",
                "Equal-width-tabs");
        tabsSettingsCheckboxGroup.setItems(tabsSettingsItems);

        controlButtonPlaceholder.add(createRemoveTabButton(), createAddTabButton());
    }

    @Subscribe("tabsSettingsCheckboxGroup")
    protected void onTabsSettingsCheckboxGroupValueChange(
            AbstractField.ComponentValueChangeEvent<JmixCheckboxGroup<String>, Collection<String>> event) {
        if (event.getValue() == null) {
            return;
        }

        applyTabsThemes(event.getValue());
    }

    protected void applyTabsThemes(Collection<String> themes) {
        tabs.getElement().getThemeList().clear();

        themes.stream()
                .map(String::toLowerCase)
                .forEach(tabs::addThemeName);
    }

    protected Component createAddTabButton() {
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

    protected Component createRemoveTabButton() {
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
}
