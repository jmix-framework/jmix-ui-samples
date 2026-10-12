package io.jmix.uisamples.view.flowui.aichat.messageinput.simple;

import com.vaadin.flow.component.html.Span;
import io.jmix.aichat.component.messageinput.AiMessageInput;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewComponent;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@ViewController("ai-message-input-simple")
@ViewDescriptor("ai-message-input-simple.xml")
public class AiMessageInputSimpleSample extends StandardView {

    @ViewComponent
    private Span lastMessageSpan;

    @Subscribe("messageInput")
    public void onMessageInputSubmit(final AiMessageInput.SubmitEvent event) {
        lastMessageSpan.setText(event.getValue());
    }
}
