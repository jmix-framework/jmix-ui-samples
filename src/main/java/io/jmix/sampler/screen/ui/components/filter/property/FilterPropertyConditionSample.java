package io.jmix.sampler.screen.ui.components.filter.property;

import io.jmix.core.Messages;
import io.jmix.core.querycondition.PropertyConditionUtils;
import io.jmix.sampler.entity.Customer;
import io.jmix.sampler.entity.CustomerGrade;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.Filter;
import io.jmix.ui.component.PropertyFilter;
import io.jmix.ui.component.filter.configuration.DesignTimeConfiguration;
import io.jmix.ui.component.propertyfilter.PropertyFilterSupport;
import io.jmix.ui.component.propertyfilter.SingleFilterSupport;
import io.jmix.ui.model.DataLoader;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("filter-property-condition")
@UiDescriptor("filter-property-condition.xml")
public class FilterPropertyConditionSample extends ScreenFragment {

    @Autowired
    protected UiComponents uiComponents;
    @Autowired
    protected PropertyFilterSupport propertyFilterSupport;
    @Autowired
    protected SingleFilterSupport singleFilterSupport;
    @Autowired
    protected Filter filter;

    @Autowired
    protected Messages messages;

    @Subscribe
    protected void onInit(InitEvent event) {
        DesignTimeConfiguration javaConfiguration = filter.addConfiguration("javaConfiguration",
                messages.getMessage(FilterPropertyConditionSample.class, "javaConfiguration.name"));
        DataLoader dataLoader = filter.getDataLoader();

        PropertyFilter<Customer> customerPropertyFilter = uiComponents.create(PropertyFilter.NAME);
        customerPropertyFilter.setDataLoader(dataLoader);
        customerPropertyFilter.setProperty("customer");
        customerPropertyFilter.setOperation(PropertyFilter.Operation.EQUAL);
        customerPropertyFilter.setOperationEditable(true);
        customerPropertyFilter.setCaption(propertyFilterSupport.getPropertyFilterCaption(
                dataLoader.getContainer().getEntityMetaClass(),
                customerPropertyFilter.getProperty()));
        customerPropertyFilter.setParameterName(PropertyConditionUtils.generateParameterName(
                customerPropertyFilter.getProperty()));
        customerPropertyFilter.setValueComponent(singleFilterSupport.generateValueComponent(
                dataLoader.getContainer().getEntityMetaClass(),
                customerPropertyFilter.getProperty(),
                customerPropertyFilter.getOperation()
        ));
        javaConfiguration.getRootLogicalFilterComponent().add(customerPropertyFilter);

        PropertyFilter<CustomerGrade> gradePropertyFilter = uiComponents.create(PropertyFilter.NAME);
        gradePropertyFilter.setDataLoader(dataLoader);
        gradePropertyFilter.setProperty("customer.grade");
        gradePropertyFilter.setOperation(PropertyFilter.Operation.EQUAL);
        gradePropertyFilter.setOperationEditable(true);
        gradePropertyFilter.setCaption(propertyFilterSupport.getPropertyFilterCaption(
                dataLoader.getContainer().getEntityMetaClass(),
                gradePropertyFilter.getProperty()));
        gradePropertyFilter.setParameterName(PropertyConditionUtils.generateParameterName(
                gradePropertyFilter.getProperty()));
        gradePropertyFilter.setValueComponent(singleFilterSupport.generateValueComponent(
                dataLoader.getContainer().getEntityMetaClass(),
                gradePropertyFilter.getProperty(),
                gradePropertyFilter.getOperation()
        ));
        javaConfiguration.getRootLogicalFilterComponent().add(gradePropertyFilter);
    }
}
