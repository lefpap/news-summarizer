package io.github.lefpap.news_summarizer.news_api;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;

/**
 * Configuration class for customizing the News API client.
 * Sets up interceptors and request factories for the RestClient.
 */
@Configuration
@RequiredArgsConstructor
public class NewsApiClientConfig {

    private final RestClientLoggingInterceptor loggingInterceptor;

    /**
     * Configures a RestClientCustomizer to add the logging interceptor.
     *
     * @return a RestClientCustomizer with the logging interceptor
     */
    @Bean
    public RestClientCustomizer interceptorCustomizer() {
        return builder -> builder.requestInterceptor(loggingInterceptor);
    }

    /**
     * Configures a RestClientCustomizer to use a custom request factory.
     *
     * @param factory the custom ClientHttpRequestFactory
     * @return a RestClientCustomizer with the custom request factory
     */
    @Bean
    public RestClientCustomizer factoryCustomizer(ClientHttpRequestFactory factory) {
        return builder -> builder.requestFactory(factory);
    }

    /**
     * Creates a buffering ClientHttpRequestFactory for the RestClient.
     *
     * @return a buffering ClientHttpRequestFactory
     */
    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory() {
        ClientHttpRequestFactory factory = ClientHttpRequestFactoryBuilder.detect()
            .build();

        return new BufferingClientHttpRequestFactory(factory);
    }
}
