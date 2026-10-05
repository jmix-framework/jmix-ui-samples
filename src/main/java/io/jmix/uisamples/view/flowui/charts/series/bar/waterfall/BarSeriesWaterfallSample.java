package io.jmix.uisamples.view.flowui.charts.series.bar.waterfall;

import io.jmix.chartsflowui.component.Chart;
import io.jmix.chartsflowui.data.item.MapDataItem;
import io.jmix.chartsflowui.kit.component.model.DataSet;
import io.jmix.chartsflowui.kit.data.chart.ListChartItems;
import io.jmix.flowui.view.*;

import java.util.List;
import java.util.Map;

@ViewController("bar-series-waterfall")
@ViewDescriptor("bar-series-waterfall.xml")
public class BarSeriesWaterfallSample extends StandardView {

    @ViewComponent
    private Chart chart;

    @Subscribe
    public void onInit(InitEvent event) {
        List<Map.Entry<String, Integer>> expenses = List.of(
                Map.entry("Rent", 1200),
                Map.entry("Utilities", 300),
                Map.entry("Transportation", 200),
                Map.entry("Meals", 900),
                Map.entry("Other", 300)
        );

        int rest = expenses.stream()
                .mapToInt(Map.Entry::getValue)
                .sum();
        ListChartItems<MapDataItem> items = new ListChartItems<>(createItem("Total", 0, rest));

        for (Map.Entry<String, Integer> expense : expenses) {
            rest -= expense.getValue();
            items.addItem(createItem(expense.getKey(), rest, expense.getValue()));
        }

        chart.setDataSet(
                new DataSet()
                        .withSource(
                                new DataSet.Source<MapDataItem>()
                                        .withDataProvider(items)
                                        .withCategoryField("category")
                                        .withValueFields("placeholder", "amount")
                        )
        );
    }

    protected MapDataItem createItem(String category, int placeholder, int amount) {
        return new MapDataItem(Map.of("category", category,
                "placeholder", placeholder,
                "amount", amount));
    }
}
