package io.jmix.uisamples.view.flowui.containers.tabsheet.icons;

// theme-only:lumo
import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
// theme-only:lumo:end
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
// theme-only:lumo
import com.vaadin.flow.component.tabs.TabVariant;
// theme-only:lumo:end
import io.jmix.flowui.UiComponents;
// theme-only:lumo
import io.jmix.flowui.component.checkbox.JmixCheckbox;
// theme-only:lumo:end
import io.jmix.flowui.component.tabsheet.JmixTabSheet;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@ViewController("tabsheet-icons")
@ViewDescriptor("tabsheet-icons.xml")
public class TabSheetIconsSample extends StandardView {

    @ViewComponent
    protected JmixTabSheet tabSheet;

    @Autowired
    protected UiComponents uiComponents;

    @Subscribe
    protected void onInit(InitEvent event) {
        initTabSheet();
    }

    protected void initTabSheet() {
        tabSheet.add(createProfileTab(), new Span("Profile tab content"));
        tabSheet.add(createNotificationsTab(), new Span("Notifications tab content"));
        tabSheet.add(createSettingsTab(), new Span("Settings tab content"));
    }
    // theme-only:lumo
    @Subscribe("iconControlCheckbox")
    protected void onIconControlCheckboxValueChane(ComponentValueChangeEvent<JmixCheckbox, Boolean> event) {
        if (event.getValue()) {
            tabSheet.getChildren().forEach(tab -> ((Tab) tab).addThemeVariants(TabVariant.LUMO_ICON_ON_TOP));
        } else {
            tabSheet.getChildren().forEach(tab -> tab.getElement().getThemeList().clear());
        }
    }
    // theme-only:lumo:end

    protected Tab createProfileTab() {
        Tab tab = uiComponents.create(Tab.class);
        tab.add(VaadinIcon.USER.create(), new Text("Profile"));

        return tab;
    }

    protected Tab createNotificationsTab() {
        Tab tab = uiComponents.create(Tab.class);
        tab.add(VaadinIcon.BELL.create(), new Text("Notifications"));

        return tab;
    }

    protected Tab createSettingsTab() {
        Tab tab = uiComponents.create(Tab.class);
        tab.add(VaadinIcon.COG.create(), new Text("Settings"));

        return tab;
    }
}
