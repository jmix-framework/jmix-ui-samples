package io.jmix.uisamples.view.flowui.kanban.datasaving;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.view.Install;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import io.jmix.uisamples.entity.KanbanTask;
import org.springframework.beans.factory.annotation.Autowired;

@ViewController("kanban-data-saving")
@ViewDescriptor("kanban-data-saving.xml")
public class KanbanDataSavingSample extends StandardView {

    @Autowired
    private Notifications notifications;

    @Install(to = "kanban", subject = "saveDelegate")
    public void kanbanSaveDelegate(final KanbanTask kanbanTask) {
        notifications.create("Task '%s' saved!".formatted(kanbanTask.getName()))
                .withThemeVariant(NotificationVariant.SUCCESS)
                .withPosition(Notification.Position.TOP_END)
                .show();
    }
}
