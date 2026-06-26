package io.jmix.uisamples.view.flowui.components.contexthelp.simple;

import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import io.jmix.flowui.Dialogs;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.theme.StyleUtility;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@ViewController("context-help-simple")
@ViewDescriptor("context-help-simple.xml")
public class ContextHelpSimpleSample extends StandardView {

    @ViewComponent
    protected TypedTextField<String> notificationField;
    @ViewComponent
    protected TypedTextField<String> dialogField;

    @Autowired
    protected UiComponents uiComponents;
    @Autowired
    protected Notifications notifications;
    @Autowired
    protected Dialogs dialogs;

    @Subscribe
    protected void onInit(InitEvent event) {
        initNotificationField();
        initDialogField();
    }

    protected void initNotificationField() {
        JmixButton helperButton = createHelperButton();
        helperButton.addClickListener(event ->
                notifications.create("This is a helper notification")
                        .withCloseable(true)
                        .show());

        notificationField.setSuffixComponent(helperButton);
    }

    protected void initDialogField() {
        JmixButton helperButton = createHelperButton();
        helperButton.addClickListener(event ->
                dialogs.createMessageDialog()
                        .withText("This is a helper dialog")
                        .open());

        dialogField.setSuffixComponent(helperButton);
    }

    protected JmixButton createHelperButton() {
        JmixButton helperButton = uiComponents.create(JmixButton.class);
        helperButton.setIcon(VaadinIcon.QUESTION_CIRCLE.create());
        helperButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        helperButton.addClassName(StyleUtility.Button.LINK_BUTTON);

        return helperButton;
    }
}
