package io.jmix.sampler.screen.ui.components.calendar.month;

import io.jmix.ui.component.Button;
import io.jmix.ui.component.Label;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

@UiController("calendar-event-screen")
@UiDescriptor("calendar-event-screen.xml")
public class CalendarEventScreen extends Screen {
    
    @Autowired
    protected Label<String> startDateValue;
    @Autowired
    protected Label<String> endDateValue;
    @Autowired
    protected Label<String> captionValue;
    @Autowired
    protected Label<String> descriptionValue;

    protected SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm");

    public void setStartDateValue(Date startDate) {
        startDateValue.setValue(simpleDateFormat.format(startDate));
    }

    public void setEndDateValue(Date endDate) {
        endDateValue.setValue(simpleDateFormat.format(endDate));
    }

    public void setCaptionValue(String caption) {
        captionValue.setValue(caption);
    }

    public void setDescriptionValue(String description) {
        descriptionValue.setValue(description);
    }

    @Subscribe("closeButton")
    protected void onCloseButtonClick(Button.ClickEvent event) {
        close(WINDOW_CLOSE_ACTION);
    }
}
