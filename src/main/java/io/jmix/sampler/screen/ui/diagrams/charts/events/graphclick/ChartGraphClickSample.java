package io.jmix.sampler.screen.ui.diagrams.charts.events.graphclick;

import io.jmix.charts.component.Chart.GraphClickEvent;
import io.jmix.charts.component.Chart.GraphItemClickEvent;
import io.jmix.charts.component.Chart.GraphItemRightClickEvent;
import io.jmix.charts.component.SerialChart;
import io.jmix.ui.Notifications;
import io.jmix.ui.Notifications.NotificationType;
import io.jmix.ui.component.ContentMode;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("chart-graph-click")
@UiDescriptor("chart-graph-click.xml")
public class ChartGraphClickSample extends ScreenFragment {
    @Autowired
    private SerialChart chart;
    @Autowired
    private Notifications notifications;

    @Subscribe
    protected void onInit(InitEvent event) {
        chart.addGraphClickListener(graphClickEvent ->
                notifications.create()
                        .withCaption("GraphClickEvent")
                        .withDescription(clickEventInfo(graphClickEvent))
                        .withType(NotificationType.TRAY)
                        .withContentMode(ContentMode.HTML)
                        .show());

        chart.addGraphItemClickListener(graphItemClickEvent ->
                notifications.create()
                        .withCaption("GraphItemClickEvent")
                        .withDescription(itemClickEventInfo(graphItemClickEvent))
                        .withContentMode(ContentMode.HTML)
                        .show());

        chart.addGraphItemRightClickListener(graphItemRightClickEvent ->
                notifications.create()
                        .withCaption("GraphItemRightClickEvent")
                        .withDescription(itemClickEventInfo(graphItemRightClickEvent))
                        .withContentMode(ContentMode.HTML)
                        .show());
    }

    private String clickEventInfo(GraphClickEvent event) {
        return String.format("<strong>Graph Id:</strong> %s<br/>" +
                        "<strong>X:</strong> %d<br/><strong>Y:</strong> %d<br/>" +
                        "<strong>Absolute X:</strong> %d<br/><strong>Absolute Y:</strong> %d<br/>",
                event.getGraphId(), event.getX(), event.getY(), event.getAbsoluteX(), event.getAbsoluteY());
    }

    private String itemClickEventInfo(GraphItemClickEvent event) {
        return String.format("<strong>Graph Id:</strong> %s<br/><strong>Item Index:</strong> %d<br/>" +
                        "<strong>X:</strong> %d<br/><strong>Y:</strong> %d<br/>" +
                        "<strong>Absolute X:</strong> %d<br/><strong>Absolute Y:</strong> %d<br/>",
                event.getGraphId(), event.getItemIndex(),
                event.getX(), event.getY(),
                event.getAbsoluteX(), event.getAbsoluteY());
    }

    private String itemClickEventInfo(GraphItemRightClickEvent event) {
        return String.format("<strong>Graph Id:</strong> %s<br/><strong>Item Index:</strong> %d<br/>" +
                        "<strong>X:</strong> %d<br/><strong>Y:</strong> %d<br/>" +
                        "<strong>Absolute X:</strong> %d<br/><strong>Absolute Y:</strong> %d<br/>",
                event.getGraphId(), event.getItemIndex(),
                event.getX(), event.getY(),
                event.getAbsoluteX(), event.getAbsoluteY());
    }
}