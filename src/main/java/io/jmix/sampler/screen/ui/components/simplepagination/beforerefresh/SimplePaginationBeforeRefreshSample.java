package io.jmix.sampler.screen.ui.components.simplepagination.beforerefresh;

import io.jmix.ui.Notifications;
import io.jmix.ui.Notifications.NotificationType;
import io.jmix.ui.component.PaginationComponent;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("simplepagination-beforerefresh")
@UiDescriptor("simplepagination-beforerefresh.xml")
public class SimplePaginationBeforeRefreshSample extends ScreenFragment {

    @Autowired
    private Notifications notifications;

    @Subscribe("simplePagination")
    public void onSimplePaginationBeforeRefresh(PaginationComponent.BeforeRefreshEvent event) {
        notifications.create(NotificationType.HUMANIZED)
                .withCaption("Before data refresh")
                .show();
    }
}
