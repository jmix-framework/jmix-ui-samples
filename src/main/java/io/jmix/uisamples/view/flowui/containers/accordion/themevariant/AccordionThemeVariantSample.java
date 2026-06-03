package io.jmix.uisamples.view.flowui.containers.accordion.themevariant;

import com.vaadin.flow.component.accordion.AccordionPanel;
import io.jmix.flowui.component.SupportsTypedValue.TypedValueChangeEvent;
import io.jmix.flowui.component.checkboxgroup.JmixCheckboxGroup;
import io.jmix.flowui.view.*;
// sample-hide:start
import io.jmix.uisamples.theme.AppTheme;
import io.jmix.uisamples.theme.ThemeManager;
import org.springframework.beans.factory.annotation.Autowired;
// sample-hide:end

import java.util.Collection;
import java.util.List;

@ViewController("accordion-theme-variant")
@ViewDescriptor("accordion-theme-variant.xml")
public class AccordionThemeVariantSample extends StandardView {

    @ViewComponent
    private AccordionPanel firstPanel;
    @ViewComponent
    private JmixCheckboxGroup<String> firstPanelSettingsCheckboxGroup;

    @ViewComponent
    private AccordionPanel secondPanel;
    @ViewComponent
    private JmixCheckboxGroup<String> secondPanelSettingsCheckboxGroup;

    // sample-hide:start
    @Autowired
    private ThemeManager themeManager;
    // sample-hide:end
    @Subscribe
    public void onInit(InitEvent event) {
        List<String> settingsItems = getThemeList();
        firstPanelSettingsCheckboxGroup.setItems(settingsItems);
        secondPanelSettingsCheckboxGroup.setItems(settingsItems);
    }

    @Subscribe("firstPanelSettingsCheckboxGroup")
    public void onFirstPanelSettingsValueChange(
            TypedValueChangeEvent<JmixCheckboxGroup<String>, Collection<String>> event) {
        if (event.getValue() == null) {
            return;
        }

        applyPanelSettings(event.getValue(), firstPanel);
    }

    @Subscribe("secondPanelSettingsCheckboxGroup")
    public void onSecondPanelSettingsValueChange(
            TypedValueChangeEvent<JmixCheckboxGroup<String>, Collection<String>> event) {
        if (event.getValue() == null) {
            return;
        }

        applyPanelSettings(event.getValue(), secondPanel);
    }

    private void applyPanelSettings(Collection<String> settings, AccordionPanel targetPanel) {
        targetPanel.setThemeName("");

        settings.stream()
                .map(String::toLowerCase)
                .forEach(targetPanel::addThemeName);
    }

    private List<String> getThemeList() {
        if (themeManager.getCurrentTheme() == AppTheme.LUMO) {            // sample-hide
            // theme-only:lumo
        return List.of("Filled", "Reverse", "Small");
            // theme-only:lumo:end
        } else { // sample-hide
            // theme-only:aura
        return List.of("Filled", "Reverse", "Small", "No-padding");
            // theme-only:aura:end
        } // sample-hide
    }
}
