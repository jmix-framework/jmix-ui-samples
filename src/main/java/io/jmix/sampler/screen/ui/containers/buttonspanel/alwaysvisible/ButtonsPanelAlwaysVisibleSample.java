package io.jmix.sampler.screen.ui.containers.buttonspanel.alwaysvisible;

import io.jmix.sampler.entity.Customer;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.component.Button;
import io.jmix.ui.screen.OpenMode;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("buttonspanel-always-visible")
@UiDescriptor("buttonspanel-always-visible.xml")
public class ButtonsPanelAlwaysVisibleSample extends ScreenFragment {

    @Autowired
    protected ScreenBuilders screenBuilders;

    @Subscribe("showVisible")
    protected void onShowVisibleClick(Button.ClickEvent event) {
        screenBuilders.lookup(Customer.class, this)
                .withScreenId("buttonspanel-visible")
                .withLaunchMode(OpenMode.DIALOG)
                .withSelectHandler(customers -> {
                })
                .build()
                .show();
    }

    @Subscribe("showInvisible")
    protected void onShowInvisibleClick(Button.ClickEvent event) {
        screenBuilders.lookup(Customer.class, this)
                .withScreenId("buttonspanel-invisible")
                .withLaunchMode(OpenMode.DIALOG)
                .withSelectHandler(customers -> {
                })
                .build()
                .show();
    }
}
