package io.jmix.sampler.screen.ui.components.datagrid.selectionmode;

import io.jmix.sampler.entity.Customer;
import io.jmix.ui.component.ComboBox;
import io.jmix.ui.component.DataGrid;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashMap;
import java.util.Map;

@UiController("datagrid-selection-mode")
@UiDescriptor("datagrid-selection-mode.xml")
public class DataGridSelectionModeSample extends ScreenFragment {
    @Autowired
    protected DataGrid<Customer> customersDataGrid;
    @Autowired
    protected ComboBox<DataGrid.SelectionMode> selectionModeField;

    @Subscribe
    protected void onInit(InitEvent event) {
        Map<String, DataGrid.SelectionMode> selectionModeMap = new LinkedHashMap<>();
        for (DataGrid.SelectionMode mode : DataGrid.SelectionMode.values()) {
            selectionModeMap.put(mode.name(), mode);
        }
        selectionModeField.setOptionsMap(selectionModeMap);
        selectionModeField.setValue(customersDataGrid.getSelectionMode());
    }

    @Subscribe("selectionModeField")
    protected void onSelectionModeFieldValueChange(HasValue.ValueChangeEvent<DataGrid.SelectionMode> event) {
        customersDataGrid.setSelectionMode(event.getValue());
    }
}