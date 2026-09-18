package io.jmix.uisamples;

import io.jmix.uisamples.bean.PlaceholderChatModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Wires the chat client the AI Chat samples answer from. No language model is contacted:
 * the samples run on the local {@link PlaceholderChatModel}, which streams prepared answers.
 */
@Configuration
public class UiSamplesAiConfiguration {

    @Bean
    public ChatModel placeholderChatModel() {
        return new PlaceholderChatModel();
    }

    @Bean
    public ChatClient placeholderChatClient(ChatModel chatModel) {
        return ChatClient.create(chatModel);
    }
}
