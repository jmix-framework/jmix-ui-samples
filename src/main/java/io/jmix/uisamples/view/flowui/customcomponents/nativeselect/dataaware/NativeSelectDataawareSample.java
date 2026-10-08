package io.jmix.uisamples.view.flowui.customcomponents.nativeselect.dataaware;

import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.view.*;
import io.jmix.uisamples.entity.Employee;

@ViewController("native-select-dataaware")
@ViewDescriptor("native-select-dataaware.xml")
public class NativeSelectDataawareSample extends StandardView {

    @ViewComponent
    private DataGrid<Employee> employeesDataGrid;
    @ViewComponent
    private CollectionContainer<Employee> employeesDc;

    @Subscribe(id = "employeesDl", target = Target.DATA_LOADER)
    public void onEmployeesDlPostLoad(final CollectionLoader.PostLoadEvent<Employee> event) {
        employeesDc.getItems().stream()
                .findFirst()
                .ifPresent(employeesDataGrid::select);
    }
}
