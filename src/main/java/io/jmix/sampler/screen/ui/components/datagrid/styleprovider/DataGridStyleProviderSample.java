package io.jmix.sampler.screen.ui.components.datagrid.styleprovider;

import io.jmix.sampler.entity.Customer;
import io.jmix.ui.component.DataGrid;
import io.jmix.ui.screen.Install;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("datagrid-style-provider")
@UiDescriptor("datagrid-style-provider.xml")
public class DataGridStyleProviderSample extends ScreenFragment {
    @Autowired
    protected DataGrid<Customer> customersDataGrid;

    @Install(to = "customersDataGrid", subject = "rowStyleProvider")
    protected String customersDataGridRowStyleProvider(Customer customer) {
        return Boolean.TRUE.equals(customer.getActive()) ? "active-customer" : null;
    }

    @Subscribe
    protected void onInit(InitEvent event) {
        customersDataGrid.getColumnNN("grade")
                .setStyleProvider(customer -> {
                    switch (customer.getGrade()) {
                        case PREMIUM:
                            return "premium-grade";
                        case HIGH:
                            return "high-grade";
                        case STANDARD:
                            return "standard-grade";
                        default:
                            return null;
                    }
                });
    }
}
