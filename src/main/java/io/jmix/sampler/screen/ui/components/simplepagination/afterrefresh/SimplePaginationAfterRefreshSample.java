package io.jmix.sampler.screen.ui.components.simplepagination.afterrefresh;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.PaginationComponent;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("simplepagination-afterrefresh")
@UiDescriptor("simplepagination-afterrefresh.xml")
public class SimplePaginationAfterRefreshSample extends ScreenFragment {

    @Autowired
    private Notifications notifications;

    @Subscribe("simplePagination")
    public void onSimplePaginationAfterRefresh(PaginationComponent.AfterRefreshEvent event) {
        notifications.create(Notifications.NotificationType.HUMANIZED)
                .withCaption("After data refresh")
                .show();
    }
}
