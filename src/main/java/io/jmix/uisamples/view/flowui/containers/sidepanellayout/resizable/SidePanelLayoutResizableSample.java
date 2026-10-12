package io.jmix.uisamples.view.flowui.containers.sidepanellayout.resizable;

import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.ClickEvent;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.checkbox.JmixCheckbox;
import io.jmix.flowui.component.select.JmixSelect;
import io.jmix.flowui.component.sidepanellayout.SidePanelLayout;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.kit.component.sidepanellayout.SidePanelAfterResizeEvent;
import io.jmix.flowui.kit.component.sidepanellayout.SidePanelPosition;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@ViewController("side-panel-layout-resizable")
@ViewDescriptor("side-panel-layout-resizable.xml")
public class SidePanelLayoutResizableSample extends StandardView {

    private static final String DEFAULT_HORIZONTAL_SIZE = "25em";
    private static final String DEFAULT_VERTICAL_SIZE = "15em";

    @Autowired
    private Notifications notifications;
    @ViewComponent
    private MessageBundle messageBundle;

    @ViewComponent
    private JmixSelect<SidePanelPosition> positionSelect;
    @ViewComponent
    private SidePanelLayout sidePanelLayout;

    @Subscribe
    public void onInit(InitEvent event) {
        positionSelect.setItems(SidePanelPosition.values());
        positionSelect.setValue(sidePanelLayout.getSidePanelPosition());
        positionSelect.addValueChangeListener(e -> sidePanelLayout.setSidePanelPosition(e.getValue()));
        positionSelect.setItemLabelGenerator(Enum::name);

        sidePanelLayout.openSidePanel();
    }

    @Subscribe(id = "toggleSidePanelButton", subject = "clickListener")
    public void onToggleSidePanelButtonClick(ClickEvent<JmixButton> event) {
        sidePanelLayout.toggleSidePanel();
    }

    @Subscribe("smallResizerCheckbox")
    public void onSmallResizerCheckboxComponentValueChange(ComponentValueChangeEvent<JmixCheckbox, Boolean> event) {
        sidePanelLayout.setThemeName("resizer-small", event.getValue());
    }

    @Subscribe(id = "resetSizeButton", subject = "clickListener")
    public void onResetSizeButtonClick(ClickEvent<JmixButton> event) {
        sidePanelLayout.setSidePanelHorizontalSize(DEFAULT_HORIZONTAL_SIZE);
        sidePanelLayout.setSidePanelVerticalSize(DEFAULT_VERTICAL_SIZE);
    }

    @Subscribe(id = "sidePanelLayout", subject = "addSidePanelAfterResizeListener")
    public void onSidePanelLayoutAfterResize(SidePanelAfterResizeEvent event) {
        notifications.show(messageBundle.formatMessage("sidePanelResized.notification", event.getSize()));
    }
}
