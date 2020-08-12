package io.jmix.sampler.screen.ui.components.singleselectlist.customoptions;

import io.jmix.sampler.entity.CustomerGrade;
import io.jmix.ui.component.SingleSelectList;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@UiController("singleselectlist-custom-options")
@UiDescriptor("singleselectlist-custom-options.xml")
public class SingleSelectListCustomOptionsSample extends ScreenFragment {
    @Autowired
    protected SingleSelectList<Integer> singleSelectListWithList;
    @Autowired
    protected SingleSelectList<Integer> singleSelectListWithMap;
    @Autowired
    protected SingleSelectList<CustomerGrade> singleSelectListWithEnum;

    @Subscribe
    protected void onInit(InitEvent event) {
        List<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(4);
        list.add(5);
        list.add(7);
        singleSelectListWithList.setOptionsList(list);

        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("two", 2);
        map.put("four", 4);
        map.put("five", 5);
        map.put("seven", 7);
        singleSelectListWithMap.setOptionsMap(map);

        singleSelectListWithEnum.setOptionsEnum(CustomerGrade.class);
    }
}
