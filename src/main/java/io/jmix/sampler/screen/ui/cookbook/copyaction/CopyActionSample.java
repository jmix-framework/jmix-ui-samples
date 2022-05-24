package io.jmix.sampler.screen.ui.cookbook.copyaction;

import io.jmix.core.DataManager;
import io.jmix.core.MetadataTools;
import io.jmix.core.UuidProvider;
import io.jmix.sampler.entity.Customer;
import io.jmix.sampler.screen.entity.customer.CustomerEdit;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.DataGrid;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("copy-action")
@UiDescriptor("copy-action.xml")
public class CopyActionSample extends ScreenFragment {

    @Autowired
    private DataGrid<Customer> customersTable;
    @Autowired
    private MetadataTools metadataTools;
    @Autowired
    private DataManager dataManager;
    @Autowired
    private CollectionContainer<Customer> customersDc;
    @Autowired
    private ScreenBuilders screenBuilders;

    @Subscribe("customersTable.copy")
    public void onCustomersTableCopy(Action.ActionPerformedEvent event) {
        Customer customer = customersTable.getSingleSelected();

        Customer customerCopy = createCopy(customer);

        Customer savedCopy = dataManager.save(customerCopy);
        customersDc.getMutableItems().add(0, savedCopy);
    }

    @Subscribe("customersTable.copyAndEdit")
    public void onCustomersTableCopyAndEdit(Action.ActionPerformedEvent event) {
        Customer customer = customersTable.getSingleSelected();

        Customer customerCopy = createCopy(customer);

        screenBuilders.editor(customersTable)
                .withScreenClass(CustomerEdit.class)
                .withOpenMode(OpenMode.DIALOG)
                .newEntity(customerCopy)
                .show();
    }

    private Customer createCopy(Customer customer) {
        Customer customerCopy = metadataTools.copy(customer);
        customerCopy.setId(UuidProvider.createUuid());
        customerCopy.setName(StringUtils.abbreviate("Copy of " + customer.getName(), 50));
        return customerCopy;
    }
}