package io.jmix.uisamples.view.flowui.components.button.themevariant;

import com.vaadin.flow.component.icon.VaadinIcon;
import io.jmix.flowui.component.SupportsTypedValue.TypedValueChangeEvent;
import io.jmix.flowui.component.checkboxgroup.JmixCheckboxGroup;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
// sample-hide:start
import io.jmix.uisamples.theme.AppTheme;
import io.jmix.uisamples.theme.ThemeManager;
import org.springframework.beans.factory.annotation.Autowired;
// sample-hide:end

import java.util.Collection;
import java.util.List;

@ViewController("button-theme-variant")
@ViewDescriptor("button-theme-variant.xml")
public class ButtonThemeVariantSample extends StandardView {

    @ViewComponent
    private JmixButton testButton;
    @ViewComponent
    private JmixCheckboxGroup<String> settingsCheckboxGroup;
    // sample-hide:start
    @Autowired
    private ThemeManager themeManager;
    // sample-hide:end

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
        testButton.setIcon(null);
        testButton.setText("");
        testButton.setThemeName("");

        event.getValue().stream()
                .map(String::toLowerCase)
                .forEach(this::applyTestButtonTheme);
    }

    private void applyTestButtonTheme(String command) {
        if ("text".equalsIgnoreCase(command)) {
            testButton.setText("Button text");
        } else if ("icon".equalsIgnoreCase(command)) {
            testButton.addThemeName(command);
            testButton.setIcon(VaadinIcon.SMILEY_O.create());
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
