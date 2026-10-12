package io.jmix.uisamples.view.flowui.cookbook.composition7;

import com.vaadin.flow.component.badge.Badge;
import io.jmix.flowui.model.DataContext;
import io.jmix.flowui.view.*;
import io.jmix.uisamples.entity.Order;

@ViewController(id = "order-detail-2")
@ViewDescriptor(path = "order-detail-view.xml")
@EditedEntityContainer("orderDc")
public class OrderDetailView extends StandardDetailView<Order> {

    @ViewComponent
    private Badge infoPanel;
    @ViewComponent
    private MessageBundle messageBundle;

    @Subscribe(target = Target.DATA_CONTEXT)
    public void onChange(final DataContext.ChangeEvent event) {
        updateInfoPanel();
    }

    @Install(to = "itemsDataGrid.editAction", subject = "viewConfigurer")
    private void itemsDataGridEditActionViewConfigurer(final OrderItemDetailView orderItemDetailView) {
        orderItemDetailView.setChangeEventListener(this::updateInfoPanel);
    }

    private void updateInfoPanel() {
        infoPanel.setText(messageBundle.getMessage("infoPanel.text.unsavedChanges"));
        infoPanel.getElement().getThemeList().remove("success");
        infoPanel.getElement().getThemeList().add("error");
    }
}
