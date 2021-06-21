package io.jmix.sampler.screen.ui.diagrams.stockcharts.stockevents;

import io.jmix.charts.component.StockChart;
import io.jmix.core.Metadata;
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

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@UiController("stockchart-stock-events")
@UiDescriptor("stockchart-stock-events.xml")
public class StockChartStockEventsSample extends ScreenFragment {
    private static final int DAYS_COUNT = 500;

    @Autowired
    private StockChart stockChart;
    @Autowired
    private CollectionContainer<DateValueVolume> stockChartDc;

    @Autowired
    private Notifications notifications;
    @Autowired
    private Metadata metadata;

    private final Random random = new Random();

    @Subscribe
    protected void onInit(InitEvent event) {
        generateData();
        addEventListeners();
    }

    private void generateData() {
        List<DateValueVolume> items = new ArrayList<>();
        LocalDate localDate = LocalDate.of(2015, Month.DECEMBER, 31);
        Date startDate = Date.from(localDate.minusDays(DAYS_COUNT).atStartOfDay(ZoneId.systemDefault()).toInstant());
        for (int i = 0; i < DAYS_COUNT; i++) {
            items.add(generateDateValueVolume(DateUtils.addDays(startDate, i), i));
        }
        stockChartDc.setItems(items);
    }

    private DateValueVolume generateDateValueVolume(Date date, int i) {
        Long value = Math.round(random.nextDouble() * (40 + i)) + 100 + i;
        Long volume = Math.round(random.nextDouble() * (1000 + i)) + 600 + i + 2;
        return dateValueVolume(date, value, volume);
    }

    private DateValueVolume dateValueVolume(Date date, Long value, Long volume) {
        DateValueVolume dateValueVolume = metadata.create(DateValueVolume.class);
        dateValueVolume.setDate(date);
        dateValueVolume.setValue(value);
        dateValueVolume.setVolume(volume);
        return dateValueVolume;
    }

    private void addEventListeners() {
        stockChart.addStockEventClickListener(event ->
                notifications.create()
                        .withCaption("StockEventClickEvent")
                        .withDescription("<Strong>Graph ID:</Strong> " + event.getGraphId() + "</br>"
                                + "<Strong>Date:</Strong> " + event.getDate() + "</br>"
                                + "<Strong>StockEvent:</Strong> " + event.getStockEvent())
                        .withType(NotificationType.TRAY)
                        .withContentMode(ContentMode.HTML)
                        .show());
    }
}