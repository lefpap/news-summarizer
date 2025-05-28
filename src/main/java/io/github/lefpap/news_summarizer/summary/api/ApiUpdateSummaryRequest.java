package io.github.lefpap.news_summarizer.summary.api;

import java.util.List;

/**
 * Represents a request to create a summary.
 */
public record ApiUpdateSummaryRequest(
    String title,
    String description,
    String content,
    String readingTime,
    String language,
    List<String> highlights,
    List<ApiSummary.ApiSource> sources
) {
}
