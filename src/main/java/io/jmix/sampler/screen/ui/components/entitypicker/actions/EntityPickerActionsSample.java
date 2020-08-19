package io.jmix.sampler.screen.ui.components.entitypicker.actions;

import io.jmix.core.Metadata;
import io.jmix.sampler.entity.Customer;
import io.jmix.sampler.entity.Order;
import io.jmix.ui.Actions;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.Action;
import io.jmix.ui.action.BaseAction;
import io.jmix.ui.action.entitypicker.EntityClearAction;
import io.jmix.ui.component.EntityPicker;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("entitypicker-actions")
@UiDescriptor("entitypicker-actions.xml")
public class EntityPickerActionsSample extends ScreenFragment {

    @Autowired
    protected EntityPicker<Customer> entityPicker;
    @Autowired
    protected InstanceContainer<Order> orderDc;

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
        Order order = metadata.create(Order.class);
        orderDc.setItem(order);

        entityPicker.addAction(new BaseAction("showGrade")
                .withHandler(actionPerformedEvent -> {
                    Customer customer = entityPicker.getValue();
                    if (customer != null) {
                        notifications.create()
                                .withCaption(customer.getName() + "'s grade is " + customer.getGrade())
                                .show();
                    } else {
                        notifications.create()
                                .withCaption("Choose a customer")
                                .show();
                    }
                })
                .withIcon("font-icon:USERS")
        );

        Action clearAction = actions.create(EntityClearAction.ID);
        clearAction.setIcon("font-icon:BAN");
        entityPicker.addAction(clearAction);
    }

    @Subscribe("entityPicker.greeting")
    protected void onEntityPickerGreetingActionPerformed(Action.ActionPerformedEvent event) {
        Customer customer = entityPicker.getValue();
        if (customer != null) {
            notifications.create()
                    .withCaption("Hello, " + customer.getName())
                    .show();
        } else {
            notifications.create()
                    .withCaption("Choose a customer")
                    .show();
        }
    }
}
