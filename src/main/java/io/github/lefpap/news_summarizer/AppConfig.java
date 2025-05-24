package io.github.lefpap.news_summarizer;

import io.github.lefpap.news_summarizer.news_api.NewsApiClientSettings;
import io.github.lefpap.news_summarizer.summarizer.NewsSummarizerSettings;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({NewsApiClientSettings.class, NewsSummarizerSettings.class})
public class AppConfig {
}
