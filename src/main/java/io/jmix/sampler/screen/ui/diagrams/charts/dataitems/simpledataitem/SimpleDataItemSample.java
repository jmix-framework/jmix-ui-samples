package io.jmix.sampler.screen.ui.diagrams.charts.dataitems.simpledataitem;

import io.jmix.charts.component.PieChart;
import io.jmix.ui.data.impl.ListDataProvider;
import io.jmix.ui.data.impl.SimpleDataItem;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("simple-data-item")
@UiDescriptor("simple-data-item.xml")
public class SimpleDataItemSample extends ScreenFragment {
    @Autowired
    private PieChart chart;

    @Subscribe
    protected void onInit(InitEvent event) {
        ListDataProvider dataProvider = new ListDataProvider();
        dataProvider.addItem(new SimpleDataItem(new ValueDescription(75, "Sky")));
        dataProvider.addItem(new SimpleDataItem(new ValueDescription(7, "Shady side of pyramid")));
        dataProvider.addItem(new SimpleDataItem(new ValueDescription(18, "Sunny side of pyramid")));

        chart.setDataProvider(dataProvider);
    }

    public class ValueDescription {
        private Integer value;
        private String description;

        public ValueDescription(Integer value, String description) {
            this.value = value;
            this.description = description;
        }

        public Integer getValue() {
            return value;
        }

        public String getDescription() {
            return description;
        }
    }
}