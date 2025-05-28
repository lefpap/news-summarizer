package io.github.lefpap.news_summarizer.news_api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * REST controller for handling News API requests.
 * Provides endpoints for fetching news articles.
 */
@RestController
@RequestMapping("/api/v1/news")
@RequiredArgsConstructor
public class NewsApiController {

    private final NewsApiClient newsApiClient;

    /**
     * Retrieves news articles based on the provided query parameters.
     *
     * @param params the query parameters as key-value pairs
     * @return the response containing news articles
     */
    @GetMapping("/everything")
    public ResponseEntity<NewsApiResponse> everything(@RequestParam Map<String, String> params) {
        var queryParams = NewsApiQueryParams.of(params);
        NewsApiResponse response = newsApiClient.getEverything(queryParams);
        return ResponseEntity.ok(response);
    }

}
