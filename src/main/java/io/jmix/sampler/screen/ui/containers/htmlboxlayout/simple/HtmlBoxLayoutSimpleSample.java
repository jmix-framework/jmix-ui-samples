package io.jmix.sampler.screen.ui.containers.htmlboxlayout.simple;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("htmlboxlayout-simple")
@UiDescriptor("htmlboxlayout-simple.xml")
public class HtmlBoxLayoutSimpleSample extends ScreenFragment {

    @Autowired
    protected Notifications notifications;

    @Subscribe("addons")
    protected void onAddonsClick(Button.ClickEvent event) {
        click();
    }

    @Subscribe("buy")
    protected void onBuyClick(Button.ClickEvent event) {
        click();
    }

    @Subscribe("support")
    protected void onSupportClick(Button.ClickEvent event) {
        click();
    }

    public void click() {
        notifications.create()
                .withCaption("Button clicked")
                .show();
    }
}
