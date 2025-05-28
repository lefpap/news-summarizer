package io.github.lefpap.news_summarizer.auth;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Value
@EqualsAndHashCode(callSuper = false)
public class ApiKeyAuthenticationToken extends AbstractAuthenticationToken {

    ApiKey apiKey;

    private ApiKeyAuthenticationToken(ApiKey apiKey) {
        super(null);
        this.apiKey = apiKey;
        setAuthenticated(false);
    }

    private ApiKeyAuthenticationToken(ApiKey apiKey, GrantedAuthority authority) {
        super(List.of(authority));
        this.apiKey = apiKey;
        setAuthenticated(true);
    }

    public static ApiKeyAuthenticationToken unauthenticated(ApiKey apiKey) {
        return new ApiKeyAuthenticationToken(apiKey);
    }

    public static ApiKeyAuthenticationToken authenticated(ApiKey apiKey) {
        return new ApiKeyAuthenticationToken(apiKey, new SimpleGrantedAuthority("ROLE_" + apiKey.role().name()));
    }

    @Override
    public Object getCredentials() {
        return apiKey.secret();
    }

    @Override
    public Object getPrincipal() {
        return apiKey.id();
    }
}
