package io.jmix.sampler.screen.ui.facets.timer;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.Label;
import io.jmix.ui.component.Timer;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("facet-timer")
@UiDescriptor("facet-timer.xml")
public class FacetTimerSample extends ScreenFragment {

    @Autowired
    protected Timer timer;

    @Autowired
    protected Label<String> statusLabel;

    @Autowired
    protected Notifications notifications;

    protected int seconds = 0;

    @Subscribe("startTimer")
    protected void onStartTimerClick(Button.ClickEvent event) {
        timer.start();
        statusLabel.setValue("Timer started");
        notifications.create(Notifications.NotificationType.TRAY)
                .withCaption("Timer started")
                .show();
    }

    @Subscribe("stopTimer")
    protected void onStopTimerClick(Button.ClickEvent event) {
        timer.stop();
        seconds = 0;
        statusLabel.setValue("Timer stopped");
        notifications.create(Notifications.NotificationType.TRAY)
                .withCaption("Timer stopped")
                .show();
    }

    @Subscribe("timer")
    protected void onTimerTick(Timer.TimerActionEvent event) {
        seconds += event.getSource().getDelay() / 1000;
        notifications.create(Notifications.NotificationType.TRAY)
                .withCaption("Timer tick")
                .withDescription(seconds + " seconds passed")
                .show();
    }
}