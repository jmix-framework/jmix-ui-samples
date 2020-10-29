package io.jmix.sampler.screen.ui.components.calendar.month;

import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.component.Calendar;
import io.jmix.ui.component.calendar.CalendarEvent;
import io.jmix.ui.screen.OpenMode;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@UiController("calendar-month")
@UiDescriptor("calendar-month.xml")
public class CalendarMonthSample extends ScreenFragment {

    @Autowired
    protected ScreenBuilders screenBuilders;

    @Subscribe("calendar")
    protected void onCalendarCalendarEventClick(Calendar.CalendarEventClickEvent<Date> event) {
        CalendarEventScreen screen = screenBuilders.screen(this)
                .withScreenClass(CalendarEventScreen.class)
                .withOpenMode(OpenMode.DIALOG)
                .build();

        CalendarEvent<Date> calendarEvent = event.getCalendarEvent();
        screen.setStartDateValue(calendarEvent.getStart());
        screen.setEndDateValue(calendarEvent.getEnd());
        screen.setCaptionValue(calendarEvent.getCaption());
        screen.setDescriptionValue(calendarEvent.getDescription());

        screen.show();
    }
}