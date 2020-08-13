package io.jmix.sampler.screen.ui.components.progressbar.standard;

import io.jmix.ui.component.ProgressBar;
import io.jmix.ui.executor.BackgroundTask;
import io.jmix.ui.executor.BackgroundTaskHandler;
import io.jmix.ui.executor.BackgroundWorker;
import io.jmix.ui.executor.TaskLifeCycle;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.TimeUnit;

@UiController("progressbar-standard")
@UiDescriptor("progressbar-standard.xml")
public class ProgressBarStandardSample extends ScreenFragment {

    private static final int ITERATIONS = 20;

    @Autowired
    protected ProgressBar progressBar;
    @Autowired
    protected BackgroundWorker backgroundWorker;

    @Subscribe
    protected void onInit(InitEvent event) {
        BackgroundTask<Integer, Void> task = new BackgroundTask<Integer, Void>(100, TimeUnit.SECONDS, getHostScreen()) {
            @Override
            public Void run(TaskLifeCycle<Integer> taskLifeCycle) throws Exception {
                for (int i = 1; i <= ITERATIONS; i++) {
                    TimeUnit.SECONDS.sleep(1);
                    taskLifeCycle.publish(i);
                }
                return null;
            }

            @Override
            public void progress(List<Integer> changes) {
                double lastValue = changes.get(changes.size() - 1);
                progressBar.setValue(lastValue / ITERATIONS);
            }
        };

        BackgroundTaskHandler taskHandler = backgroundWorker.handle(task);
        taskHandler.execute();
    }
}
