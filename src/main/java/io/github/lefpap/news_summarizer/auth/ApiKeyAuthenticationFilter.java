package io.github.lefpap.news_summarizer.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filter for processing API key-based authentication.
 * Extracts API key information from request headers and authenticates the request.
 */
@Component
@RequiredArgsConstructor
public class ApiKeyAuthenticationFilter extends OncePerRequestFilter {

    private static final String HEADER_ID = "X-API-ID";
    private static final String HEADER_SECRET = "X-API-SECRET";

    private final AuthenticationManager authManager;

    /**
     * Processes the incoming request to authenticate using API keys.
     *
     * @param request     the HTTP request
     * @param response    the HTTP response
     * @param filterChain the filter chain
     * @throws ServletException if an error occurs during filtering
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        String id = request.getHeader(HEADER_ID);
        String secret = request.getHeader(HEADER_SECRET);

        if (id == null || secret == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            var apiKey = ApiKey.of(id, secret);
            var token = ApiKeyAuthenticationToken.unauthenticated(apiKey);
            var authentication = authManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (AuthenticationException ex) {
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}
