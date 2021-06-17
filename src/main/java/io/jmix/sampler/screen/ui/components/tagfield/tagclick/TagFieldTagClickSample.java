package io.jmix.sampler.screen.ui.components.tagfield.tagclick;

import io.jmix.sampler.entity.ProductTag;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.TagField;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("tagfield-tagclick")
@UiDescriptor("tagfield-tagclick.xml")
public class TagFieldTagClickSample extends ScreenFragment {

    @Autowired
    private Notifications notifications;

    @Subscribe("tagField")
    public void onTagFieldTagClick(TagField.TagClickEvent<ProductTag> event) {
        notifications.create(Notifications.NotificationType.HUMANIZED)
                .withCaption(event.getItem().getName())
                .show();
    }
}
