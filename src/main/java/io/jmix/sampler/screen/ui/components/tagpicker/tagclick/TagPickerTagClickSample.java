package io.jmix.sampler.screen.ui.components.tagpicker.tagclick;

import io.jmix.sampler.entity.ProductTag;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.TagPicker;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("tagpicker-tagclick")
@UiDescriptor("tagpicker-tagclick.xml")
public class TagPickerTagClickSample extends ScreenFragment {

    @Autowired
    private Notifications notifications;

    @Subscribe("tagPicker")
    public void onTagPickerTagClick(TagPicker.TagClickEvent<ProductTag> event) {
        notifications.create(Notifications.NotificationType.HUMANIZED)
                .withCaption(event.getItem().getInstanceName())
                .show();
    }
}
