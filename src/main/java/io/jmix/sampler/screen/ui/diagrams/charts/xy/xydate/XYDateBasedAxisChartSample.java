package io.jmix.sampler.screen.ui.diagrams.charts.xy.xydate;

import com.google.common.collect.ImmutableMap;
import io.jmix.charts.component.XYChart;
import io.jmix.ui.data.DataItem;
import io.jmix.ui.data.impl.ListDataProvider;
import io.jmix.ui.data.impl.MapDataItem;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Random;

@UiController("xy-dateaxis-chart")
@UiDescriptor("xy-dateaxis-chart.xml")
public class XYDateBasedAxisChartSample extends ScreenFragment {
    private static final int DAYS_COUNT = 10;

    @Autowired
    private XYChart xyChart;

    private final Random random = new Random();

    @Subscribe
    protected void onInit(InitEvent event) {
        ListDataProvider dataProvider = new ListDataProvider();

        Date startDate = DateUtils.addDays(new Date(), -DAYS_COUNT);
        for (int i = 0; i < DAYS_COUNT; i++) {
            dataProvider.addItem(generateDateValueVolume(DateUtils.addDays(startDate, i), i));
        }
        xyChart.setDataProvider(dataProvider);

        xyChart.getValueAxes().get(0).setMinimumDate(DateUtils.addDays(startDate, -1));
        xyChart.getValueAxes().get(0).setMaximumDate(DateUtils.addDays(startDate, DAYS_COUNT + 1));
    }

    private DataItem generateDateValueVolume(Date date, int i) {
        Long roundValue = Math.round(random.nextDouble() * (5 + i)) + 2 + i;
        Long diamondValue = Math.round(random.nextDouble() * (2 + i)) + 2 + i;
        Long roundY = Math.round(random.nextDouble() * (2 + i)) + 1 + i;
        Long diamondY = Math.round(random.nextDouble() * (6 + i)) + 1 + i;
        return dateValueVolume(date, roundValue, roundY, diamondValue, diamondY);
    }

    private DataItem dateValueVolume(Date date, Long roundValue, Long roundY, Long diamondValue, Long diamondY) {
        return new MapDataItem(ImmutableMap.of(
                "date", date,
                "roundValue", roundValue,
                "roundY", roundY,
                "diamondValue", diamondValue,
                "diamondY", diamondY)
        );
    }
}
