package io.github.lefpap.news_summarizer;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.model.ollama.autoconfigure.OllamaChatProperties;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
@EnableConfigurationProperties({OllamaChatProperties.class, NewsApiConfigProperties.class})
@RequiredArgsConstructor
public class AppConfig {

    private final NewsApiConfigProperties newsApiConfig;

    @Bean
    public NewsApiClient newsApiClient() {
        // Build a RestClient with the base URL for NewsAPI
        RestClient restClient = RestClient.builder()
            .baseUrl(newsApiConfig.getBaseUrl())
            .defaultHeader("X-Api-Key", newsApiConfig.getApiKey())
            .build();

        // Create a proxy factory using the RestClient (synchronous client)
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient))
            .build();

        // Generate a proxy implementation of the NewsApiClient interface
        return factory.createClient(NewsApiClient.class);
    }

    @Bean
    public ChatClient ollamaChatClient(OllamaChatModel chatModel) {
        return ChatClient.create(chatModel);
    }
}
