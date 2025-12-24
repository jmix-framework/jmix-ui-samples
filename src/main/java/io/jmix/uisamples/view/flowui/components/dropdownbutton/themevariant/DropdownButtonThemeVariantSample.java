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
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

@ViewController("dropdown-button-theme-variant")
@ViewDescriptor("dropdown-button-theme-variant.xml")
public class DropdownButtonThemeVariantSample extends StandardView {

    @ViewComponent
    protected VerticalLayout testButtonPlaceholder;
    @ViewComponent
    protected JmixCheckboxGroup<String> settingsCheckboxGroup;

    @Autowired
    protected UiComponents uiComponents;
    @Autowired
    protected Icons icons;

    protected AbstractDropdownButton testButton;

    @Subscribe
    protected void onInit(InitEvent event) {
        testButton = uiComponents.create(DropdownButton.class);
        testButtonPlaceholder.addComponentAsFirst(testButton);
        settingsCheckboxGroup.setItems(getSettingsCheckboxGroupItems());
    }

    @Subscribe("settingsCheckboxGroup")
    protected void onSettingsValueChange(SupportsTypedValue.TypedValueChangeEvent<JmixCheckboxGroup<String>, Collection<String>> event) {
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
        testButton.setIconComponent(null);
        testButton.setText("");
        testButton.getThemeNames().clear();

        event.getValue().remove("ComboButton");

        event.getValue().stream()
                .map(String::toLowerCase)
                .forEach(this::applyTestButtonTheme);
    }

    protected void applyTestButtonTheme(String command) {
        if ("text".equalsIgnoreCase(command)) {
            testButton.setText("Button text");
        } else if ("icon".equalsIgnoreCase(command)) {
            testButton.addThemeName(command);
            testButton.setIconComponent(icons.get(JmixFontIcon.SMILEY_O));
        } else {
            testButton.addThemeName(command);
        }
    }

    protected List<String> getSettingsCheckboxGroupItems() {
        return List.of("ComboButton", "Text", "Primary", "Success", "Error", "Contrast",
                "Large", "Small", "Icon", "Tertiary", "Tertiary-inline");
    }
}
