package io.jmix.sampler.screen.ui.components.tree.itemclick;

import io.jmix.core.MetadataTools;
import io.jmix.sampler.entity.Task;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.BaseAction;
import io.jmix.ui.component.Tree;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("tree-item-click")
@UiDescriptor("tree-item-click.xml")
public class TreeItemClickSample extends ScreenFragment {
    @Autowired
    protected Tree<Task> tree;
    @Autowired
    protected Notifications notifications;
    @Autowired
    protected MetadataTools metadataTools;

    @Subscribe
    protected void onInit(InitEvent event) {
        tree.setItemClickAction(new BaseAction("treeClickAction")
                .withHandler(actionPerformedEvent -> {
                    Task task = tree.getSingleSelected();
                    if (task != null) {
                        notifications.create()
                                .withCaption("Item clicked for: " + metadataTools.getInstanceName(task))
                                .show();
                    }
                }));
    }
}
