package io.github.lefpap.news_summarizer.news_api;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;

@Configuration
@RequiredArgsConstructor
public class NewsApiClientConfig {

    private final RestClientLoggingInterceptor loggingInterceptor;

    @Bean
    public RestClientCustomizer interceptorCustomizer() {
        return builder -> builder.requestInterceptor(loggingInterceptor);
    }

    @Bean
    public RestClientCustomizer factoryCustomizer(ClientHttpRequestFactory factory) {
        return builder -> builder.requestFactory(factory);
    }

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory() {
        ClientHttpRequestFactory factory = ClientHttpRequestFactoryBuilder.detect()
            .build();

        return new BufferingClientHttpRequestFactory(factory);
    }

}
