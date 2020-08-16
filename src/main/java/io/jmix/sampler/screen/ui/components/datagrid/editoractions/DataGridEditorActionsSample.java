package io.jmix.sampler.screen.ui.components.datagrid.editoractions;

import io.jmix.core.Metadata;
import io.jmix.sampler.entity.Customer;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.DataGrid;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.LoadDataBeforeShow;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("datagrid-editor-actions")
@UiDescriptor("datagrid-editor-actions.xml")
@LoadDataBeforeShow
public class DataGridEditorActionsSample extends ScreenFragment {

    @Autowired
    protected DataGrid<Customer> customersDataGrid;
    @Autowired
    protected CollectionContainer<Customer> customersDc;
    @Autowired
    protected Notifications notifications;
    @Autowired
    protected Metadata metadata;

    @Subscribe("customersDataGrid.create")
    public void onCustomersDataGridCreate(Action.ActionPerformedEvent event) {
        if (customersDataGrid.isEditorActive()) {
            notifications.create()
                    .withCaption("Close the editor before creating a new entity")
                    .show();
        } else {
            Customer newCustomer = metadata.create(Customer.class);
            customersDc.getMutableItems().add(newCustomer);
            customersDataGrid.edit(newCustomer);
        }
    }

    @Subscribe("customersDataGrid.edit")
    public void onCustomersDataGridEdit(Action.ActionPerformedEvent event) {
        Customer selected = customersDataGrid.getSingleSelected();
        if (selected != null) {
            customersDataGrid.edit(selected);
        } else {
            notifications.create()
                    .withCaption("Item is not selected")
                    .show();
        }
    }
}
