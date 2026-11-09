package io.jmix.uisamples.view.flowui.components.genericfilter.builder.group;

import io.jmix.flowui.component.genericfilter.GenericFilter;
import io.jmix.flowui.component.logicalfilter.GroupFilter;
import io.jmix.flowui.component.logicalfilter.LogicalFilterComponent;
import io.jmix.flowui.component.propertyfilter.PropertyFilter;
import io.jmix.flowui.view.*;

@ViewController("generic-filter-builder-group")
@ViewDescriptor("generic-filter-builder-group.xml")
public class GenericFilterBuilderGroupSample extends StandardView {

    @ViewComponent
    protected GenericFilter genericFilter;

    @Subscribe
    protected void onInit(InitEvent event) {
        var cb = genericFilter.filterComponentBuilder();

        PropertyFilter<String> byName = cb.<String>propertyFilter()
                .property("name")
                .operation(PropertyFilter.Operation.CONTAINS)
                .label("First name")
                .build();

        PropertyFilter<String> byLastName = cb.<String>propertyFilter()
                .property("lastName")
                .operation(PropertyFilter.Operation.CONTAINS)
                .label("Last name")
                .build();

        // An OR group: a customer matches if either the first or the last name contains the value.
        GroupFilter orGroup = cb.groupFilter()
                .operation(LogicalFilterComponent.Operation.OR)
                .addAll(byName, byLastName)
                .build();

        genericFilter.filterConfigurationBuilder()
                .id("nameGroup")
                .name("Name group (OR)")
                .add(orGroup)
                .makeCurrent()
                .buildAndRegister();
    }
}
