package io.github.lefpap.news_summarizer.auth;

import io.github.lefpap.news_summarizer.auth.ApiKey.AuthRole;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration class for setting up authentication and authorization.
 * Configures security filters and authentication providers.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AuthConfig {

    private final AuthSettings authSettings;

    /**
     * Configures the AuthenticationManager with the API key authentication provider.
     *
     * @param http         the HttpSecurity object
     * @param authProvider the API key authentication provider
     * @return the configured AuthenticationManager
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, ApiKeyAuthenticationProvider authProvider)
        throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
            .authenticationProvider(authProvider)
            .build();
    }

    /**
     * Configures the security filter chain for handling API requests.
     *
     * @param http       the HttpSecurity object
     * @param authFilter the API key authentication filter
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, ApiKeyAuthenticationFilter authFilter)
        throws Exception {

        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);

        if (!authSettings.isEnabled()) {
            return http
                .authorizeHttpRequests(config -> config
                    .anyRequest().permitAll())
                .build();
        }

        http.sessionManagement(config ->
            config.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests(config -> config
            .requestMatchers(HttpMethod.GET, "/api/v1/summaries/**")
            .hasAnyRole(AuthRole.READ_ONLY.name(), AuthRole.FULL_ACCESS.name())

            .requestMatchers("/api/v1/summaries/**")
            .hasRole(AuthRole.FULL_ACCESS.name())
        );

        http.exceptionHandling(config ->
            config.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));

        http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
