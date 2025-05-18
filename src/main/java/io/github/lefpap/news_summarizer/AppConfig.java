package io.github.lefpap.news_summarizer;

import io.github.lefpap.news_summarizer.news_api.NewsApiClient;
import io.github.lefpap.news_summarizer.news_api.NewsApiClientHttpInterceptor;
import io.github.lefpap.news_summarizer.news_api.NewsApiConfigProperties;
import io.github.lefpap.news_summarizer.summarizer.NewsSummarizerConfigProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
@EnableConfigurationProperties({NewsApiConfigProperties.class, NewsSummarizerConfigProperties.class})
@RequiredArgsConstructor
public class AppConfig {

    private final NewsApiConfigProperties newsApiConfig;
    private final NewsSummarizerConfigProperties newsSummarizerConfig;

    private final NewsApiClientHttpInterceptor newsApiClientHttpInterceptor;

    @Bean
    public NewsApiClient newsApiClient() {

        // Build a RestClient with the base URL for NewsAPI
        RestClient restClient = RestClient.builder()
            .baseUrl(newsApiConfig.getBaseUrl())
            .defaultHeader(NewsApiConfigProperties.API_KEY_HEADER, newsApiConfig.getApiKey())
            .requestInterceptor(newsApiClientHttpInterceptor)
            .build();

        // Create a proxy factory using the RestClient (synchronous client)
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient))
            .build();

        // Generate a proxy implementation of the NewsApiClient interface
        return factory.createClient(NewsApiClient.class);
    }

    @Bean
    public ChatClient ollamaChatClient(OllamaChatModel chatModel) {
        return ChatClient.create(chatModel).mutate()
            .defaultSystem(newsSummarizerConfig.getInstructions())
            .build();
    }
}
