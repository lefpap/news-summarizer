package io.github.lefpap.news_summarizer.summary.api;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Represents a summary of a news article in the API response format.
 */
@Builder(toBuilder = true)
public record ApiSummary(
    UUID id,
    String title,
    String description,
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
