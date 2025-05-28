package io.github.lefpap.news_summarizer.summarizer;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = NewsSummarizerSettings.CONFIG_PREFIX)
public class NewsSummarizerSettings {
    public static final String CONFIG_PREFIX = "app.summarizer";

    private String instructions;
}
