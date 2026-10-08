package io.jmix.uisamples.view.flowui.customcomponents.nativeselect.customitems;

import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.view.*;
import io.jmix.uisamples.component.nativeselect.NativeSelect;
import io.jmix.uisamples.entity.City;
import org.springframework.beans.factory.annotation.Autowired;

@ViewController("native-select-custom-items")
@ViewDescriptor("native-select-custom-items.xml")
public class NativeSelectCustomItemsSample extends StandardView {

    @ViewComponent
    private NativeSelect<City> citySelect;

    @Autowired
    private Notifications notifications;

    @Subscribe
    public void onInit(final InitEvent event) {
        citySelect.setItemLabelGenerator(City::getName);
        citySelect.setGroupLabelGenerator(city -> city.getCountry().getName());
    }

    @Subscribe("citySelect")
    public void onCitySelectComponentValueChange(final ComponentValueChangeEvent<NativeSelect<City>, City> event) {
        City city = event.getValue();
        if (city != null) {
            notifications.show("Selected city: %s, %s".formatted(city.getName(), city.getCountry().getName()));
        }
    }
}
