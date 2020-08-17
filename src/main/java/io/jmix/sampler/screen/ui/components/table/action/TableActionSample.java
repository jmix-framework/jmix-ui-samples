package io.jmix.sampler.screen.ui.components.table.action;

import io.jmix.core.MetadataTools;
import io.jmix.sampler.entity.Customer;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.Table;
import io.jmix.ui.screen.LoadDataBeforeShow;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("table-action")
@UiDescriptor("table-action.xml")
@LoadDataBeforeShow
public class TableActionSample extends ScreenFragment {

    @Autowired
    protected Table<Customer> customerTable;
    @Autowired
    protected Notifications notifications;
    @Autowired
    protected MetadataTools metadataTools;

    @Subscribe("customerTable.greeting")
    protected void onCustomerTableGreetingActionPerformed(Action.ActionPerformedEvent event) {
        Customer customer = customerTable.getSingleSelected();
        notifications.create()
                .withCaption(customer != null
                        ? "Hello, " + metadataTools.getInstanceName(customer)
                        : "No selection")
                .show();
    }
}
