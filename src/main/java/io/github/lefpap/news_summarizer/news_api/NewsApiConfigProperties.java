package io.github.lefpap.news_summarizer.news_api;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static io.github.lefpap.news_summarizer.news_api.NewsApiConfigProperties.CONFIG_PREFIX;

@Getter
@Setter
@ConfigurationProperties(prefix = CONFIG_PREFIX)
public class NewsApiConfigProperties {

    public static final String CONFIG_PREFIX = "news-api";
    public static final String API_KEY_HEADER = "X-Api-Key";

    private String baseUrl;
    private String apiKey;
}

