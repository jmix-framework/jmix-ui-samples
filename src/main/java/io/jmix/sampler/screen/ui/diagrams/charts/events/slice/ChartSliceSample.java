package io.jmix.sampler.screen.ui.diagrams.charts.events.slice;

import io.jmix.charts.component.Chart.SliceClickEvent;
import io.jmix.charts.component.Chart.SliceRightClickEvent;
import io.jmix.charts.component.PieChart;
import io.jmix.core.Entity;
import io.jmix.sampler.entity.CountryLitres;
import io.jmix.ui.Notifications;
import io.jmix.ui.Notifications.NotificationType;
import io.jmix.ui.component.ContentMode;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("chart-slice")
@UiDescriptor("chart-slice.xml")
public class ChartSliceSample extends ScreenFragment {
    @Autowired
    private PieChart pieChart;
    @Autowired
    private Notifications notifications;

    @Subscribe
    protected void onInit(InitEvent event) {
        pieChart.addSlicePullOutListener(slicePullOutEvent ->
                notifications.create()
                        .withCaption("PullOutEvent")
                        .withDescription(itemInfo(slicePullOutEvent.getEntity()))
                        .show());

        pieChart.addSlicePullInListener(slicePullInEvent ->
                notifications.create()
                        .withCaption("PullInEvent")
                        .withDescription(itemInfo(slicePullInEvent.getEntity()))
                        .show());

        pieChart.addSliceClickListener(sliceClickEvent ->
                notifications.create()
                        .withCaption("SliceClickEvent")
                        .withDescription(eventInfo(sliceClickEvent))
                        .withType(NotificationType.TRAY)
                        .withContentMode(ContentMode.HTML)
                        .show());

        pieChart.addSliceRightClickListener(sliceRightClickEvent ->
                notifications.create()
                        .withCaption("SliceRightClickEvent")
                        .withDescription(eventInfo(sliceRightClickEvent))
                        .withType(NotificationType.TRAY)
                        .withContentMode(ContentMode.HTML)
                        .show());
    }

    private String itemInfo(Entity entity) {
        CountryLitres countryLitres = (CountryLitres) entity;
        return countryLitres.getCountry() + ": " + countryLitres.getLitres() + " litres";
    }

    private String eventInfo(SliceClickEvent event) {
        return String.format("<strong>X:</strong> %d<br/><strong>Y:</strong> %d<br/>" +
                        "<strong>Absolute X:</strong> %d<br/><strong>Absolute Y:</strong> %d<br/>",
                event.getX(), event.getY(), event.getAbsoluteX(), event.getAbsoluteY());
    }

    private String eventInfo(SliceRightClickEvent event) {
        return String.format("<strong>X:</strong> %d<br/><strong>Y:</strong> %d<br/>" +
                        "<strong>Absolute X:</strong> %d<br/><strong>Absolute Y:</strong> %d<br/>",
                event.getX(), event.getY(), event.getAbsoluteX(), event.getAbsoluteY());
    }
}