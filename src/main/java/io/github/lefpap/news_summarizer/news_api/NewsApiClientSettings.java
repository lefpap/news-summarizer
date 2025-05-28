package io.github.lefpap.news_summarizer.news_api;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@Data
@ConfigurationProperties(prefix = NewsApiClientSettings.CONFIG_PREFIX)
public class NewsApiClientSettings {

    public static final String CONFIG_PREFIX = "app.news-api";

    private String baseUrl;
    private String apiKey;
}

