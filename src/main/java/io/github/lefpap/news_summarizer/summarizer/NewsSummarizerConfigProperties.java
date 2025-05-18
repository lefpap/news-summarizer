package io.github.lefpap.news_summarizer.summarizer;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = NewsSummarizerConfigProperties.CONFIG_PREFIX)
public class NewsSummarizerConfigProperties {
    public static final String CONFIG_PREFIX = "summarizer";

    private String instructions;
}
