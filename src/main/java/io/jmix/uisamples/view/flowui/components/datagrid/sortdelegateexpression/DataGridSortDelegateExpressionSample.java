package io.jmix.uisamples.view.flowui.components.datagrid.sortdelegateexpression;

import io.jmix.flowui.component.grid.DataGridSortContext;
import io.jmix.flowui.component.grid.sort.DataGridSort;
import io.jmix.flowui.component.grid.sort.DataGridSortBuilder;
import io.jmix.flowui.view.Install;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import io.jmix.uisamples.entity.Order;

import java.util.List;

@ViewController("data-grid-sort-delegate-expression")
@ViewDescriptor("data-grid-sort-delegate-expression.xml")
public class DataGridSortDelegateExpressionSample extends StandardView {

    @Install(to = "ordersDataGrid", subject = "sortBuilderDelegate")
    public DataGridSort ordersDataGridSortBuilderDelegate(DataGridSortContext<Order> context) {
        return DataGridSortBuilder.create(context)
                .replaceSort("customer", List.of("{E}.customer.name", "{E}.customer.lastName"))
                .build();
    }
}
