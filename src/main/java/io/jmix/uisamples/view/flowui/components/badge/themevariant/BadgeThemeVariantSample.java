package io.jmix.uisamples.view.flowui.components.badge.themevariant;

import com.vaadin.flow.component.badge.Badge;
import com.vaadin.flow.component.icon.VaadinIcon;
import io.jmix.flowui.component.SupportsTypedValue.TypedValueChangeEvent;
import io.jmix.flowui.component.checkboxgroup.JmixCheckboxGroup;
import io.jmix.flowui.view.*;

import java.util.Collection;
import java.util.List;

@ViewController("badge-theme-variant")
@ViewDescriptor("badge-theme-variant.xml")
public class BadgeThemeVariantSample extends StandardView {

    @ViewComponent
    protected Badge testBadge;
    @ViewComponent
    protected JmixCheckboxGroup<String> settingsCheckboxGroup;

    @Subscribe
    protected void onInit(InitEvent event) {
        settingsCheckboxGroup.setItems(getSettingsCheckboxGroupItems());
    }

    @Subscribe("settingsCheckboxGroup")
    protected void onSettingsValueChange(TypedValueChangeEvent<JmixCheckboxGroup<String>, Collection<String>> event) {
        if (event.getValue() == null) {
            return;
        }

        //clear
        testBadge.setText(null);
        testBadge.setNumber(null);
        testBadge.setIcon(null);
        testBadge.getElement().getThemeList().clear();

        event.getValue().stream()
                .map(String::toLowerCase)
                .forEach(this::applyTestBadgeSetting);
    }

    protected void applyTestBadgeSetting(String setting) {
        switch (setting) {
            case "text" -> testBadge.setText("Badge text");
            case "number" -> testBadge.setNumber(5);
            case "icon" -> testBadge.setIcon(VaadinIcon.SMILEY_O.create());
            default -> testBadge.getElement().getThemeList().add(setting);
        }
    }

    protected List<String> getSettingsCheckboxGroupItems() {
        return List.of("Text", "Number", "Icon", "Success", "Warning", "Error",
                "Filled", "Small", "Dot", "Icon-only", "Number-only");
    }
}
