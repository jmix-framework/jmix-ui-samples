package io.jmix.uisamples.view.flowui.containers.split.themevariant;

import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import io.jmix.flowui.component.checkboxgroup.JmixCheckboxGroup;
import io.jmix.flowui.component.splitlayout.JmixSplitLayout;
import io.jmix.flowui.view.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@ViewController("split-theme-variant")
@ViewDescriptor("split-theme-variant.xml")
public class SplitThemeVariantSample extends StandardView {

    @ViewComponent
    protected JmixSplitLayout split;
    @ViewComponent
    protected JmixCheckboxGroup<String> settingsCheckboxGroup;

    @Subscribe
    protected void onInit(InitEvent event) {
        List<String> settingsItems = List.of("Minimal", "Small", "Splitter-spacing");
        settingsCheckboxGroup.setItems(settingsItems);
        settingsCheckboxGroup.setValue(Set.of("Splitter-spacing"));
    }

    @Subscribe("settingsCheckboxGroup")
    protected void onSettingsCheckboxGroupValueChange(ComponentValueChangeEvent<JmixCheckboxGroup<String>, Collection<String>> event) {
        if (event.getValue() == null) {
            return;
        }

        applySplitSettings(event.getValue());
    }

    protected void applySplitSettings(Collection<String> settings) {
        split.getElement().getThemeList().clear();

        settings.stream()
                .map(String::toLowerCase)
                .forEach(split::addThemeName);
    }
}