package io.jmix.uisamples.view.flowui.charts.other.clickevent;

import io.jmix.chartsflowui.kit.component.event.ChartClickEvent;
import io.jmix.chartsflowui.kit.component.event.dto.ChartClickEventDetail;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@ViewController("chart-click-event")
@ViewDescriptor("chart-click-event.xml")
public class ChartClickEventSample extends StandardView {

    @Autowired
    private Notifications notifications;

    @Subscribe("chart")
    public void onChartChartClick(final ChartClickEvent event) {
        ChartClickEventDetail detail = event.getDetail();

        String message = "%s: %s liters (%s%%)".formatted(
                detail.getName(), detail.getData().get("litres"), detail.getPercent());
        notifications.show(message);
    }
}
