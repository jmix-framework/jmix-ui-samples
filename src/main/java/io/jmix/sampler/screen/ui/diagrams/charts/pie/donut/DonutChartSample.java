package io.jmix.sampler.screen.ui.diagrams.charts.pie.donut;

import io.jmix.charts.component.PieChart;
import io.jmix.ui.data.impl.ListDataProvider;
import io.jmix.ui.data.impl.MapDataItem;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("donut-chart")
@UiDescriptor("donut-chart.xml")
public class DonutChartSample extends ScreenFragment {

    @Autowired
    private PieChart donutChart;

    @Subscribe
    protected void onInit(InitEvent event) {
        ListDataProvider dataProvider = new ListDataProvider();

        dataProvider.addItem(new MapDataItem().add("title", "New").add("value", 4852));
        dataProvider.addItem(new MapDataItem().add("title", "Returning").add("value", 9899));

        donutChart.setDataProvider(dataProvider);
    }
}