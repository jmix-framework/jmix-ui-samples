package io.jmix.sampler.screen.ui.components.tokenlist.lookup;

import io.jmix.core.Metadata;
import io.jmix.sampler.entity.Order;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.LoadDataBeforeShow;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("tokenlist-lookup")
@UiDescriptor("tokenlist-lookup.xml")
@LoadDataBeforeShow
public class TokenListLookupSample extends ScreenFragment {

    @Autowired
    protected InstanceContainer<Order> orderDc;
    @Autowired
    protected Metadata metadata;

    @Subscribe
    protected void onInit(InitEvent event) {
        // InstanceContainer initialization. It is usually done automatically if the screen is
        // inherited from StandardEditor and is used as an entity editor.
        Order order = metadata.create(Order.class);
        orderDc.setItem(order);
    }
}