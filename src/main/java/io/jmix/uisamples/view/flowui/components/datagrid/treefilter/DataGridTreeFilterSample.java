package io.jmix.uisamples.view.flowui.components.datagrid.treefilter;

import io.jmix.flowui.component.grid.TreeDataGrid;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.model.CollectionContainerSortManager;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.view.*;
import io.jmix.uisamples.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;

@ViewController("data-grid-tree-filter")
@ViewDescriptor("data-grid-tree-filter.xml")
public class DataGridTreeFilterSample extends StandardView {

    @ViewComponent
    private CollectionContainer<Task> tasksDc;
    @ViewComponent
    private CollectionLoader<Task> tasksDl;
    @ViewComponent
    private CollectionContainer<Task> tasksTreeDc;
    @ViewComponent
    private TreeDataGrid<Task> tasksDataGrid;

    @Autowired
    private TreeAncestorsLoader treeAncestorsLoader;
    @Autowired
    private CollectionContainerSortManager collectionContainerSortManager;

    @Subscribe
    public void onInit(InitEvent event) {
        tasksTreeDc.setSorter(collectionContainerSortManager.createCollectionContainerSorter(tasksTreeDc, tasksDl));
    }

    @Subscribe(id = "tasksDc", target = Target.DATA_CONTAINER)
    public void onTasksDcCollectionChange(CollectionContainer.CollectionChangeEvent<Task> event) {
        tasksTreeDc.setItems(treeAncestorsLoader.withAncestors(tasksDc, Task::getParentTask));
        tasksDataGrid.expand(tasksTreeDc.getItems());
    }
}
