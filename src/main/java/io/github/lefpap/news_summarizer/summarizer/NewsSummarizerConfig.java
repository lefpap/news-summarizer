package io.github.lefpap.news_summarizer.summarizer;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up the News Summarizer components.
 * Defines beans required for summarization tasks.
 */
@Configuration
@RequiredArgsConstructor
public class NewsSummarizerConfig {

    private final NewsSummarizerSettings newsSummarizerSettings;

    /**
     * Creates a ChatClient bean configured with the default system instructions.
     *
     * @param chatModel the chat model to use for AI interactions
     * @return a configured ChatClient instance
     */
    @Bean
    public ChatClient ollamaChatClient(OllamaChatModel chatModel) {
        return ChatClient.create(chatModel).mutate()
            .defaultSystem(newsSummarizerSettings.getInstructions())
            .build();
    }
}
