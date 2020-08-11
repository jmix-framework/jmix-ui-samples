package io.jmix.sampler.screen.ui.components.combobox.filtermode;

import io.jmix.ui.component.ComboBox;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

@UiController("combobox-filter-mode")
@UiDescriptor("combobox-filter-mode.xml")
public class ComboBoxFilterModeSample extends ScreenFragment {

    @Autowired
    protected ComboBox<String> containsFilterComboBox;
    @Autowired
    protected ComboBox<String> noFilterComboBox;
    @Autowired
    protected ComboBox<String> startsWithFilterComboBox;

    @Subscribe
    protected void onInit(InitEvent event) {
        List<String> list = Arrays.asList(
                "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
        );

        noFilterComboBox.setOptionsList(list);
        startsWithFilterComboBox.setOptionsList(list);
        containsFilterComboBox.setOptionsList(list);
    }
}
