package io.jmix.sampler.screen.ui.components.form.simple;

import io.jmix.core.Metadata;
import io.jmix.core.MetadataTools;
import io.jmix.sampler.entity.Order;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.ContentMode;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("form-simple")
@UiDescriptor("form-simple.xml")
public class FormSimpleSample extends ScreenFragment {
    @Autowired
    protected InstanceContainer<Order> orderDc;
    @Autowired
    protected Notifications notifications;
    @Autowired
    protected MetadataTools metadataTools;
    @Autowired
    protected Metadata metadata;

    @Subscribe
    protected void onInit(InitEvent event) {
        // InstanceContainer initialization. It is usually done automatically if the screen is
        // inherited from StandardEditor and is used as an entity editor.
        Order order = metadata.create(Order.class);
        orderDc.setItem(order);
    }

    @Subscribe("showOrderBtn")
    protected void onShowOrderBtnClick(Button.ClickEvent event) {
        Order order = orderDc.getItem();
        String sb = "<strong>date:</strong> " + order.getDate() + "<br>" +
                "<strong>customer:</strong> " + (order.getCustomer() != null
                ? metadataTools.getInstanceName(order.getCustomer())
                : null) + "<br>" +
                "<strong>amount:</strong> " + order.getAmount() + "<br>" +
                "<strong>description:</strong> " + order.getDescription();

        notifications.create()
                .withCaption(sb)
                .withContentMode(ContentMode.HTML)
                .show();
    }
}