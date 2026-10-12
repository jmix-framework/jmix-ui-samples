package io.jmix.uisamples.view.flowui.charts.series.scatter.large;

import io.jmix.chartsflowui.component.Chart;
import io.jmix.chartsflowui.data.item.MapDataItem;
import io.jmix.chartsflowui.kit.component.model.DataSet;
import io.jmix.chartsflowui.kit.data.chart.ListChartItems;
import io.jmix.flowui.view.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@ViewController("scatter-series-large")
@ViewDescriptor("scatter-series-large.xml")
public class ScatterSeriesLargeSample extends StandardView {

    private static final int POINT_COUNT = 20_000;

    @ViewComponent
    private Chart chart;

    @Subscribe
    public void onInit(InitEvent event) {
        Random random = new Random();
        double[][] clusterCenters = {{20, 30}, {60, 70}, {75, 25}};

        List<MapDataItem> points = new ArrayList<>(POINT_COUNT);
        for (int i = 0; i < POINT_COUNT; i++) {
            double[] center = clusterCenters[i % clusterCenters.length];
            double x = center[0] + random.nextGaussian() * 10;
            double y = center[1] + random.nextGaussian() * 10;

            points.add(new MapDataItem(Map.of("x", round(x), "y", round(y))));
        }

        chart.setDataSet(
                new DataSet()
                        .withSource(
                                new DataSet.Source<MapDataItem>()
                                        .withDataProvider(new ListChartItems<>(points))
                                        .withValueFields("x", "y")
                        )
        );
    }

    private double round(double value) {
        return Math.round(value * 100) / 100.0;
    }
}
