package io.jmix.uisamples.view.flowui.charts.series.boxplot.simple;

import io.jmix.chartsflowui.component.Chart;
import io.jmix.chartsflowui.data.item.MapDataItem;
import io.jmix.chartsflowui.kit.component.model.DataSet;
import io.jmix.chartsflowui.kit.data.chart.ListChartItems;
import io.jmix.flowui.view.*;

import java.util.Map;

@ViewController("boxplot-series-simple")
@ViewDescriptor("boxplot-series-simple.xml")
public class BoxplotSeriesSimpleSample extends StandardView {

    @ViewComponent
    private Chart chart;

    @Subscribe
    public void onInit(InitEvent event) {
        ListChartItems<MapDataItem> items = new ListChartItems<>(
                createItem("North", 1, 2, 3, 4, 7),
                createItem("South", 2, 3, 4, 6, 9),
                createItem("East", 1, 2, 2.5, 3.5, 5),
                createItem("West", 2, 4, 5, 6, 10),
                createItem("Central", 1, 1.5, 2, 3, 4)
        );

        chart.setDataSet(
                new DataSet()
                        .withSource(
                                new DataSet.Source<MapDataItem>()
                                        .withDataProvider(items)
                                        .withCategoryField("region")
                                        .withValueFields("min", "q1", "median", "q3", "max")
                        )
        );
    }

    private MapDataItem createItem(String region, double min, double q1, double median, double q3, double max) {
        return new MapDataItem(Map.of("region", region,
                "min", min,
                "q1", q1,
                "median", median,
                "q3", q3,
                "max", max));
    }
}
