package io.jmix.sampler.screen.ui.components.datepicker.simple;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.DatePicker;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@UiController("datepicker-simple")
@UiDescriptor("datepicker-simple.xml")
public class DatePickerSimpleSample extends ScreenFragment {

    @Autowired
    protected DatePicker<Date> datePicker;

    @Autowired
    protected Notifications notifications;

    @Subscribe("button")
    protected void onButtonClick(Button.ClickEvent event) {
        notifications.create()
                .withCaption(String.valueOf(datePicker.getValue()))
                .show();
    }
}
