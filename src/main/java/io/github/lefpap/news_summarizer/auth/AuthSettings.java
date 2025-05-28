package io.github.lefpap.news_summarizer.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Configuration properties for authentication settings.
 * Includes API keys and a flag to enable or disable authentication.
 */
@Data
@ConfigurationProperties("app.auth")
public class AuthSettings {

    /**
     * Flag to enable or disable authentication.
     */
    boolean enabled = true;

    /**
     * List of API keys for authentication.
     */
    List<@NotNull ApiKey> apiKeys = new ArrayList<>();
}
