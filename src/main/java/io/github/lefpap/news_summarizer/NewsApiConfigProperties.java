package io.github.lefpap.news_summarizer;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static io.github.lefpap.news_summarizer.NewsApiConfigProperties.CONFIG_PREFIX;

@Getter
@Setter
@ConfigurationProperties(prefix = CONFIG_PREFIX)
public class NewsApiConfigProperties {

    public static final String CONFIG_PREFIX = "news-api";

    private String baseUrl;
    private String apiKey;
}

