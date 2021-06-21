package io.jmix.sampler.screen.ui.diagrams.stockcharts.drawing;

import io.jmix.core.Metadata;
import io.jmix.core.TimeSource;
import io.jmix.sampler.entity.DateValueVolume;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@UiController("stockchart-drawing")
@UiDescriptor("stockchart-drawing.xml")
public class StockChartDrawingSample extends ScreenFragment {
    private static final int DAYS_COUNT = 500;

    @Autowired
    private CollectionContainer<DateValueVolume> stockChartDc;
    @Autowired
    private TimeSource timeSource;
    @Autowired
    private Metadata metadata;

    private final Random random = new Random();

    @Subscribe
    protected void onInit(InitEvent event) {
        List<DateValueVolume> items = new ArrayList<>();
        Date startDate = DateUtils.addDays(timeSource.currentTimestamp(), -DAYS_COUNT);
        for (int i = 0; i < DAYS_COUNT; i++) {
            items.add(generateDateValueVolume(DateUtils.addDays(startDate, i), i));
        }
        stockChartDc.setItems(items);
    }

    private DateValueVolume generateDateValueVolume(Date date, int i) {
        Long value = Math.round(random.nextDouble() * (40 + i)) + 100 + i;
        Long volume = Math.round(random.nextDouble() * i) + i;
        return dateValueVolume(date, value, volume);
    }

    private DateValueVolume dateValueVolume(Date date, Long value, Long volume) {
        DateValueVolume dateValueVolume = metadata.create(DateValueVolume.class);
        dateValueVolume.setDate(date);
        dateValueVolume.setValue(value);
        dateValueVolume.setVolume(volume);
        return dateValueVolume;
    }
}