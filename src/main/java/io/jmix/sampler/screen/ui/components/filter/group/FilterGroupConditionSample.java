package io.jmix.sampler.screen.ui.components.filter.group;

import io.jmix.core.Messages;
import io.jmix.core.querycondition.PropertyConditionUtils;
import io.jmix.sampler.entity.CustomerGrade;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.Filter;
import io.jmix.ui.component.GroupFilter;
import io.jmix.ui.component.LogicalFilterComponent;
import io.jmix.ui.component.PropertyFilter;
import io.jmix.ui.component.filter.configuration.DesignTimeConfiguration;
import io.jmix.ui.component.groupfilter.LogicalFilterSupport;
import io.jmix.ui.component.propertyfilter.PropertyFilterSupport;
import io.jmix.ui.component.propertyfilter.SingleFilterSupport;
import io.jmix.ui.model.DataLoader;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("filter-group-condition")
@UiDescriptor("filter-group-condition.xml")
public class FilterGroupConditionSample extends ScreenFragment {

    @Autowired
    protected UiComponents uiComponents;
    @Autowired
    protected PropertyFilterSupport propertyFilterSupport;
    @Autowired
    protected SingleFilterSupport singleFilterSupport;
    @Autowired
    protected LogicalFilterSupport logicalFilterSupport;
    @Autowired
    protected Filter filter;

    @Autowired
    protected Messages messages;

    @Subscribe
    protected void onInit(InitEvent event) {
        DesignTimeConfiguration javaConfiguration = filter.addConfiguration("javaConfiguration",
                messages.getMessage(FilterGroupConditionSample.class, "javaConfiguration.name"));
        DataLoader dataLoader = filter.getDataLoader();

        GroupFilter groupFilter = uiComponents.create(GroupFilter.NAME);
        groupFilter.setConditionModificationDelegated(true);
        groupFilter.setDataLoader(dataLoader);
        groupFilter.setOperation(LogicalFilterComponent.Operation.OR);
        groupFilter.setCaption(logicalFilterSupport.getOperationCaption(groupFilter.getOperation()));

        PropertyFilter<Boolean> activePropertyFilter = uiComponents.create(PropertyFilter.NAME);
        activePropertyFilter.setConditionModificationDelegated(true);
        activePropertyFilter.setDataLoader(dataLoader);
        activePropertyFilter.setProperty("active");
        activePropertyFilter.setOperation(PropertyFilter.Operation.EQUAL);
        activePropertyFilter.setOperationEditable(true);
        activePropertyFilter.setCaption(propertyFilterSupport.getPropertyFilterCaption(
                dataLoader.getContainer().getEntityMetaClass(),
                activePropertyFilter.getProperty()));
        activePropertyFilter.setParameterName(PropertyConditionUtils.generateParameterName(
                activePropertyFilter.getProperty()));
        activePropertyFilter.setValueComponent(singleFilterSupport.generateValueComponent(
                dataLoader.getContainer().getEntityMetaClass(),
                activePropertyFilter.getProperty(),
                activePropertyFilter.getOperation()
        ));
        groupFilter.add(activePropertyFilter);

        PropertyFilter<CustomerGrade> gradePropertyFilter = uiComponents.create(PropertyFilter.NAME);
        gradePropertyFilter.setConditionModificationDelegated(true);
        gradePropertyFilter.setDataLoader(dataLoader);
        gradePropertyFilter.setProperty("grade");
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
        groupFilter.add(gradePropertyFilter);
        javaConfiguration.getRootLogicalFilterComponent().add(groupFilter);

        PropertyFilter<Integer> agePropertyFilter = uiComponents.create(PropertyFilter.NAME);
        agePropertyFilter.setConditionModificationDelegated(true);
        agePropertyFilter.setDataLoader(dataLoader);
        agePropertyFilter.setProperty("age");
        agePropertyFilter.setOperation(PropertyFilter.Operation.GREATER_OR_EQUAL);
        agePropertyFilter.setOperationEditable(true);
        agePropertyFilter.setCaption(propertyFilterSupport.getPropertyFilterCaption(
                dataLoader.getContainer().getEntityMetaClass(),
                agePropertyFilter.getProperty()));
        agePropertyFilter.setParameterName(PropertyConditionUtils.generateParameterName(
                agePropertyFilter.getProperty()));
        agePropertyFilter.setValueComponent(singleFilterSupport.generateValueComponent(
                dataLoader.getContainer().getEntityMetaClass(),
                agePropertyFilter.getProperty(),
                agePropertyFilter.getOperation()
        ));

        javaConfiguration.getRootLogicalFilterComponent().add(agePropertyFilter);

        activePropertyFilter.setValue(false);
        javaConfiguration.setFilterComponentDefaultValue(activePropertyFilter.getParameterName(), false);

        gradePropertyFilter.setValue(CustomerGrade.STANDARD);
        javaConfiguration.setFilterComponentDefaultValue(gradePropertyFilter.getParameterName(), CustomerGrade.STANDARD);

        agePropertyFilter.setValue(30);
        javaConfiguration.setFilterComponentDefaultValue(agePropertyFilter.getParameterName(), 30);
    }
}
