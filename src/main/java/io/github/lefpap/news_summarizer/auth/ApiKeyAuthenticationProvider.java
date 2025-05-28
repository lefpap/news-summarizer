package io.github.lefpap.news_summarizer.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

/**
 * Authentication provider for validating API keys. Checks the provided API key against the configured settings.
 */
@Service
@RequiredArgsConstructor
public class ApiKeyAuthenticationProvider implements AuthenticationProvider {

    private final AuthSettings authSettings;

    /**
     * Authenticates the provided API key.
     *
     * @param authentication the authentication request object
     * @return the authenticated token
     * @throws AuthenticationException if authentication fails
     */
    @Override
    public Authentication authenticate(Authentication authentication)
        throws AuthenticationException {

        ApiKeyAuthenticationToken token = (ApiKeyAuthenticationToken) authentication;
        String id = token.getPrincipal().toString();
        String secret = token.getCredentials().toString();

        var match = authSettings.getApiKeys().stream()
            .filter(key -> key.id().equals(id))
            .findFirst();

        if (match.isEmpty()) {
            throw new BadCredentialsException("Unknown API key");
        }

        var apiKey = match.get();
        if (!apiKey.secret().equals(secret)) {
            throw new BadCredentialsException("Invalid API secret");
        }

        return ApiKeyAuthenticationToken.authenticated(apiKey);
    }

    /**
     * Checks if this provider supports the given authentication type.
     *
     * @param authentication the authentication type
     * @return true if supported, false otherwise
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return ApiKeyAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
