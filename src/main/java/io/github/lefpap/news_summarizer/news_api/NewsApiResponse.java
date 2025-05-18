package io.github.lefpap.news_summarizer.news_api;

import java.util.List;

public record NewsApiResponse(
    String status,
    int totalResults,
    List<NewsApiArticle> articles
) {}
