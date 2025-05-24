package io.github.lefpap.news_summarizer.news_api;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClient;

@Service
public class NewsApiClient {

    public static final String API_KEY_HEADER = "X-Api-Key";

    private final RestClient restClient;

    public NewsApiClient(RestClient.Builder restClientBuilder, NewsApiClientSettings clientSettings) {
        this.restClient = restClientBuilder
            .baseUrl(clientSettings.getBaseUrl())
            .defaultHeader(API_KEY_HEADER, clientSettings.getApiKey())
            .build();
    }

    public NewsApiResponse getEverything(@RequestParam(value = "q") String query) {
        return restClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/everything")
                .queryParam("q", query)
                .build()
            )
            .retrieve()
            .body(NewsApiResponse.class);
    }

    public NewsApiResponse getEverything(@Valid NewsApiQueryParams queryParams) {
        return restClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/everything")
                .queryParams(MultiValueMap.fromSingleValue(queryParams.toMap()))
                .build()
            )
            .retrieve()
            .body(NewsApiResponse.class);
    }
}
