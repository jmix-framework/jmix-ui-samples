package io.jmix.uisamples.view.flowui.customcomponents.nativeselect.simple;

import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import io.jmix.uisamples.component.nativeselect.NativeSelect;
import org.springframework.beans.factory.annotation.Autowired;

@ViewController("native-select-simple")
@ViewDescriptor("native-select-simple.xml")
public class NativeSelectSimpleSample extends StandardView {

    @Autowired
    private Notifications notifications;

    @Subscribe("dinosaurSelect")
    public void onDinosaurSelectComponentValueChange(
            final ComponentValueChangeEvent<NativeSelect<String>, String> event) {

        if (event.getValue() != null) {
            notifications.show("Selected value: " + event.getValue());
        }
    }
}
