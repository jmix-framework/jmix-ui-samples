package io.jmix.sampler.screen.ui.components.combobox.userinput;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.ComboBox;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
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
        final List<String> list = new ArrayList<>(Arrays.asList("One", "Two", "Three"));
        comboBox.setOptionsList(list);

        comboBox.setNewOptionHandler(caption -> {
            list.add(caption);
            comboBox.setOptionsList(list);

            notifications.create()
                    .withCaption(caption + " added")
                    .show();
        });
    }
}
