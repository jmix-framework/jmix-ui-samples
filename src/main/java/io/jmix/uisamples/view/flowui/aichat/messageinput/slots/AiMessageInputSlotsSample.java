package io.jmix.uisamples.view.flowui.aichat.messageinput.slots;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.notification.Notification;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.MessageBundle;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewComponent;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@ViewController("ai-message-input-slots")
@ViewDescriptor("ai-message-input-slots.xml")
public class AiMessageInputSlotsSample extends StandardView {

    @ViewComponent
    private MessageBundle messageBundle;

    @Autowired
    private Notifications notifications;

    @Subscribe("promptLibraryButton")
    public void onPromptLibraryButtonClick(final ClickEvent<JmixButton> event) {
        notifications.create(messageBundle.getMessage("promptLibraryButton.notification"))
                .withPosition(Notification.Position.BOTTOM_END)
                .show();
    }

    @Subscribe("dictateButton")
    public void onDictateButtonClick(final ClickEvent<JmixButton> event) {
        notifications.create(messageBundle.getMessage("dictateButton.notification"))
                .withPosition(Notification.Position.BOTTOM_END)
                .show();
    }
}
