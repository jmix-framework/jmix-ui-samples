package io.jmix.sampler.screen.ui.components.popupview.opening;

import io.jmix.ui.component.Button;
import io.jmix.ui.component.PopupView;
import io.jmix.ui.screen.LoadDataBeforeShow;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("popupview-opening")
@UiDescriptor("popupview-opening.xml")
@LoadDataBeforeShow
public class PopupViewOpeningSample extends ScreenFragment {

    @Autowired
    protected PopupView popupView;

    @Subscribe("button")
    protected void onButtonClick(Button.ClickEvent event) {
        popupView.setPopupVisible(true);
    }
}
