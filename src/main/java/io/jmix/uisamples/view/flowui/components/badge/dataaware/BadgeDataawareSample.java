package io.jmix.uisamples.view.flowui.components.badge.dataaware;

import io.jmix.core.Metadata;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.view.*;
import io.jmix.uisamples.entity.Customer;
import io.jmix.uisamples.entity.CustomerGrade;
import org.springframework.beans.factory.annotation.Autowired;

@ViewController("badge-dataaware")
@ViewDescriptor("badge-dataaware.xml")
public class BadgeDataawareSample extends StandardView {

    @ViewComponent
    protected InstanceContainer<Customer> customerDc;

    @Autowired
    protected Metadata metadata;

    @Subscribe
    protected void onInit(InitEvent event) {
        Customer customer = metadata.create(Customer.class);
        customer.setName("Jane");
        customer.setLastName("Smith");
        customer.setGrade(CustomerGrade.PREMIUM);
        customerDc.setItem(customer);
    }
}
