package io.jmix.sampler.screen.ui.containers.drawer;

import io.jmix.ui.component.Button;
import io.jmix.ui.component.mainwindow.Drawer;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("drawer-sample")
@UiDescriptor("drawer-sample.xml")
public class DrawerSample extends ScreenFragment {

    @Autowired
    private Drawer drawer;

    @Subscribe("toggleDrawerButton")
    private void onToggleDrawer(Button.ClickEvent event) {
        drawer.toggle();
    }
}