package io.jmix.uisamples.view.flowui.components.tabs.icons;

// theme-only:lumo
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import io.jmix.flowui.component.checkbox.JmixCheckbox;
import io.jmix.flowui.view.*;
// theme-only:lumo:end

@ViewController("tabs-icons")
@ViewDescriptor("tabs-icons.xml")
public class TabsIconsSample extends StandardView {
    // theme-only:lumo
    @ViewComponent
    protected Tabs tabs;

    @Subscribe("iconControlCheckbox")
    protected void onIconControlCheckboxValueChane(AbstractField.ComponentValueChangeEvent<JmixCheckbox, Boolean> event) {
        if (event.getValue()) {
            tabs.getChildren().forEach(tab -> ((Tab) tab).addThemeVariants(TabVariant.LUMO_ICON_ON_TOP));
        } else {
            tabs.getChildren().forEach(tab -> tab.getElement().getThemeList().clear());
        }
    }
    // theme-only:lumo:end
}
