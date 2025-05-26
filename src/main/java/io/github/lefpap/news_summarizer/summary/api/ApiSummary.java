package io.github.lefpap.news_summarizer.summary.api;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder(toBuilder = true)
public record ApiSummary(
    UUID id,
    String title,
    String readingTime,
    List<String> highlights,
    List<ApiSource> sources,
    String content,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public record ApiSource(
        String name,
        String url
    ) {
    }
}
