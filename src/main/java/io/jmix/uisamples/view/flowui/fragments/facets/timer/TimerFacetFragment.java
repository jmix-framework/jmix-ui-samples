package io.jmix.uisamples.view.flowui.fragments.facets.timer;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.facet.Timer;
import io.jmix.flowui.fragment.Fragment;
import io.jmix.flowui.fragment.FragmentDescriptor;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewComponent;
import org.springframework.beans.factory.annotation.Autowired;

@FragmentDescriptor("timer-facet-fragment.xml")
public class TimerFacetFragment extends Fragment<VerticalLayout> {

    @ViewComponent
    private Timer timer;
    @ViewComponent
    private Span timerIndicator;
    @ViewComponent
    private JmixButton timerStartBtn;
    @ViewComponent
    private JmixButton timerStopBtn;

    @Autowired
    private Notifications notifications;

    private int seconds = 0;
    private int ticks = 0;

    @Subscribe("timerStartBtn")
    public void onStartTimerClick(ClickEvent<JmixButton> event) {
        timer.start();

        timerIndicator.setText("Timer started");
        timerIndicator.getElement().getThemeList().clear();
        timerIndicator.getElement().getThemeList().add("badge pill success");

        notifications.create("Timer started")
                .withPosition(Notification.Position.BOTTOM_END)
                .withThemeVariant(NotificationVariant.SUCCESS)
                .show();

        timerStartBtn.setEnabled(false);
        timerStopBtn.setEnabled(true);
    }

    @Subscribe("timerStopBtn")
    public void onStopTimerClick(ClickEvent<JmixButton> event) {
        timer.stop();
        seconds = 0;

        timerIndicator.setText("Timer stopped");
        timerIndicator.getElement().getThemeList().clear();
        timerIndicator.getElement().getThemeList().add("badge pill error");

        timerStartBtn.setEnabled(true);
        timerStopBtn.setEnabled(false);
    }

    @Subscribe("timer")
    public void onTimerTick(Timer.TimerActionEvent event) {
        ++ticks;
        seconds += event.getSource().getDelay() / 1000;

        notifications.create("Timer tick", seconds + " seconds passed")
                .withDuration(2750)
                .show();
    }

    @Subscribe("timer")
    public void onTimerStop(Timer.TimerStopEvent event) {
        notifications.create("Timer stopped", "Number of ticks: " + ticks)
                .withThemeVariant(NotificationVariant.LUMO_PRIMARY)
                .withDuration(5000)
                .show();
        ticks = 0;
    }
}
