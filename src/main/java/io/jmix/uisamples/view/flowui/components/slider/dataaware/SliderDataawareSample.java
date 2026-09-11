package io.jmix.uisamples.view.flowui.components.slider.dataaware;

import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.html.Span;
import io.jmix.core.Metadata;
import io.jmix.flowui.component.slider.JmixIntegerSlider;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.view.*;
import io.jmix.uisamples.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;

@ViewController("slider-dataaware")
@ViewDescriptor("slider-dataaware.xml")
public class SliderDataawareSample extends StandardView {

    @ViewComponent
    protected InstanceContainer<Customer> customerDc;
    @ViewComponent
    protected Span spanValue;

    @Autowired
    protected Metadata metadata;

    @Subscribe
    protected void onInit(InitEvent event) {
        Customer customer = metadata.create(Customer.class);
        customer.setAge(26);
        customerDc.setItem(customer);

        spanValue.setText(String.valueOf(customer.getAge()));
    }

    @Subscribe("ageSlider")
    protected void onAgeSliderValueChange(ComponentValueChangeEvent<JmixIntegerSlider, Integer> event) {
        spanValue.setText(String.valueOf(customerDc.getItem().getAge()));
    }
}
