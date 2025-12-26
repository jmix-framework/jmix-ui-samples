package io.jmix.uisamples.view.flowui.containers.card.themevariant;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.card.CardVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.server.streams.DownloadHandler;
import com.vaadin.flow.theme.lumo.LumoUtility;
import io.jmix.core.Messages;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.SupportsTypedValue.TypedValueChangeEvent;
import io.jmix.flowui.component.card.JmixCard;
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
    private JmixCheckboxGroup<String> slotsGroup;
    @ViewComponent
    private JmixCheckboxGroup<String> themeGroup;

    @ViewComponent
    private MessageBundle messageBundle;

    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private Messages messages;

    private JmixSelect<String> mediaSelect;
    private JmixSelect<String> contentSelect;

    @Subscribe
    public void onInit(final InitEvent event) {
        initSlotsGroup();
        initThemeGroup();
    }

    @Subscribe
    public void onReady(final ReadyEvent event) {
        slotsGroup.setTypedValue(List.of("Media", "Content", "Title", "Subtitle", "Header Suffix", "Footer"));
        themeGroup.setTypedValue(List.of(
                CardVariant.LUMO_OUTLINED.getVariantName(),
                CardVariant.LUMO_ELEVATED.getVariantName(),
                CardVariant.LUMO_COVER_MEDIA.getVariantName()
        ));
    }

    private void initSlotsGroup() {
        slotsGroup.setItems(
                "Media", "Header Prefix", "Header", "Header Suffix",
                "Title", "Subtitle", "Content", "Footer"
        );
    }

    @Supply(to = "slotsGroup", subject = "renderer")
    public ComponentRenderer<Component, String> slotsGroupRenderer() {
        return new ComponentRenderer<>(slot ->
                switch (slot) {
                    case "Media" -> initMediaSelect();
                    case "Content" -> initContentSelect();
                    default -> new Text(slot);
                });
    }

    private Component initMediaSelect() {
        Div div = new Div();
        div.addClassNames(LumoUtility.Display.FLEX, LumoUtility.AlignItems.BASELINE);

        mediaSelect = uiComponents.create(JmixSelect.class);
        mediaSelect.addClassName("card-select");

        mediaSelect.setItems("Image", "Icon", "Avatar");
        mediaSelect.addValueChangeListener(e -> updateMedia(e.getValue()));

        mediaSelect.setValue("Image");

        div.add(new Text("Media:"), mediaSelect);
        return div;
    }

    private Component initContentSelect() {
        Div div = new Div();
        div.addClassNames(LumoUtility.Display.FLEX, LumoUtility.AlignItems.BASELINE);

        contentSelect = uiComponents.create(JmixSelect.class);
        contentSelect.addClassName("card-select");

        contentSelect.setItems("Text", "Scroller", "Image & Text");
        contentSelect.addValueChangeListener(e -> updateContent(e.getValue()));

        contentSelect.setValue("Text");

        div.add(new Text("Content:"), contentSelect);
        return div;
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
                div.addClassName(LumoUtility.Gap.SMALL);

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
                CardVariant.LUMO_OUTLINED.getVariantName(),
                CardVariant.LUMO_ELEVATED.getVariantName(),
                CardVariant.LUMO_HORIZONTAL.getVariantName(),
                CardVariant.LUMO_STRETCH_MEDIA.getVariantName(),
                CardVariant.LUMO_COVER_MEDIA.getVariantName()
        );
    }

    @Subscribe("slotsGroup")
    public void onSlotsGroupValueChange(TypedValueChangeEvent<JmixCheckboxGroup<String>, Collection<String>> event) {
        if (event.getValue() == null) {
            return;
        }

        // clear component

        card.setMedia(null);
        card.setHeaderPrefix(null);
        card.setHeader(null);
        card.setHeaderSuffix(null);
        card.setTitle((Component) null);
        card.setSubtitle(null);
        card.removeAll();
        Arrays.stream(card.getFooterComponents()).forEach(Component::removeFromParent);

        mediaSelect.setEnabled(false);
        contentSelect.setEnabled(false);

        for (String slot : event.getValue()) {
            switch (slot) {
                case "Media" -> {
                    mediaSelect.setEnabled(true);
                    updateMedia(mediaSelect.getValue());
                }
                case "Header Prefix" -> {
                    Component avatar = createAvatar();
                    card.setHeaderPrefix(avatar);
                }
                case "Header" -> {
                    Component header = createHeader();
                    card.setHeader(header);
                }
                case "Header Suffix" -> {
                    Component badge = createBadge();
                    card.setHeaderSuffix(badge);
                }
                case "Title" -> card.setTitle("Jmix");
                case "Subtitle" -> card.setSubtitle(new Div("Modern Dev Platform"));
                case "Content" -> {
                    contentSelect.setEnabled(true);
                    updateContent(contentSelect.getValue());
                }
                case "Footer" -> {
                    Component[] footerComponents = createFooter();
                    card.addToFooter(footerComponents);
                }
            }
        }
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
        Div header = new Div();
        header.addClassNames(
                LumoUtility.Display.FLEX,
                LumoUtility.FlexDirection.COLUMN_REVERSE,
                LumoUtility.LineHeight.XSMALL
        );

        H2 title = new H2("Jmix");

        Div subtitle = new Div("Modern Dev Platform");
        subtitle.addClassNames(
                LumoUtility.TextTransform.UPPERCASE,
                LumoUtility.FontSize.XSMALL,
                LumoUtility.TextColor.SECONDARY
        );

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
