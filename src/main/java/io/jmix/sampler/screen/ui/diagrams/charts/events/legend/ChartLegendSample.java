package io.jmix.sampler.screen.ui.diagrams.charts.events.legend;

import io.jmix.charts.component.PieChart;
import io.jmix.core.Entity;
import io.jmix.sampler.entity.CountryLitres;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.ContentMode;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("chart-legend")
@UiDescriptor("chart-legend.xml")
public class ChartLegendSample extends ScreenFragment {
    @Autowired
    private PieChart chart;
    @Autowired
    private Notifications notifications;

    @Subscribe
    protected void onInit(InitEvent event) {
        chart.addLegendItemHideListener(legendItemHideEvent ->
                notifications.create()
                        .withCaption("LegendItemHideEvent")
                        .withDescription(itemInfo(legendItemHideEvent.getEntity()))
                        .withContentMode(ContentMode.HTML)
                        .show());

        chart.addLegendItemShowListener(legendItemShowEvent ->
                notifications.create()
                        .withCaption("LegendItemShowEvent")
                        .withDescription(itemInfo(legendItemShowEvent.getEntity()))
                        .withContentMode(ContentMode.HTML)
                        .show());
    }

    private String itemInfo(Entity entity) {
        CountryLitres countryLitres = (CountryLitres) entity;
        return countryLitres.getCountry() + ": " + countryLitres.getLitres() + " litres";
    }
}