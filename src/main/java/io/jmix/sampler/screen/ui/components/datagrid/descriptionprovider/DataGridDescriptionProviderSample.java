package io.jmix.sampler.screen.ui.components.datagrid.descriptionprovider;

import io.jmix.core.MessageTools;
import io.jmix.core.Messages;
import io.jmix.core.Metadata;
import io.jmix.core.MetadataTools;
import io.jmix.sampler.entity.Customer;
import io.jmix.ui.component.ContentMode;
import io.jmix.ui.component.DataGrid;
import io.jmix.ui.screen.Install;
import io.jmix.ui.screen.LoadDataBeforeShow;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("datagrid-description-provider")
@UiDescriptor("datagrid-description-provider.xml")
@LoadDataBeforeShow
public class DataGridDescriptionProviderSample extends ScreenFragment {
    @Autowired
    protected DataGrid<Customer> customersDataGrid;

    @Autowired
    protected Messages messages;
    @Autowired
    protected MessageTools messageTools;
    @Autowired
    protected MetadataTools metadataTools;
    @Autowired
    protected Metadata metadata;

    @Subscribe
    protected void onInit(InitEvent event) {
        customersDataGrid.getColumnNN("age").setDescriptionProvider(customer ->
                        getPropertyCaption("age") + customer.getAge(),
                ContentMode.HTML);

        customersDataGrid.getColumnNN("active").setDescriptionProvider(customer ->
                        getPropertyCaption("active") +
                                messages.getMessage(DataGridDescriptionProviderSample.class,
                                        customer.getActive() ? "trueString" : "falseString"),
                ContentMode.HTML);

        customersDataGrid.getColumnNN("grade").setDescriptionProvider(customer ->
                        getPropertyCaption("grade") + messages.getMessage(customer.getGrade()),
                ContentMode.HTML);
    }

    protected String getPropertyCaption(String property) {
        return "<strong>" +
                messageTools.getPropertyCaption(metadata.getClass(Customer.class), property) +
                ": </strong>";
    }

    @Install(to = "customersDataGrid", subject = "rowDescriptionProvider")
    protected String customersDataGridRowDescriptionProvider(Customer customer) {
        return metadataTools.getInstanceName(customer);
    }
}
