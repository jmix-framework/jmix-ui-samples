package io.jmix.uisamples.view.flowui.containers.card.themevariant;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.card.CardVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.streams.DownloadHandler;
import io.jmix.core.Messages;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.SupportsTypedValue.TypedValueChangeEvent;
import io.jmix.flowui.component.card.JmixCard;
import io.jmix.flowui.component.checkbox.JmixCheckbox;
import io.jmix.flowui.component.checkboxgroup.JmixCheckboxGroup;
import io.jmix.flowui.component.scroller.JmixScroller;
import io.jmix.flowui.component.select.JmixSelect;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@ViewController("card-theme-variant")
@ViewDescriptor("card-theme-variant.xml")
public class CardThemeVariantSample extends StandardView {

    @ViewComponent
    private JmixCard card;

    @ViewComponent
    private VerticalLayout slotsGroupContainer;

    @ViewComponent
    private JmixCheckboxGroup<String> themeGroup;

    @ViewComponent
    private MessageBundle messageBundle;

    @ViewComponent
    private JmixSelect<String> mediaSelect;

    @ViewComponent
    private JmixSelect<String> contentSelect;

    @ViewComponent
    private JmixCheckbox mediaCheckbox;

    @ViewComponent
    private JmixCheckbox headerPrefixCheckbox;

    @ViewComponent
    private JmixCheckbox headerCheckbox;

    @ViewComponent
    private JmixCheckbox headerSuffixCheckbox;

    @ViewComponent
    private JmixCheckbox titleCheckbox;

    @ViewComponent
    private JmixCheckbox subtitleCheckbox;

    @ViewComponent
    private JmixCheckbox contentCheckbox;

    @ViewComponent
    private JmixCheckbox footerCheckbox;

    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private Messages messages;

    @Subscribe
    public void onInit(final InitEvent event) {
        initSlotsGroup();
        initThemeGroup();
    }

    @Subscribe
    public void onReady(final ReadyEvent event) {
        updateSlotsFromCheckboxes();

        themeGroup.setTypedValue(List.of(
                CardVariant.OUTLINED.getVariantName(),
                CardVariant.ELEVATED.getVariantName(),
                CardVariant.COVER_MEDIA.getVariantName()
        ));
    }

    private void initSlotsGroup() {
        mediaSelect.setItems("Image", "Icon", "Avatar");
        mediaSelect.addValueChangeListener(e -> updateMedia(e.getValue()));
        mediaSelect.setValue("Image");

        contentSelect.setItems("Text", "Scroller", "Image & Text");
        contentSelect.addValueChangeListener(e -> updateContent(e.getValue()));
        contentSelect.setValue("Text");

        mediaCheckbox.addValueChangeListener(e -> updateSlotsFromCheckboxes());
        headerPrefixCheckbox.addValueChangeListener(e -> updateSlotsFromCheckboxes());
        headerCheckbox.addValueChangeListener(e -> updateSlotsFromCheckboxes());
        headerSuffixCheckbox.addValueChangeListener(e -> updateSlotsFromCheckboxes());
        titleCheckbox.addValueChangeListener(e -> updateSlotsFromCheckboxes());
        subtitleCheckbox.addValueChangeListener(e -> updateSlotsFromCheckboxes());
        contentCheckbox.addValueChangeListener(e -> updateSlotsFromCheckboxes());
        footerCheckbox.addValueChangeListener(e -> updateSlotsFromCheckboxes());

        mediaSelect.addValueChangeListener(e -> {
            if (Boolean.TRUE.equals(mediaCheckbox.getValue())) {
                updateMedia(e.getValue());
            }
        });
        contentSelect.addValueChangeListener(e -> {
            if (Boolean.TRUE.equals(contentCheckbox.getValue())) {
                updateContent(e.getValue());
            }
        });
    }

    private void updateSlotsFromCheckboxes() {
        card.setMedia(null);
        card.setHeaderPrefix(null);
        card.setHeader(null);
        card.setHeaderSuffix(null);
        card.setTitle((Component) null);
        card.setSubtitle((Component) null);
        card.removeAll();
        Arrays.stream(card.getFooterComponents()).forEach(Component::removeFromParent);

        mediaSelect.setEnabled(false);
        contentSelect.setEnabled(false);

        if (Boolean.TRUE.equals(mediaCheckbox.getValue())) {
            mediaSelect.setEnabled(true);
            updateMedia(mediaSelect.getValue());
        }
        if (Boolean.TRUE.equals(headerPrefixCheckbox.getValue())) {
            card.setHeaderPrefix(createAvatar());
        }
        if (Boolean.TRUE.equals(headerCheckbox.getValue())) {
            card.setHeader(createHeader());
        }
        if (Boolean.TRUE.equals(headerSuffixCheckbox.getValue())) {
            card.setHeaderSuffix(createBadge());
        }
        if (Boolean.TRUE.equals(titleCheckbox.getValue())) {
            card.setTitle("Jmix");
        }
        if (Boolean.TRUE.equals(subtitleCheckbox.getValue())) {
            card.setSubtitle(new Div("Modern Dev Platform"));
        }
        if (Boolean.TRUE.equals(contentCheckbox.getValue())) {
            contentSelect.setEnabled(true);
            updateContent(contentSelect.getValue());
        }
        if (Boolean.TRUE.equals(footerCheckbox.getValue())) {
            card.addToFooter(createFooter());
        }
    }

