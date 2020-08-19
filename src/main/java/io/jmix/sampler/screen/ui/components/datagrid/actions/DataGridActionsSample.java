package io.jmix.sampler.screen.ui.components.datagrid.actions;

import io.jmix.core.MetadataTools;
import io.jmix.sampler.entity.Customer;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.DataGrid;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("datagrid-actions")
@UiDescriptor("datagrid-actions.xml")
public class DataGridActionsSample extends ScreenFragment {
    @Autowired
    protected DataGrid<Customer> customersDataGrid;
    @Autowired
    protected Notifications notifications;
    @Autowired
    protected MetadataTools metadataTools;

    @Subscribe("customersDataGrid.greeting")
    protected void onCustomersDataGridGreetingActionPerformed(Action.ActionPerformedEvent event) {
        Customer customer = customersDataGrid.getSingleSelected();
        notifications.create()
                .withCaption(customer != null
                        ? "Hello, " + metadataTools.getInstanceName(customer)
                        : "No selection")
                .show();
    }
}
