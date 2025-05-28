package io.github.lefpap.news_summarizer.summary;

import io.github.lefpap.news_summarizer.summary.api.ApiCreateSummaryRequest;
import io.github.lefpap.news_summarizer.summary.api.ApiSummary;
import io.github.lefpap.news_summarizer.summary.api.ApiUpdateSummaryRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Maps between API models and OutputSummary domain models.
 * Provides methods for converting between different representations.
 */
@Component
public class OutputSummaryMapper {

    /**
     * Converts an OutputSummary to an ApiSummary.
     *
     * @param summary the OutputSummary to convert
     * @return the converted ApiSummary
     */
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

    /**
     * Converts an ApiCreateSummaryRequest to an OutputSummary.
     *
     * @param request the request to convert
     * @return the converted OutputSummary
     */
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

    /**
     * Applies updates from an ApiUpdateSummaryRequest to an existing OutputSummary.
     *
     * @param summary the existing summary
     * @param request the update request
     * @return the updated OutputSummary
     */
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

    /**
     * Converts a list of ApiSummary.ApiSource to a list of OutputSummary.Source.
     *
     * @param sources the list of ApiSource to convert
     * @return the converted list of OutputSummary.Source
     */
    private List<OutputSummary.Source> toOutputSources(List<ApiSummary.ApiSource> sources) {
        return Optional.ofNullable(sources).orElse(List.of()).stream()
            .map(s -> new OutputSummary.Source(s.name(), s.url()))
            .toList();
    }

    /**
     * Converts a list of OutputSummary.Source to a list of ApiSummary.ApiSource.
     *
     * @param sources the list of OutputSummary.Source to convert
     * @return the converted list of ApiSummary.ApiSource
     */
    private List<ApiSummary.ApiSource> toApiSources(List<OutputSummary.Source> sources) {
        return Optional.ofNullable(sources).orElse(List.of()).stream()
            .map(s -> new ApiSummary.ApiSource(s.name(), s.url()))
            .toList();
    }
}
