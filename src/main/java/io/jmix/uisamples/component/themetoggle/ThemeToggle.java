package io.jmix.uisamples.component.themetoggle;


import com.vaadin.flow.component.*;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.shared.HasTooltip;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.shared.Registration;
import io.jmix.flowui.kit.component.HasTitle;
import org.jspecify.annotations.Nullable;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Tag("theme-toggle")
@JsModule("./src/component/theme-switcher/theme-toggle.js")
public class ThemeToggle extends Component implements ClickNotifier<ThemeToggle>,
        Focusable<ThemeToggle>, HasTheme, HasEnabled, HasSize, HasStyle, HasText, HasTooltip, HasTitle {

    public static final String STORAGE_KEY_PROPERTY = "storageKey";
    public static final String THEME_CHANGED_EVENT = "theme-changed";
    private Component iconComponent;

    public ThemeToggle() {
        setIcon(getDefaultIcon());
    }

    public ThemeToggle(Component icon) {
        setIcon(icon);
    }

    public ThemeToggle(String text, Component icon) {
        setIcon(icon);
        setText(text);
    }

    private Icon getDefaultIcon() {
        Icon icon = VaadinIcon.ADJUST.create();
        icon.getElement().getStyle().set("rotate", "180deg");
        return icon;
    }

    /**
     * Sets the given string as the text content of this component.
     * <p>
     * This method removes any existing text-content and replaces it with the
     * given text.
     * <p>
     * This method also sets or removes this button's <code>theme=icon</code>
     * attribute based on whether this button contains only an icon after this
     * operation or not.
     *
     * @param text the text content to set, may be <code>null</code> to only
     *             remove existing text
     */
    @Override
    public void setText(@Nullable String text) {
        removeAll(getNonTextNodes());
        if (text != null && !text.isEmpty()) {
            getElement().appendChild(Element.createText(text));
        }
        updateThemeAttribute();
    }

    /**
     * Gets the component that is defined as the icon of this button.
     *
     * @return the icon of this button, or <code>null</code> if the icon is not
     * set
     */
    public Component getIcon() {
        return iconComponent;
    }

    /**
     * Sets the given component as the icon of this button.
     * <p>
     * Even though you can use almost any component as an icon, some good
     * options are {@code Icon} and {@link Image}.
     * <p>
     * This method also sets or removes this button's <code>theme=icon</code>
     * attribute based on whether this button contains only an icon after this
     * operation or not.
     *
     * @param icon component to be used as an icon, may be <code>null</code> to
     *             only remove the current icon, can't be a text-node
     */
    public void setIcon(@Nullable Component icon) {
        if (icon != null && icon.getElement().isTextNode()) {
            throw new IllegalArgumentException(
                    "Text node can't be used as an icon.");
        }
        if (iconComponent != null) {
            remove(iconComponent);
        }

        iconComponent = icon;
        if (icon != null) {
            add(icon);
            updateIconSlot();
        }

        updateThemeAttribute();
    }

    private void updateIconSlot() {
        iconComponent.getElement().setAttribute("slot", "prefix");
    }

    /**
     * Adds the given components as children of this component.
     * <p>
     * Note that using this method together with convenience methods, such as
     * {@link #setText(String)} and {@link #setIcon(Component)}, may have
     * unexpected results, e.g. in the order of child elements and the result of
     * {@link #getText()}.
     *
     * @param components the components to add
     */
    private void add(Component... components) {
        for (Component component : components) {
            assert component != null;
            getElement().appendChild(component.getElement());
        }
    }

    /**
     * Removes the given child components from this component.
     *
     * @param components The components to remove.
     * @throws IllegalArgumentException if any of the components is not a child of this component.
     */
    private void remove(Component... components) {
        for (Component component : components) {
            if (getElement().equals(component.getElement().getParent())) {
                component.getElement().removeAttribute("slot");
                getElement().removeChild(component.getElement());
            } else {
                throw new IllegalArgumentException("The given component ("
                        + component + ") is not a child of this component");
            }
        }
    }

    /**
     * Get the state for the autofocus property of the button.
     * <p>
     * This property is not synchronized automatically from the client side, so
     * the returned value may not be the same as in client side.
     *
     * @return the {@code autofocus} property from the button
     */
    public boolean isAutofocus() {
        return getElement().getProperty("autofocus", false);
    }

    /**
     * Set the button to be input focused when the page loads.
     *
     * @param autofocus the boolean value to set
     */
    public void setAutofocus(boolean autofocus) {
        getElement().setProperty("autofocus", autofocus);
    }

    public String getStorageKey() {
        return getElement().getProperty(STORAGE_KEY_PROPERTY);
    }

    public void setStorageKey(String storageKey) {
        getElement().setProperty(STORAGE_KEY_PROPERTY, storageKey);
    }

    /**
     * Removes all contents from this component except elements in
     * {@code exclusion} array. This includes child components, text content as
     * well as child elements that have been added directly to this component
     * using the {@link Element} API.
     */
    private void removeAll(Element... exclusion) {
        Set<Element> toExclude = Stream.of(exclusion)
                .collect(Collectors.toSet());
        Predicate<Element> filter = toExclude::contains;
        getElement().getChildren().filter(filter.negate())
                .forEach(child -> child.removeAttribute("slot"));
        getElement().removeAllChildren();
        getElement().appendChild(exclusion);
    }

    private Element[] getNonTextNodes() {
        return getElement().getChildren()
                .filter(element -> !element.isTextNode())
                .toArray(Element[]::new);
    }

    private void updateThemeAttribute() {
        // Add theme attribute "icon" when the button contains only an icon to
        // fully support themes like Lumo. This doesn't override explicitly set
        // theme attribute.
        long childCount = getElement().getChildren()
                .filter(el ->
                        el.isTextNode() || !"vaadin-tooltip".equals(el.getTag()))
                .count();

        if (childCount == 1 && iconComponent != null) {
            getThemeNames().add("icon");
        } else {
            getThemeNames().remove("icon");
        }
    }

    public Registration addThemeChangeListener(ComponentEventListener<ThemeToggleThemeChangedEvent> listener) {
        return addListener(ThemeToggleThemeChangedEvent.class, listener);
    }

    @DomEvent(THEME_CHANGED_EVENT)
    public static class ThemeToggleThemeChangedEvent extends ComponentEvent<ThemeToggle> {

        protected String value;

        public ThemeToggleThemeChangedEvent(ThemeToggle source, boolean fromClient,
                                            @EventData("event.detail.value") String value) {
            super(source, fromClient);
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
