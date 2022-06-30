package io.jmix.sampler.screen.ui.cookbook.dbview;

import io.jmix.core.DataManager;
import io.jmix.core.Id;
import io.jmix.sampler.entity.Customer;
import io.jmix.sampler.entity.ReadOnlyCustomer;
import io.jmix.sampler.screen.entity.customer.CustomerEdit;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("readonly-customers")
@UiDescriptor("readonly-customers.xml")
public class ReadOnlyCustomersSample extends ScreenFragment {

    @Autowired
    private CollectionLoader<ReadOnlyCustomer> readOnlyCustomersDl;
    @Autowired
    private GroupTable<ReadOnlyCustomer> readOnlyCustomersTable;
    @Autowired
    private ScreenBuilders screenBuilders;
    @Autowired
    private DataManager dataManager;

    @Subscribe("readOnlyCustomersTable.create")
    public void onReadOnlyCustomersTableCreate(Action.ActionPerformedEvent event) {
        screenBuilders.editor(Customer.class, this)
                .newEntity()
                .withScreenClass(CustomerEdit.class)
                .withOpenMode(OpenMode.DIALOG)
                .withAfterCloseListener(afterCloseEvent -> {
                    if (afterCloseEvent.closedWith(StandardOutcome.COMMIT)) {
                        readOnlyCustomersDl.load();
                    }
                })
                .show();
    }

    @Subscribe("readOnlyCustomersTable.edit")
    public void onReadOnlyCustomersTableEdit(Action.ActionPerformedEvent event) {
        ReadOnlyCustomer readOnlyCustomer = readOnlyCustomersTable.getSingleSelected();
        if (readOnlyCustomer == null)
            return;
        Customer customer = dataManager.load(Customer.class).id(readOnlyCustomer.getId()).one();

        screenBuilders.editor(Customer.class, this)
                .editEntity(customer)
                .withScreenClass(CustomerEdit.class)
                .withOpenMode(OpenMode.DIALOG)
                .withAfterCloseListener(afterCloseEvent -> {
                    if (afterCloseEvent.closedWith(StandardOutcome.COMMIT)) {
                        readOnlyCustomersDl.load();
                    }
                })
                .show();
    }

    @Subscribe("readOnlyCustomersTable.remove")
    public void onReadOnlyCustomersTableRemove(Action.ActionPerformedEvent event) {
        ReadOnlyCustomer readOnlyCustomer = readOnlyCustomersTable.getSingleSelected();
        if (readOnlyCustomer == null)
            return;
        dataManager.remove(Id.of(readOnlyCustomer.getId(), Customer.class));
        readOnlyCustomersDl.load();
    }
}