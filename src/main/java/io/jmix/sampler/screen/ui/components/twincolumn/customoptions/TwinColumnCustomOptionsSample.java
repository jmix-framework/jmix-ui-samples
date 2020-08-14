package io.jmix.sampler.screen.ui.components.twincolumn.customoptions;

import io.jmix.sampler.entity.CustomerGrade;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.TwinColumn;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@UiController("twincolumn-custom-options")
@UiDescriptor("twincolumn-custom-options.xml")
public class TwinColumnCustomOptionsSample extends ScreenFragment {

    @Autowired
    protected TwinColumn<Integer> twinColumnWithOptionsList;
    @Autowired
    protected TwinColumn<Integer> twinColumnWithOptionsMap;
    @Autowired
    protected TwinColumn<CustomerGrade> twinColumnWithOptionsEnum;
    @Autowired
    protected Notifications notifications;

    @Subscribe
    protected void onInit(InitEvent event) {
        List<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(4);
        list.add(5);
        list.add(7);
        twinColumnWithOptionsList.setOptionsList(list);

        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("two", 2);
        map.put("four", 4);
        map.put("five", 5);
        map.put("seven", 7);
        twinColumnWithOptionsMap.setOptionsMap(map);

        twinColumnWithOptionsEnum.setOptionsEnum(CustomerGrade.class);
    }

    @Subscribe("showOptionsListValue")
    protected void onShowOptionsListValueClick(Button.ClickEvent event) {
        showValue(twinColumnWithOptionsList.getValue());
    }

    @Subscribe("showOptionsMapValue")
    protected void onShowOptionsMapValueClick(Button.ClickEvent event) {
        showValue(twinColumnWithOptionsMap.getValue());
    }

    @Subscribe("showOptionsEnumValue")
    protected void onShowOptionsEnumValueClick(Button.ClickEvent event) {
        showValue(twinColumnWithOptionsEnum.getValue());
    }

    protected void showValue(Object value) {
        notifications.create()
                .withCaption(String.valueOf(value))
                .show();
    }
}
