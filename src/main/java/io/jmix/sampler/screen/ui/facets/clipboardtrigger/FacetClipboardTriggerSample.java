package io.jmix.sampler.screen.ui.facets.clipboardtrigger;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.ClipboardTrigger;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("facet-clipboard-trigger")
@UiDescriptor("facet-clipboard-trigger.xml")
public class FacetClipboardTriggerSample extends ScreenFragment {

    @Autowired
    protected Notifications notifications;

    @Subscribe("clipboardTrigger")
    protected void onClipboardTrigger(ClipboardTrigger.CopyEvent event) {
        notifications.create()
                .withCaption("Copied to clipboard")
                .show();
    }
}
