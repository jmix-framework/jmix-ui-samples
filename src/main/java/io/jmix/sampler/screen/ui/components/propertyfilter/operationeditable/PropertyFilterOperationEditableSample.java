package io.jmix.sampler.screen.ui.components.propertyfilter.operationeditable;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.PropertyFilter;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("propertyfilter-operationeditable")
@UiDescriptor("propertyfilter-operationeditable.xml")
public class PropertyFilterOperationEditableSample extends ScreenFragment {

    @Autowired
    private Notifications notifications;

    @Subscribe("propertyFilter")
    public void onPropertyFilterOperationChange(PropertyFilter.OperationChangeEvent event) {
        notifications.create(Notifications.NotificationType.TRAY)
                .withCaption("Before: " + event.getPreviousOperation().name() + "\n"
                        + "After: " + event.getNewOperation().name())
                .show();
    }
}
