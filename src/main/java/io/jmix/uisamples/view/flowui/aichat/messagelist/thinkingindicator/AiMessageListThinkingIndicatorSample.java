package io.jmix.uisamples.view.flowui.aichat.messagelist.thinkingindicator;

import com.vaadin.flow.component.ClickEvent;
import io.jmix.aichat.component.messagelist.AiMessageList;
import io.jmix.aichat.component.messagelist.AiMessageListItem;
import io.jmix.aichat.component.messagelist.GenerationState;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.MessageBundle;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewComponent;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@ViewController("ai-message-list-thinking-indicator")
@ViewDescriptor("ai-message-list-thinking-indicator.xml")
public class AiMessageListThinkingIndicatorSample extends StandardView {

    @ViewComponent
    private AiMessageList messageList;
    @ViewComponent
    private MessageBundle messageBundle;

    @Subscribe("toggleButton")
    public void onToggleButtonClick(final ClickEvent<JmixButton> event) {
        if (messageList.getState() == GenerationState.GENERATING) {
            messageList.setState(GenerationState.IDLE);
            return;
        }

        // The indicator is attached to the message being generated, so the list needs a
        // trailing assistant message for it to appear on
        messageList.setItems(
                AiMessageListItem.user(messageBundle.getMessage("question.text")),
                AiMessageListItem.assistant(""));
        messageList.setState(GenerationState.GENERATING);
    }
}
