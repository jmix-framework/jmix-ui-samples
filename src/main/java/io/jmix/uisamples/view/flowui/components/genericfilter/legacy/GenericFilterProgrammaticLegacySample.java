package io.jmix.uisamples.view.flowui.components.genericfilter.legacy;

import io.jmix.core.querycondition.PropertyConditionUtils;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.genericfilter.GenericFilter;
import io.jmix.flowui.component.genericfilter.configuration.DesignTimeConfiguration;
import io.jmix.flowui.component.propertyfilter.PropertyFilter;
import io.jmix.flowui.component.propertyfilter.SingleFilterSupport;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.view.*;
import io.jmix.uisamples.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;

// The pre-builder way of configuring a GenericFilter in Java: every component is created and wired by hand.
// Compare with "GenericFilter builder: overview", which does the same with the builder API.
@ViewController("generic-filter-programmatic-legacy")
@ViewDescriptor("generic-filter-programmatic-legacy.xml")
public class GenericFilterProgrammaticLegacySample extends StandardView {

    @ViewComponent
    protected CollectionLoader<Customer> customersDl;

    @Autowired
    protected UiComponents uiComponents;
    @Autowired
    protected SingleFilterSupport singleFilterSupport;

    @Subscribe
    protected void onInit(InitEvent event) {
        initFilter();
    }

    protected void initFilter() {
        GenericFilter genericFilter = uiComponents.create(GenericFilter.class);
        genericFilter.setId("genericFilter");
        getContent().addComponentAsFirst(genericFilter);
        genericFilter.setDataLoader(customersDl);

        genericFilter.loadConfigurationsAndApplyDefault();

        initConfiguration(genericFilter);
    }

    protected void initConfiguration(GenericFilter genericFilter) {
        DesignTimeConfiguration javaConfiguration = genericFilter.addConfiguration("javaConfiguration",
                "Programmatically defined configuration");

        javaConfiguration.getRootLogicalFilterComponent().add(createAgePropertyFilter());
        genericFilter.setCurrentConfiguration(javaConfiguration);
    }

    protected PropertyFilter<Integer> createAgePropertyFilter() {
        PropertyFilter<Integer> agePropertyFilter = uiComponents.create(PropertyFilter.class);
        agePropertyFilter.setConditionModificationDelegated(true);
        agePropertyFilter.setDataLoader(customersDl);
        agePropertyFilter.setProperty("age");
        agePropertyFilter.setOperation(PropertyFilter.Operation.LESS_OR_EQUAL);
        agePropertyFilter.setOperationEditable(true);

        agePropertyFilter.setParameterName(PropertyConditionUtils.generateParameterName(
                agePropertyFilter.getProperty()));
        agePropertyFilter.setValueComponent(singleFilterSupport.generateValueComponent(
                customersDl.getContainer().getEntityMetaClass(),
                agePropertyFilter.getProperty(),
                agePropertyFilter.getOperation()
        ));

        return agePropertyFilter;
    }
}
