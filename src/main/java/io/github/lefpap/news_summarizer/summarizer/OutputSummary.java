package io.github.lefpap.news_summarizer.summarizer;

import lombok.Builder;

import java.util.List;

@Builder
public record OutputSummary(
    String title,
    String readingTime,
    List<String> highlights,
    List<Source> sources,
    String content
) {

    public record Source(
        String name,
        String url
    ) {
    }
}
