package io.jmix.sampler.screen.ui.components.checkboxgroup.customoptions;

import io.jmix.sampler.entity.CustomerGrade;
import io.jmix.ui.component.CheckBoxGroup;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@UiController("checkboxgroup-custom-options")
@UiDescriptor("checkboxgroup-custom-options.xml")
public class CheckBoxGroupCustomOptionsSample extends ScreenFragment {

    @Autowired
    protected CheckBoxGroup<Integer> checkBoxGroupWithList;
    @Autowired
    protected CheckBoxGroup<Integer> checkBoxGroupWithMap;
    @Autowired
    protected CheckBoxGroup<CustomerGrade> checkBoxGroupWithEnum;

    @Subscribe
    protected void onInit(InitEvent event) {
        List<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(4);
        list.add(5);
        list.add(7);
        checkBoxGroupWithList.setOptionsList(list);

        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("two", 2);
        map.put("four", 4);
        map.put("five", 5);
        map.put("seven", 7);
        checkBoxGroupWithMap.setOptionsMap(map);

        checkBoxGroupWithEnum.setOptionsEnum(CustomerGrade.class);
    }
}