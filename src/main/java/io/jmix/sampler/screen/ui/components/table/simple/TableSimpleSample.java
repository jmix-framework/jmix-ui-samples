package io.jmix.sampler.screen.ui.components.table.simple;

import io.jmix.sampler.entity.Order;
import io.jmix.ui.component.CheckBox;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.component.Table;
import io.jmix.ui.screen.LoadDataBeforeShow;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("table-simple")
@UiDescriptor("table-simple.xml")
@LoadDataBeforeShow
public class TableSimpleSample extends ScreenFragment {

    @Autowired
    protected CheckBox multiselect;
    @Autowired
    protected CheckBox columnControlVisible;
    @Autowired
    protected CheckBox sortable;
    @Autowired
    protected CheckBox reorderingAllowed;
    @Autowired
    protected CheckBox showSelection;
    @Autowired
    protected Table<Order> ordersTable;

    @Subscribe
    protected void onInit(InitEvent event) {
        multiselect.setValue(ordersTable.isMultiSelect());
        sortable.setValue(ordersTable.isSortable());
        columnControlVisible.setValue(ordersTable.getColumnControlVisible());
        reorderingAllowed.setValue(ordersTable.getColumnReorderingAllowed());
        showSelection.setValue(ordersTable.isShowSelection());
    }

    @Subscribe("multiselect")
    protected void onMultiselectValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        ordersTable.setMultiSelect(Boolean.TRUE.equals(event.getValue()));
    }

    @Subscribe("sortable")
    protected void onSortableValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        ordersTable.setSortable(Boolean.TRUE.equals(event.getValue()));
    }

    @Subscribe("columnControlVisible")
    protected void onColumnControlVisibleValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        ordersTable.setColumnControlVisible(Boolean.TRUE.equals(event.getValue()));
    }

    @Subscribe("reorderingAllowed")
    protected void onReorderingAllowedValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        ordersTable.setColumnReorderingAllowed(Boolean.TRUE.equals(event.getValue()));
    }

    @Subscribe("showSelection")
    protected void onShowSelectionValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        ordersTable.setShowSelection(Boolean.TRUE.equals(event.getValue()));
    }
}
