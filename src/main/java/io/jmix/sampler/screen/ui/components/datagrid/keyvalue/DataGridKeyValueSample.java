package io.jmix.sampler.screen.ui.components.datagrid.keyvalue;

import io.jmix.core.FetchPlanRepository;
import io.jmix.core.FetchPlans;
import io.jmix.core.metamodel.datatype.DatatypeRegistry;
import io.jmix.sampler.entity.Customer;
import io.jmix.ui.model.DataComponents;
import io.jmix.ui.model.KeyValueCollectionContainer;
import io.jmix.ui.model.KeyValueCollectionLoader;
import io.jmix.ui.screen.LoadDataBeforeShow;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("datagrid-key-value")
@UiDescriptor("datagrid-key-value.xml")
@LoadDataBeforeShow
public class DataGridKeyValueSample extends ScreenFragment {

/*    @Autowired
    protected DataComponents dataComponents;
    @Autowired
    protected DatatypeRegistry datatypeRegistry;
    @Autowired
    protected FetchPlanRepository fetchPlanRepository;
    @Autowired
    protected FetchPlans fetchPlans;

    @Subscribe
    protected void onInit(InitEvent event) {
        KeyValueCollectionContainer container = dataComponents.createKeyValueCollectionContainer();
        container.addProperty("customer", Customer.class);
        container.addProperty("sum", datatypeRegistry.get("decimal"));
        getScreenData().registerContainer("salesDc", container);

        KeyValueCollectionLoader loader = dataComponents.createKeyValueCollectionLoader();
        loader.setContainer(container);
        loader.setQuery("select o.customer, sum(o.amount) from sampler_Order o group by o.customer");
        getScreenData().registerLoader("salesDl", loader);
        
        fetchPlans
    }*/


}
