package io.jmix.sampler.screen.ui.components.valuepicker.actions;

import io.jmix.core.Metadata;
import io.jmix.sampler.entity.Customer;
import io.jmix.ui.Actions;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.Action;
import io.jmix.ui.action.BaseAction;
import io.jmix.ui.action.valuepicker.ValueClearAction;
import io.jmix.ui.component.ValuePicker;
import io.jmix.ui.icon.JmixIcon;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("valuepicker-actions")
@UiDescriptor("valuepicker-actions.xml")
public class ValuePickerActionsSample extends ScreenFragment {

    @Autowired
    protected ValuePicker<String> valuePicker;
    @Autowired
    protected InstanceContainer<Customer> customerDc;

    @Autowired
    protected Metadata metadata;
    @Autowired
    protected Notifications notifications;
    @Autowired
    protected Actions actions;

    @Subscribe
    protected void onInit(InitEvent event) {
        // InstanceContainer initialization. It is usually done automatically if the screen is
        // inherited from StandardEditor and is used as an entity editor.
        Customer customer = metadata.create(Customer.class);
        customerDc.setItem(customer);

        valuePicker.addAction(new BaseAction("showValue")
                .withHandler(actionPerformedEvent -> {
                    String value = valuePicker.getValue();

                    notifications.create()
                            .withCaption(value != null ? value : "No value")
                            .show();
                })
                .withDescription("Show Value")
                .withIcon(JmixIcon.EYE.source()));

        valuePicker.addAction(actions.create(ValueClearAction.ID));
    }

    @Subscribe("valuePicker.generate")
    protected void onValuePickerGenerate(Action.ActionPerformedEvent event) {
        customerDc.getItem().setName(RandomStringUtils.randomAlphabetic(5, 10));
    }


}
