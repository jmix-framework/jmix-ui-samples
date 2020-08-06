package io.jmix.sampler.screen.ui.components.calendar.week;

import io.jmix.core.Messages;
import io.jmix.sampler.entity.CalendarEventStyle;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.Calendar;
import io.jmix.ui.component.CheckBox;
import io.jmix.ui.component.ComboBox;
import io.jmix.ui.component.DateField;
import io.jmix.ui.component.TextField;
import io.jmix.ui.component.calendar.SimpleCalendarEvent;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

@UiController("calendar-week")
@UiDescriptor("calendar-week.xml")
public class CalendarWeekSample extends ScreenFragment {
    @Autowired
    protected Calendar<Date> calendar;
    @Autowired
    protected CheckBox isAllDay;
    @Autowired
    protected DateField<Date> startDateField;
    @Autowired
    protected DateField<Date> endDateField;
    @Autowired
    protected TextField<String> descriptionField;
    @Autowired
    protected TextField<String> captionField;
    @Autowired
    protected ComboBox<CalendarEventStyle> styleNameField;

    @Autowired
    protected Messages messages;

    protected SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Subscribe
    protected void onInit(InitEvent event) {
        startDateField.setValue(df.parse("2020-07-20 09:00", new ParsePosition(0)));
        endDateField.setValue(df.parse("2020-07-20 16:00", new ParsePosition(0)));
        captionField.setValue(messages.getMessage("io.jmix.sampler.entity", "CalendarEvent.caption"));
        descriptionField.setValue(messages.getMessage("io.jmix.sampler.entity", "CalendarEvent.description"));
        styleNameField.setValue(CalendarEventStyle.RED);
        generateEvents();
    }

    @Subscribe("addEvent")
    protected void onAddEventClick(Button.ClickEvent event) {
        generateEvent(
                captionField.getValue(),
                descriptionField.getValue(),
                startDateField.getValue(),
                endDateField.getValue(),
                isAllDay.getValue(),
                styleNameField.getValue().getId()
        );
    }

    protected void generateEvents() {
        String[] captions = {"Training", "Development", "Design", "Weekend", "Party"};
        String[] descriptions = {
                "Student training",
                "Platform development",
                "UI development",
                "Weekend",
                "Party with friends"
        };

        generateEvent(captions[0], descriptions[0], "2020-07-17 09:00", "2020-07-17 14:00", false, "event-blue");
        generateEvent(captions[1], descriptions[1], "2020-07-17 15:00", "2020-07-17 18:00", false, "event-red");
        generateEvent(captions[2], descriptions[2], "2020-07-18 08:00", "2020-07-18 12:00", false, "event-yellow");
        generateEvent(captions[1], descriptions[1], "2020-07-18 13:00", "2020-07-18 18:00", false, "event-red");
        generateEvent(captions[3], descriptions[3], "2020-07-19 00:00", "2020-07-19 23:59", true, "event-green");
        generateEvent(captions[2], descriptions[2], "2020-07-20 08:00", "2020-07-20 12:00", false, "event-yellow");
        generateEvent(captions[1], descriptions[1], "2020-07-20 13:00", "2020-07-20 18:00", false, "event-red");
        generateEvent(captions[4], descriptions[4], "2020-07-20 19:00", "2020-07-21 07:59", false, "event-green");
        generateEvent(captions[0], descriptions[0], "2020-07-21 09:00", "2020-07-21 14:00", false, "event-blue");
        generateEvent(captions[1], descriptions[1], "2020-07-21 15:00", "2020-07-21 18:00", false, "event-red");
        generateEvent(captions[2], descriptions[2], "2020-07-22 08:00", "2020-07-22 12:00", false, "event-yellow");
        generateEvent(captions[1], descriptions[1], "2020-07-22 13:00", "2020-07-22 18:00", false, "event-red");
        generateEvent(captions[0], descriptions[0], "2020-07-23 09:00", "2020-07-23 14:00", false, "event-blue");
        generateEvent(captions[1], descriptions[1], "2020-07-23 15:00", "2020-07-23 18:00", false, "event-red");
    }

    protected void generateEvent(String caption, String description, String start, String end,
                                 boolean isAllDay, String stylename) {
        generateEvent(
                caption,
                description,
                df.parse(start, new ParsePosition(0)),
                df.parse(end, new ParsePosition(0)),
                isAllDay,
                stylename
        );
    }

    protected void generateEvent(String caption, String description, Date start, Date end,
                                 boolean isAllDay, String stylename) {
        SimpleCalendarEvent<Date> calendarEvent = new SimpleCalendarEvent<>();
        calendarEvent.setCaption(caption);
        calendarEvent.setDescription(description);
        calendarEvent.setStart(start);
        calendarEvent.setEnd(end);
        calendarEvent.setAllDay(isAllDay);
        calendarEvent.setStyleName(stylename);

        calendar.getEventProvider().addEvent(calendarEvent);
    }
}