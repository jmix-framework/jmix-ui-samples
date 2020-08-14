package io.jmix.sampler.screen.ui.components.timefield.timemode;

import io.jmix.core.Metadata;
import io.jmix.core.TimeSource;
import io.jmix.sampler.entity.Order;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("timefield-time-mode")
@UiDescriptor("timefield-time-mode.xml")
public class TimeFieldTimeModeSample extends ScreenFragment {
    @Autowired
    protected InstanceContainer<Order> orderDc;
    @Autowired
    protected Metadata metadata;
    @Autowired
    protected TimeSource timeSource;

    @Subscribe
    protected void onInit(InitEvent event) {
        // InstanceContainer initialization. It is usually done automatically if the screen is
        // inherited from StandardEditor and is used as an entity editor.
        Order order = metadata.create(Order.class);
        order.setCreateTs(timeSource.currentTimestamp());
        orderDc.setItem(order);
    }
}
