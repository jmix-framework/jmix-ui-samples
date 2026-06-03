package io.jmix.uisamples.view.flowui.components.combobox.themevariant;

import io.jmix.flowui.component.SupportsTypedValue.TypedValueChangeEvent;
import io.jmix.flowui.component.checkboxgroup.JmixCheckboxGroup;
import io.jmix.flowui.component.combobox.JmixComboBox;
import io.jmix.flowui.view.*;
import io.jmix.uisamples.entity.CustomerGrade;

import java.util.Collection;
import java.util.List;

@ViewController("combobox-theme-variant")
@ViewDescriptor("combobox-theme-variant.xml")
public class ComboBoxThemeVariantSample extends StandardView {

    @ViewComponent
    private JmixComboBox<CustomerGrade> testComboBox;
    @ViewComponent
    private JmixCheckboxGroup<String> settingsCheckboxGroup;

    @Subscribe
    public void onInit(InitEvent event) {
        settingsCheckboxGroup.setItems(getSettingsCheckboxGroupItems());
    }

    @Subscribe("settingsCheckboxGroup")
    public void onSettingsValueChange(TypedValueChangeEvent<JmixCheckboxGroup<String>, Collection<String>> event) {
        if (event.getValue() == null) {
            return;
        }

        //clear
        testComboBox.setLabel("");
        testComboBox.setPlaceholder("");
        testComboBox.setHelperText("");
        testComboBox.setThemeName("");
        testComboBox.setReadOnly(false);

        event.getValue().stream()
                .map(String::toLowerCase)
                .forEach(this::applyTestComboBoxTheme);
    }

    private void applyTestComboBoxTheme(String command) {
        switch (command) {
            case "label" -> testComboBox.setLabel("Label");
            case "placeholder" -> testComboBox.setPlaceholder("Placeholder");
            case "helper-text" -> testComboBox.setHelperText("Helper text");
            case "readonly" -> testComboBox.setReadOnly(true);
            default -> testComboBox.addThemeName(command);
        }
    }

    private List<String> getSettingsCheckboxGroupItems() {
        return List.of("Label", "Placeholder", "Helper-Text", "Readonly", "Small", "Align-Left", "Align-Center",
                "Align-Right", "Helper-Above-Field");
    }
}
