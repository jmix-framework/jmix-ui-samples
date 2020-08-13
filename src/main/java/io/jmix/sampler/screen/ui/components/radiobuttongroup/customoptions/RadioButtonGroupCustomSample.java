package io.jmix.sampler.screen.ui.components.radiobuttongroup.customoptions;

import io.jmix.sampler.entity.CustomerGrade;
import io.jmix.ui.component.RadioButtonGroup;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@UiController("radiobuttongroup-custom-options")
@UiDescriptor("radiobuttongroup-custom-options.xml")
public class RadioButtonGroupCustomSample extends ScreenFragment {

    @Autowired
    protected RadioButtonGroup<Integer> radioButtonGroupWithList;
    @Autowired
    protected RadioButtonGroup<Integer> radioButtonGroupWithMap;
    @Autowired
    protected RadioButtonGroup<CustomerGrade> radioButtonGroupWithEnum;

    @Subscribe
    protected void onInit(InitEvent event) {
        List<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(4);
        list.add(5);
        list.add(7);
        radioButtonGroupWithList.setOptionsList(list);

        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("two", 2);
        map.put("four", 4);
        map.put("five", 5);
        map.put("seven", 7);
        radioButtonGroupWithMap.setOptionsMap(map);

        radioButtonGroupWithEnum.setOptionsEnum(CustomerGrade.class);
    }
}
