package io.jmix.sampler.screen.ui.components.timefield.dataaware;

import io.jmix.core.Metadata;
import io.jmix.core.TimeSource;
import io.jmix.sampler.entity.Task;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("timefield-dataaware")
@UiDescriptor("timefield-dataaware.xml")
public class TimeFieldDataawareSample extends ScreenFragment {
    @Autowired
    protected InstanceContainer<Task> taskDc;

    @Autowired
    protected Metadata metadata;
    @Autowired
    protected TimeSource timeSource;

    @Subscribe
    protected void onInit(InitEvent event) {
        // InstanceContainer initialization. It is usually done automatically if the screen is
        // inherited from StandardEditor and is used as an entity editor.
        Task task = metadata.create(Task.class);
        task.setDueDate(timeSource.currentTimestamp());
        taskDc.setItem(task);
    }
}
