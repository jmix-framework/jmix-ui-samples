package io.jmix.sampler.screen.ui.predefinedstyles.styles.misc;

import io.jmix.core.TimeSource;
import io.jmix.ui.component.DateField;
import io.jmix.ui.component.ProgressBar;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@UiController("styles-misc")
@UiDescriptor("styles-misc.xml")
public class StylesMiscSample extends ScreenFragment {
    @Autowired
    protected ProgressBar defaultPB;
    @Autowired
    protected ProgressBar pointPB;
    @Autowired
    protected DateField<Date> dateField;

    @Autowired
    protected TimeSource timeSource;

    @Subscribe
    protected void onInit(InitEvent event) {
        defaultPB.setValue(0.4);
        pointPB.setValue(0.8);

        dateField.setValue(timeSource.currentTimestamp());
    }
}
