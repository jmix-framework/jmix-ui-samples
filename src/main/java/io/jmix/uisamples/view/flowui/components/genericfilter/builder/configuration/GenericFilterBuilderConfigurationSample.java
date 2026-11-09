package io.jmix.uisamples.view.flowui.components.genericfilter.builder.configuration;

import io.jmix.flowui.component.genericfilter.GenericFilter;
import io.jmix.flowui.component.logicalfilter.LogicalFilterComponent;
import io.jmix.flowui.component.propertyfilter.PropertyFilter;
import io.jmix.flowui.view.*;
import io.jmix.uisamples.entity.CustomerGrade;

@ViewController("generic-filter-builder-configuration")
@ViewDescriptor("generic-filter-builder-configuration.xml")
public class GenericFilterBuilderConfigurationSample extends StandardView {

    @ViewComponent
    protected GenericFilter genericFilter;

    @Subscribe
    protected void onInit(InitEvent event) {
        // First configuration -- active on open.
        // age: default value and operation-label visibility are set on the builder itself.
        PropertyFilter<Integer> age = genericFilter.filterComponentBuilder()
                .<Integer>propertyFilter()
                .property("age")
                .operation(PropertyFilter.Operation.GREATER_OR_EQUAL)
                .operationTextVisible(false)   // hide the operation label
                .defaultValue(18)              // builder-level default value
                .build();
        PropertyFilter<Boolean> active = genericFilter.filterComponentBuilder()
                .<Boolean>propertyFilter()
                .property("active")
                .operation(PropertyFilter.Operation.EQUAL)
                .build();

        genericFilter.filterConfigurationBuilder()
                .id("activeAdults")
                .name("Active adults")
                .add(age)                      // default comes from the builder
                .add(active, true)             // typed default value via the configuration
                .makeCurrent()
                .buildAndRegister();

        // Second configuration -- root OR operation: premium grade OR senior age.
        PropertyFilter<CustomerGrade> grade = genericFilter.filterComponentBuilder()
                .<CustomerGrade>propertyFilter()
                .property("grade")
                .operation(PropertyFilter.Operation.EQUAL)
                .build();
        PropertyFilter<Integer> seniorAge = genericFilter.filterComponentBuilder()
                .<Integer>propertyFilter()
                .property("age")
                .operation(PropertyFilter.Operation.GREATER_OR_EQUAL)
                .build();

        genericFilter.filterConfigurationBuilder()
                .id("premiumOrSenior")
                .name("Premium or senior")
                .operation(LogicalFilterComponent.Operation.OR)   // root logical operation
                .add(grade, CustomerGrade.PREMIUM)
                .add(seniorAge, 65)
                .buildAndRegister();
    }
}
