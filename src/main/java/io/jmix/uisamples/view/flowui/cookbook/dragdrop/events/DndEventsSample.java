package io.jmix.uisamples.view.flowui.cookbook.dragdrop.events;

import com.vaadin.flow.component.dnd.DragEndEvent;
import com.vaadin.flow.component.dnd.DragSource;
import com.vaadin.flow.component.dnd.DragStartEvent;
import com.vaadin.flow.component.dnd.DropEvent;
import com.vaadin.flow.component.dnd.DropTarget;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@ViewController("dnd-events")
@ViewDescriptor("dnd-events.xml")
public class DndEventsSample extends StandardView {

    @ViewComponent
    private Div card1;
    @ViewComponent
    private Div card2;
    @ViewComponent
    private Div card3;

    @ViewComponent
    private VerticalLayout sourcePanel;
    @ViewComponent
    private VerticalLayout dropZone;

    @Autowired
    private Notifications notifications;

    @Subscribe
    public void onInit(InitEvent event) {
        List.of(card1, card2, card3).forEach(this::makeDraggable);

        makeDropTarget(sourcePanel);
        makeDropTarget(dropZone);
    }

    private void makeDraggable(Div card) {
        DragSource<Div> dragSource = DragSource.create(card);
        dragSource.addDragStartListener(this::onDragStart);
        dragSource.addDragEndListener(this::onDragEnd);
    }

    private void makeDropTarget(VerticalLayout zone) {
        DropTarget<VerticalLayout> dropTarget = DropTarget.create(zone);
        dropTarget.addDropListener(dropEvent -> onDrop(dropEvent, zone));
    }

    private void onDragStart(DragStartEvent<Div> event) {
        showNotification("Drag started");
    }

    private void onDrop(DropEvent<VerticalLayout> event, VerticalLayout zone) {
        event.getDragSourceComponent().ifPresent(zone::add);
        showNotification("Dropped");
    }

    private void onDragEnd(DragEndEvent<Div> event) {
        showNotification("Drag ended: " + (event.isSuccessful() ? "successful" : "cancelled"));
    }

    private void showNotification(String message) {
        notifications.create(message)
                .withPosition(Notification.Position.BOTTOM_END)
                .show();
    }
}
