package io.jmix.sampler.screen.ui.components.image.click;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.ContentMode;
import io.jmix.ui.component.Image;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("image-click")
@UiDescriptor("image-click.xml")
public class ImageClickSample extends ScreenFragment {

    @Autowired
    protected Notifications notifications;

    @Subscribe("imageClick")
    protected void onImageClickClick(Image.ClickEvent event) {
        if (!event.isDoubleClick()) {
            notifications.create()
                    .withCaption(getEventMessage(event))
                    .withContentMode(ContentMode.HTML)
                    .show();
        }
    }

    @Subscribe("imageDoubleClick")
    protected void onImageDoubleClickClick(Image.ClickEvent event) {
        if (event.isDoubleClick()) {
            notifications.create()
                    .withCaption(getEventMessage(event))
                    .withContentMode(ContentMode.HTML)
                    .show();
        }
    }

    protected String getEventMessage(Image.ClickEvent event) {
        return String.format("<strong>Click info:</strong>"
                        + "<br><strong>Client X:</strong> %s"
                        + "<br><strong>Client Y:</strong> %s"
                        + "<br><strong>Is Ctrl click:</strong> %s"
                        + "<br><strong>Is Alt click:</strong> %s<br>"
                        + "<strong>Is Shift click:</strong> %s",
                event.getClientX(), event.getClientY(),
                event.isCtrlKey(), event.isAltKey(), event.isShiftKey());
    }
}
