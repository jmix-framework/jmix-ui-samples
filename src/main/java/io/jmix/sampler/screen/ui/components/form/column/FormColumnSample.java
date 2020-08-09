package io.jmix.sampler.screen.ui.components.form.column;

import io.jmix.core.Metadata;
import io.jmix.sampler.entity.Customer;
import io.jmix.sampler.entity.CustomerGrade;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("form-column")
@UiDescriptor("form-column.xml")
public class FormColumnSample extends ScreenFragment {

    @Autowired
    protected InstanceContainer<Customer> customerDc;
    @Autowired
    protected Metadata metadata;

    @Subscribe
    protected void onInit(InitEvent event) {
        // InstanceContainer initialization. It is usually done automatically if the screen is
        // inherited from StandardEditor and is used as an entity editor.
        Customer customer = metadata.create(Customer.class);
        customer.setName("John");
        customer.setLastName("Doe");
        customer.setAge(40);
        customer.setActive(true);
        customer.setGrade(CustomerGrade.STANDARD);
        customerDc.setItem(customer);
    }
}