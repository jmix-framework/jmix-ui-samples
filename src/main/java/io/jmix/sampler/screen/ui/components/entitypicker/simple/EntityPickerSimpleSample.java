package io.jmix.sampler.screen.ui.components.entitypicker.simple;

import io.jmix.core.JmixEntity;
import io.jmix.core.Metadata;
import io.jmix.core.MetadataTools;
import io.jmix.sampler.entity.Order;
import io.jmix.ui.Notifications;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.Target;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("entitypicker-simple")
@UiDescriptor("entitypicker-simple.xml")
public class EntityPickerSimpleSample extends ScreenFragment {

    @Autowired
    protected InstanceContainer<Order> orderDc;
    @Autowired
    protected Metadata metadata;
    @Autowired
    protected MetadataTools metadataTools;
    @Autowired
    protected Notifications notifications;

    @Subscribe
    protected void onInit(InitEvent event) {
        // InstanceContainer initialization. It is usually done automatically if the screen is
        // inherited from StandardEditor and is used as an entity editor.
        Order order = metadata.create(Order.class);
        orderDc.setItem(order);
    }

    @Subscribe(id = "orderDc", target = Target.DATA_CONTAINER)
    protected void onOrderDcItemPropertyChange(InstanceContainer.ItemPropertyChangeEvent<Order> event) {
        Object str = event.getValue() instanceof JmixEntity
                ? metadataTools.getInstanceName((JmixEntity) event.getValue())
                : event.getValue();

        notifications.create()
                .withCaption(event.getProperty() + " = " + str)
                .show();
    }
}
