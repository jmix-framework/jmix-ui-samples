package io.jmix.sampler.screen.ui.diagrams.charts.dataitems.entitydataitem;

import io.jmix.charts.component.PieChart;
import io.jmix.core.Entity;
import io.jmix.core.Metadata;
import io.jmix.sampler.entity.ValueDescription;
import io.jmix.ui.data.impl.EntityDataItem;
import io.jmix.ui.data.impl.ListDataProvider;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("entity-data-item")
@UiDescriptor("entity-data-item.xml")
public class EntityDataItemSample extends ScreenFragment {
    @Autowired
    private PieChart chart;
    @Autowired
    private Metadata metadata;

    @Subscribe
    protected void onInit(InitEvent event) {
        ListDataProvider dataProvider = new ListDataProvider();
        dataProvider.addItem(new EntityDataItem((Entity) valueDescription(75, "Sky")));
        dataProvider.addItem(new EntityDataItem((Entity) valueDescription(7, "Shady side of pyramid")));
        dataProvider.addItem(new EntityDataItem((Entity) valueDescription(18, "Sunny side of pyramid")));

        chart.setDataProvider(dataProvider);
    }

    private ValueDescription valueDescription(Integer value, String description) {
        ValueDescription entity = metadata.create(ValueDescription.class);
        entity.setValue(value);
        entity.setDescription(description);
        return entity;
    }
}