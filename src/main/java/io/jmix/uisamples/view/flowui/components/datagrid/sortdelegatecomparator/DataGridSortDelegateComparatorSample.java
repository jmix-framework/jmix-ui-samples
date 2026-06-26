package io.jmix.uisamples.view.flowui.components.datagrid.sortdelegatecomparator;

import io.jmix.flowui.component.grid.DataGridSortContext;
import io.jmix.flowui.component.grid.sort.DataGridSort;
import io.jmix.flowui.component.grid.sort.DataGridSortBuilder;
import io.jmix.flowui.view.Install;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import io.jmix.uisamples.entity.Customer;
import io.jmix.uisamples.entity.CustomerGrade;

import java.util.Comparator;
import java.util.List;

@ViewController("data-grid-sort-delegate-comparator")
@ViewDescriptor("data-grid-sort-delegate-comparator.xml")
public class DataGridSortDelegateComparatorSample extends StandardView {

    private static final List<CustomerGrade> GRADE_PRIORITY =
            List.of(CustomerGrade.HIGH, CustomerGrade.PREMIUM, CustomerGrade.STANDARD);

    @Install(to = "customersDataGrid", subject = "sortBuilderDelegate")
    public DataGridSort customersDataGridSortBuilderDelegate(DataGridSortContext<Customer> context) {
        return DataGridSortBuilder.create(context)
                .replaceSort("grade", gradePriorityComparator())
                .build();
    }

    private Comparator<Customer> gradePriorityComparator() {
        return Comparator.comparing(Customer::getGrade,
                        Comparator.nullsLast(Comparator.comparingInt(GRADE_PRIORITY::indexOf)))
                .thenComparing(Customer::getName)
                .thenComparing(Customer::getLastName);
    }
}
