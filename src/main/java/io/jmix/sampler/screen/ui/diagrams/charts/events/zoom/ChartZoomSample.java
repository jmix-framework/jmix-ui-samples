package io.jmix.sampler.screen.ui.diagrams.charts.events.zoom;

import io.jmix.charts.component.Chart.CursorZoomEvent;
import io.jmix.charts.component.SerialChart;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.ContentMode;
import io.jmix.ui.component.Label;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("chart-zoom")
@UiDescriptor("chart-zoom.xml")
public class ChartZoomSample extends ScreenFragment {
    @Autowired
    private SerialChart lineChart;
    @Autowired
    private Label<String> label;

    @Autowired
    private Notifications notifications;

    @Subscribe
    protected void onInit(InitEvent event) {
        lineChart.addZoomListener(zoomEvent ->
                label.setValue(String.format("Zoom: <strong>[%s, %s]</strong>",
                        zoomEvent.getStartValue(), zoomEvent.getEndValue())));

        lineChart.addCursorZoomListener(cursorZoomEvent ->
                notifications.create()
                        .withCaption("CursorZoomEvent")
                        .withDescription(eventInfo(cursorZoomEvent))
                        .withContentMode(ContentMode.HTML)
                        .show());
    }

    private String eventInfo(CursorZoomEvent event) {
        return "<strong>Start: </strong>" + event.getStart() + "<br/>" +
                "<strong>End: </strong>" + event.getEnd();
    }
}