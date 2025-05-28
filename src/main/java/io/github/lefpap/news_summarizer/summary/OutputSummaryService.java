package io.github.lefpap.news_summarizer.summary;

import io.github.lefpap.news_summarizer.summary.api.ApiCreateSummaryRequest;
import io.github.lefpap.news_summarizer.summary.api.ApiSummary;
import io.github.lefpap.news_summarizer.summary.api.ApiUpdateSummaryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

/**
 * Service for managing OutputSummary entities.
 * Provides methods for listing, retrieving, creating, updating, and deleting summaries.
 */
@Service
@RequiredArgsConstructor
public class OutputSummaryService {

    private final OutputSummaryJdbcRepository repository;
    private final OutputSummaryMapper mapper;

    /**
     * Retrieves a list of summaries, optionally filtered by a query.
     *
     * @param query the optional search query
     * @return a list of summaries
     */
    public List<ApiSummary> listSummaries(String query) {
        if (Objects.nonNull(query) && !query.isBlank()) {
            return repository.findAllMatched(query).stream()
                .map(mapper::toApi)
                .toList();
        }

        return repository.findAll().stream()
            .map(mapper::toApi)
            .toList();
    }

    /**
     * Retrieves a specific summary by its ID.
     *
     * @param id the ID of the summary
     * @return the requested summary
     */
    public ApiSummary getSummary(UUID id) {
        return repository.findOne(id)
            .map(mapper::toApi)
            .orElseThrow(() -> new NoSuchElementException("Summary [%s] not found".formatted(id)));
    }

    /**
     * Creates a new summary.
     *
     * @param request the request containing summary details
     * @return the created summary
     */
    public ApiSummary createSummary(ApiCreateSummaryRequest request) {

        OutputSummary summaryToSave = mapper.toOutputSummary(request);
        OutputSummary savedSummary = repository.save(summaryToSave);

        return mapper.toApi(savedSummary);
    }

    /**
     * Updates an existing summary by its ID.
     *
     * @param id      the ID of the summary to update
     * @param request the request containing updated summary details
     * @return the updated summary
     */
    public ApiSummary updateSummary(UUID id, ApiUpdateSummaryRequest request) {
        var updatedSummary = repository.findOne(id)
            .map(summary -> mapper.applyAndGetUpdate(summary, request))
            .orElseThrow(() -> new NoSuchElementException("Summary [%s] not found".formatted(id)));

        OutputSummary savedSummary = repository.save(updatedSummary);

        return mapper.toApi(savedSummary);
    }

    /**
     * Deletes a summary by its ID.
     *
     * @param id the ID of the summary to delete
     */
    public void deleteSummary(UUID id) {
        repository.delete(id);
    }

    /**
     * Deletes all summaries.
     */
    public void deleteAllSummaries() {
        repository.deleteAll();
    }
}
