package io.jmix.sampler.screen.ui.components.maskedfield;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.MaskedField;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("maskedfield-simple")
@UiDescriptor("maskedfield-simple.xml")
public class MaskedFieldSimpleSample extends ScreenFragment {

    @Autowired
    protected MaskedField<String> phoneFieldMasked;
    @Autowired
    protected MaskedField<String> phoneFieldClear;

    @Autowired
    protected Notifications notifications;

    @Subscribe("showMasked")
    protected void onShowMaskedClick(Button.ClickEvent event) {
        notifications.create()
                .withCaption(phoneFieldMasked.getValue())
                .show();
    }

    @Subscribe("showClear")
    protected void onShowClearClick(Button.ClickEvent event) {
        notifications.create()
                .withCaption(phoneFieldClear.getValue())
                .show();
    }
}
