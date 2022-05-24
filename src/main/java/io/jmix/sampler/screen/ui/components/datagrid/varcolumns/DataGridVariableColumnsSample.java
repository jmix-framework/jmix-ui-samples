package io.jmix.sampler.screen.ui.components.datagrid.varcolumns;

import io.jmix.core.entity.KeyValueEntity;
import io.jmix.ui.Notifications;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.DataGrid;
import io.jmix.ui.component.TextField;
import io.jmix.ui.component.VBoxLayout;
import io.jmix.ui.component.data.datagrid.ContainerDataGridItems;
import io.jmix.ui.model.DataComponents;
import io.jmix.ui.model.KeyValueCollectionContainer;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@UiController("datagrid-var-columns")
@UiDescriptor("datagrid-var-columns.xml")
public class DataGridVariableColumnsSample extends ScreenFragment {

    @Autowired
    private VBoxLayout box;
    @Autowired
    private TextField<Integer> columnCountField;
    @Autowired
    private DataComponents dataComponents;
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private Notifications notifications;

    @Subscribe("createDataGridBtn")
    public void onCreateDataGridBtnClick(Button.ClickEvent event) {
        box.removeAll();
        Integer columnCount = columnCountField.getValue();
        if (columnCount == null || columnCount < 1 || columnCount > 10) {
            notifications.create().withCaption("Column count must be between 1 and 10").show();
            return;
        }
        KeyValueCollectionContainer container = createDataContainer(columnCount);
        DataGrid<KeyValueEntity> dataGrid = createDataGrid(columnCount, container);
        box.add(dataGrid);
    }

    private DataGrid<KeyValueEntity> createDataGrid(Integer columnCount, KeyValueCollectionContainer container) {
        DataGrid<KeyValueEntity> dataGrid = uiComponents.create(DataGrid.class);
        dataGrid.setHeight("100%");
        dataGrid.setWidth("100%");

        for (int col = 1; col <= columnCount; col++) {
            dataGrid.addColumn("prop" + col, container.getEntityMetaClass().getPropertyPath("prop" + col));
        }
        dataGrid.setItems(new ContainerDataGridItems<>(container));
        return dataGrid;
    }

    private KeyValueCollectionContainer createDataContainer(Integer columnCount) {
        KeyValueCollectionContainer container = dataComponents.createKeyValueCollectionContainer();
        for (int col = 1; col <= columnCount; col++) {
            container.addProperty("prop" + col, String.class);
        }
        container.setItems(loadData(columnCount));
        return container;
    }

    private List<KeyValueEntity> loadData(Integer columnCount) {
        List<KeyValueEntity> list = new ArrayList<>();
        for (int row = 0; row < 5; row++) {
            KeyValueEntity entity = new KeyValueEntity();
            for (int col = 1; col <= columnCount; col++) {
                entity.setValue("prop" + col, "value" + row + col);
            }
            list.add(entity);
        }
        return list;
    }

    @Subscribe
    public void onInit(InitEvent event) {
        columnCountField.setValue(3);
    }
}
