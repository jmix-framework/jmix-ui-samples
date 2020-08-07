package io.jmix.sampler.screen.entity.order;

import io.jmix.sampler.entity.Order;
import io.jmix.sampler.entity.OrderItem;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.EditedEntityContainer;
import io.jmix.ui.screen.LoadDataBeforeShow;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.Target;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@UiController("sampler_Order.edit")
@UiDescriptor("order-edit.xml")
@EditedEntityContainer("orderDc")
@LoadDataBeforeShow
public class OrderEdit extends StandardEditor<Order> {
    @Autowired
    protected CollectionContainer<OrderItem> itemsDc;

    @Subscribe(id = "itemsDc", target = Target.DATA_CONTAINER)
    protected void onItemsDcCollectionChange(CollectionContainer.CollectionChangeEvent<OrderItem> event) {
        calculateAmount();
    }

    @Subscribe(id = "itemsDc", target = Target.DATA_CONTAINER)
    protected void onItemsDcItemPropertyChange(InstanceContainer.ItemPropertyChangeEvent<OrderItem> event) {
        calculateAmount();
    }

    protected void calculateAmount() {
        BigDecimal amount = BigDecimal.ZERO;
        for (OrderItem invoice : itemsDc.getItems()) {
            amount = amount.add(invoice.getProduct().getPrice().multiply(invoice.getQuantity()));
        }
        getEditedEntity().setAmount(amount);
    }
}