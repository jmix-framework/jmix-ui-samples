package io.jmix.uisamples.view.flowui.customcomponents.fromscratch;

import com.vaadin.flow.component.notification.Notification;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import io.jmix.uisamples.component.themetoggle.ThemeToggle;
import org.springframework.beans.factory.annotation.Autowired;

@ViewController("custom-component-from-scratch")
@ViewDescriptor("theme-toggle-usage.xml")
public class ThemeToggleUsage extends StandardView {

    @Autowired
    protected Notifications notifications;

    @Subscribe("themeToggle")
    protected void onThemeChange(ThemeToggle.ThemeToggleThemeChangedEvent event) {
        notifications.create("Theme switched: " + getThemeValue(event))
                .withPosition(Notification.Position.TOP_CENTER)
                .show();
    }

    protected String getThemeValue(ThemeToggle.ThemeToggleThemeChangedEvent event) {
        return "dark".equals(event.getValue()) ? "Dark" : "Light";
    }
}
