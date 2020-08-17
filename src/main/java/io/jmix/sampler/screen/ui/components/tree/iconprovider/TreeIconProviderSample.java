package io.jmix.sampler.screen.ui.components.tree.iconprovider;

import io.jmix.sampler.entity.Task;
import io.jmix.ui.component.Tree;
import io.jmix.ui.icon.JmixIcon;
import io.jmix.ui.screen.Install;
import io.jmix.ui.screen.LoadDataBeforeShow;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("tree-icon-provider")
@UiDescriptor("tree-icon-provider.xml")
@LoadDataBeforeShow
public class TreeIconProviderSample extends ScreenFragment {
    @Autowired
    protected Tree<Task> tree;

    @Install(to = "tree", subject = "iconProvider")
    protected String treeIconProvider(Task task) {
        return task.getParentTask() != null ? JmixIcon.OK.source() : JmixIcon.CANCEL.source();
    }
}
