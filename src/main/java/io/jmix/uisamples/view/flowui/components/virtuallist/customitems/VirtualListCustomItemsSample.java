package io.jmix.uisamples.view.flowui.components.virtuallist.customitems;

import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import io.jmix.core.MetadataTools;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.theme.StyleUtility;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Supply;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import io.jmix.uisamples.entity.Day;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

@ViewController("virtual-list-custom-items")
@ViewDescriptor("virtual-list-custom-items.xml")
public class VirtualListCustomItemsSample extends StandardView {

    @Autowired
    protected UiComponents uiComponents;
    @Autowired
    protected MetadataTools metadataTools;
    @Autowired
    protected Notifications notifications;

    @Supply(to = "virtualList", subject = "renderer")
    protected Renderer<Day> virtualListRenderer() {
        return new ComponentRenderer<>(this::createDayRenderer);
    }

    protected HorizontalLayout createDayRenderer(Day day) {
        HorizontalLayout layout = uiComponents.create(HorizontalLayout.class);
        layout.setPadding(false);

        String dayValue = metadataTools.format(day);
        H3 label = new H3(dayValue);

        JmixButton button = uiComponents.create(JmixButton.class);
        button.addThemeVariants(ButtonVariant.LUMO_ICON);
        button.addClassName(StyleUtility.Button.LINK_BUTTON);
        button.addClickListener(__ -> showNotification(dayValue));

        Icon icon = switch (day) {
            case MONDAY -> VaadinIcon.BRIEFCASE.create();
            case TUESDAY -> VaadinIcon.LINE_CHART.create();
            case WEDNESDAY -> VaadinIcon.TROPHY.create();
            case THURSDAY -> VaadinIcon.GROUP.create();
            case FRIDAY -> VaadinIcon.CASH.create();
            case SATURDAY -> VaadinIcon.GLASS.create();
            case SUNDAY -> VaadinIcon.BED.create();
        };
        button.setIcon(icon);

        layout.add(button, label);
        return layout;
    }

    protected void showNotification(String dayValue) {
        String message = String.format("You've clicked on %s!", StringUtils.capitalize(dayValue));
        notifications.show(message);
    }
}
