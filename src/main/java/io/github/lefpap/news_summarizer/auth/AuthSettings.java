package io.github.lefpap.news_summarizer.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Data
@ConfigurationProperties("app.auth")
public class AuthSettings {
    boolean enabled = true;
    List<@NotNull ApiKey> apiKeys = new ArrayList<>();
}
