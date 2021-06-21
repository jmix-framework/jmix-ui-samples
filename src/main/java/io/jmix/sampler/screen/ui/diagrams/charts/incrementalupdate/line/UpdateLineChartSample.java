package io.jmix.sampler.screen.ui.diagrams.charts.incrementalupdate.line;

import io.jmix.core.Metadata;
import io.jmix.core.TimeSource;
import io.jmix.sampler.entity.DateValue;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.Label;
import io.jmix.ui.component.Timer;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@UiController("update-line-chart")
@UiDescriptor("update-line-chart.xml")
public class UpdateLineChartSample extends ScreenFragment {
    @Autowired
    private CollectionContainer<DateValue> dateValueDc;
    @Autowired
    private Metadata metadata;
    @Autowired
    private Label<String> statusLabel;
    @Autowired
    private Timer timer;

    @Autowired
    private TimeSource timeSource;

    private Date lastDate;

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
        dateValueDc.getMutableItems().add(generateDateValue());
    }

    private void removeData() {
        List<DateValue> items = dateValueDc.getMutableItems();
        if (!items.isEmpty()) {
            items.remove(0);
        }
    }

    private DateValue generateDateValue() {
        DateValue dateValue = metadata.create(DateValue.class);
        lastDate = DateUtils.addDays(getLastDate(), 1);
        dateValue.setDate(lastDate);
        dateValue.setValue(RandomUtils.nextInt(0, 50));
        return dateValue;
    }

    private Date getLastDate() {
        if (lastDate == null) {
            List<DateValue> items = dateValueDc.getItems();
            if (items.isEmpty()) {
                return timeSource.currentTimestamp();
            } else {
                DateValue dateValue = items.get(items.size() - 1);
                return dateValue.getDate();
            }
        }
        return lastDate;
    }
}