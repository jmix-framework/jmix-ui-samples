package io.jmix.sampler.screen.ui.components.popupbutton.customlayout;

import io.jmix.sampler.entity.SendingStatus;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.ComboBox;
import io.jmix.ui.component.PopupButton;
import io.jmix.ui.component.TextField;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("popupbutton-custom-layout")
@UiDescriptor("popupbutton-custom-layout.xml")
public class PopupButtonCustomLayoutSample extends ScreenFragment {

    @Autowired
    protected PopupButton popupButton;

    @Autowired
    protected TextField<String> textField;

    @Autowired
    protected ComboBox<SendingStatus> comboBox;

    @Autowired
    protected Notifications notifications;

    @Subscribe("saveAndCloseButton")
    protected void onSaveAndCloseButtonClick(Button.ClickEvent event) {
        popupButton.setPopupVisible(false);

        notifications.create()
                .withCaption("Settings saved")
                .show();
    }

    @Subscribe("cancelAndCloseButton")
    protected void onCancelAndCloseButtonClick(Button.ClickEvent event) {
        popupButton.setPopupVisible(false);

        comboBox.setValue(null);
        textField.setValue(null);

        notifications.create()
                .withCaption("Cancelled")
                .show();
    }
}