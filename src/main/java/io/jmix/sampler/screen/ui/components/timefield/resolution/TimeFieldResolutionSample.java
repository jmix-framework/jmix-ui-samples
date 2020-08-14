package io.jmix.sampler.screen.ui.components.timefield.resolution;

import io.jmix.core.TimeSource;
import io.jmix.ui.component.TimeField;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@UiController("timefield-resolution")
@UiDescriptor("timefield-resolution.xml")
public class TimeFieldResolutionSample extends ScreenFragment {
    @Autowired
    protected TimeField<Date> hourTimeField;
    @Autowired
    protected TimeField<Date> minuteTimeField;
    @Autowired
    protected TimeField<Date> secondTimeField;

    @Autowired
    protected TimeSource timeSource;

    @Subscribe
    protected void onInit(InitEvent event) {
        hourTimeField.setValue(timeSource.currentTimestamp());
        minuteTimeField.setValue(timeSource.currentTimestamp());
        secondTimeField.setValue(timeSource.currentTimestamp());
    }
}
