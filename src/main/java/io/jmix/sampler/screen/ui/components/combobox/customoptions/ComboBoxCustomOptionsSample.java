package io.jmix.sampler.screen.ui.components.combobox.customoptions;

import io.jmix.core.JmixEntity;
import io.jmix.core.Metadata;
import io.jmix.sampler.entity.Customer;
import io.jmix.sampler.entity.Order;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.ComboBox;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.Target;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@UiController("combobox-custom-options")
@UiDescriptor("combobox-custom-options.xml")
public class ComboBoxCustomOptionsSample extends ScreenFragment {

    @Autowired
    protected ComboBox<Integer> ageComboBox;
    @Autowired
    protected ComboBox<BigDecimal> amountComboBox;

    @Autowired
    protected InstanceContainer<Customer> customerDc;
    @Autowired
    protected InstanceContainer<Order> orderDc;

    @Autowired
    protected Metadata metadata;
    @Autowired
    protected Notifications notifications;

    @Subscribe
    protected void onInit(InitEvent event) {
        // InstanceContainer initialization. It is usually done automatically if the screen is
        // inherited from StandardEditor and is used as an entity editor.
        Order order = metadata.create(Order.class);
        orderDc.setItem(order);
        Customer customer = metadata.create(Customer.class);
        customerDc.setItem(customer);

        List<BigDecimal> list = new ArrayList<>();
        list.add(BigDecimal.valueOf(1000));
        list.add(BigDecimal.valueOf(2000));
        list.add(BigDecimal.valueOf(3000));
        list.add(BigDecimal.valueOf(4000));
        amountComboBox.setOptionsList(list);

        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("twenty", 20);
        map.put("thirty", 30);
        map.put("forty", 40);
        map.put("fifty", 50);
        ageComboBox.setOptionsMap(map);
    }

    @Subscribe(id = "orderDc", target = Target.DATA_CONTAINER)
    protected void onOrderDcItemPropertyChange(InstanceContainer.ItemPropertyChangeEvent<Order> event) {
        itemPropertyChanged(event);
    }

    @Subscribe(id = "customerDc", target = Target.DATA_CONTAINER)
    protected void onCustomerDcItemPropertyChange(InstanceContainer.ItemPropertyChangeEvent<Customer> event) {
        itemPropertyChanged(event);
    }

    protected void itemPropertyChanged(InstanceContainer.ItemPropertyChangeEvent<? extends JmixEntity> event) {
        String msg = event.getItem().getClass().getSimpleName() + "." + event.getProperty() + " = " + event.getValue();
        notifications.create()
                .withCaption(msg)
                .show();
    }
}
