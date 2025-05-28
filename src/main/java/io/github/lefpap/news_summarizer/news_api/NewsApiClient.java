package io.github.lefpap.news_summarizer.news_api;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClient;

/**
 * Service for interacting with the News API.
 */
@Service
public class NewsApiClient {

    /**
     * Header name for the API key.
     */
    public static final String API_KEY_HEADER = "X-Api-Key";

    private final RestClient restClient;

    /**
     * Constructs a NewsApiClient with the specified RestClient builder and settings.
     *
     * @param restClientBuilder the RestClient builder
     * @param clientSettings    the settings for the News API client
     */
    public NewsApiClient(RestClient.Builder restClientBuilder, NewsApiClientSettings clientSettings) {
        this.restClient = restClientBuilder
            .baseUrl(clientSettings.getBaseUrl())
            .defaultHeader(API_KEY_HEADER, clientSettings.getApiKey())
            .build();
    }

    /**
     * Retrieves news articles matching the specified query.
     *
     * @param query the search query
     * @return the response containing news articles
     */
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

    /**
     * Retrieves news articles based on the specified query parameters.
     *
     * @param queryParams the query parameters
     * @return the response containing news articles
     */
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
