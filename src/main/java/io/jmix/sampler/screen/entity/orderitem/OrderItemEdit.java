package io.jmix.sampler.screen.entity.orderitem;

import io.jmix.sampler.entity.OrderItem;
import io.jmix.ui.screen.EditedEntityContainer;
import io.jmix.ui.screen.LoadDataBeforeShow;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

@UiController("sampler_OrderItem.edit")
@UiDescriptor("order-item-edit.xml")
@EditedEntityContainer("orderItemDc")
@LoadDataBeforeShow
public class OrderItemEdit extends StandardEditor<OrderItem> {
}