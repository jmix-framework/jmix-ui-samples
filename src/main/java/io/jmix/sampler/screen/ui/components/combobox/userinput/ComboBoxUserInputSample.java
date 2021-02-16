package io.jmix.sampler.screen.ui.components.combobox.userinput;

import com.google.common.collect.Lists;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.ComboBox;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@UiController("combobox-user-input")
@UiDescriptor("combobox-user-input.xml")
public class ComboBoxUserInputSample extends ScreenFragment {

    @Autowired
    protected ComboBox<String> comboBox;
    @Autowired
    protected Notifications notifications;

    @Subscribe
    protected void onInit(InitEvent event) {
        List<String> options = Lists.newArrayList("One", "Two", "Three");
        comboBox.setOptionsList(options);

        comboBox.setEnterPressHandler(enterPressEvent -> {
            String text = enterPressEvent.getText();
            options.add(text);
            comboBox.setOptionsList(options);

            notifications.create()
                    .withCaption(text + " added")
                    .show();
        });
    }
}
