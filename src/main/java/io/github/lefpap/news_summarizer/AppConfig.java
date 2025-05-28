package io.github.lefpap.news_summarizer;

import io.github.lefpap.news_summarizer.auth.AuthSettings;
import io.github.lefpap.news_summarizer.news_api.NewsApiClientSettings;
import io.github.lefpap.news_summarizer.summarizer.NewsSummarizerSettings;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Configuration class for enabling application-specific settings. It enables configuration properties and scheduling.
 */
@Configuration
@EnableConfigurationProperties({NewsApiClientSettings.class, NewsSummarizerSettings.class, AuthSettings.class})
@EnableScheduling
public class AppConfig {
}
