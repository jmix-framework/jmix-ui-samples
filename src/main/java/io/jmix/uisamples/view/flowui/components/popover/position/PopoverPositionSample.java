package io.jmix.uisamples.view.flowui.components.popover.position;

import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.popover.Popover;
import com.vaadin.flow.component.popover.PopoverPosition;
import com.vaadin.flow.component.popover.PopoverVariant;
import io.jmix.flowui.component.checkbox.JmixCheckbox;
import io.jmix.flowui.component.select.JmixSelect;
import io.jmix.flowui.view.*;

@ViewController("popover-position")
@ViewDescriptor("popover-position.xml")
public class PopoverPositionSample extends StandardView {

    @ViewComponent
    private Popover popover;
    @ViewComponent
    private JmixSelect<PopoverPosition> positionSelect;

    @Subscribe
    public void onInit(InitEvent event) {
        positionSelect.setItems(PopoverPosition.values());
        positionSelect.setItemLabelGenerator(Enum::name);
        positionSelect.setValue(popover.getPosition());
    }

    @Subscribe("positionSelect")
    public void onPositionSelectComponentValueChange(
            ComponentValueChangeEvent<JmixSelect<PopoverPosition>, PopoverPosition> event) {
        popover.setPosition(event.getValue());
    }

    @Subscribe("arrowCheckbox")
    public void onArrowCheckboxComponentValueChange(ComponentValueChangeEvent<JmixCheckbox, Boolean> event) {
        updateThemeVariant(PopoverVariant.ARROW, event.getValue());
    }

    @Subscribe("noPaddingCheckbox")
    public void onNoPaddingCheckboxComponentValueChange(ComponentValueChangeEvent<JmixCheckbox, Boolean> event) {
        updateThemeVariant(PopoverVariant.NO_PADDING, event.getValue());
    }

    private void updateThemeVariant(PopoverVariant variant, boolean enabled) {
        if (enabled) {
            popover.addThemeVariants(variant);
        } else {
            popover.removeThemeVariants(variant);
        }
    }
}
