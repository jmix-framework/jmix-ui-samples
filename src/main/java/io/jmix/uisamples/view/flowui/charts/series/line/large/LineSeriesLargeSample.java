package io.jmix.uisamples.view.flowui.charts.series.line.large;

import io.jmix.chartsflowui.component.Chart;
import io.jmix.chartsflowui.data.item.MapDataItem;
import io.jmix.chartsflowui.kit.component.model.DataSet;
import io.jmix.chartsflowui.kit.data.chart.ListChartItems;
import io.jmix.flowui.view.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@ViewController("line-series-large")
@ViewDescriptor("line-series-large.xml")
public class LineSeriesLargeSample extends StandardView {

    private static final int POINT_COUNT = 20_000;

    @ViewComponent
    private Chart chart;

    @Subscribe
    public void onInit(InitEvent event) {
        Random random = new Random();
        LocalDate startDate = LocalDate.of(1970, 1, 1);
        double value = 1000;

        List<MapDataItem> points = new ArrayList<>(POINT_COUNT);
        for (int i = 0; i < POINT_COUNT; i++) {
            value = Math.max(0, value + random.nextDouble(-20, 21));
            points.add(new MapDataItem(Map.of("date", startDate.plusDays(i), "value", Math.round(value))));
        }

        chart.setDataSet(
                new DataSet()
                        .withSource(
                                new DataSet.Source<MapDataItem>()
                                        .withDataProvider(new ListChartItems<>(points))
                                        .withCategoryField("date")
                                        .withValueField("value")
                        )
        );
    }
}
