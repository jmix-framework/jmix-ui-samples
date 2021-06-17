package io.jmix.sampler.screen.ui.pivottable.showpivotaction;

import io.jmix.pivottable.action.list.PivotScreenBuilder;
import io.jmix.pivottable.action.list.ShowPivotAction;
import io.jmix.sampler.entity.TipInfo;
import io.jmix.ui.Actions;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Collection;

@UiController("pivottable-show-pivot-action")
@UiDescriptor("pivottable-show-pivot-action.xml")
public class PivotTableShowPivotActionSample extends ScreenFragment {

    @Autowired
    private GroupTable<TipInfo> tipsTable;
    @Autowired
    private Button defaultShowPivotBtn;

    @Autowired
    private Actions actions;
    @Autowired
    private MessageBundle messageBundle;

    @Subscribe
    public void onInit(InitEvent event) {
        ShowPivotAction showPivotAction = actions.create(ShowPivotAction.class);
        showPivotAction.setTarget(tipsTable);
        defaultShowPivotBtn.setAction(showPivotAction);
    }

    @Subscribe("tipsTable.customShowPivotAction")
    public void onTipsTableCustomShowPivotAction(Action.ActionPerformedEvent event) {
        PivotScreenBuilder builder = getApplicationContext().getBean(PivotScreenBuilder.class, tipsTable);
        builder.withIncludedProperties(Arrays.asList("sex", "smoker", "day", "time"))
                .withNativeJson("{\"cols\": " + messageBundle.getMessage("localizedCols") + ","
                        + "\"rows\": " + messageBundle.getMessage("localizedRows") + "}")
                .withItems((Collection) tipsTable.getItems().getItems())
                .build()
                .show();
    }
}
