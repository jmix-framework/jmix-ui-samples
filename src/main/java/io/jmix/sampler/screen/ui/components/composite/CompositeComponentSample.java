package io.jmix.sampler.screen.ui.components.composite;

import io.jmix.core.Metadata;
import io.jmix.sampler.entity.OrderItem;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@UiController("composite-component")
@UiDescriptor("composite-component.xml")
public class CompositeComponentSample extends ScreenFragment {

    @Autowired
    protected Metadata metadata;
    @Autowired
    protected InstanceContainer<OrderItem> orderItemDc;

    @Subscribe
    protected void onInit(InitEvent event) {
        // InstanceContainer initialization. It is usually done automatically if the screen is
        // inherited from StandardEditor and is used as an entity editor.
        OrderItem orderItem = metadata.create(OrderItem.class);
        orderItem.setQuantity(BigDecimal.ZERO);
        orderItemDc.setItem(orderItem);
    }
}
