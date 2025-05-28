package io.github.lefpap.news_summarizer.summary.api;

import java.util.List;

/**
 * Represents a request to create a summary.
 */
public record ApiCreateSummaryRequest(
    String title,
    String description,
    String readingTime,
    List<String> highlights,
    List<ApiSummary.ApiSource> sources,
    String content
) {
}
