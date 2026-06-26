package io.jmix.uisamples.view.flowui.components.dropdownbutton.themevariant;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.SupportsTypedValue;
import io.jmix.flowui.component.checkboxgroup.JmixCheckboxGroup;
import io.jmix.flowui.icon.Icons;
import io.jmix.flowui.kit.component.combobutton.ComboButton;
import io.jmix.flowui.kit.component.dropdownbutton.AbstractDropdownButton;
import io.jmix.flowui.kit.component.dropdownbutton.DropdownButton;
import io.jmix.flowui.kit.icon.JmixFontIcon;
import io.jmix.flowui.view.*;
// sample-hide:start
import io.jmix.uisamples.theme.AppTheme;
import io.jmix.uisamples.theme.ThemeManager;
// sample-hide:end
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ViewController("dropdown-button-theme-variant")
@ViewDescriptor("dropdown-button-theme-variant.xml")
public class DropdownButtonThemeVariantSample extends StandardView {

    @ViewComponent
    private VerticalLayout testButtonPlaceholder;
    @ViewComponent
    private JmixCheckboxGroup<String> settingsCheckboxGroup;

    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private Icons icons;
    // sample-hide:start
    @Autowired
    private ThemeManager themeManager;
    // sample-hide:end

    private AbstractDropdownButton testButton;

    @Subscribe
    public void onInit(InitEvent event) {
        testButton = uiComponents.create(DropdownButton.class);
        testButton.addItem("textItem", "Text");
        testButtonPlaceholder.addComponentAsFirst(testButton);
        settingsCheckboxGroup.setItems(getSettingsCheckboxGroupItems());
    }

    @Subscribe("settingsCheckboxGroup")
    public void onSettingsValueChange(SupportsTypedValue.TypedValueChangeEvent<JmixCheckboxGroup<String>, Collection<String>> event) {
        if (event.getValue() == null) {
            return;
        }

        if (event.getValue().contains("ComboButton")) {
            if (testButton instanceof DropdownButton) {
                testButton.removeFromParent();
                testButton = uiComponents.create(ComboButton.class);
                testButtonPlaceholder.addComponentAsFirst(testButton);
            }
        } else if (testButton instanceof ComboButton) {
            testButton.removeFromParent();
            testButton = uiComponents.create(DropdownButton.class);
            testButtonPlaceholder.addComponentAsFirst(testButton);
        }

        //clear
        testButton.setIcon(null);
        testButton.setText("");
        testButton.getThemeNames().clear();

        event.getValue().remove("ComboButton");

        event.getValue().stream()
                .map(String::toLowerCase)
                .forEach(this::applyTestButtonTheme);
    }

    public void applyTestButtonTheme(String command) {
        if ("text".equalsIgnoreCase(command)) {
            testButton.setText("Button text");
        } else if ("icon".equalsIgnoreCase(command)) {
            testButton.addThemeName(command);
            testButton.setIcon(icons.get(JmixFontIcon.SMILEY_O));
        } else {
            testButton.addThemeName(command);
        }
    }

    private List<String> getSettingsCheckboxGroupItems() {
        if (themeManager.getCurrentTheme() == AppTheme.LUMO) {            // sample-hide
            // theme-only:lumo
            return List.of("Text", "Primary", "Success", "Warning", "Error", "Contrast",
                    "Large", "Small", "Icon", "Tertiary", "Tertiary-inline");
            // theme-only:lumo:end
        } else { // sample-hide
            // theme-only:aura
            return List.of(
                    "Text", "Primary", "Success", "Warning", "Error", "Large", "Small", "Icon", "Tertiary");
            // theme-only:aura:end
        } // sample-hide
    }
}
