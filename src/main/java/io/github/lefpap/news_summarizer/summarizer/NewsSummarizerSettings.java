package io.github.lefpap.news_summarizer.summarizer;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for the News Summarizer.
 * Includes settings such as summarization instructions.
 */
@Getter
@Setter
@ConfigurationProperties(prefix = NewsSummarizerSettings.CONFIG_PREFIX)
public class NewsSummarizerSettings {

    /**
     * Prefix for News Summarizer configuration properties.
     */
    public static final String CONFIG_PREFIX = "app.summarizer";

    /**
     * Instructions for the summarization process.
     */
    private String instructions;
}
