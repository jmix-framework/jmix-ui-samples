package io.jmix.uisamples.view.flowui.cookbook.dragdrop.effects;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dnd.DragSource;
import com.vaadin.flow.component.dnd.DropEffect;
import com.vaadin.flow.component.dnd.DropTarget;
import com.vaadin.flow.component.dnd.EffectAllowed;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import io.jmix.flowui.view.*;

import java.util.List;

@ViewController("dnd-effects")
@ViewDescriptor("dnd-effects.xml")
public class DndEffectsSample extends StandardView {

    @ViewComponent
    private Div card1;
    @ViewComponent
    private Div card2;
    @ViewComponent
    private Div card3;
    @ViewComponent
    private Div card4;
    @ViewComponent
    private Div card5;
    @ViewComponent
    private Div card6;

    @ViewComponent
    private VerticalLayout moveZone;
    @ViewComponent
    private VerticalLayout copyZone;
    @ViewComponent
    private VerticalLayout linkZone;
    @ViewComponent
    private VerticalLayout noneZone;

    @Subscribe
    public void onInit(InitEvent event) {
        List.of(card1, card2, card3, card4, card5, card6).forEach(card ->
                DragSource.create(card).setEffectAllowed(EffectAllowed.ALL));

        DropTarget<VerticalLayout> moveTarget = DropTarget.create(moveZone);
        moveTarget.setDropEffect(DropEffect.MOVE);
        moveTarget.addDropListener(dropEvent ->
                dropEvent.getDragSourceComponent().ifPresent(moveZone::add));

        DropTarget<VerticalLayout> copyTarget = DropTarget.create(copyZone);
        copyTarget.setDropEffect(DropEffect.COPY);
        copyTarget.addDropListener(dropEvent ->
                dropEvent.getDragSourceComponent().ifPresent(component -> copyZone.add(copy(component))));

        DropTarget<VerticalLayout> linkTarget = DropTarget.create(linkZone);
        linkTarget.setDropEffect(DropEffect.LINK);
        linkTarget.addDropListener(dropEvent ->
                dropEvent.getDragSourceComponent().ifPresent(component -> linkZone.add(link(component))));

        DropTarget<VerticalLayout> noneTarget = DropTarget.create(noneZone);
        noneTarget.setDropEffect(DropEffect.NONE);
        noneTarget.addDropListener(dropEvent ->
                dropEvent.getDragSourceComponent().ifPresent(noneZone::add));
    }

    private Component copy(Component component) {
        Div card = new Div(new Span(component.getElement().getTextRecursively()));
        card.setClassName("dnd-card");
        DragSource.create(card).setEffectAllowed(EffectAllowed.ALL);
        return card;
    }

    private Component link(Component component) {
        Div card = new Div(new Span(component.getElement().getTextRecursively() + " (link)"));
        card.setClassName("dnd-card");
        return card;
    }
}
