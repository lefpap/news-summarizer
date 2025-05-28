package io.github.lefpap.news_summarizer.news_api;

import java.util.List;

/**
 * Represents the response from the News API. Contains the status, total results, and a list of articles.
 */
public record NewsApiResponse(
    String status,
    int totalResults,
    List<NewsApiArticle> articles
) {
}
