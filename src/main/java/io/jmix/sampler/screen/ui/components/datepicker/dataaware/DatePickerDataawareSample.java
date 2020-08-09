package io.jmix.sampler.screen.ui.components.datepicker.dataaware;

import io.jmix.core.Metadata;
import io.jmix.sampler.entity.Order;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@UiController("datepicker-dataaware")
@UiDescriptor("datepicker-dataaware.xml")
public class DatePickerDataawareSample extends ScreenFragment {

    @Autowired
    protected InstanceContainer<Order> ordersDc;
    @Autowired
    protected Metadata metadata;

    @Subscribe
    protected void onInit(InitEvent event) {
        // InstanceContainer initialization. It is usually done automatically if the screen is
        // inherited from StandardEditor and is used as an entity editor.
        Order order = metadata.create(Order.class);
        order.setDate(new Date());
        ordersDc.setItem(order);
    }
}
