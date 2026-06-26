package io.jmix.uisamples.view.flowui.dialogsandnotifications.dialogs.sidedialog;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.card.CardVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.theme.lumo.LumoIcon;
import com.vaadin.flow.theme.lumo.LumoUtility;
import io.jmix.flowui.Dialogs;
import io.jmix.flowui.component.sidedialog.SideDialog;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@ViewController("side-dialog")
@ViewDescriptor("side-dialog.xml")
public class SideDialogSample extends StandardView {

    @Autowired
    private Dialogs dialogs;

    @ViewComponent
    private MessageBundle messageBundle;

    @Subscribe(id = "simpleSideDialogButton", subject = "clickListener")
    public void onSimpleSideDialogButtonClick(final ClickEvent<JmixButton> event) {
        dialogs.createSideDialog()
                .withHorizontalSize("22em")
                .withHeaderProvider(this::createHeader)
                .withContentComponents(createContent())
                .open();
    }

    private HorizontalLayout createHeader(SideDialog sideDialog) {
        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();
        header.add(new H2(messageBundle.getMessage("sideDialogHeader")));
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        Button closeButton = new Button(LumoIcon.CROSS.create(), event -> sideDialog.close());
        closeButton.addThemeVariants(ButtonVariant.TERTIARY);
        header.add(closeButton);

        return header;
    }

    private Component createContent() {
        VirtualList<String> list = new VirtualList<>();
        list.setWidthFull();
        list.setItems("Item 1", "Item 2", "Item 3");
        list.setRenderer(new ComponentRenderer<>((item -> {
            Card root = new Card();
            root.setTitle(item);
            root.setHeaderSuffix(LumoIcon.CROSS.create());
            root.add(messageBundle.getMessage("activityDescription"));
            root.addThemeVariants(CardVariant.HORIZONTAL);
            root.addClassName(LumoUtility.Margin.Bottom.MEDIUM);
            return root;
        })));
        return list;
    }
}
