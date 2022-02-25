package io.jmix.sampler.screen.ui.cookbook.draganddrop.ddverticallayout;

import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.shared.ui.dd.VerticalDropLocation;
import com.vaadin.ui.Component;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.*;
import io.jmix.ui.icon.JmixIcon;
import io.jmix.ui.screen.*;
import io.jmix.ui.widget.*;
import io.jmix.ui.widget.addon.dragdroplayouts.DDVerticalLayout;
import io.jmix.ui.widget.addon.dragdroplayouts.events.LayoutBoundTransferable;
import io.jmix.ui.widget.client.addon.dragdroplayouts.ui.LayoutDragMode;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("dd-verticallayout")
@UiDescriptor("dd-verticallayout.xml")
public class DDVerticalLayoutSample extends ScreenFragment {

    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private MessageBundle messageBundle;

    @Autowired
    private ScrollBoxLayout paletteScrollBox;
    @Autowired
    private ScrollBoxLayout dashboardScrollBox;

    @Subscribe
    public void onInit(InitEvent event) {
        initPalette(paletteScrollBox);
        initDashboard(dashboardScrollBox);
    }

    private void initPalette(ScrollBoxLayout paletteScrollBox) {
        DDVerticalLayout ddPalette = new DDVerticalLayout();
        ddPalette.setDragMode(LayoutDragMode.CLONE);
        ddPalette.addComponent(createPaletteButton("callBtn", messageBundle.getMessage("call.btn.caption")));
        ddPalette.addComponent(createPaletteButton("chatBtn", messageBundle.getMessage("chat.btn.caption")));
        ddPalette.addComponent(createPaletteButton("meetingBtn", messageBundle.getMessage("meeting.btn.caption")));
        ddPalette.addComponent(createPaletteButton("buyBtn", messageBundle.getMessage("buy.btn.caption")));

        paletteScrollBox.withUnwrapped(JmixScrollBoxLayout.class, vScrollBox -> vScrollBox.addComponent(ddPalette));
    }

    private JmixButton createPaletteButton(String id, String caption) {
        Button callBtn = uiComponents.create(Button.NAME);
        callBtn.setId(id);
        callBtn.setCaption(caption);
        callBtn.setWidthFull();
        return callBtn.unwrap(JmixButton.class);
    }

    private void initDashboard(ScrollBoxLayout scrollBox) {
        DDVerticalLayout dashboard = new DDVerticalLayout();
        dashboard.setDragMode(LayoutDragMode.CLONE);
        dashboard.setDropHandler(new DropHandler() {
            @Override
            public void drop(DragAndDropEvent event) {
                onDrop(event);
            }

            @Override
            public AcceptCriterion getAcceptCriterion() {
                return AcceptAll.get();
            }
        });

        HtmlAttributesExtension.get(dashboard).setCssProperty("min-height", "100px");

        scrollBox.withUnwrapped(JmixScrollBoxLayout.class, vScrollBox-> vScrollBox.addComponent(dashboard));
    }

    protected void onDrop(DragAndDropEvent event) {
        LayoutBoundTransferable t = (LayoutBoundTransferable) event.getTransferable();
        DDVerticalLayout.VerticalLayoutTargetDetails details = (DDVerticalLayout.VerticalLayoutTargetDetails) event.getTargetDetails();

        Component sourceLayout = t.getSourceComponent();
        DDVerticalLayout targetLayout = (DDVerticalLayout) details.getTarget();
        Component tComponent = t.getComponent();

        VerticalDropLocation loc = details.getDropLocation();

        int indexTo = details.getOverIndex();
        int indexFrom = targetLayout.getComponentIndex(tComponent);

        if (tComponent == null) {
            return;
        }
        Component componentToAdd;

        if (sourceLayout == targetLayout) {
            componentToAdd = tComponent;
            if (indexFrom == indexTo) {
                return;
            }
            targetLayout.removeComponent(tComponent);
            if (indexTo > indexFrom) {
                indexTo--;
            }
            if (indexTo == -1) {
                targetLayout.addComponent(tComponent, indexFrom);
            }
        } else {
            componentToAdd = createDashboardElement(tComponent);
            if (indexTo == -1) {
                targetLayout.addComponent(componentToAdd, targetLayout.getComponentCount());
            }
        }

        if (indexTo != -1) {
            if (loc == VerticalDropLocation.MIDDLE || loc == VerticalDropLocation.BOTTOM) {
                indexTo++;
            }
            targetLayout.addComponent(componentToAdd, indexTo);
        }
    }

    private Component createDashboardElement(Component tComponent) {
        GroupBoxLayout groupBox = uiComponents.create(GroupBoxLayout.class);
        groupBox.setWidthFull();

        HBoxLayout layout = uiComponents.create(HBoxLayout.class);
        layout.setWidthFull();
        layout.setSpacing(true);

        Label<String> titleLabel = uiComponents.create(Label.NAME);
        titleLabel.setValue(tComponent.getCaption());
        titleLabel.setWidth("65px");
        titleLabel.setAlignment(io.jmix.ui.component.Component.Alignment.MIDDLE_CENTER);

        TextField<String> textField = uiComponents.create(TextField.NAME);

        Button deleteButton = uiComponents.create(Button.class);
        deleteButton.setIconFromSet(JmixIcon.TIMES);
        deleteButton.addClickListener(event -> {
            DDVerticalLayout ddLayout = (DDVerticalLayout) groupBox.unwrap(JmixGroupBox.class).getParent();
            ddLayout.removeComponent(groupBox.unwrap(JmixGroupBox.class));
        });

        layout.add(titleLabel, textField, deleteButton);
        layout.expand(textField);

        groupBox.add(layout);

        return groupBox.unwrap(JmixGroupBox.class);
    }
}
