package io.jmix.uisamples.view.flowui.fragments.events;

import com.vaadin.flow.component.badge.Badge;
import com.vaadin.flow.component.badge.BadgeVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import io.jmix.flowui.fragment.Fragment;
import io.jmix.flowui.fragment.FragmentDescriptor;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.Target;
import io.jmix.flowui.view.View;

@FragmentDescriptor("events-fragment.xml")
public class EventsFragment extends Fragment<HorizontalLayout> {

    @Subscribe
    public void onReady(ReadyEvent event) {
        addExecutedEvent("Fragment ready event");
    }

    @Subscribe(target = Target.HOST_CONTROLLER)
    public void onHostInit(View.InitEvent event) {
        addExecutedEvent("Host init event");
    }

    @Subscribe(target = Target.HOST_CONTROLLER)
    public void onHostBeforeShow(View.BeforeShowEvent event) {
        addExecutedEvent("Host before show event");
    }

    @Subscribe(target = Target.HOST_CONTROLLER)
    public void onHostReady(View.ReadyEvent event) {
        addExecutedEvent("Host ready event");
    }

    public void addExecutedEvent(String eventName) {
        Badge badge = new Badge(eventName);

        if (eventName.contains("Fragment")) {
            badge.addThemeVariants(BadgeVariant.ERROR);
        } else if (eventName.contains("View")) {
            badge.addThemeVariants(BadgeVariant.SUCCESS);
        }

        getContent().add(badge);
    }
}