    private void updateMedia(String value) {
        Component media = switch (value) {
            case "Image" -> {
                Image image = uiComponents.create(Image.class);
                image.setSrc("icons/jmix-background.png");
                image.getElement().setAttribute("width", "150");
                yield image;
            }
            case "Icon" -> {
                Icon icon = VaadinIcon.VAADIN_H.create();
                icon.setSize("4em");
                yield icon;
            }
            case "Avatar" -> createAvatar();
            default -> throw new IllegalStateException("Unexpected value: " + value);
        };

        card.setMedia(media);
    }

    private void updateContent(String value) {
        Component content = switch (value) {
            case "Text" -> {
                Div div = new Div();
                div.setText(messageBundle.getMessage("card.text"));
                yield div;
            }
            case "Scroller" -> {
                JmixScroller scroller = uiComponents.create(JmixScroller.class);
                scroller.setHeight("5em");

                Div div = new Div();
                div.setText(messageBundle.getMessage("card.text-long"));

                scroller.setContent(div);
                yield scroller;
            }
            case "Image & Text" -> {
                Div div = new Div();
                div.addClassName("image-text-container");

                Image image = uiComponents.create(Image.class);
                image.setSizeFull();
                image.setSrc("icons/jmix-logo.png");

                div.add(image, new Span(messageBundle.getMessage("card.text")));
                yield div;
            }
            default -> throw new IllegalStateException("Unexpected value: " + value);
        };

        card.removeAll();
        card.add(content);
    }

    private void initThemeGroup() {
        themeGroup.setItems(
                CardVariant.OUTLINED.getVariantName(),
                CardVariant.ELEVATED.getVariantName(),
                CardVariant.HORIZONTAL.getVariantName(),
                CardVariant.STRETCH_MEDIA.getVariantName(),
                CardVariant.COVER_MEDIA.getVariantName()
        );
    }

    @Subscribe("themeGroup")
    public void onThemeGroupValueChange(TypedValueChangeEvent<JmixCheckboxGroup<String>, Collection<String>> event) {
        card.setThemeName("");

        if (event.getValue() == null) {
            return;
        }

        card.setThemeName(String.join(" ", event.getValue()));
    }

    @Install(to = "themeGroup", subject = "itemLabelGenerator")
    public String themeGroupItemLabelGenerator(String theme) {
        return StringUtils.capitalize(theme).replaceAll("-", " ");
    }

    private Component createAvatar() {
        Avatar avatar = uiComponents.create(Avatar.class);
        avatar.setImageHandler(DownloadHandler.forClassResource(getClass(),
                "/META-INF/resources/icons/jmix-icon.png"));
        return avatar;
    }

    private Component createHeader() {
        FlexLayout header = new FlexLayout();
        header.setFlexDirection(FlexLayout.FlexDirection.COLUMN_REVERSE);
        header.addClassNames("header");

        H2 title = new H2("Jmix");

        Div subtitle = new Div("Modern Dev Platform");
        subtitle.addClassNames("subtitle");

        header.add(title, subtitle);
        return header;
    }

    private Component createBadge() {
        Span span = new Span("Fullstack");
        span.getElement().getThemeList().add("badge success");
        return span;
    }

    private Component[] createFooter() {
        JmixButton visitSiteButton = uiComponents.create(JmixButton.class);
        visitSiteButton.setText(messageBundle.getMessage("visitSiteButton.text"));
        visitSiteButton.setIconAfterText(true);
        visitSiteButton.setIcon(VaadinIcon.EXTERNAL_LINK.create());
        visitSiteButton.addClickListener(__ ->
                UI.getCurrent().getPage().open(messages.getMessage("websiteUrl")));

        JmixButton learnMoreButton = uiComponents.create(JmixButton.class);
        learnMoreButton.setText(messageBundle.getMessage("learnMoreButton.text"));
        learnMoreButton.setIconAfterText(true);
        learnMoreButton.setIcon(VaadinIcon.QUESTION.create());
        learnMoreButton.addClickListener(__ ->
                UI.getCurrent().getPage().open(messages.getMessage("docUrl")));

        return new Component[]{visitSiteButton, learnMoreButton};
    }
}
