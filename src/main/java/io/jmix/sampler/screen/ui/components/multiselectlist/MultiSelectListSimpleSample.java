package io.jmix.sampler.screen.ui.components.multiselectlist;

import io.jmix.sampler.entity.Product;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.screen.LoadDataBeforeShow;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@UiController("multiselectlist-simple")
@UiDescriptor("multiselectlist-simple.xml")
@LoadDataBeforeShow
public class MultiSelectListSimpleSample extends ScreenFragment {
    @Autowired
    protected Notifications notifications;

    @Subscribe("multiSelectList")
    protected void onMultiSelectListValueChange(HasValue.ValueChangeEvent<Collection<Product>> event) {
        String str = event.getValue() != null
                ? String.valueOf(event.getValue().size())
                : "0";

        notifications.create()
                .withCaption("Selected items: " + str)
                .show();
    }
}
