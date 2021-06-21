package io.jmix.sampler.screen.ui.diagrams.charts.radar.polar;

import io.jmix.charts.component.RadarChart;
import io.jmix.ui.data.impl.ListDataProvider;
import io.jmix.ui.data.impl.MapDataItem;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("polar-chart")
@UiDescriptor("polar-chart.xml")
public class PolarChartSample extends ScreenFragment {

    @Autowired
    private RadarChart polarChart;

    @Subscribe
    protected void onInit(InitEvent event) {
        ListDataProvider dataProvider = new ListDataProvider();

        dataProvider.addItem(new MapDataItem().add("direction", "N").add("value", 8.0));
        dataProvider.addItem(new MapDataItem().add("direction", "NE").add("value", 9.0));
        dataProvider.addItem(new MapDataItem().add("direction", "E").add("value", 4.5));
        dataProvider.addItem(new MapDataItem().add("direction", "SE").add("value", 3.5));
        dataProvider.addItem(new MapDataItem().add("direction", "S").add("value", 9.2));
        dataProvider.addItem(new MapDataItem().add("direction", "SW").add("value", 8.4));
        dataProvider.addItem(new MapDataItem().add("direction", "W").add("value", 11.1));
        dataProvider.addItem(new MapDataItem().add("direction", "NW").add("value", 10.0));

        polarChart.setDataProvider(dataProvider);
    }
}