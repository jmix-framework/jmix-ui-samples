package io.jmix.uisamples.view.flowui.components.datagrid.tooltipgenerator;

import com.vaadin.flow.component.grid.Grid;
import io.jmix.core.MetadataTools;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.view.*;
import io.jmix.uisamples.entity.Customer;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;

@ViewController("data-grid-tooltip-generator")
@ViewDescriptor("data-grid-tooltip-generator.xml")
public class DataGridTooltipGeneratorSample extends StandardView {

    @ViewComponent
    protected DataGrid<Customer> customersDataGrid;

    @Autowired
    protected MetadataTools metadataTools;

    @Subscribe
    protected void onInit(InitEvent event) {
        Grid.Column<Customer> gradeColumn = customersDataGrid.getColumnByKey("lastName");

        if (gradeColumn != null) {
            gradeColumn.setTooltipGenerator(this::instanceNameTooltipGenerator);
        }
    }

    @Nullable
    protected String instanceNameTooltipGenerator(Customer customer) {
        return metadataTools.getInstanceName(customer);
    }
}
