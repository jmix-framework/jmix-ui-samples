package io.jmix.uisamples.view.flowui.components.datagrid.customrenderer;

import com.vaadin.flow.component.badge.Badge;
import com.vaadin.flow.component.badge.BadgeVariant;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import io.jmix.core.Messages;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.checkbox.JmixCheckbox;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Supply;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import io.jmix.uisamples.entity.Customer;
import io.jmix.uisamples.entity.CustomerGrade;
import org.springframework.beans.factory.annotation.Autowired;

@ViewController("data-grid-custom-renderer")
@ViewDescriptor("data-grid-custom-renderer.xml")
public class DataGridCustomRendererSample extends StandardView {

    // tag::component-renderer[] sample-hide
    @Autowired
    protected UiComponents uiComponents;
    @Autowired
    protected Messages messages;

    @Supply(to = "customersDataGrid.active", subject = "renderer")
    protected Renderer<Customer> activeComponentRenderer() {
        return new ComponentRenderer<>(
                () -> {
                    JmixCheckbox checkbox = uiComponents.create(JmixCheckbox.class);
                    checkbox.setReadOnly(true);
                    return checkbox;
                },
                (checkbox, customer) -> checkbox.setValue(customer.isActive())
        );
    }

    @Supply(to = "customersDataGrid.grade", subject = "renderer")
    protected Renderer<Customer> statusComponentRenderer() {
        return new ComponentRenderer<>(this::createGradeComponent, this::gradeComponentUpdater);
    }

    protected Badge createGradeComponent() {
        return uiComponents.create(Badge.class);
    }

    protected void gradeComponentUpdater(Badge badge, Customer customer) {
        badge.getElement().getThemeList().clear();

        CustomerGrade grade = customer.getGrade();
        if (grade == null) {
            badge.setText("No data");
            return;
        }

        badge.setText(messages.getMessage(CustomerGrade.class, grade.name()));

        if (grade == CustomerGrade.HIGH) {
            badge.addThemeVariants(BadgeVariant.SUCCESS);
        } else if (grade == CustomerGrade.PREMIUM) {
            badge.addThemeVariants(BadgeVariant.FILLED);
        }
    }
    // end::component-renderer[] sample-hide
}
