package io.jmix.uisamples.view.flowui.components.genericfilter.builder.refresh;

import io.jmix.flowui.component.genericfilter.GenericFilter;
import io.jmix.flowui.component.genericfilter.configuration.DesignTimeConfiguration;
import io.jmix.flowui.component.propertyfilter.PropertyFilter;
import io.jmix.flowui.view.*;

@ViewController("generic-filter-builder-refresh")
@ViewDescriptor("generic-filter-builder-refresh.xml")
public class GenericFilterBuilderRefreshSample extends StandardView {

    @ViewComponent
    protected GenericFilter genericFilter;

    @Subscribe
    protected void onInit(InitEvent event) {
        // Register an empty configuration and activate it.
        DesignTimeConfiguration configuration = genericFilter.filterConfigurationBuilder()
                .id("dynamic")
                .name("Dynamic configuration")
                .makeCurrent()
                .buildAndRegister();

        // Build a component and add it to the configuration directly, bypassing the builder.
        PropertyFilter<Integer> ageFilter = genericFilter.filterComponentBuilder()
                .<Integer>propertyFilter()
                .property("age")
                .operation(PropertyFilter.Operation.GREATER_OR_EQUAL)
                .build();

        configuration.getRootLogicalFilterComponent().add(ageFilter);
        // Re-render the current configuration to reflect the change.
        genericFilter.refreshCurrentConfiguration();
    }
}
