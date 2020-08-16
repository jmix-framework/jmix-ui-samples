package io.jmix.sampler.screen.ui.components.datagrid.largedataset;

import io.jmix.core.LoadContext;
import io.jmix.core.Metadata;
import io.jmix.sampler.entity.Customer;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.Install;
import io.jmix.ui.screen.LoadDataBeforeShow;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.Target;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@UiController("datagrid-large-dataset")
@UiDescriptor("datagrid-large-dataset.xml")
@LoadDataBeforeShow
public class DataGridLargeDatasetSample extends ScreenFragment {
    private static final int COUNT = 1000;

    @Autowired
    protected CollectionContainer<Customer> customersDc;
    @Autowired
    protected Metadata metadata;

    protected List<Customer> items;

    @Subscribe
    protected void onInit(InitEvent event) {
        items = new ArrayList<>();
        for (int i = 0; i < COUNT; i++) {
            Customer customer = metadata.create(Customer.class);
            customer.setName("First Name " + i);
            customer.setLastName("Last Name " + i);
            items.add(customer);
        }
    }

    @Install(to = "customerDl", target = Target.DATA_LOADER)
    protected List<Customer> customerDlLoadDelegate(LoadContext<Customer> loadContext) {
        return items;
    }
}
