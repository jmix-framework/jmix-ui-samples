package io.jmix.uisamples.view.flowui.charts.series.line.step;

import io.jmix.chartsflowui.component.Chart;
import io.jmix.chartsflowui.data.item.MapDataItem;
import io.jmix.chartsflowui.kit.component.model.DataSet;
import io.jmix.chartsflowui.kit.data.chart.ListChartItems;
import io.jmix.flowui.view.*;

import java.util.Map;

@ViewController("line-series-step")
@ViewDescriptor("line-series-step.xml")
public class LineSeriesStepSample extends StandardView {

    @ViewComponent
    private Chart chart;

    @Subscribe
    public void onInit(InitEvent event) {
        ListChartItems<MapDataItem> items = new ListChartItems<>(
                createItem("Mon", 120, 220, 450),
                createItem("Tue", 132, 282, 432),
                createItem("Wed", 101, 201, 401),
                createItem("Thu", 134, 234, 454),
                createItem("Fri", 90, 290, 590),
                createItem("Sat", 230, 430, 530),
                createItem("Sun", 210, 410, 510)
        );

        chart.setDataSet(
                new DataSet()
                        .withSource(
                                new DataSet.Source<MapDataItem>()
                                        .withDataProvider(items)
                                        .withCategoryField("day")
                                        .withValueFields("start", "middle", "end")
                        )
        );
    }

    private MapDataItem createItem(String day, int start, int middle, int end) {
        return new MapDataItem(Map.of("day", day,
                "start", start,
                "middle", middle,
                "end", end));
    }
}
