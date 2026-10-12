package io.jmix.uisamples.view.flowui.components.popover.triggers;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.popover.Popover;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;

@ViewController("popover-triggers")
@ViewDescriptor("popover-triggers.xml")
public class PopoverTriggersSample extends StandardView {

    @ViewComponent
    private Popover promoCodePopover;

    @Subscribe(id = "promoCodeHintButton", subject = "clickListener")
    public void onPromoCodeHintButtonClick(ClickEvent<JmixButton> event) {
        promoCodePopover.open();
    }
}
