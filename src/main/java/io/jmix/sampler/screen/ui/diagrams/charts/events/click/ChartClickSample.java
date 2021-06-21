package io.jmix.sampler.screen.ui.diagrams.charts.events.click;

import io.jmix.charts.component.Chart.ChartClickEvent;
import io.jmix.charts.component.Chart.ChartRightClickEvent;
import io.jmix.charts.component.XYChart;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.ContentMode;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("chart-click")
@UiDescriptor("chart-click.xml")
public class ChartClickSample extends ScreenFragment {
    @Autowired
    private XYChart chart;
    @Autowired
    private Notifications notifications;

    @Subscribe
    protected void onInit(InitEvent event) {
        chart.addClickListener(chartClickEvent ->
                notifications.create()
                        .withCaption("ClickEvent")
                        .withDescription(eventInfo(chartClickEvent))
                        .withContentMode(ContentMode.HTML)
                        .show());

        chart.addRightClickListener(chartRightClickEvent ->
                notifications.create()
                        .withCaption("RightClickEvent")
                        .withDescription(eventInfo(chartRightClickEvent))
                        .withContentMode(ContentMode.HTML)
                        .show());
    }

    private String eventInfo(ChartClickEvent event) {
        return String.format("<strong>X Value:</strong> %f<br/><strong>Y Value:</strong> %f<br/>" +
                        "<strong>X:</strong> %d<br/><strong>Y:</strong> %d<br/>" +
                        "<strong>Absolute X:</strong> %d<br/><strong>Absolute Y:</strong> %d<br/>",
                event.getxAxis(), event.getyAxis(),
                event.getX(), event.getY(),
                event.getAbsoluteX(), event.getAbsoluteY());
    }

    private String eventInfo(ChartRightClickEvent event) {
        return String.format("<strong>X Value:</strong> %f<br/><strong>Y Value:</strong> %f<br/>" +
                        "<strong>X:</strong> %d<br/><strong>Y:</strong> %d<br/>" +
                        "<strong>Absolute X:</strong> %d<br/><strong>Absolute Y:</strong> %d<br/>",
                event.getxAxis(), event.getyAxis(),
                event.getX(), event.getY(),
                event.getAbsoluteX(), event.getAbsoluteY());
    }
}