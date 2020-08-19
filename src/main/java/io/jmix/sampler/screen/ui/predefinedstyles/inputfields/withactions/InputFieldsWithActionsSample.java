package io.jmix.sampler.screen.ui.predefinedstyles.inputfields.withactions;

import io.jmix.core.Metadata;
import io.jmix.sampler.entity.Customer;
import io.jmix.sampler.entity.Order;
import io.jmix.ui.model.CollectionChangeType;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.Target;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@UiController("inputfields-with-actions")
@UiDescriptor("inputfields-with-actions.xml")
public class InputFieldsWithActionsSample extends ScreenFragment {
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

    @Subscribe(id = "customersDc", target = Target.DATA_CONTAINER)
    protected void onCustomersDcCollectionChange(CollectionContainer.CollectionChangeEvent<Customer> event) {
        if (CollectionChangeType.REFRESH.equals(event.getChangeType())) {
            List<Customer> items = event.getSource().getItems();
            orderDc.getItem().setCustomer(items.isEmpty() ? null : items.get(0));
        }
    }
}
