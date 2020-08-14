package io.jmix.sampler.screen.ui.components.passwordfield;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.PasswordField;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("passwordfield-simple")
@UiDescriptor("passwordfield-simple.xml")
public class PasswordFieldSimpleSample extends ScreenFragment {

    @Autowired
    protected PasswordField passwordField;
    @Autowired
    protected Notifications notifications;

    @Subscribe("button")
    protected void onButtonClick(Button.ClickEvent event) {
        notifications.create()
                .withCaption(passwordField.getValue())
                .show();
    }
}
