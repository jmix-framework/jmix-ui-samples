package io.jmix.uisamples.view.flowui.components.genericfilter.programmatic;

import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.genericfilter.GenericFilter;
import io.jmix.flowui.component.propertyfilter.PropertyFilter;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.view.*;
import io.jmix.uisamples.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;

@ViewController("generic-filter-programmatically-defined")
@ViewDescriptor("generic-filter-programmatically-defined.xml")
public class GenericFilterProgrammaticallyDefinedSample extends StandardView {

    @ViewComponent
    protected CollectionLoader<Customer> customersDl;

    @Autowired
    protected UiComponents uiComponents;

    @Subscribe
    protected void onInit(InitEvent event) {
        GenericFilter genericFilter = uiComponents.create(GenericFilter.class);
        genericFilter.setId("genericFilter");
        genericFilter.setDataLoader(customersDl);
        getContent().addComponentAsFirst(genericFilter);

        genericFilter.loadConfigurationsAndApplyDefault();

        // Build a condition and register a configuration with the fluent builder API.
        // The builder performs the wiring (parameter name, value component, condition
        // delegation) that otherwise has to be done by hand.
        PropertyFilter<Integer> ageFilter = genericFilter.filterComponentBuilder()
                .<Integer>propertyFilter()
                .property("age")
                .operation(PropertyFilter.Operation.LESS_OR_EQUAL)
                .operationEditable(true)
                .build();

        genericFilter.filterConfigurationBuilder()
                .id("javaConfiguration")
                .name("Programmatically defined configuration")
                .add(ageFilter)
                .makeCurrent()
                .buildAndRegister();
    }
}
