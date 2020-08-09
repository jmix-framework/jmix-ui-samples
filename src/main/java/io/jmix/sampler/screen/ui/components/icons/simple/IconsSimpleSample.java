package io.jmix.sampler.screen.ui.components.icons.simple;

import io.jmix.ui.UiComponents;
import io.jmix.ui.component.GroupBoxLayout;
import io.jmix.ui.component.Label;
import io.jmix.ui.icon.JmixIcon;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("icons-simple")
@UiDescriptor("icons-simple.xml")
public class IconsSimpleSample extends ScreenFragment {

    @Autowired
    protected GroupBoxLayout groupBoxProgrammatic;

    @Autowired
    protected UiComponents uiComponents;

    @Subscribe
    protected void onInit(InitEvent event) {
        Label<String> dollarLabel = uiComponents.create(Label.TYPE_STRING);
        dollarLabel.setIcon("font-icon:DOLLAR");
        dollarLabel.setValue("DOLLAR");
        groupBoxProgrammatic.add(dollarLabel);

        Label<String> yenLabel = uiComponents.create(Label.TYPE_STRING);
        yenLabel.setIconFromSet(JmixIcon.YEN);
        yenLabel.setValue("YEN");
        groupBoxProgrammatic.add(yenLabel);
    }
}
