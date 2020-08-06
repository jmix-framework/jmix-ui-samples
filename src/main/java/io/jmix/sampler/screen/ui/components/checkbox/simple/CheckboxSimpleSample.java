package io.jmix.sampler.screen.ui.components.checkbox.simple;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.CheckBox;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("checkbox-simple")
@UiDescriptor("checkbox-simple.xml")
public class CheckboxSimpleSample extends ScreenFragment {

    @Autowired
    protected CheckBox carField;

    @Autowired
    protected Notifications notifications;

    @Subscribe
    protected void onInit(InitEvent event) {
        carField.setValue(true);
        carField.addValueChangeListener(e ->
                notifications.create()
                        .withCaption(Boolean.TRUE.equals(e.getValue())
                                ? "I have a car"
                                : "I don't have a car")
                        .show());
    }
}