package io.jmix.sampler.screen.ui.components.datagrid.itemclick;

import io.jmix.core.MetadataTools;
import io.jmix.sampler.entity.Customer;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.BaseAction;
import io.jmix.ui.component.DataGrid;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("datagrid-item-click")
@UiDescriptor("datagrid-item-click.xml")
public class DataGridItemClickSample extends ScreenFragment {
    @Autowired
    protected DataGrid<Customer> customersDataGrid;
    @Autowired
    protected Notifications notifications;
    @Autowired
    protected MetadataTools metadataTools;

    @Subscribe
    protected void onInit(InitEvent event) {
        customersDataGrid.setItemClickAction(new BaseAction("itemClickAction")
                .withHandler(actionPerformedEvent -> {
                    Customer customer = customersDataGrid.getSingleSelected();
                    if (customer != null) {
                        notifications.create()
                                .withCaption("Item clicked for: " + metadataTools.getInstanceName(customer))
                                .show();
                    }
                }));

        customersDataGrid.setEnterPressAction(new BaseAction("enterPressAction")
                .withHandler(actionPerformedEvent -> {
                    Customer customer = customersDataGrid.getSingleSelected();
                    if (customer != null) {
                        notifications.create()
                                .withCaption("Enter pressed for: " + metadataTools.getInstanceName(customer))
                                .show();
                    }
                }));
    }
}
