package io.github.lefpap.news_summarizer.summarizer;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class NewsSummarizerConfig {

    private final NewsSummarizerSettings newsSummarizerSettings;

    @Bean
    public ChatClient ollamaChatClient(OllamaChatModel chatModel) {
        return ChatClient.create(chatModel).mutate()
            .defaultSystem(newsSummarizerSettings.getInstructions())
            .build();
    }
}
