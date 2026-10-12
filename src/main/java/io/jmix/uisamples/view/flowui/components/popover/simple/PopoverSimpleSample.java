package io.jmix.uisamples.view.flowui.components.popover.simple;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.popover.Popover;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;

@ViewController("popover-simple")
@ViewDescriptor("popover-simple.xml")
public class PopoverSimpleSample extends StandardView {

    @ViewComponent
    private Popover infoPopover;

    @Subscribe(id = "closeButton", subject = "clickListener")
    public void onCloseButtonClick(ClickEvent<JmixButton> event) {
        infoPopover.close();
    }
}
