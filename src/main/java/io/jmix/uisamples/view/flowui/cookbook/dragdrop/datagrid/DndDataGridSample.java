package io.jmix.uisamples.view.flowui.cookbook.dragdrop.datagrid;

import com.vaadin.flow.component.grid.Grid;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.component.grid.TreeDataGrid;
import io.jmix.flowui.component.groupgrid.GroupInfo;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.*;
import io.jmix.groupgridflowui.component.GroupDataGrid;
import io.jmix.uisamples.entity.Customer;
import io.jmix.uisamples.entity.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ViewController("dnd-data-grid")
@ViewDescriptor("dnd-data-grid.xml")
public class DndDataGridSample extends StandardView {

    @ViewComponent
    private DataGrid<Customer> customersSourceGrid;
    @ViewComponent
    private DataGrid<Customer> customersTargetGrid;
    @ViewComponent
    private CollectionContainer<Customer> customersSourceDc;
    @ViewComponent
    private CollectionContainer<Customer> customersTargetDc;

    @ViewComponent
    private TreeDataGrid<Task> tasksSourceGrid;
    @ViewComponent
    private TreeDataGrid<Task> tasksTargetGrid;
    @ViewComponent
    private CollectionContainer<Task> tasksSourceDc;
    @ViewComponent
    private CollectionContainer<Task> tasksTargetDc;

    @ViewComponent
    private GroupDataGrid<Customer> customersGroupSourceGrid;
    @ViewComponent
    private GroupDataGrid<Customer> customersGroupTargetGrid;
    @ViewComponent
    private CollectionContainer<Customer> customersGroupSourceDc;
    @ViewComponent
    private CollectionContainer<Customer> customersGroupTargetDc;

    private List<?> draggedItems;
    private CollectionContainer<?> sourceDc;

    @Subscribe
    public void onInit(InitEvent event) {
        setupRowTransfer(customersSourceGrid, customersSourceDc, customersTargetGrid, customersTargetDc);

        setupSubtreeTransfer(tasksSourceGrid, tasksSourceDc, tasksTargetGrid, tasksTargetDc);

        setupGroupRowTransfer(customersGroupSourceGrid, customersGroupSourceDc,
                customersGroupTargetGrid, customersGroupTargetDc);
    }

    private void setupRowTransfer(Grid<?> gridA, CollectionContainer<?> dcA,
                                  Grid<?> gridB, CollectionContainer<?> dcB) {
        rememberDraggedItems(gridA, dcA);
        rememberDraggedItems(gridB, dcB);

        gridA.addDropListener(dropEvent -> moveRows(dcA));
        gridB.addDropListener(dropEvent -> moveRows(dcB));
    }

    private void setupSubtreeTransfer(TreeDataGrid<Task> gridA, CollectionContainer<Task> dcA,
                                      TreeDataGrid<Task> gridB, CollectionContainer<Task> dcB) {
        rememberDraggedItems(gridA, dcA);
        rememberDraggedItems(gridB, dcB);

        gridA.addDropListener(dropEvent -> moveSubtrees(dcA));
        gridB.addDropListener(dropEvent -> moveSubtrees(dcB));
    }

    private void rememberDraggedItems(Grid<?> grid, CollectionContainer<?> dc) {
        grid.addDragStartListener(dragStartEvent -> {
            draggedItems = dragStartEvent.getDraggedItems();
            sourceDc = dc;
        });
    }

    private void setupGroupRowTransfer(GroupDataGrid<Customer> gridA, CollectionContainer<Customer> dcA,
                                       GroupDataGrid<Customer> gridB, CollectionContainer<Customer> dcB) {
        gridA.setDragFilter(item -> dcA.getItems().contains(item));
        gridB.setDragFilter(item -> dcB.getItems().contains(item));

        rememberDraggedGroupRows(gridA, dcA);
        rememberDraggedGroupRows(gridB, dcB);

        gridA.addDropListener(dropEvent -> moveGroupRows(dcA, gridA, gridB));
        gridB.addDropListener(dropEvent -> moveGroupRows(dcB, gridA, gridB));
    }

    private void rememberDraggedGroupRows(GroupDataGrid<Customer> grid, CollectionContainer<Customer> dc) {
        grid.addDragStartListener(dragStartEvent -> {
            draggedItems = dragStartEvent.getDraggedItems();
            sourceDc = dc;
        });
    }

    private void moveGroupRows(CollectionContainer<Customer> targetDc,
                               GroupDataGrid<Customer> gridA, GroupDataGrid<Customer> gridB) {
        Set<GroupInfo> expandedA = expandedGroups(gridA);
        Set<GroupInfo> expandedB = expandedGroups(gridB);

        moveRows(targetDc);

        restoreExpandedGroups(gridA, expandedA);
        restoreExpandedGroups(gridB, expandedB);
    }

    private Set<GroupInfo> expandedGroups(GroupDataGrid<Customer> grid) {
        return grid.getRootGroups().stream()
                .filter(grid::isExpanded)
                .collect(Collectors.toSet());
    }

    private void restoreExpandedGroups(GroupDataGrid<Customer> grid, Set<GroupInfo> expandedGroups) {
        grid.getRootGroups().stream()
                .filter(expandedGroups::contains)
                .forEach(grid::expand);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void moveRows(CollectionContainer targetDc) {
        if (draggedItems == null || sourceDc == null || sourceDc == targetDc) {
            return;
        }
        targetDc.getMutableItems().addAll(draggedItems);
        ((CollectionContainer) sourceDc).getMutableItems().removeAll(draggedItems);

        clearDragState();
    }

    @SuppressWarnings("unchecked")
    private void moveSubtrees(CollectionContainer<Task> targetDc) {
        if (draggedItems == null || sourceDc == null || sourceDc == targetDc) {
            return;
        }

        CollectionContainer<Task> from = (CollectionContainer<Task>) sourceDc;
        for (Object item : draggedItems) {
            Task root = (Task) item;
            List<Task> subtree = new ArrayList<>();

            collectSubtree(root, from, subtree);
            root.setParentTask(null);

            targetDc.getMutableItems().addAll(subtree);
            from.getMutableItems().removeAll(subtree);
        }

        clearDragState();
    }

    private void collectSubtree(Task root, CollectionContainer<Task> dc, List<Task> result) {
        result.add(root);
        for (Task task : dc.getItems()) {
            if (root.equals(task.getParentTask())) {
                collectSubtree(task, dc, result);
            }
        }
    }

    private void clearDragState() {
        draggedItems = null;
        sourceDc = null;
    }
}
