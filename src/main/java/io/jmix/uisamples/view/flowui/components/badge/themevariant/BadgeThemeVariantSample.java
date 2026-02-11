package io.jmix.uisamples.view.flowui.components.badge.themevariant;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
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
    protected Span testBadge;
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
        testBadge.removeAll();
        testBadge.getElement().getThemeList().clear();
        testBadge.getElement().getThemeList().add("badge");

        event.getValue().stream()
                .map(String::toLowerCase)
                .forEach(this::applyTestBadgeTheme);
    }

    protected void applyTestBadgeTheme(String command) {
        if ("text".equalsIgnoreCase(command)) {
            testBadge.add(new Span("Badge text"));
        } else if ("icon".equalsIgnoreCase(command)) {
            Icon icon = VaadinIcon.SMILEY_O.create();
            icon.addClassName("badge-icon");
            testBadge.addComponentAsFirst(icon);
        } else {
            testBadge.getElement().getThemeList().add(command);
        }
    }

    protected List<String> getSettingsCheckboxGroupItems() {
        return List.of("Text", "Icon", "Primary", "Pill", "Success", "Error", "Contrast", "Small");
    }
}
