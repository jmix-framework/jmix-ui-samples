package io.jmix.sampler.screen.ui.components.filter.jpql;

import io.jmix.core.Messages;
import io.jmix.sampler.entity.Product;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.Filter;
import io.jmix.ui.component.JpqlFilter;
import io.jmix.ui.component.filter.configuration.DesignTimeConfiguration;
import io.jmix.ui.component.jpqlfilter.JpqlFilterSupport;
import io.jmix.ui.component.propertyfilter.SingleFilterSupport;
import io.jmix.ui.model.DataLoader;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("filter-jpql-condition")
@UiDescriptor("filter-jpql-condition.xml")
public class FilterJpqlConditionSample extends ScreenFragment {

    @Autowired
    protected UiComponents uiComponents;
    @Autowired
    protected JpqlFilterSupport jpqlFilterSupport;
    @Autowired
    protected SingleFilterSupport singleFilterSupport;
    @Autowired
    protected Filter filter;

    @Autowired
    protected Messages messages;

    @Subscribe
    protected void onInit(InitEvent event) {
        DesignTimeConfiguration javaConfiguration = filter.addConfiguration("javaConfiguration",
                messages.getMessage(FilterJpqlConditionSample.class, "javaConfiguration.name"));
        DataLoader dataLoader = filter.getDataLoader();

        JpqlFilter<Product> jpqlFilter = uiComponents.create(JpqlFilter.NAME);
        jpqlFilter.setFrame(getFragment());
        jpqlFilter.setDataLoader(dataLoader);
        jpqlFilter.setCondition("i.product.id = ?", "join {E}.items i");
        jpqlFilter.setParameterClass(Product.class);
        jpqlFilter.setCaption(messages.getMessage(FilterJpqlConditionSample.class, "jpqlFilter.caption"));
        jpqlFilter.setParameterName(jpqlFilterSupport.generateParameterName(
                jpqlFilter.getId(),
                jpqlFilter.getParameterClass().getSimpleName()));
        jpqlFilter.setValueComponent(singleFilterSupport.generateValueComponent(
                dataLoader.getContainer().getEntityMetaClass(),
                jpqlFilter.hasInExpression(),
                jpqlFilter.getParameterClass()
        ));

        javaConfiguration.getRootLogicalFilterComponent().add(jpqlFilter);
    }
}
