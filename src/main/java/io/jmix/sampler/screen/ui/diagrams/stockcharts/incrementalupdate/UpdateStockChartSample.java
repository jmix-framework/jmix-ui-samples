package io.jmix.sampler.screen.ui.diagrams.stockcharts.incrementalupdate;

import io.jmix.core.Metadata;
import io.jmix.core.TimeSource;
import io.jmix.sampler.entity.DateValueVolume;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.Label;
import io.jmix.ui.component.Timer;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Random;

@UiController("update-stock-chart")
@UiDescriptor("update-stock-chart.xml")
public class UpdateStockChartSample extends ScreenFragment {
    private static final int DAYS_COUNT = 20;

    @Autowired
    private CollectionContainer<DateValueVolume> stockChartDc1;
    @Autowired
    private CollectionContainer<DateValueVolume> stockChartDc2;
    @Autowired
    private CollectionContainer<DateValueVolume> stockChartDc3;
    @Autowired
    private CollectionContainer<DateValueVolume> stockChartDc4;
    @Autowired
    private Label<String> statusLabel;
    @Autowired
    private Timer timer;

    @Autowired
    private Metadata metadata;
    @Autowired
    private TimeSource timeSource;

    private final Random random = new Random();
    private int lastIndex = DAYS_COUNT;
    private Date lastDate;

    @Subscribe
    protected void onInit(InitEvent event) {
        generateData();

        lastDate = timeSource.currentTimestamp();
    }

    @Subscribe("timer")
    private void onTimerTick(Timer.TimerActionEvent event) {
        addData();
        removeData();
    }

    @Subscribe("startTimer")
    protected void onStartTimerClick(Button.ClickEvent event) {
        timer.start();
        statusLabel.setValue("Update enabled");
    }

    @Subscribe("stopTimer")
    protected void onStopTimerClick(Button.ClickEvent event) {
        timer.stop();
        statusLabel.setValue("Update disabled");
    }

    private void addData() {
        addDateValueVolume(stockChartDc1, 40, 100, 1000, 500, 2, lastDate, lastIndex);
        addDateValueVolume(stockChartDc2, 100, 200, 1000, 600, 2, lastDate, lastIndex);
        addDateValueVolume(stockChartDc3, 100, 200, 1000, 600, 2, lastDate, lastIndex);
        addDateValueVolume(stockChartDc4, 100, 200, 100, 600, 1, lastDate, lastIndex);
        lastDate = DateUtils.addDays(lastDate, 1);
        lastIndex++;
    }

    private void removeData() {
        removeData(stockChartDc1);
        removeData(stockChartDc2);
        removeData(stockChartDc3);
        removeData(stockChartDc4);
    }

    private void removeData(CollectionContainer<DateValueVolume> container) {
        if (!container.getItems().isEmpty()) {
            container.getMutableItems().remove(0);
        }
    }

    private void generateData() {
        populateStockContainer(stockChartDc1, 40, 100, 1000, 500, 2);
        populateStockContainer(stockChartDc2, 100, 200, 1000, 600, 2);
        populateStockContainer(stockChartDc3, 100, 200, 1000, 600, 2);
        populateStockContainer(stockChartDc4, 100, 200, 100, 600, 1);
    }

    private void populateStockContainer(CollectionContainer<DateValueVolume> container,
                                        int valueX1, int valueX2, int volumeX1, int volumeX2, int volumeX3) {
        Date startDate = DateUtils.addDays(timeSource.currentTimestamp(), -DAYS_COUNT);
        for (int i = 0; i < DAYS_COUNT; i++) {
            addDateValueVolume(container, valueX1, valueX2, volumeX1, volumeX2, volumeX3,
                    DateUtils.addDays(startDate, i), i);
        }
    }

    private void addDateValueVolume(CollectionContainer<DateValueVolume> container,
                                    int valueX1, int valueX2, int volumeX1, int volumeX2, int volumeX3,
                                    Date date, int i) {
        Long value = Math.round(random.nextDouble() * (valueX1 + i)) + valueX2 + i;
        Long volume = Math.round(random.nextDouble() * (volumeX1 + i)) + volumeX2 + i + volumeX3;
        container.getMutableItems().add(dateValueVolume(date, value, volume));
    }

    private DateValueVolume dateValueVolume(Date date, Long value, Long volume) {
        DateValueVolume dateValueVolume = metadata.create(DateValueVolume.class);
        dateValueVolume.setDate(date);
        dateValueVolume.setValue(value);
        dateValueVolume.setVolume(volume);
        return dateValueVolume;
    }
}