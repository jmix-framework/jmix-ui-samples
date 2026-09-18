package io.jmix.uisamples.view.flowui.aichat.chat.themevariant;

import com.vaadin.flow.component.ai.provider.LLMProvider;
import io.jmix.aichat.component.chat.AiChat;
import io.jmix.aichat.component.chat.AiChatVariant;
import io.jmix.aichat.data.SimpleAiChatMessage;
import io.jmix.flowui.component.multiselectcombobox.JmixMultiSelectComboBox;
import io.jmix.flowui.view.Install;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewComponent;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;

import java.util.Set;

@ViewController("ai-chat-theme-variant")
@ViewDescriptor("ai-chat-theme-variant.xml")
public class AiChatThemeVariantSample extends StandardView {

    @ViewComponent
    private AiChat<SimpleAiChatMessage> chat;
    @ViewComponent
    private JmixMultiSelectComboBox<AiChatVariant> variantsBox;

    @Autowired
    private ChatClient chatClient;

    @Install(to = "chat", subject = "llmProvider")
    private Flux<String> llmProvider(final LLMProvider.LLMRequest request) {
        return chatClient.prompt()
                .user(request.userMessage())
                .stream()
                .content();
    }

    @Subscribe
    public void onInit(final InitEvent event) {
        variantsBox.setItems(AiChatVariant.values());
        variantsBox.setItemLabelGenerator(AiChatVariant::getVariantName);
        variantsBox.addValueChangeListener(valueChange -> applyVariants());
    }

    /**
     * Applies the whole selection, adding what is ticked and removing what is not, so that
     * unticking a variant takes it off the chat again.
     */
    private void applyVariants() {
        Set<AiChatVariant> selected = variantsBox.getValue();
        for (AiChatVariant variant : AiChatVariant.values()) {
            if (selected != null && selected.contains(variant)) {
                chat.addThemeVariants(variant);
            } else {
                chat.removeThemeVariants(variant);
            }
        }
    }
}
