package io.jmix.uisamples.view.flowui.components.icon.all;

import com.google.common.base.Strings;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyPressEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.IconFactory;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.theme.lumo.LumoIcon;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.radiobuttongroup.JmixRadioButtonGroup;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.theme.StyleUtility;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@ViewController("icon-all")
@ViewDescriptor("icon-all.xml")
public class IconAllSample extends StandardView {

    protected static final String ICON_LAYOUT_CLASS_NAME = "icon-layout";
    protected static final String ICON_INFO_CLASS_NAME = "icon-info-layout";
    protected static final String ICON_SIZE_CLASS_NAME = "icon-size";
    protected static final String ICON_DESCRIPTION_CLASS_NAME = "icon-info-description";
    protected static final String ICON_NAME_TEXT_CLASS_NAME = "icon-name-text";
    protected static final String ICON_FQN_TEXT_CLASS_NAME = "icon-fqn-text";

    protected static final String ICON_ATTRIBUTE_NAME = "icon";

    @ViewComponent
    protected JmixRadioButtonGroup<String> iconSetRadioButtonGroup;
    @ViewComponent
    protected TypedTextField<String> searchField;

    @Autowired
    protected UiComponents uiComponents;

    protected Scroller scroller;

    @Subscribe
    protected void onInit(InitEvent event) {
        initIconSetRadioGroupButton();
        initSearchField();
        initScroller();
        initLayout("");
    }

    protected void initScroller() {
        scroller = uiComponents.create(Scroller.class);
        scroller.setWidthFull();
        getContent().add(scroller);
    }

    protected void initLayout(String searchValue) {
        HorizontalLayout content = uiComponents.create(HorizontalLayout.class);
        content.setClassName(ICON_LAYOUT_CLASS_NAME);
        content.setPadding(true);

        if ("Vaadin".equals(iconSetRadioButtonGroup.getValue())) {
            initVaadinIconSet(content, searchValue);
        } else if ("Lumo".equals(iconSetRadioButtonGroup.getValue())) {
            initLumoIconSet(content, searchValue);
        }

        scroller.setContent(content);
    }

    protected void initVaadinIconSet(HorizontalLayout content, String searchValue) {
        for (VaadinIcon icon : VaadinIcon.values()) {
            if (Strings.isNullOrEmpty(searchValue)) {
                content.add(createIconInfo(icon, icon.name()));
            } else if (icon.name().toLowerCase().contains(searchValue.toLowerCase())) {
                content.add(createIconInfo(icon, icon.name()));
            }
        }
    }

    protected void initLumoIconSet(HorizontalLayout content, String searchValue) {
        for (LumoIcon icon : LumoIcon.values()) {
            if (Strings.isNullOrEmpty(searchValue)) {
                content.add(createIconInfo(icon, icon.name()));
            } else if (icon.name().toLowerCase().contains(searchValue.toLowerCase())) {
                content.add(createIconInfo(icon, icon.name()));
            }
        }
    }

    protected Component createIconInfo(IconFactory icon, String name) {
        Div div = uiComponents.create(Div.class);
        div.setClassName(ICON_INFO_CLASS_NAME);

        Icon vaadinIcon = icon.create();
        vaadinIcon.setClassName(ICON_SIZE_CLASS_NAME);

        Div infoDiv = uiComponents.create(Div.class);
        infoDiv.setClassName(ICON_DESCRIPTION_CLASS_NAME);

        Span iconName = uiComponents.create(Span.class);
        iconName.setClassName(ICON_NAME_TEXT_CLASS_NAME);
        iconName.setText(name);

        Span iconFqn = uiComponents.create(Span.class);
        iconFqn.setClassName(ICON_FQN_TEXT_CLASS_NAME);
        iconFqn.setText(vaadinIcon.getElement().getAttribute(ICON_ATTRIBUTE_NAME));

        infoDiv.add(iconName, iconFqn);

        div.add(vaadinIcon, infoDiv);
        return div;
    }

    protected void initIconSetRadioGroupButton() {
        iconSetRadioButtonGroup.setItems("Vaadin", "Lumo");
        iconSetRadioButtonGroup.setValue("Vaadin");

        iconSetRadioButtonGroup.addValueChangeListener(e -> doSearch());
    }

    protected void initSearchField() {
        JmixButton searchButton = createSearchButton();

        searchField.setSuffixComponent(searchButton);
        searchField.addKeyPressListener(Key.ENTER, this::searchFieldEnterPressListener);
    }

    protected void searchFieldEnterPressListener(KeyPressEvent keyPressEvent) {
        doSearch();
    }

    protected JmixButton createSearchButton() {
        JmixButton searchButton = uiComponents.create(JmixButton.class);
        searchButton.setIcon(VaadinIcon.SEARCH.create());
        searchButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        searchButton.addClassName(StyleUtility.Button.LINK_BUTTON);

        searchButton.addClickListener(this::searchButtonClickListener);

        return searchButton;
    }

    protected void searchButtonClickListener(ClickEvent<Button> buttonClickEvent) {
        doSearch();
    }

    protected void doSearch() {
        initLayout(searchField.getValue());
    }
}
