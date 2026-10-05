package io.jmix.uisamples.view.flowui.charts.series.bar.race;

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

import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

@ViewController("bar-series-race")
@ViewDescriptor("bar-series-race.xml")
public class BarSeriesRaceSample extends StandardView {

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
    private boolean raceStarted;

    @Subscribe
    protected void onInit(InitEvent event) {
        Stream.of("Laptops", "Phones", "Tablets", "Monitors", "Printers", "Cameras", "Headphones", "Speakers")
                .map(this::toMapDataItem)
                .forEach(items::addItem);

        chart.setDataSet(
                new DataSet()
                        .withSource(
                                new DataSet.Source<MapDataItem>()
                                        .withDataProvider(items)
                                        .withCategoryField("product")
                                        .withValueField("sales")
                        )
        );

        prepareButtonToStart();
    }

    @Subscribe(id = "startStopRace", subject = "clickListener")
    public void onStartStopRaceClick(final ClickEvent<JmixButton> event) {
        if (raceStarted) {
            timer.stop();
            prepareButtonToStart();
        } else {
            timer.start();
            prepareButtonToStop();
        }

        raceStarted = !raceStarted;
    }

    @Subscribe("timer")
    public void onTimerTick(Timer.TimerActionEvent event) {
        for (MapDataItem item : items.getItems()) {
            int sales = (int) item.getValue("sales") + random.nextInt(0, 500);
            items.updateItem(item.add("sales", sales));
        }
    }

    private MapDataItem toMapDataItem(String product) {
        return new MapDataItem(Map.of("product", product,
                "sales", random.nextInt(100, 1000)));
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
