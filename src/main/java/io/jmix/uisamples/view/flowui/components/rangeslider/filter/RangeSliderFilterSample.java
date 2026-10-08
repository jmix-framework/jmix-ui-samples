package io.jmix.uisamples.view.flowui.components.rangeslider.filter;

import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.slider.IntegerRangeSlider;
import com.vaadin.flow.component.slider.IntegerRangeSliderValue;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.view.*;
import io.jmix.uisamples.entity.Customer;

@ViewController("range-slider-filter")
@ViewDescriptor("range-slider-filter.xml")
public class RangeSliderFilterSample extends StandardView {

    // tag::filter[] sample-hide
    @ViewComponent
    protected IntegerRangeSlider ageRangeSlider;
    @ViewComponent
    protected CollectionLoader<Customer> customersDl;

    @Subscribe
    protected void onBeforeShow(BeforeShowEvent event) {
        loadCustomers(ageRangeSlider.getValue());
    }

    @Subscribe("ageRangeSlider")
    protected void onAgeRangeSliderValueChange(
            ComponentValueChangeEvent<IntegerRangeSlider, IntegerRangeSliderValue> event) {
        loadCustomers(event.getValue());
    }

    protected void loadCustomers(IntegerRangeSliderValue range) {
        customersDl.setParameter("minAge", range.start());
        customersDl.setParameter("maxAge", range.end());
        customersDl.load();
    }
    // end::filter[] sample-hide
}
