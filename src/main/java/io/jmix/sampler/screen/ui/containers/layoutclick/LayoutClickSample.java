package io.jmix.sampler.screen.ui.containers.layoutclick;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.ContentMode;
import io.jmix.ui.component.LayoutClickNotifier;
import io.jmix.ui.component.MouseEventDetails;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("layout-click")
@UiDescriptor("layout-click.xml")
public class LayoutClickSample extends ScreenFragment {

    @Autowired
    protected Notifications notifications;

    @Subscribe("layout")
    protected void onBoxLayoutLayoutClick(LayoutClickNotifier.LayoutClickEvent event) {
        notifications.create()
                .withCaption(getMessage(event.getMouseEventDetails()))
                .withContentMode(ContentMode.HTML)
                .show();
    }

    protected String getMessage(MouseEventDetails mouseEventDetails) {
        return "<strong>Button:</strong> " + mouseEventDetails.getButton()
                + "<br><strong>Client X:</strong> " + mouseEventDetails.getClientX()
                + "<br><strong>Client Y</strong>" + mouseEventDetails.getClientY();
    }
}
