package io.jmix.uisamples.view.flowui.cookbook.dragdrop.components;

import com.vaadin.flow.component.dnd.DragSource;
import com.vaadin.flow.component.dnd.DropTarget;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import io.jmix.flowui.view.*;

import java.util.List;

@ViewController("dnd-components")
@ViewDescriptor("dnd-components.xml")
public class DndComponentsSample extends StandardView {

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

    @Subscribe
    public void onInit(InitEvent event) {
        List.of(card1, card2, card3).forEach(DragSource::create);

        makeDropTarget(sourcePanel);
        makeDropTarget(dropZone);
    }

    private void makeDropTarget(VerticalLayout zone) {
        DropTarget<VerticalLayout> dropTarget = DropTarget.create(zone);
        dropTarget.addDropListener(dropEvent ->
                dropEvent.getDragSourceComponent()
                        .ifPresent(zone::add));
    }
}
