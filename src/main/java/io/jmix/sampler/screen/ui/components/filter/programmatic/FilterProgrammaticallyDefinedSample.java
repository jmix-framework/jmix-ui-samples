package io.jmix.sampler.screen.ui.components.filter.programmatic;

import io.jmix.core.Messages;
import io.jmix.core.querycondition.PropertyConditionUtils;
import io.jmix.sampler.entity.Customer;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.Filter;
import io.jmix.ui.component.PropertyFilter;
import io.jmix.ui.component.filter.configuration.DesignTimeConfiguration;
import io.jmix.ui.component.propertyfilter.PropertyFilterSupport;
import io.jmix.ui.component.propertyfilter.SingleFilterSupport;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("filter-programmatically-defined")
@UiDescriptor("filter-programmatically-defined.xml")
public class FilterProgrammaticallyDefinedSample extends ScreenFragment {

    @Autowired
    protected UiComponents uiComponents;
    @Autowired
    protected PropertyFilterSupport propertyFilterSupport;
    @Autowired
    protected SingleFilterSupport singleFilterSupport;
    @Autowired
    protected Messages messages;

    @Autowired
    protected CollectionLoader<Customer> customersDl;

    @Subscribe
    protected void onAfterInit(AfterInitEvent event) {
        Filter filter = uiComponents.create(Filter.NAME);
        filter.setId("filter");
        getFragment().add(filter, 0);
        filter.setDataLoader(customersDl);

        filter.loadConfigurationsAndApplyDefault();

        DesignTimeConfiguration javaConfiguration = filter.addConfiguration("javaConfiguration",
                messages.getMessage(FilterProgrammaticallyDefinedSample.class, "javaConfiguration.name"));

        PropertyFilter<Integer> agePropertyFilter = uiComponents.create(PropertyFilter.NAME);
        agePropertyFilter.setConditionModificationDelegated(true);
        agePropertyFilter.setDataLoader(customersDl);
        agePropertyFilter.setProperty("age");
        agePropertyFilter.setOperation(PropertyFilter.Operation.LESS_OR_EQUAL);
        agePropertyFilter.setOperationEditable(true);
        agePropertyFilter.setCaption(propertyFilterSupport.getPropertyFilterCaption(
                customersDl.getContainer().getEntityMetaClass(),
                agePropertyFilter.getProperty()));
        agePropertyFilter.setParameterName(PropertyConditionUtils.generateParameterName(
                agePropertyFilter.getProperty()));
        agePropertyFilter.setValueComponent(singleFilterSupport.generateValueComponent(
                customersDl.getContainer().getEntityMetaClass(),
                agePropertyFilter.getProperty(),
                agePropertyFilter.getOperation()
        ));

        javaConfiguration.getRootLogicalFilterComponent().add(agePropertyFilter);
        filter.setCurrentConfiguration(javaConfiguration);
    }
}
