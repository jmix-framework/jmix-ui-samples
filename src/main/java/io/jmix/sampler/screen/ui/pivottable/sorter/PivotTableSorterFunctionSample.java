package io.jmix.sampler.screen.ui.pivottable.sorter;

import io.jmix.pivottable.component.PivotTable;
import io.jmix.pivottable.model.JsFunction;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("pivottable-sorter-function")
@UiDescriptor("pivottable-sorter-function.xml")
public class PivotTableSorterFunctionSample extends ScreenFragment {

    @Autowired
    private PivotTable pivotTable;
    @Autowired
    private MessageBundle messageBundle;

    @Subscribe
    protected void onInit(InitEvent event) {
        pivotTable.setSortersFunction(new JsFunction(messageBundle.getMessage("function.sorters.dayOfWeek")));
    }
}
