package io.jmix.sampler.screen.ui.components.javascript.timepicker.state;

import java.io.Serializable;

public class TimePickerState implements Serializable {
    public String now;             // hh:mm 24-hour format only, defaults to current time
    public boolean twentyFour;     // Display 24-hour format, defaults to false
    public boolean showSeconds;    // Whether to show seconds
}
