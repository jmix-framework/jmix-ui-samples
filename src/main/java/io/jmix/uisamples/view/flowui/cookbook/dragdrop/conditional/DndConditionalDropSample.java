package io.jmix.uisamples.view.flowui.cookbook.dragdrop.conditional;

import com.vaadin.flow.component.dnd.DragSource;
import com.vaadin.flow.component.dnd.DropTarget;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@ViewController("dnd-conditional-drop")
@ViewDescriptor("dnd-conditional-drop.xml")
public class DndConditionalDropSample extends StandardView {

    @ViewComponent
    private Div redCard1;
    @ViewComponent
    private Div redCard2;
    @ViewComponent
    private Div greenCard1;
    @ViewComponent
    private Div greenCard2;

    @ViewComponent
    private VerticalLayout redZone;
    @ViewComponent
    private VerticalLayout greenZone;

    @Autowired
    private Notifications notifications;

    @Subscribe
    public void onInit(InitEvent event) {
        DragSource.create(redCard1).setDragData("red");
        DragSource.create(redCard2).setDragData("red");
        DragSource.create(greenCard1).setDragData("green");
        DragSource.create(greenCard2).setDragData("green");

        configureZone(redZone, "red");
        configureZone(greenZone, "green");
    }

    private void configureZone(VerticalLayout zone, String acceptedCategory) {
        DropTarget<VerticalLayout> dropTarget = DropTarget.create(zone);
        dropTarget.addDropListener(dropEvent -> {
            if (acceptedCategory.equals(dropEvent.getDragData().orElse(null))) {
                dropEvent.getDragSourceComponent().ifPresent(zone::add);
            } else {
                notifications.create("This container accepts only " + acceptedCategory + " items")
                        .withPosition(Notification.Position.BOTTOM_END)
                        .show();
            }
        });
    }
}
