package io.jmix.uisamples.view.flowui.components.progressbar.themevariant;

import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.progressbar.ProgressBar;
import io.jmix.flowui.backgroundtask.BackgroundTask;
import io.jmix.flowui.backgroundtask.BackgroundTaskHandler;
import io.jmix.flowui.backgroundtask.BackgroundWorker;
import io.jmix.flowui.backgroundtask.TaskLifeCycle;
import io.jmix.flowui.component.checkbox.JmixCheckbox;
// sample-hide:start
import io.jmix.uisamples.theme.AppTheme;
import io.jmix.uisamples.theme.ThemeManager;
// sample-hide:end
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

@ViewController("progress-bar-theme-variant")
@ViewDescriptor("progress-bar-theme-variant.xml")
public class ProgressBarThemeVariantSample extends StandardView {

    private static final int ITERATIONS = 20;

    @ViewComponent
    private ProgressBar standardProgressBar;
    @ViewComponent
    private ProgressBar successProgressBar;
    // theme-only:aura
    @ViewComponent
    private ProgressBar warningProgressBar;
    // theme-only:aura:end
    @ViewComponent
    private ProgressBar errorProgressBar;
    // theme-only:lumo
    @ViewComponent
    private ProgressBar contrastProgressBar;
    // theme-only:lumo:end

    @Autowired
    private BackgroundWorker backgroundWorker;
    // sample-hide:start
    @Autowired
    private ThemeManager themeManager;
    // sample-hide:end

    private BackgroundTaskHandler<Void> taskHandler;
    private Collection<ProgressBar> progressBars;

    @Subscribe
    public void onInit(InitEvent event) {
        if (themeManager.getCurrentTheme() == AppTheme.LUMO) { // sample-hide
            // theme-only:lumo
        progressBars = List.of(standardProgressBar, successProgressBar, errorProgressBar, contrastProgressBar);
            // theme-only:lumo:end
        } else { // sample-hide
            // theme-only:aura
        progressBars = List.of(standardProgressBar, successProgressBar, warningProgressBar, errorProgressBar);
            // theme-only:aura:end
        } // sample-hide
    }

    @Subscribe("indeterminateCheckbox")
    public void onIndeterminateCheckboxValueChange(ComponentValueChangeEvent<JmixCheckbox, Boolean> event) {
        Boolean value = event.getValue();

        if (value && taskHandler != null && taskHandler.isAlive()) {
            taskHandler.cancel();
        } else if (!value){
            runNewTask();
        }

        progressBars.forEach(progressBar -> progressBar.setIndeterminate(value));
    }

    private void runNewTask() {
        taskHandler = backgroundWorker.handle(createBackgroundTask());
        taskHandler.execute();
    }

    private BackgroundTask<Integer, Void> createBackgroundTask() {
        return new BackgroundTask<>(100, TimeUnit.SECONDS) {

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
                progressBars.forEach(progressBar -> progressBar.setValue(lastValue / ITERATIONS));
            }
        };
    }
}
