package io.jmix.uisamples.view.flowui.components.virtuallist.simple;

import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.dom.ThemeList;
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
            Span gradeSpan = createGradeSpan(customer.getGrade());
            infoLayout.add(customerName, gradeSpan);

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

    protected Span createGradeSpan(@Nullable CustomerGrade grade) {
        Span gradeSpan = new Span(metadataTools.format(grade));

        if (grade != null) {
            ThemeList gradeThemeList = gradeSpan.getElement().getThemeList();

            switch (grade) {
                case STANDARD -> gradeThemeList.add("badge contrast");
                case PREMIUM -> gradeThemeList.add("badge primary");
                default -> gradeThemeList.add("badge");
            }
        }

        return gradeSpan;
    }
}
