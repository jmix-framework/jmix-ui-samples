package io.jmix.sampler.screen.ui.components.colorpicker.simple;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.ColorPicker;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("colorpicker-simple")
@UiDescriptor("colorpicker-simple.xml")
public class ColorPickerSimpleSample extends ScreenFragment {

    @Autowired
    protected ColorPicker colorPicker;
    @Autowired
    protected Notifications notifications;

    @Subscribe("button")
    protected void onButtonClick(Button.ClickEvent event) {
        notifications.create()
                .withCaption(String.valueOf(colorPicker.getValue()))
                .show();
    }
}
