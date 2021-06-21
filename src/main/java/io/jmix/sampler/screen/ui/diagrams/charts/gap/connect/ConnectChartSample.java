package io.jmix.sampler.screen.ui.diagrams.charts.gap.connect;

import io.jmix.core.Metadata;
import io.jmix.core.TimeSource;
import io.jmix.sampler.entity.DateValueGap;
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

@UiController("connect-chart")
@UiDescriptor("connect-chart.xml")
public class ConnectChartSample extends ScreenFragment {

    private static final int DAYS_COUNT = 15;

    @Autowired
    private CollectionContainer<DateValueGap> gapChartDc;

    @Autowired
    private Metadata metadata;

    @Autowired
    private TimeSource timeSource;

    private final Random random = new Random();

    @Subscribe
    private void onInit(InitEvent event) {
        List<DateValueGap> items = new ArrayList<>(DAYS_COUNT);
        Date startDate = DateUtils.addDays(timeSource.currentTimestamp(), -DAYS_COUNT);
        for (int i = 0; i < DAYS_COUNT; i++) {
            if (i < 7 || i > 8) {
                items.add(generateDateValueGap(DateUtils.addDays(startDate, i), i));
            }
        }
        gapChartDc.setItems(items);
    }

    private DateValueGap generateDateValueGap(Date date, int i) {
        Long value = Math.round(random.nextDouble() * (20 + i)) - 20 + i;
        return dateValueVolume(date, value);
    }

    private DateValueGap dateValueVolume(Date date, Long value) {
        DateValueGap dateValueGap = metadata.create(DateValueGap.class);
        dateValueGap.setDate(date);
        dateValueGap.setValue(value);
        return dateValueGap;
    }
}
