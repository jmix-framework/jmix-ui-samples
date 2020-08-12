package io.jmix.sampler.screen.ui.components.entitypicker.metaclass;

import io.jmix.core.MetadataTools;
import io.jmix.sampler.entity.Customer;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("entitypicker-metaclass")
@UiDescriptor("entitypicker-metaclass.xml")
public class EntityPickerMetaClassSample extends ScreenFragment {

    @Autowired
    protected MetadataTools metadataTools;
    @Autowired
    protected Notifications notifications;

    @Subscribe("entityPicker")
    protected void onEntityPickerValueChange(HasValue.ValueChangeEvent<Customer> event) {
        String str = event.getValue() == null ? "null" : metadataTools.getInstanceName(event.getValue());
        notifications.create()
                .withCaption("value = " + str)
                .show();
    }
}
