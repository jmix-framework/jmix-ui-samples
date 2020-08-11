package io.jmix.sampler.screen.ui.components.label.formatter;

import io.jmix.core.TimeSource;
import io.jmix.ui.component.Label;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@UiController("label-formatter")
@UiDescriptor("label-formatter.xml")
public class LabelFormatterSample extends ScreenFragment {
    @Autowired
    protected Label<Date> dateLabel;
    @Autowired
    protected Label<Long> numberLabel;
    @Autowired
    protected TimeSource timeSource;

    @Subscribe
    protected void onInit(InitEvent event) {
        dateLabel.setValue(timeSource.currentTimestamp());
        numberLabel.setValue(timeSource.currentTimeMillis());
    }
}
