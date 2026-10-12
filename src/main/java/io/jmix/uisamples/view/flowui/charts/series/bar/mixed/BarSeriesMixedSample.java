package io.jmix.uisamples.view.flowui.charts.series.bar.mixed;

import io.jmix.chartsflowui.component.Chart;
import io.jmix.chartsflowui.data.item.MapDataItem;
import io.jmix.chartsflowui.kit.component.model.DataSet;
import io.jmix.chartsflowui.kit.data.chart.ListChartItems;
import io.jmix.flowui.view.*;

import java.util.Map;

@ViewController("bar-series-mixed")
@ViewDescriptor("bar-series-mixed.xml")
public class BarSeriesMixedSample extends StandardView {

    @ViewComponent
    private Chart chart;

    @Subscribe
    public void onInit(InitEvent event) {
        ListChartItems<MapDataItem> items = new ListChartItems<>(
                createItem("Jan", 2.6, 2.0),
                createItem("Feb", 5.9, 2.2),
                createItem("Mar", 9.0, 3.3),
                createItem("Apr", 26.4, 4.5),
                createItem("May", 28.7, 6.3),
                createItem("Jun", 70.7, 10.2),
                createItem("Jul", 175.6, 20.3),
                createItem("Aug", 182.2, 23.4),
                createItem("Sep", 48.7, 23.0),
                createItem("Oct", 18.8, 16.5),
                createItem("Nov", 6.0, 12.0),
                createItem("Dec", 2.3, 6.2)
        );

        chart.setDataSet(
                new DataSet()
                        .withSource(
                                new DataSet.Source<MapDataItem>()
                                        .withDataProvider(items)
                                        .withCategoryField("month")
                                        .withValueFields("precipitation", "temperature")
                        )
        );
    }

    private MapDataItem createItem(String month, double precipitation, double temperature) {
        return new MapDataItem(Map.of("month", month,
                "precipitation", precipitation,
                "temperature", temperature));
    }
}
