package io.jmix.sampler.screen.ui.components.datefield.simple;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.DateField;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@UiController("datefield-simple")
@UiDescriptor("datefield-simple.xml")
public class DateFieldSimpleSample extends ScreenFragment {

    @Autowired
    protected DateField<Date> dateField;

    @Autowired
    protected Notifications notifications;

    @Subscribe("showValueBtn")
    protected void onButtonClick(Button.ClickEvent event) {
        notifications.create()
                .withCaption(String.valueOf(dateField.getValue()))
                .show();
    }
}
