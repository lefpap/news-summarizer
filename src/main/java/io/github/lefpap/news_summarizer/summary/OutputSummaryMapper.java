package io.github.lefpap.news_summarizer.summary;

import io.github.lefpap.news_summarizer.summary.api.ApiCreateSummaryRequest;
import io.github.lefpap.news_summarizer.summary.api.ApiSummary;
import io.github.lefpap.news_summarizer.summary.api.ApiUpdateSummaryRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OutputSummaryMapper {

    public ApiSummary toApi(OutputSummary summary) {
        return ApiSummary.builder()
            .id(summary.id())
            .title(summary.title())
            .description(summary.description())
            .readingTime(summary.readingTime())
            .highlights(summary.highlights())
            .sources(toApiSources(summary.sources()))
            .content(summary.content())
            .createdAt(summary.createdAt())
            .updatedAt(summary.updatedAt())
            .build();
    }

    public OutputSummary toOutputSummary(ApiCreateSummaryRequest request) {
        return OutputSummary.builder()
            .title(request.title())
            .description(request.description())
            .readingTime(request.readingTime())
            .highlights(request.highlights())
            .sources(toOutputSources(request.sources()))
            .content(request.content())
            .build();
    }

    public OutputSummary applyAndGetUpdate(OutputSummary summary, ApiUpdateSummaryRequest request) {
        return summary.toBuilder()
            .title(request.title())
            .description(request.description())
            .readingTime(request.readingTime())
            .highlights(request.highlights())
            .sources(toOutputSources(request.sources()))
            .content(request.content())
            .build();
    }

    private List<OutputSummary.Source> toOutputSources(List<ApiSummary.ApiSource> sources) {
        return Optional.ofNullable(sources).orElse(List.of()).stream()
            .map(s -> new OutputSummary.Source(s.name(), s.url()))
            .toList();
    }

    private List<ApiSummary.ApiSource> toApiSources(List<OutputSummary.Source> sources) {
        return Optional.ofNullable(sources).orElse(List.of()).stream()
            .map(s -> new ApiSummary.ApiSource(s.name(), s.url()))
            .toList();
    }
}
