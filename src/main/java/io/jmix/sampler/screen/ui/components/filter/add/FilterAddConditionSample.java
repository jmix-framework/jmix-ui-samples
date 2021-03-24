package io.jmix.sampler.screen.ui.components.filter.add;

import io.jmix.core.querycondition.PropertyConditionUtils;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.Filter;
import io.jmix.ui.component.PropertyFilter;
import io.jmix.ui.component.propertyfilter.SingleFilterSupport;
import io.jmix.ui.model.DataLoader;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("filter-add-condition")
@UiDescriptor("filter-add-condition.xml")
public class FilterAddConditionSample extends ScreenFragment {

    @Autowired
    protected UiComponents uiComponents;
    @Autowired
    protected SingleFilterSupport singleFilterSupport;

    @Autowired
    protected Filter filter;

    @Subscribe
    protected void onAfterInit(AfterInitEvent event) {
        DataLoader dataLoader = filter.getDataLoader();

        PropertyFilter<Integer> agePropertyFilter = uiComponents.create(PropertyFilter.NAME);
        agePropertyFilter.setConditionModificationDelegated(true);
        agePropertyFilter.setDataLoader(dataLoader);
        agePropertyFilter.setProperty("age");
        agePropertyFilter.setOperation(PropertyFilter.Operation.GREATER_OR_EQUAL);
        agePropertyFilter.setOperationEditable(true);
        agePropertyFilter.setParameterName(PropertyConditionUtils.generateParameterName(
                agePropertyFilter.getProperty()));
        agePropertyFilter.setValueComponent(singleFilterSupport.generateValueComponent(
                dataLoader.getContainer().getEntityMetaClass(),
                agePropertyFilter.getProperty(),
                agePropertyFilter.getOperation()
        ));

        filter.getCurrentConfiguration().getRootLogicalFilterComponent().add(agePropertyFilter);
        filter.refreshCurrentConfigurationLayout();
    }
}
