package io.jmix.sampler.screen.ui.components.javascript;

import io.jmix.sampler.screen.ui.components.javascript.component.TimePickerState;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.JavaScriptComponent;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("java-script-component")
@UiDescriptor("java-script-component.xml")
public class JavaScriptComponentSample extends ScreenFragment {
    @Autowired
    protected JavaScriptComponent timePicker;
    @Autowired
    protected Notifications notifications;

    @Subscribe
    protected void onInit(InitEvent event) {
        TimePickerState state = new TimePickerState();
        state.now = "12:35:57";
        state.showSeconds = true;
        state.twentyFour = true;

        timePicker.addFunction("onBeforeShow", callbackEvent ->
                notifications.create()
                        .withCaption("Before Show Event")
                        .withPosition(Notifications.Position.MIDDLE_RIGHT)
                        .show());

        timePicker.addFunction("onShow", callbackEvent ->
                notifications.create()
                        .withCaption("Show Event")
                        .show());

        timePicker.setState(state);
    }

    @Subscribe("showValueBtn")
    protected void onShowValueBtnClick(Button.ClickEvent event) {
        timePicker.callFunction("showValue");
    }
}
