package io.jmix.sampler.screen.ui.components.datagrid.frozencolumns;

import io.jmix.sampler.entity.Customer;
import io.jmix.ui.component.DataGrid;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.component.TextField;
import io.jmix.ui.screen.LoadDataBeforeShow;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("datagrid-frozen-columns")
@UiDescriptor("datagrid-frozen-columns.xml")
@LoadDataBeforeShow
public class DataGridFrozenColumnsSample extends ScreenFragment {
    @Autowired
    protected DataGrid<Customer> customersDataGrid;
    @Autowired
    protected TextField<Integer> frozenColumnCountField;

    @Subscribe
    protected void onInit(InitEvent event) {
        frozenColumnCountField.setValue(customersDataGrid.getFrozenColumnCount());
    }

    @Subscribe("frozenColumnCountField")
    protected void onFrozenColumnCountFieldValueChange(HasValue.ValueChangeEvent<Integer> event) {
        customersDataGrid.setFrozenColumnCount(event.getValue() != null ? event.getValue() : 0);
    }
}