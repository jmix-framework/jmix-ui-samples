package io.jmix.sampler.screen.ui.components.textfield.validator;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.TextField;
import io.jmix.ui.component.ValidationException;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("textfield-validator")
@UiDescriptor("textfield-validator.xml")
public class TextFieldValidatorSample extends ScreenFragment {

    @Autowired
    protected TextField<Integer> integerTextField;
    @Autowired
    protected TextField<Double> doubleTextField;
    @Autowired
    protected Notifications notifications;

    @Subscribe("validate")
    protected void onValidateClick(Button.ClickEvent event) {
        try {
            integerTextField.validate();
            doubleTextField.validate();
            notifications.create()
                    .withCaption("Validation successful")
                    .show();
        } catch (ValidationException e) {
            notifications.create()
                    .withCaption("Validation failed: " + e.getMessage())
                    .withType(Notifications.NotificationType.ERROR)
                    .show();
        }
    }
}
