package io.jmix.uisamples.view.flowui.components.contexthelp.tooltip;

import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.shared.Tooltip;
import com.vaadin.flow.component.textfield.IntegerField;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.SupportsTypedValue;
import io.jmix.flowui.component.checkbox.JmixCheckbox;
import io.jmix.flowui.component.select.JmixSelect;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.kit.component.ComponentUtils;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.theme.StyleUtility;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@ViewController("context-help-tooltip")
@ViewDescriptor("context-help-tooltip.xml")
public class ContextHelpTooltipSample extends StandardView {

    @ViewComponent
    protected TypedTextField<String> manualTooltipField;
    @ViewComponent
    protected TypedTextField<String> customTooltipField;
    @ViewComponent
    protected JmixSelect<Tooltip.TooltipPosition> position;

    @Autowired
    protected UiComponents uiComponents;

    @Subscribe
    protected void onInit(InitEvent event) {
        initManualTooltip();
        initPositionItems();
    }

    protected void initManualTooltip() {
        JmixButton helperButton = createHelperButton();
        Tooltip tooltip = manualTooltipField.getTooltip();
        helperButton.addClickListener(e -> tooltip.setOpened(!tooltip.isOpened()));

        manualTooltipField.setSuffixComponent(helperButton);
    }

    protected void initPositionItems() {
        ComponentUtils.setItemsMap(position, getPositionItemsMap());
        position.setValue(Tooltip.TooltipPosition.TOP);
    }

    @Subscribe("focusDelay")
    protected void onFocusDelayChange(ComponentValueChangeEvent<IntegerField, Integer> event) {
        Integer value = event.getValue();

        if (value != null && value > 0 && value <= 5000) {
            customTooltipField.getTooltip().setFocusDelay(value);
        } else {
            customTooltipField.getTooltip().setFocusDelay(0);
        }
    }

    @Subscribe("hideDelay")
    protected void onHideDelayChange(ComponentValueChangeEvent<IntegerField, Integer> event) {
        Integer value = event.getValue();

        if (value != null && value > 0 && value <= 5000) {
            customTooltipField.getTooltip().setHideDelay(value);
        } else {
            customTooltipField.getTooltip().setHideDelay(0);
        }
    }

    @Subscribe("hoverDelay")
    protected void onHoverDelayChange(ComponentValueChangeEvent<IntegerField, Integer> event) {
        Integer value = event.getValue();

        if (value != null && value > 0 && value <= 5000) {
            customTooltipField.getTooltip().setHoverDelay(value);
        } else {
            customTooltipField.getTooltip().setHoverDelay(0);
        }
    }

    @Subscribe("manual")
    protected void onManualChange(ComponentValueChangeEvent<JmixCheckbox, Boolean> event) {
        Tooltip tooltip = customTooltipField.getTooltip();
        tooltip.setManual(event.getValue());

        if (event.getValue()) {
            JmixButton helperButton = createHelperButton();
            helperButton.addClickListener(e -> tooltip.setOpened(!tooltip.isOpened()));

            customTooltipField.setSuffixComponent(helperButton);
        } else {
            customTooltipField.setSuffixComponent(null);
        }
    }

    @Subscribe("position")
    protected void onPositionChange(
            ComponentValueChangeEvent<JmixSelect<Tooltip.TooltipPosition>, Tooltip.TooltipPosition> event) {
        customTooltipField.getTooltip().setPosition(event.getValue());
    }

    @Subscribe("text")
    protected void onTextChange(SupportsTypedValue.TypedValueChangeEvent<TypedTextField<String>, String> event) {
        customTooltipField.getTooltip().setText(event.getValue());
    }

    protected JmixButton createHelperButton() {
        JmixButton helperButton = uiComponents.create(JmixButton.class);
        helperButton.setIcon(VaadinIcon.QUESTION_CIRCLE.create());
        helperButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        helperButton.addClassName(StyleUtility.Button.LINK_BUTTON);

        return helperButton;
    }

    protected Map<Tooltip.TooltipPosition, String> getPositionItemsMap() {
        return Arrays.stream(Tooltip.TooltipPosition.values())
                .collect(Collectors.toMap(Function.identity(),mode -> mode.name().replace('_', ' ')));
    }
}
