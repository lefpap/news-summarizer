package io.github.lefpap.news_summarizer.summary;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder(toBuilder = true)
public record OutputSummary(
    UUID id,
    String title,
    String description,
    String readingTime,
    List<String> highlights,
    List<Source> sources,
    String content,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public record Source(
        String name,
        String url
    ) {
    }
}
