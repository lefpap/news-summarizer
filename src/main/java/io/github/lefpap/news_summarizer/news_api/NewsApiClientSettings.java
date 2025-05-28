package io.github.lefpap.news_summarizer.news_api;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * Configuration properties for the News API client.
 * Includes settings such as the base URL and API key.
 */
@Validated
@Data
@ConfigurationProperties(prefix = NewsApiClientSettings.CONFIG_PREFIX)
public class NewsApiClientSettings {

    /**
     * Prefix for News API client configuration properties.
     */
    public static final String CONFIG_PREFIX = "app.news-api";

    /**
     * The base URL of the News API.
     */
    private String baseUrl;

    /**
     * The API key for authenticating with the News API.
     */
    private String apiKey;
}

