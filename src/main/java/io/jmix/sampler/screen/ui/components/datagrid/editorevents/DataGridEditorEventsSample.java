package io.jmix.sampler.screen.ui.components.datagrid.editorevents;

import io.jmix.sampler.entity.Customer;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.DataGrid;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("datagrid-editor-events")
@UiDescriptor("datagrid-editor-events.xml")
public class DataGridEditorEventsSample extends ScreenFragment {
    @Autowired
    protected Notifications notifications;

    @Subscribe("customersDataGrid")
    protected void onCustomersDataGridEditorOpen(DataGrid.EditorOpenEvent<Customer> event) {
        notifications.create()
                .withCaption("Editor will open")
                .withType(Notifications.NotificationType.TRAY)
                .show();
    }

    @Subscribe("customersDataGrid")
    protected void onCustomersDataGridEditorPreCommit(DataGrid.EditorPreCommitEvent<Customer> event) {
        notifications.create()
                .withCaption("Pre Commit")
                .withType(Notifications.NotificationType.TRAY)
                .show();
    }

    @Subscribe("customersDataGrid")
    protected void onCustomersDataGridEditorPostCommit(DataGrid.EditorPostCommitEvent<Customer> event) {
        notifications.create()
                .withCaption("Post Commit")
                .show();
    }

    @Subscribe("customersDataGrid")
    protected void onCustomersDataGridEditorClose(DataGrid.EditorCloseEvent<Customer> event) {
        notifications.create()
                .withCaption("Editor closed")
                .withType(Notifications.NotificationType.TRAY)
                .show();
    }
}
