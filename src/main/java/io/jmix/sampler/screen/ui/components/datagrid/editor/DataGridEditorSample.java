package io.jmix.sampler.screen.ui.components.datagrid.editor;

import io.jmix.sampler.entity.Customer;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.CheckBox;
import io.jmix.ui.component.DataGrid;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.screen.LoadDataBeforeShow;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("datagrid-editor")
@UiDescriptor("datagrid-editor.xml")
@LoadDataBeforeShow
public class DataGridEditorSample extends ScreenFragment {
    @Autowired
    protected DataGrid<Customer> customersDataGrid;
    @Autowired
    protected CheckBox editorBuffered;

    @Autowired
    protected Notifications notifications;

    @Subscribe
    protected void onInit(InitEvent event) {
        editorBuffered.setValue(customersDataGrid.isEditorBuffered());
    }

    @Subscribe("editorBuffered")
    protected void onEditorBufferedValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (!event.isUserOriginated()) {
            return;
        }

        if (customersDataGrid.isEditorActive()) {
            notifications.create()
                    .withCaption("Please close Editor before changing its mode")
                    .withType(Notifications.NotificationType.WARNING)
                    .show();
            editorBuffered.setValue(event.getPrevValue());
        } else {
            customersDataGrid.setEditorBuffered(Boolean.TRUE.equals(event.getValue()));
        }
    }
}
