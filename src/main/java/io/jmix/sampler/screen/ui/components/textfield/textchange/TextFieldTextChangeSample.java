package io.jmix.sampler.screen.ui.components.textfield.textchange;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.TextInputField;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("textfield-text-change")
@UiDescriptor("textfield-text-change.xml")
public class TextFieldTextChangeSample extends ScreenFragment {
    @Autowired
    protected Notifications notifications;

    @Subscribe("textField")
    protected void onTextFieldTextChange(TextInputField.TextChangeEvent event) {
        notifications.create()
                .withCaption("Text Changed: " + event.getText())
                .show();
    }
}
