package io.jmix.sampler.screen.ui.components.treedatagrid;

import io.jmix.core.MessageTools;
import io.jmix.core.MetadataTools;
import io.jmix.core.metamodel.model.MetaPropertyPath;
import io.jmix.sampler.entity.Task;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.ComboBox;
import io.jmix.ui.component.DataGrid;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.component.TreeDataGrid;
import io.jmix.ui.screen.LoadDataBeforeShow;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@UiController("treedatagrid-simple")
@UiDescriptor("treedatagrid-simple.xml")
@LoadDataBeforeShow
public class TreeDataGridSimpleSample extends ScreenFragment {
    @Autowired
    protected ComboBox<String> columnSelector;
    @Autowired
    protected TreeDataGrid<Task> taskDataGrid;

    @Autowired
    protected MessageTools messageTools;
    @Autowired
    protected MetadataTools metadataTools;
    @Autowired
    protected Notifications notifications;

    @Subscribe
    protected void onInit(InitEvent event) {
        initColumnSelector();

        taskDataGrid.addCollapseListener(this::onCollapse);
        taskDataGrid.addExpandListener(this::onExpand);
    }

    protected void onCollapse(TreeDataGrid.CollapseEvent<Task> event) {
        notifications.create()
                .withCaption("Collapsed Item: " +
                        metadataTools.getInstanceName(event.getCollapsedItem()))
                .show();
    }

    protected void onExpand(TreeDataGrid.ExpandEvent<Task> event) {
        notifications.create()
                .withCaption("Expanded Item: " +
                        metadataTools.getInstanceName(event.getExpandedItem()))
                .show();
    }

    protected void initColumnSelector() {
        List<DataGrid.Column<Task>> columns = taskDataGrid.getColumns();
        Map<String, String> columnsMap = columns.stream()
                .collect(Collectors.toMap(
                        column -> {
                            MetaPropertyPath propertyPath = column.getPropertyPath();
                            return propertyPath != null
                                    ? messageTools.getPropertyCaption(propertyPath.getMetaProperty())
                                    : column.getId();
                        },
                        DataGrid.Column::getId,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new));
        columnSelector.setOptionsMap(columnsMap);

        columnSelector.setValue(columns.get(0).getId());
    }

    @Subscribe("columnSelector")
    protected void onColumnSelectorValueChange(HasValue.ValueChangeEvent<String> event) {
        taskDataGrid.setHierarchyColumn(event.getValue());
    }
}
