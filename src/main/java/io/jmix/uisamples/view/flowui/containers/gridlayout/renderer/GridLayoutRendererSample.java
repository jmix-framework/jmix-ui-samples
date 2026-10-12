package io.jmix.uisamples.view.flowui.containers.gridlayout.renderer;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.badge.Badge;
import com.vaadin.flow.component.badge.BadgeVariant;
import com.vaadin.flow.component.card.CardVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import io.jmix.core.MessageTools;
import io.jmix.core.Metadata;
import io.jmix.core.MetadataTools;
import io.jmix.core.metamodel.model.MetaClass;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.card.JmixCard;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Supply;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import io.jmix.uisamples.entity.Customer;
import io.jmix.uisamples.entity.CustomerGrade;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

@ViewController("grid-layout-renderer")
@ViewDescriptor("grid-layout-renderer.xml")
public class GridLayoutRendererSample extends StandardView {

    // tag::renderer[] sample-hide
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private MetadataTools metadataTools;
    @Autowired
    private MessageTools messageTools;
    @Autowired
    private Metadata metadata;

    private int colorIndex;

    @Supply(to = "gridLayout", subject = "renderer")
    public ComponentRenderer<JmixCard, Customer> customerRenderer() {
        return new ComponentRenderer<>(this::createCard, this::initCard);
    }

    private JmixCard createCard() {
        JmixCard card = uiComponents.create(JmixCard.class);
        card.setWidthFull();
        card.addThemeVariants(CardVariant.OUTLINED, CardVariant.ELEVATED);
        return card;
    }

    private void initCard(JmixCard card, Customer customer) {
        card.setHeaderPrefix(createAvatar(customer));
        card.setTitle(customer.getInstanceName());
        card.setSubtitle(createSubtitle(customer));
        card.add(createAdditionalInfo(customer));
    }

    private Avatar createAvatar(Customer customer) {
        Avatar avatar = new Avatar(customer.getInstanceName());
        avatar.setColorIndex(colorIndex++ % 7);
        return avatar;
    }

    private Component createSubtitle(Customer customer) {
        Span span = uiComponents.create(Span.class);
        String ageCaption = getPropertyCaption(customer, "age");
        span.setText("%s: %s".formatted(ageCaption, customer.getAge()));
        return span;
    }

    private Component createAdditionalInfo(Customer customer) {
        HorizontalLayout emailLayout = uiComponents.create(HorizontalLayout.class);
        emailLayout.setPadding(false);
        emailLayout.add(createPropertyCaption(customer, "email"), new Span(customer.getEmail()));

        HorizontalLayout gradeLayout = uiComponents.create(HorizontalLayout.class);
        gradeLayout.setPadding(false);
        gradeLayout.add(createPropertyCaption(customer, "grade"), createGradeBadge(customer));

        VerticalLayout infoLayout = uiComponents.create(VerticalLayout.class);
        infoLayout.add(emailLayout, gradeLayout);
        return infoLayout;
    }

    private Component createPropertyCaption(Customer customer, String property) {
        Span span = uiComponents.create(Span.class);
        String propertyCaption = getPropertyCaption(customer, property);
        span.setText("%s: ".formatted(propertyCaption));
        return span;
    }

    private String getPropertyCaption(Customer customer, String property) {
        MetaClass metaClass = metadata.getClass(customer);
        return messageTools.getPropertyCaption(metaClass, property);
    }

    private Component createGradeBadge(Customer customer) {
        CustomerGrade gradeValue = Objects.requireNonNull(customer.getGrade());

        Badge badge = uiComponents.create(Badge.class);
        badge.setText(metadataTools.format(gradeValue));

        if (gradeValue == CustomerGrade.HIGH) {
            badge.addThemeVariants(BadgeVariant.SUCCESS);
        } else if (gradeValue == CustomerGrade.PREMIUM) {
            badge.addThemeVariants(BadgeVariant.FILLED);
        }

        return badge;
    }
    // end::renderer[] sample-hide
}
