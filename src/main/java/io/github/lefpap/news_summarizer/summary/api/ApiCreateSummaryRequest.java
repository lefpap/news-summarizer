package io.github.lefpap.news_summarizer.summary.api;

import java.util.List;

public record ApiCreateSummaryRequest(
    String title,
    String readingTime,
    List<String> highlights,
    List<ApiSummary.ApiSource> sources,
    String content
) {
}
