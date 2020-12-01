package io.jmix.sampler.screen.ui.components.valuepicker.editablefield;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.ValuePicker;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("valuepicker-editable-field")
@UiDescriptor("valuepicker-editable-field.xml")
public class ValuePickerEditableFieldSample extends ScreenFragment {

    @Autowired
    protected ValuePicker<String> valuePicker;

    @Autowired
    protected Notifications notifications;

    @Subscribe("valuePicker")
    protected void onValuePickerFieldValueChange(ValuePicker.FieldValueChangeEvent<String> event) {
        String text = event.getText();

        notifications.create()
                .withCaption("Entered value: " + text)
                .show();

        valuePicker.setValue(text);
    }
}
