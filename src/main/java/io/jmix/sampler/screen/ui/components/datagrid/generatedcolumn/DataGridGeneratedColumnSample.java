package io.jmix.sampler.screen.ui.components.datagrid.generatedcolumn;

import io.jmix.core.MetadataTools;
import io.jmix.sampler.entity.Customer;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.Component;
import io.jmix.ui.component.DataGrid;
import io.jmix.ui.component.Label;
import io.jmix.ui.screen.Install;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("datagrid-generated-column")
@UiDescriptor("datagrid-generated-column.xml")
public class DataGridGeneratedColumnSample extends ScreenFragment {
    @Autowired
    protected DataGrid<Customer> customersDataGrid;
    @Autowired
    protected MetadataTools metadataTools;
    @Autowired
    protected UiComponents uiComponents;

    @Install(to = "customersDataGrid.fullName", subject = "columnGenerator")
    protected Component customersDataGridFullNameColumnGenerator(DataGrid.ColumnGeneratorEvent<Customer> columnGeneratorEvent) {
        Label<String> label = uiComponents.create(Label.TYPE_STRING);
        label.setValue(metadataTools.getInstanceName(columnGeneratorEvent.getItem()));
        return label;
    }
}
