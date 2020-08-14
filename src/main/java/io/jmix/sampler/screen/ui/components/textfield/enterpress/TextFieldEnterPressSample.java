package io.jmix.sampler.screen.ui.components.textfield.enterpress;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.TextInputField;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("textfield-enter-press")
@UiDescriptor("textfield-enter-press.xml")
public class TextFieldEnterPressSample extends ScreenFragment {

    @Autowired
    protected Notifications notifications;

    @Subscribe("textField")
    protected void onTextFieldEnterPress(TextInputField.EnterPressEvent event) {
        notifications.create()
                .withCaption("Enter Pressed: " + event.getSource().getValue())
                .show();
    }
}
