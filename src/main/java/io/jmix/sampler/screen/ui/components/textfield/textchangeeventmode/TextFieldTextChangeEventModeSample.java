package io.jmix.sampler.screen.ui.components.textfield.textchangeeventmode;

import io.jmix.ui.component.HasValue;
import io.jmix.ui.component.RadioButtonGroup;
import io.jmix.ui.component.TextField;
import io.jmix.ui.component.TextInputField;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@UiController("textfield-text-change-event-mode")
@UiDescriptor("textfield-text-change-event-mode.xml")
public class TextFieldTextChangeEventModeSample extends ScreenFragment {
    @Autowired
    protected TextField<String> textField;
    @Autowired
    protected RadioButtonGroup<TextInputField.TextChangeEventMode> modeGroup;

    @Subscribe
    protected void onInit(InitEvent event) {
        Map<String, TextInputField.TextChangeEventMode> modes = new HashMap<>();
        Arrays.stream(TextInputField.TextChangeEventMode.values()).forEach(mode -> modes.put(mode.toString(), mode));
        modeGroup.setOptionsMap(modes);
        modeGroup.setValue(textField.getTextChangeEventMode());

        updateTextFieldCaption(0);
    }

    @Subscribe("modeGroup")
    protected void onModeGroupValueChange(HasValue.ValueChangeEvent<TextInputField.TextChangeEventMode> event) {
        textField.setTextChangeEventMode(event.getValue());
    }

    @Subscribe("textField")
    protected void onTextFieldTextChange(TextInputField.TextChangeEvent event) {
        updateTextFieldCaption(event.getText().length());
    }

    protected void updateTextFieldCaption(int length) {
        textField.setCaption("Characters left: " + (textField.getMaxLength() - length));
    }
}
