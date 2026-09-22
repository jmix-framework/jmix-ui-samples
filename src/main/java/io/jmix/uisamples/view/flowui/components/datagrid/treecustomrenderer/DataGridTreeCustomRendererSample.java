package io.jmix.uisamples.view.flowui.components.datagrid.treecustomrenderer;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.treegrid.HierarchyColumnComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import io.jmix.flowui.component.grid.TreeDataGrid;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Supply;
import io.jmix.flowui.view.ViewComponent;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import io.jmix.uisamples.entity.Task;

@ViewController("data-grid-tree-custom-renderer")
@ViewDescriptor("data-grid-tree-custom-renderer.xml")
public class DataGridTreeCustomRendererSample extends StandardView {

    // tag::hierarchy-column-component-renderer[] sample-hide
    @ViewComponent
    protected TreeDataGrid<Task> taskDataGrid;

    @Supply(to = "taskDataGrid.name", subject = "renderer")
    protected Renderer<Task> nameRenderer() {
        return new HierarchyColumnComponentRenderer<>(task -> {
            HorizontalLayout layout = new HorizontalLayout();
            layout.setPadding(false);
            layout.setAlignItems(HorizontalLayout.Alignment.CENTER);
            layout.add(VaadinIcon.BRIEFCASE.create(), new Span(task.getName()));

            return layout;
        }, taskDataGrid);
    }
    // end::hierarchy-column-component-renderer[] sample-hide
}
