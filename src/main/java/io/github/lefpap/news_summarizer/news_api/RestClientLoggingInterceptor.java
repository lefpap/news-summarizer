package io.github.lefpap.news_summarizer.news_api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Interceptor for logging HTTP requests and responses.
 * Logs details such as headers, body, and response time.
 */
@Component
@Slf4j
public class RestClientLoggingInterceptor implements ClientHttpRequestInterceptor {

    /**
     * Intercepts and logs HTTP requests and responses.
     *
     * @param request   the HTTP request
     * @param body      the request body
     * @param execution the request execution chain
     * @return the HTTP response
     * @throws IOException if an I/O error occurs
     */
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
        throws IOException {
        log.info("==================== Request Start =======================");
        log.info("REQ: {} {}", request.getMethod(), request.getURI());
        log.info("REQ Headers: {}", request.getHeaders());
        log.info("REQ Body: {}", new String(body, StandardCharsets.UTF_8));
        var start = System.currentTimeMillis();
        ClientHttpResponse response = execution.execute(request, body);
        var duration = System.currentTimeMillis() - start;
        log.info("RES: {}", response.getStatusCode());
        log.info("RES Headers: {}", response.getHeaders());
        log.info("RES Body: {}", new String(response.getBody().readAllBytes(), StandardCharsets.UTF_8));
        log.info("==================== Request End [{}ms] ======================", duration);
        return response;
    }

}
