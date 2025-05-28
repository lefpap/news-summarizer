package io.github.lefpap.news_summarizer.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApiKeyAuthenticationProvider implements AuthenticationProvider {

    private final AuthSettings authSettings;

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

    @Override
    public boolean supports(Class<?> authentication) {
        return ApiKeyAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
