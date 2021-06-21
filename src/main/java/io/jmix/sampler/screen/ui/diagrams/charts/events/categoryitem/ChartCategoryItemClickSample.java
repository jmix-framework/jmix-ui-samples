package io.jmix.sampler.screen.ui.diagrams.charts.events.categoryitem;

import io.jmix.charts.component.SerialChart;
import io.jmix.charts.component.SeriesBasedChart.CategoryItemClickEvent;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.ContentMode;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("chart-category-item-click")
@UiDescriptor("chart-category-item-click.xml")
public class ChartCategoryItemClickSample extends ScreenFragment {

    @Autowired
    private SerialChart lineChart;
    @Autowired
    private Notifications notifications;

    @Subscribe
    protected void onInit(InitEvent event) {
        lineChart.addCategoryItemClickListener(categoryItemClickEvent ->
                notifications.create()
                        .withCaption("CategoryItemClickEvent")
                        .withDescription(eventInfo(categoryItemClickEvent))
                        .withContentMode(ContentMode.HTML)
                        .show());
    }

    private String eventInfo(CategoryItemClickEvent event) {
        return String.format("<strong>Item name:</strong> %s<br/> <strong>X:</strong> %d<br/>" +
                             "<strong>Y:</strong> %d<br/> <strong>Axis X:</strong> %d<br/>" +
                             "<strong>Axis Y:</strong> %d<br/> <strong>Offset X:</strong> %d<br/>" +
                             "<strong>Offset Y:</strong> %d<br/>",
                event.getValue(), event.getX(), event.getY(), event.getXAxis(), event.getYAxis(),
                event.getOffsetX(), event.getOffsetY());
    }
}
