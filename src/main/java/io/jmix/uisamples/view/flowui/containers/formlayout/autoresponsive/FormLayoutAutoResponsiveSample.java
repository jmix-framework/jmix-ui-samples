package io.jmix.uisamples.view.flowui.containers.formlayout.autoresponsive;

import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.ClickEvent;
import io.jmix.flowui.component.checkbox.JmixCheckbox;
import io.jmix.flowui.component.formlayout.JmixFormLayout;
import io.jmix.flowui.component.textfield.JmixIntegerField;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;

import static io.jmix.core.common.util.Numbers.nullToZero;

@ViewController("form-layout-auto-responsive")
@ViewDescriptor("form-layout-auto-responsive.xml")
public class FormLayoutAutoResponsiveSample extends StandardView {

    @ViewComponent
    private JmixFormLayout formLayout;
    @ViewComponent
    private JmixIntegerField maxColumnsField;
    @ViewComponent
    private JmixIntegerField minColumnsField;

    @Subscribe("columnSpacingField")
    public void onColumnSpacingFieldComponentValueChange(final ComponentValueChangeEvent<TypedTextField<String>, String> event) {
        formLayout.setColumnSpacing(event.getValue());
    }

    @Subscribe("rowSpacingField")
    public void onRowSpacingFieldComponentValueChange(final ComponentValueChangeEvent<TypedTextField<String>, String> event) {
        formLayout.setRowSpacing(event.getValue());
    }

    @Subscribe("labelSpacingField")
    public void onLabelSpacingFieldComponentValueChange(final ComponentValueChangeEvent<TypedTextField<String>, String> event) {
        formLayout.setLabelSpacing(event.getValue());
    }

    @Subscribe("expandColumnsField")
    public void onExpandColumnsFieldComponentValueChange(final ComponentValueChangeEvent<JmixCheckbox, Boolean> event) {
        formLayout.setExpandColumns(event.getValue());
    }

    @Subscribe("expandFieldsField")
    public void onExpandFieldsFieldComponentValueChange(final ComponentValueChangeEvent<JmixCheckbox, Boolean> event) {
        formLayout.setExpandFields(event.getValue());
    }

    @Subscribe("labelsAsideField")
    public void onLabelsAsideFieldComponentValueChange(final ComponentValueChangeEvent<JmixCheckbox, Boolean> event) {
        formLayout.setLabelsAside(event.getValue());
    }

    @Subscribe("maxColumnsField")
    public void onMaxColumnsFieldComponentValueChange(final ComponentValueChangeEvent<JmixIntegerField, Integer> event) {
        formLayout.setMaxColumns(nullToZero(event.getValue()));
    }

    @Subscribe("minColumnsField")
    public void onMinColumnsFieldComponentValueChange(final ComponentValueChangeEvent<JmixIntegerField, Integer> event) {
        formLayout.setMinColumns(nullToZero(event.getValue()));
    }

    @Subscribe(id = "maxColumnsFieldMinus", subject = "clickListener")
    public void onMaxColumnsFieldMinusClick(final ClickEvent<JmixButton> event) {
        maxColumnsField.setValue(nullToZero(maxColumnsField.getValue()) - 1);
    }

    @Subscribe(id = "maxColumnsFieldPlus", subject = "clickListener")
    public void onMaxColumnsFieldPlusClick(final ClickEvent<JmixButton> event) {
        maxColumnsField.setValue(nullToZero(maxColumnsField.getValue()) + 1);
    }

    @Subscribe(id = "minColumnsFieldMinus", subject = "clickListener")
    public void onMinColumnsFieldMinusClick(final ClickEvent<JmixButton> event) {
        minColumnsField.setValue(nullToZero(minColumnsField.getValue()) - 1);
    }

    @Subscribe(id = "minColumnsFieldPlus", subject = "clickListener")
    public void onMinColumnsFieldPlusClick(final ClickEvent<JmixButton> event) {
        minColumnsField.setValue(nullToZero(minColumnsField.getValue()) + 1);
    }
}
