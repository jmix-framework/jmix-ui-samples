package io.jmix.sampler.screen.ui.components.datagrid.basicsettings;

import io.jmix.sampler.entity.Order;
import io.jmix.ui.component.CheckBox;
import io.jmix.ui.component.DataGrid;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.screen.LoadDataBeforeShow;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("datagrid-basic-settings")
@UiDescriptor("datagrid-basic-settings.xml")
@LoadDataBeforeShow
public class DataGridBasicSettingsSample extends ScreenFragment {
    @Autowired
    protected DataGrid<Order> ordersDataGrid;
    @Autowired
    protected CheckBox columnsCollapsingAllowed;
    @Autowired
    protected CheckBox reorderingAllowed;
    @Autowired
    protected CheckBox sortable;

    @Subscribe
    protected void onInit(InitEvent event) {
        sortable.setValue(ordersDataGrid.isSortable());
        columnsCollapsingAllowed.setValue(ordersDataGrid.isColumnsCollapsingAllowed());
        reorderingAllowed.setValue(ordersDataGrid.isColumnReorderingAllowed());
    }

    @Subscribe("columnsCollapsingAllowed")
    protected void onColumnsCollapsingAllowedValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        ordersDataGrid.setColumnsCollapsingAllowed(Boolean.TRUE.equals(event.getValue()));
    }

    @Subscribe("reorderingAllowed")
    protected void onReorderingAllowedValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        ordersDataGrid.setColumnReorderingAllowed(Boolean.TRUE.equals(event.getValue()));
    }

    @Subscribe("sortable")
    protected void onSortableValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        ordersDataGrid.setSortable(Boolean.TRUE.equals(event.getValue()));
    }
}
