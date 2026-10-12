package io.jmix.uisamples.view.flowui.components.virtuallist.simple;

import com.vaadin.flow.component.badge.Badge;
import com.vaadin.flow.component.badge.BadgeVariant;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import io.jmix.core.MetadataTools;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.details.JmixDetails;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Supply;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import io.jmix.uisamples.entity.Customer;
import io.jmix.uisamples.entity.CustomerGrade;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;

@ViewController("virtual-list-simple")
@ViewDescriptor("virtual-list-simple.xml")
public class VirtualListSimpleSample extends StandardView {

    // tag::renderer[] sample-hide
    @Autowired
    protected UiComponents uiComponents;
    @Autowired
    protected MetadataTools metadataTools;

    @Supply(to = "virtualList", subject = "renderer")
    protected Renderer<Customer> virtualListRenderer() {
        return new ComponentRenderer<>(customer -> {
            VerticalLayout infoLayout = createVerticalLayout();
            infoLayout.addClassNames("info-layout");

            H4 customerName = new H4(customer.getInstanceName());
            Badge gradeBadge = createGradeBadge(customer.getGrade());
            infoLayout.add(customerName, gradeBadge);

            HorizontalLayout infoLine = createHorizontalLayout();
            infoLine.setAlignItems(FlexComponent.Alignment.CENTER);

            H5 emailLabel = new H5("Email:");
            Span email = new Span(customer.getEmail());
            infoLine.add(emailLabel, email);

            HorizontalLayout infoLine2 = createHorizontalLayout();

            H5 ageLabel = new H5("Age:");
            Span age = new Span(String.valueOf(customer.getAge()));

            infoLine2.add(ageLabel, age);

            VerticalLayout additionalInfoLayout = createVerticalLayout();
            additionalInfoLayout.add(infoLine, infoLine2);

            JmixDetails infoDetails = uiComponents.create(JmixDetails.class);
            infoDetails.setSummaryText("Additional information");
            infoDetails.add(additionalInfoLayout);

            infoLayout.add(infoDetails, new Hr());
            return infoLayout;
        });
    }

    protected VerticalLayout createVerticalLayout() {
        VerticalLayout layout = uiComponents.create(VerticalLayout.class);
        layout.setSpacing(false);
        layout.setPadding(false);
        return layout;
    }

    protected HorizontalLayout createHorizontalLayout() {
        HorizontalLayout layout = uiComponents.create(HorizontalLayout.class);
        layout.setPadding(false);
        return layout;
    }

    protected Badge createGradeBadge(@Nullable CustomerGrade grade) {
        Badge gradeBadge = new Badge(metadataTools.format(grade));

        if (grade == CustomerGrade.HIGH) {
            gradeBadge.addThemeVariants(BadgeVariant.SUCCESS);
        } else if (grade == CustomerGrade.PREMIUM) {
            gradeBadge.addThemeVariants(BadgeVariant.FILLED);
        }

        return gradeBadge;
    }
    // end::renderer[] sample-hide
}
