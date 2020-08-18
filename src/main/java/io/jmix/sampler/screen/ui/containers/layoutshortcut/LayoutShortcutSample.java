package io.jmix.sampler.screen.ui.containers.layoutshortcut;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.ContentMode;
import io.jmix.ui.component.GroupBoxLayout;
import io.jmix.ui.component.ShortcutAction;
import io.jmix.ui.component.ShortcutTriggeredEvent;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("layout-shortcut")
@UiDescriptor("layout-shortcut.xml")
public class LayoutShortcutSample extends ScreenFragment {

    @Autowired
    protected Notifications notifications;
    @Autowired
    protected GroupBoxLayout groupBox;

    @Subscribe
    protected void onInit(InitEvent event) {
        groupBox.addShortcutAction(new ShortcutAction("SHIFT-A", shortcutTriggeredEvent ->
                notifications.create()
                        .withCaption(getMessage(shortcutTriggeredEvent))
                        .withContentMode(ContentMode.HTML)
                        .show()
        ));
    }

    protected String getMessage(ShortcutTriggeredEvent shortcutEvent) {
        return "<strong>Source id:</strong> " + shortcutEvent.getSource().getId()
                + "<br><strong>Target id:</strong> " + shortcutEvent.getTarget().getId();
    }
}
