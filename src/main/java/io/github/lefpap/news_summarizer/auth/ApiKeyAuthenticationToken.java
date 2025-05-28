package io.github.lefpap.news_summarizer.auth;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

/**
 * Represents an authentication token for API key-based authentication.
 * Contains the API key and its associated authorities.
 */
@Value
@EqualsAndHashCode(callSuper = false)
public class ApiKeyAuthenticationToken extends AbstractAuthenticationToken {

    /**
     * The API key associated with this token.
     */
    ApiKey apiKey;

    /**
     * Constructs an unauthenticated token with the given API key.
     *
     * @param apiKey the API key
     */
    private ApiKeyAuthenticationToken(ApiKey apiKey) {
        super(null);
        this.apiKey = apiKey;
        setAuthenticated(false);
    }

    /**
     * Constructs an authenticated token with the given API key and authority.
     *
     * @param apiKey    the API key
     * @param authority the granted authority
     */
    private ApiKeyAuthenticationToken(ApiKey apiKey, GrantedAuthority authority) {
        super(List.of(authority));
        this.apiKey = apiKey;
        setAuthenticated(true);
    }

    /**
     * Creates an unauthenticated token with the given API key.
     *
     * @param apiKey the API key
     * @return an unauthenticated token
     */
    public static ApiKeyAuthenticationToken unauthenticated(ApiKey apiKey) {
        return new ApiKeyAuthenticationToken(apiKey);
    }

    /**
     * Creates an authenticated {@link ApiKeyAuthenticationToken} with the given API key.
     * The token will have a single authority based on the API key's role.
     *
     * @param apiKey the API key to authenticate
     * @return an authenticated token with the appropriate authority
     */
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
