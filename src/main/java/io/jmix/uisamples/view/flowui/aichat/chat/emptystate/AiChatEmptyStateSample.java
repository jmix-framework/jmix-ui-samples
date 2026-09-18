package io.jmix.uisamples.view.flowui.aichat.chat.emptystate;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ai.provider.LLMProvider;
import io.jmix.aichat.component.chat.AiChat;
import io.jmix.aichat.data.SimpleAiChatMessage;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.Install;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewComponent;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;

@ViewController("ai-chat-empty-state")
@ViewDescriptor("ai-chat-empty-state.xml")
public class AiChatEmptyStateSample extends StandardView {

    // The words the placeholder model keys on. They are model input rather than interface
    // text, so they are not localized: the button label is, the prompt it sends is not.
    private static final String CODE_PROMPT = "code";
    private static final String LONG_TEXT_PROMPT = "long text";
    private static final String ERROR_PROMPT = "error";

    @ViewComponent
    private AiChat<SimpleAiChatMessage> chat;

    @Autowired
    private ChatClient chatClient;

    @Install(to = "chat", subject = "llmProvider")
    private Flux<String> llmProvider(final LLMProvider.LLMRequest request) {
        return chatClient.prompt()
                .user(request.userMessage())
                .stream()
                .content();
    }

    @Subscribe("codeButton")
    public void onCodeButtonClick(final ClickEvent<JmixButton> event) {
        chat.prompt(CODE_PROMPT);
    }

    @Subscribe("longTextButton")
    public void onLongTextButtonClick(final ClickEvent<JmixButton> event) {
        chat.prompt(LONG_TEXT_PROMPT);
    }

    @Subscribe("errorButton")
    public void onErrorButtonClick(final ClickEvent<JmixButton> event) {
        chat.prompt(ERROR_PROMPT);
    }
}
