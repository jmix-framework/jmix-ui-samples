package io.jmix.sampler.screen.entity.orderitem;

import io.jmix.sampler.entity.OrderItem;
import io.jmix.ui.screen.LookupComponent;
import io.jmix.ui.screen.StandardLookup;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

@UiController("sampler_OrderItem.browse")
@UiDescriptor("order-item-browse.xml")
@LookupComponent("orderItemsTable")
public class OrderItemBrowse extends StandardLookup<OrderItem> {
}
