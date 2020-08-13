package io.jmix.sampler.screen.ui.components.popupview.listener;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.PopupView;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("popupview-listener")
@UiDescriptor("popupview-listener.xml")
public class PopupViewListenerSample extends ScreenFragment {

    @Autowired
    protected Notifications notifications;

    @Subscribe("popupView")
    protected void onPopupViewPopupVisibility(PopupView.PopupVisibilityEvent event) {
        notifications.create()
                .withCaption(event.isPopupVisible() ? "The popup is visible" : "The popup is hidden")
                .show();
    }
}
