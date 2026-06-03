package io.jmix.uisamples.view.flowui.containers.details.themevariant;

import com.vaadin.flow.component.details.Details;
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

@ViewController("details-theme-variant")
@ViewDescriptor("details-theme-variant.xml")
public class DetailsThemeVariantSample extends StandardView {

    @ViewComponent
    private Details firstDetails;
    @ViewComponent
    private JmixCheckboxGroup<String> firstDetailsSettingsCheckboxGroup;

    @ViewComponent
    private Details secondDetails;
    @ViewComponent
    private JmixCheckboxGroup<String> secondDetailsSettingsCheckboxGroup;

    // sample-hide:start
    @Autowired
    private ThemeManager themeManager;
    // sample-hide:end
    @Subscribe
    public void onInit(InitEvent event) {
        List<String> settingsItems = getThemeList();

        firstDetailsSettingsCheckboxGroup.setItems(settingsItems);
        secondDetailsSettingsCheckboxGroup.setItems(settingsItems);
    }

    @Subscribe("firstDetailsSettingsCheckboxGroup")
    public void onFirstDetailsSettingsValueChange(
            TypedValueChangeEvent<JmixCheckboxGroup<String>, Collection<String>> event) {
        if (event.getValue() == null) {
            return;
        }

        applyDetailsSettings(event.getValue(), firstDetails);
    }

    @Subscribe("secondDetailsSettingsCheckboxGroup")
    public void onSecondDetailsSettingsValueChange(
            TypedValueChangeEvent<JmixCheckboxGroup<String>, Collection<String>> event) {
        if (event.getValue() == null) {
            return;
        }

        applyDetailsSettings(event.getValue(), secondDetails);
    }

    private void applyDetailsSettings(Collection<String> settings, Details targetDetails) {
        targetDetails.setThemeName("");

        settings.stream()
                .map(String::toLowerCase)
                .forEach(targetDetails::addThemeName);
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
