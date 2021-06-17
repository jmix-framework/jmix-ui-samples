package io.jmix.sampler.screen.ui.pivottable.uiproperties;

import io.jmix.pivottable.component.PivotTable;
import io.jmix.ui.component.Button;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("pivottable-ui-properties")
@UiDescriptor("pivottable-ui-properties.xml")
public class PivotTableUiPropertiesSample extends ScreenFragment {

    @Autowired
    private PivotTable pivotTable;

    @Subscribe("showUiBtn")
    protected void onShowUiBtnClick(Button.ClickEvent event) {
        pivotTable.setShowUI(!pivotTable.isShowUI());
        pivotTable.repaint();
    }

    @Subscribe("showRowTotalsBtn")
    protected void onShowRowTotalsBtnClick(Button.ClickEvent event) {
        pivotTable.setShowRowTotals(!pivotTable.isRowTotalsShown());
        pivotTable.repaint();
    }

    @Subscribe("showColTotalsBtn")
    protected void onShowColTotalsBtnClick(Button.ClickEvent event) {
        pivotTable.setShowColTotals(!pivotTable.isColTotalsShown());
        pivotTable.repaint();
    }
}
