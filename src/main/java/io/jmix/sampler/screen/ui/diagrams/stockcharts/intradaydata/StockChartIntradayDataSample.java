package io.jmix.sampler.screen.ui.diagrams.stockcharts.intradaydata;

import io.jmix.charts.component.StockChart;
import io.jmix.core.Metadata;
import io.jmix.core.TimeSource;
import io.jmix.sampler.entity.DateValueVolume;
import io.jmix.ui.Notifications;
import io.jmix.ui.Notifications.NotificationType;
import io.jmix.ui.component.ContentMode;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@UiController("stockchart-intraday-data")
@UiDescriptor("stockchart-intraday-data.xml")
public class StockChartIntradayDataSample extends ScreenFragment {
    private static final int MINUTES_COUNT = 1000;

    @Autowired
    private StockChart stockChart;
    @Autowired
    private CollectionContainer<DateValueVolume> stockChartDc;

    @Autowired
    private Metadata metadata;
    @Autowired
    private Notifications notifications;
    @Autowired
    private TimeSource timeSource;

    private final Random random = new Random();

    @Subscribe
    protected void onInit(InitEvent event) {
        generateData();
        addEventListeners();
    }

    private void generateData() {
        List<DateValueVolume> items = new ArrayList<>();
        Date startDate = DateUtils.addDays(getZeroTime(timeSource.currentTimestamp()), -MINUTES_COUNT);
        for (int i = 0; i < MINUTES_COUNT; i++) {
            items.add(generateDateValueVolume(DateUtils.addMinutes(startDate, i), i));
        }
        stockChartDc.setItems(items);
    }

    private DateValueVolume generateDateValueVolume(Date date, int i) {
        Long value = Math.round(random.nextDouble() * (40 + i)) + 100 + i;
        Long volume = Math.round(random.nextDouble() * 100000000);
        return dateValueVolume(date, value, volume);
    }

    private DateValueVolume dateValueVolume(Date date, Long value, Long volume) {
        DateValueVolume dateValueVolume = metadata.create(DateValueVolume.class);
        dateValueVolume.setDate(date);
        dateValueVolume.setValue(value);
        dateValueVolume.setVolume(volume);
        return dateValueVolume;
    }

    private Date getZeroTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private void addEventListeners() {
        stockChart.addStockGraphItemClickListener(event ->
                showStockGraphItemEvent(event, "StockGraphItemClickEvent"));
        stockChart.addStockGraphItemRightClickListener(event ->
                showStockGraphItemEvent(event, "StockGraphItemRightClickEvent"));
    }

    private void showStockGraphItemEvent(StockChart.AbstractStockGraphItemEvent event, String message) {
        notifications.create()
                .withCaption(message)
                .withDescription("<Strong>Panel ID:</Strong> " + event.getPanelId() + "</br>"
                        + "<Strong>Graph ID:</Strong> " + event.getGraphId() + "</br>"
                        + "<Strong>Item Index:</Strong> " + event.getItemIndex() + "</br>"
                        + "<Strong>Item:</Strong> " + event.getEntity() + "</br>"
                        + "<Strong>X:</Strong> " + event.getX() + "</br>"
                        + "<Strong>Y:</Strong> " + event.getY() + "</br>"
                        + "<Strong>Absolute X:</Strong> " + event.getAbsoluteX() + "</br>"
                        + "<Strong>Absolute Y:</Strong> " + event.getAbsoluteY())
                .withType(NotificationType.TRAY)
                .withContentMode(ContentMode.HTML)
                .show();
    }
}