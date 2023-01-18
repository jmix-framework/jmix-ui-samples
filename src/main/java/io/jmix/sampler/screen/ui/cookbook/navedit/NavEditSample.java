package io.jmix.sampler.screen.ui.cookbook.navedit;

import io.jmix.core.Metadata;
import io.jmix.sampler.entity.Customer;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.DataGrid;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("navigating-editor")
@UiDescriptor("navedit.xml")
public class NavEditSample extends ScreenFragment {

    @Autowired
    private CollectionContainer<Customer> customersDc;
    @Autowired
    private DataGrid<Customer> customersTable;
    @Autowired
    private ScreenBuilders screenBuilders;
    @Autowired
    private Metadata metadata;

    @Subscribe("customersTable.openEditor")
    public void onCustomersTableOpenEditor(Action.ActionPerformedEvent event) {
        // Build edit screen
        CustomerNavEdit edit = screenBuilders.screen(this)
                .withScreenClass(CustomerNavEdit.class)
                .withOpenMode(OpenMode.DIALOG)
                .build();

        // Pass collection container and table to navigate through records in the editor
        edit.setCollectionContainer(customersDc);
        edit.setListComponent(customersTable);

        // Pass the selected or new instance to the editor screen
        Customer department = customersTable.getSingleSelected();
        if (department == null)
            department = metadata.create(Customer.class);
        edit.setEntityToEdit(department);

        // Open editor screen
        edit.show();
    }
}