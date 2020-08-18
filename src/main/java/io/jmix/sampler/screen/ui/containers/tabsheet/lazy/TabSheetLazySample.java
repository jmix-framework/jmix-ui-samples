package io.jmix.sampler.screen.ui.containers.tabsheet.lazy;

import io.jmix.ui.component.Label;
import io.jmix.ui.component.TabSheet;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("tabsheet-lazy")
@UiDescriptor("tabsheet-lazy.xml")
public class TabSheetLazySample extends ScreenFragment {

    @Autowired
    protected Label<String> info;

    @Subscribe
    protected void onInit(InitEvent event) {
        checkComponents();
    }

    @Subscribe("tabSheet")
    protected void onTabSheetSelectedTabChange(TabSheet.SelectedTabChangeEvent event) {
        if (event.getSelectedTab().getName().equals("tab2")) {
            checkComponents();
        }
    }

    private void checkComponents() {
        StringBuilder sb = new StringBuilder("Created components:\n");

        sb.append("label1 = ");
        Label label1 = (Label) getFragment().getComponent("label1");
        sb.append(label1 == null ? null : (String) label1.getValue());

        sb.append(", label2 = ");
        Label label2 = (Label) getFragment().getComponent("label2");
        sb.append(label2 == null ? null : (String) label2.getValue());

        info.setValue(sb.toString());
    }
}
