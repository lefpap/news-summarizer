package io.github.lefpap.news_summarizer.summary.api;

import java.util.List;

public record ApiUpdateSummaryRequest(
    String title,
    String content,
    String readingTime,
    String language,
    List<String> highlights,
    List<ApiSummary.ApiSource> sources
) {
}
