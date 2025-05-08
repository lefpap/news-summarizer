package io.github.lefpap.news_summarizer;

import lombok.Builder;

@Builder
public record OutputSummary(
    String title,
    String summary
) {}
