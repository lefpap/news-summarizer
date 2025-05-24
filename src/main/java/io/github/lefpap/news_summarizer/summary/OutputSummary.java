package io.github.lefpap.news_summarizer.summary;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record OutputSummary(
    String title,
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
