package io.jmix.uisamples.view.flowui.components.genericfilter.builder.jpql;

import io.jmix.flowui.component.genericfilter.GenericFilter;
import io.jmix.flowui.component.jpqlfilter.JpqlFilter;
import io.jmix.flowui.view.*;
import io.jmix.uisamples.entity.Customer;

import java.math.BigDecimal;

@ViewController("generic-filter-builder-jpql")
@ViewDescriptor("generic-filter-builder-jpql.xml")
public class GenericFilterBuilderJpqlSample extends StandardView {

    @ViewComponent
    protected GenericFilter genericFilter;

    @Subscribe
    protected void onInit(InitEvent event) {
        var cb = genericFilter.filterComponentBuilder();

        // Void JpqlFilter -- a checkbox that toggles a fixed WHERE clause.
        // defaultValue(true) makes the condition active when the view opens.
        JpqlFilter<Boolean> highValue = cb.jpqlFilter()
                .where("{E}.amount >= 30000")
                .label("High-value (>= 30000)")
                .defaultValue(true)
                .build();

        // Typed JpqlFilter -- a user-supplied value is bound to a named query parameter.
        JpqlFilter<BigDecimal> minAmount = cb.jpqlFilter(BigDecimal.class)
                .where("{E}.amount >= :minAmount")
                .parameterName("minAmount")
                .label("Minimum amount")
                .build();

        // Explicit JOIN -- join() adds a fragment to the FROM clause.
        JpqlFilter<String> byCustomerName = cb.jpqlFilter(String.class)
                .join("join {E}.customer c")
                .where("lower(c.name) like lower(concat('%', :customerName, '%'))")
                .parameterName("customerName")
                .label("Customer name (JOIN)")
                .build();

        // IN expression -- hasInExpression(true) binds a collection of values.
        JpqlFilter<Customer> customerIn = cb.jpqlFilter(Customer.class)
                .where("{E}.customer in :customers")
                .parameterName("customers")
                .hasInExpression(true)
                .label("Customer in")
                .build();

        genericFilter.filterConfigurationBuilder()
                .id("jpqlConditions")
                .name("JPQL conditions (built in Java)")
                .addAll(highValue, minAmount, byCustomerName, customerIn)
                .makeCurrent()
                .buildAndRegister();
    }
}
