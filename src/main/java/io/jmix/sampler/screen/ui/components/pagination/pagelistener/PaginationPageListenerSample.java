package io.jmix.sampler.screen.ui.components.pagination.pagelistener;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.Pagination;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("pagination-pagelistener")
@UiDescriptor("pagination-pagelistener.xml")
public class PaginationPageListenerSample extends ScreenFragment {

    @Autowired
    private Notifications notifications;

    @Subscribe("pagination")
    public void onPaginationPageChange(Pagination.PageChangeEvent event) {
        notifications.create(Notifications.NotificationType.HUMANIZED)
                .withCaption("Current page: " + event.getPageNumber()
                        + "\nPrevious page: " + event.getPreviousPageNumber())
                .show();
    }
}
