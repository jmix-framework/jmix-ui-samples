package io.jmix.uisamples.view.flowui.charts.series.boxplot.multiple;

import io.jmix.chartsflowui.component.Chart;
import io.jmix.chartsflowui.data.item.MapDataItem;
import io.jmix.chartsflowui.kit.component.model.DataSet;
import io.jmix.chartsflowui.kit.data.chart.ListChartItems;
import io.jmix.core.DataManager;
import io.jmix.flowui.view.*;
import io.jmix.uisamples.entity.Month;
import io.jmix.uisamples.entity.TemperatureData;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@ViewController("boxplot-series-multiple")
@ViewDescriptor("boxplot-series-multiple.xml")
public class BoxplotSeriesMultipleSample extends StandardView {

    private static final List<String> SEASONS = List.of("winter", "spring", "summer", "autumn");
    private static final List<String> STATISTICS = List.of("Min", "Q1", "Median", "Q3", "Max");
    private static final double[] QUANTILES = {0, 0.25, 0.5, 0.75, 1};

    @Autowired
    private DataManager dataManager;

    @ViewComponent
    private Chart chart;

    @Subscribe
    public void onInit(InitEvent event) {
        Map<String, Map<String, List<Double>>> temperatures = dataManager.load(TemperatureData.class)
                .all()
                .list()
                .stream()
                .collect(Collectors.groupingBy(TemperatureData::getCity, TreeMap::new,
                        Collectors.groupingBy(data -> getSeason(data.getMonth()),
                                Collectors.mapping(TemperatureData::getTemperature, Collectors.toList()))));

        ListChartItems<MapDataItem> items = new ListChartItems<>();
        temperatures.forEach((city, seasonTemperatures) -> {
            MapDataItem item = new MapDataItem().add("city", city);
            seasonTemperatures.forEach((season, values) -> addStatistics(item, season, values));
            items.addItem(item);
        });

        String[] valueFields = SEASONS.stream()
                .flatMap(season -> STATISTICS.stream().map(statistic -> season + statistic))
                .toArray(String[]::new);

        chart.setDataSet(
                new DataSet()
                        .withSource(
                                new DataSet.Source<MapDataItem>()
                                        .withDataProvider(items)
                                        .withCategoryField("city")
                                        .withValueFields(valueFields)
                        )
        );
    }

    private String getSeason(Month month) {
        return switch (month) {
            case DECEMBER, JANUARY, FEBRUARY -> "winter";
            case MARCH, APRIL, MAY -> "spring";
            case JUNE, JULY, AUGUST -> "summer";
            case SEPTEMBER, OCTOBER, NOVEMBER -> "autumn";
        };
    }

    private void addStatistics(MapDataItem item, String season, List<Double> values) {
        List<Double> sorted = values.stream().sorted().toList();

        for (int i = 0; i < STATISTICS.size(); i++) {
            item.add(season + STATISTICS.get(i), quantile(sorted, QUANTILES[i]));
        }
    }

    private double quantile(List<Double> sorted, double p) {
        double index = p * (sorted.size() - 1);
        int lower = (int) Math.floor(index);
        int upper = (int) Math.ceil(index);

        return sorted.get(lower) + (sorted.get(upper) - sorted.get(lower)) * (index - lower);
    }
}
