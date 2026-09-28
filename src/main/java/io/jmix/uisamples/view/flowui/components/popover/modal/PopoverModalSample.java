package io.jmix.uisamples.view.flowui.components.popover.modal;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.popover.Popover;
import io.jmix.flowui.component.propertyfilter.PropertyFilter;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.view.*;
import io.jmix.uisamples.entity.Customer;

@ViewController("popover-modal")
@ViewDescriptor("popover-modal.xml")
public class PopoverModalSample extends StandardView {

    @ViewComponent
    private Popover filterPopover;
    @ViewComponent
    private PropertyFilter<String> lastNameFilter;
    @ViewComponent
    private PropertyFilter<Integer> ageFilter;
    @ViewComponent
    private CollectionLoader<Customer> customersDl;

    @Subscribe(id = "applyButton", subject = "clickListener")
    public void onApplyButtonClick(ClickEvent<JmixButton> event) {
        customersDl.load();
        filterPopover.close();
    }

    @Subscribe(id = "clearButton", subject = "clickListener")
    public void onClearButtonClick(ClickEvent<JmixButton> event) {
        lastNameFilter.clear();
        ageFilter.clear();
        customersDl.load();
        filterPopover.close();
    }
}
