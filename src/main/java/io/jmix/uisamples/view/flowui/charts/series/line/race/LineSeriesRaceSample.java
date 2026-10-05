package io.jmix.uisamples.view.flowui.charts.series.line.race;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.ButtonVariant;
import io.jmix.chartsflowui.component.Chart;
import io.jmix.chartsflowui.data.item.MapDataItem;
import io.jmix.chartsflowui.kit.component.model.DataSet;
import io.jmix.chartsflowui.kit.data.chart.ListChartItems;
import io.jmix.flowui.facet.Timer;
import io.jmix.flowui.icon.Icons;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.kit.icon.JmixFontIcon;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Random;

@ViewController("line-series-race")
@ViewDescriptor("line-series-race.xml")
public class LineSeriesRaceSample extends StandardView {

    private static final List<String> PRODUCTS =
            List.of("productA", "productB", "productC", "productD", "productE");

    @ViewComponent
    private Chart chart;
    @ViewComponent
    private Timer timer;
    @ViewComponent
    private JmixButton startStopRace;

    @Autowired
    private Icons icons;

    private final ListChartItems<MapDataItem> items = new ListChartItems<>();
    private final Random random = new Random();
    private double[] sales;
    private int yearIndex;
    private boolean raceStarted;

    @Subscribe
    public void onInit(InitEvent event) {
        for (int year = 2000; year <= 2025; year++) {
            items.addItem(new MapDataItem().add("year", String.valueOf(year)));
        }

        chart.setDataSet(
                new DataSet()
                        .withSource(
                                new DataSet.Source<MapDataItem>()
                                        .withDataProvider(items)
                                        .withCategoryField("year")
                                        .withValueFields(PRODUCTS.toArray(String[]::new))
                        )
        );

        prepareButtonToStart();
    }

    @Subscribe(id = "startStopRace", subject = "clickListener")
    public void onStartStopRaceClick(final ClickEvent<JmixButton> event) {
        if (raceStarted) {
            stopRace();
        } else {
            startRace();
        }
    }

    @Subscribe("timer")
    public void onTimerTick(Timer.TimerActionEvent event) {
        MapDataItem item = items.getItems().get(yearIndex++);

        for (int i = 0; i < PRODUCTS.size(); i++) {
            sales[i] *= random.nextDouble(0.95, 1.15);
            item.add(PRODUCTS.get(i), Math.round(sales[i]));
        }
        items.updateItem(item);

        if (yearIndex == items.getItems().size()) {
            stopRace();
        }
    }

    private void startRace() {
        if (yearIndex == items.getItems().size()) {
            clearSales();
        }

        if (yearIndex == 0) {
            sales = random.doubles(PRODUCTS.size(), 100, 300).toArray();
        }

        timer.start();
        raceStarted = true;
        prepareButtonToStop();
    }

    private void stopRace() {
        timer.stop();
        raceStarted = false;
        prepareButtonToStart();
    }

    private void clearSales() {
        for (MapDataItem item : items.getItems()) {
            PRODUCTS.forEach(item::remove);
            items.updateItem(item);
        }

        yearIndex = 0;
    }

    private void prepareButtonToStart() {
        startStopRace.setText("Start");
        startStopRace.setIcon(icons.get(JmixFontIcon.PLAY));
        startStopRace.addThemeVariants(ButtonVariant.PRIMARY);
        startStopRace.removeThemeVariants(ButtonVariant.ERROR);
    }

    private void prepareButtonToStop() {
        startStopRace.setText("Stop");
        startStopRace.setIcon(icons.get(JmixFontIcon.PAUSE));
        startStopRace.addThemeVariants(ButtonVariant.ERROR);
    }
}
