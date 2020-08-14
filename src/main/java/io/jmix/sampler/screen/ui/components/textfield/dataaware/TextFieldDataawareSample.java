package io.jmix.sampler.screen.ui.components.textfield.dataaware;

import io.jmix.core.Metadata;
import io.jmix.sampler.entity.Customer;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("textfield-dataaware")
@UiDescriptor("textfield-dataaware.xml")
public class TextFieldDataawareSample extends ScreenFragment {

    @Autowired
    protected InstanceContainer<Customer> customersDc;
    @Autowired
    protected Metadata metadata;

    @Subscribe
    protected void onInit(InitEvent event) {
        // InstanceContainer initialization. It is usually done automatically if the screen is
        // inherited from StandardEditor and is used as an entity editor.
        Customer customer = metadata.create(Customer.class);
        customer.setName("John");
        customersDc.setItem(customer);
    }
}
