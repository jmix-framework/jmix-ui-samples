package io.jmix.sampler.screen.ui.diagrams.charts.incrementalupdate.xy;

import io.jmix.core.Metadata;
import io.jmix.sampler.entity.PointValue;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.Label;
import io.jmix.ui.component.Timer;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@UiController("update-xy-chart")
@UiDescriptor("update-xy-chart.xml")
public class UpdateXyChartSample extends ScreenFragment {

    @Autowired
    private CollectionContainer<PointValue> pointsDc;
    @Autowired
    private Metadata metadata;
    @Autowired
    private Label<String> statusLabel;
    @Autowired
    private Timer timer;

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
        pointsDc.getMutableItems().add(generatePointValue());
    }

    private void removeData() {
        List<PointValue> items = pointsDc.getMutableItems();
        if (!items.isEmpty()) {
            items.remove(0);
        }
    }

    private PointValue generatePointValue() {
        PointValue pointValue = metadata.create(PointValue.class);
        pointValue.setX((RandomUtils.nextInt(0, 20) - 5) * RandomUtils.nextDouble(0, 1));
        pointValue.setY((RandomUtils.nextInt(0, 40) - 20) * RandomUtils.nextDouble(0, 1));
        pointValue.setValue(RandomUtils.nextInt(10, 100));
        return pointValue;
    }
}