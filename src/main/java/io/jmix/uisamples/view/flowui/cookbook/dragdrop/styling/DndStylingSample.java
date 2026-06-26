package io.jmix.uisamples.view.flowui.cookbook.dragdrop.styling;

import com.vaadin.flow.component.dnd.DragSource;
import com.vaadin.flow.component.dnd.DropTarget;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import io.jmix.flowui.view.*;

import java.util.List;

@ViewController("dnd-styling")
@ViewDescriptor("dnd-styling.xml")
public class DndStylingSample extends StandardView {

    @ViewComponent
    private Div card1;
    @ViewComponent
    private Div card2;
    @ViewComponent
    private Div card3;

    @ViewComponent
    private VerticalLayout dropZone;

    @Subscribe
    public void onInit(InitEvent event) {
        List.of(card1, card2, card3).forEach(DragSource::create);

        DropTarget<VerticalLayout> dropTarget = DropTarget.create(dropZone);
        dropTarget.addDropListener(dropEvent ->
                dropEvent.getDragSourceComponent()
                        .ifPresent(dropZone::add));
    }
}
