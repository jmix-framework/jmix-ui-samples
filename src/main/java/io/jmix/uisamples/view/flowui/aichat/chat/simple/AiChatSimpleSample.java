package io.jmix.uisamples.view.flowui.aichat.chat.simple;

import com.vaadin.flow.component.ai.provider.LLMProvider;
import io.jmix.flowui.view.Install;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;

@ViewController("ai-chat-simple")
@ViewDescriptor("ai-chat-simple.xml")
public class AiChatSimpleSample extends StandardView {

    @Autowired
    private ChatClient chatClient;

    @Install(to = "chat", subject = "llmProvider")
    private Flux<String> llmProvider(final LLMProvider.LLMRequest request) {
        return chatClient.prompt()
                .user(request.userMessage())
                .stream()
                .content();
    }
}
