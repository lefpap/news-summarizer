package io.github.lefpap.news_summarizer.news_api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class NewsApiClientHttpInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        log.info("REQ: {} {}", request.getMethod(), request.getURI());
        log.info("Request Body: {}", new String(body));
        ClientHttpResponse response = execution.execute(request, body);
        log.info("RES: {}", response.getStatusCode());
        return response;
    }

}
