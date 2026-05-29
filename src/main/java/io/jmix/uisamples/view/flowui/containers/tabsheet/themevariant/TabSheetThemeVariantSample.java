package io.jmix.uisamples.view.flowui.containers.tabsheet.themevariant;

import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.checkboxgroup.JmixCheckboxGroup;
import io.jmix.flowui.component.tabsheet.JmixTabSheet;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.theme.StyleUtility;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

@ViewController("tabsheet-theme-variant")
@ViewDescriptor("tabsheet-theme-variant.xml")
public class TabSheetThemeVariantSample extends StandardView {

    @ViewComponent
    protected JmixTabSheet tabSheet;
    @ViewComponent
    protected JmixCheckboxGroup<String> tabSheetSettingsCheckboxGroup;

    @Autowired
    protected UiComponents uiComponents;

    @Subscribe
    protected void onInit(InitEvent event) {
        List<String> tabSheetSettingsItems = List.of("Centered", "Small", "Minimal", "Hide-scroll-buttons",
                "Equal-width-tabs", "Bordered");
        tabSheetSettingsCheckboxGroup.setItems(tabSheetSettingsItems);

        tabSheet.setSuffixComponent(createAddTabButton());
        tabSheet.setPrefixComponent(createRemoveTabButton());
    }

    @Subscribe("tabSheetSettingsCheckboxGroup")
    protected void onTabSheetSettingsCheckboxGroupValueChange(
            ComponentValueChangeEvent<JmixCheckboxGroup<String>, Collection<String>> event) {
        if (event.getValue() == null) {
            return;
        }

        applyTabSheetThemes(event.getValue());
    }

    protected void applyTabSheetThemes(Collection<String> themes) {
        tabSheet.getElement().getThemeList().clear();

        themes.stream()
                .map(String::toLowerCase)
                .forEach(tabSheet::addThemeName);
    }

    protected Component createAddTabButton() {
        JmixButton button = uiComponents.create(JmixButton.class);
        button.setIcon(VaadinIcon.PLUS.create());
        button.addThemeVariants(ButtonVariant.LUMO_ICON);
        button.addClassName(StyleUtility.Button.LINK_BUTTON);

        button.addClickListener(event -> {
            int newTabIndex = tabSheet.getOwnComponents().size() + 1;
            String tabLabel = "Tab " + newTabIndex;
            tabSheet.add(tabLabel, new Div());
        });

        return button;
    }

    protected Component createRemoveTabButton() {
        JmixButton button = uiComponents.create(JmixButton.class);
        button.setIcon(VaadinIcon.MINUS.create());
        button.addThemeVariants(ButtonVariant.LUMO_ICON);
        button.addClassName(StyleUtility.Button.LINK_BUTTON);

        button.addClickListener(event -> {
            int currentTabSheetSize = tabSheet.getOwnComponents().size();
            if (currentTabSheetSize > 1) {
                tabSheet.remove(currentTabSheetSize - 1);
            }
        });

        return button;
    }
}
