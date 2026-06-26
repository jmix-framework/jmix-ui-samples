package io.jmix.uisamples.view.flowui.containers.tabsheet.states;

import io.jmix.flowui.component.tabsheet.JmixTabSheet;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@ViewController("tabsheet-states")
@ViewDescriptor("tabsheet-states.xml")
public class TabSheetStatesSample extends StandardView {

    @Subscribe("tabSheet")
    public void onTabSheetSelectChangeEvent(JmixTabSheet.SelectedChangeEvent event) {
        event.getPreviousTab().setLabel("Unselected");
        event.getSelectedTab().setLabel("Selected");
    }
}
