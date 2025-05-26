package io.github.lefpap.news_summarizer.summary;

import io.github.lefpap.news_summarizer.summary.api.ApiCreateSummaryRequest;
import io.github.lefpap.news_summarizer.summary.api.ApiSummary;
import io.github.lefpap.news_summarizer.summary.api.ApiUpdateSummaryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OutputSummaryService {

    private final OutputSummaryJdbcRepository repository;
    private final OutputSummaryMapper mapper;

    public List<ApiSummary> listSummaries() {
        return repository.findAll().stream()
            .map(mapper::toApi)
            .toList();
    }

    public ApiSummary getSummary(UUID id) {
        return repository.findOne(id)
            .map(mapper::toApi)
            .orElseThrow(() -> new NoSuchElementException("Summary [%s] not found".formatted(id)));
    }

    public ApiSummary createSummary(ApiCreateSummaryRequest request) {

        OutputSummary summaryToSave = mapper.toOutputSummary(request);
        OutputSummary savedSummary = repository.save(summaryToSave);

        return mapper.toApi(savedSummary);
    }

    public ApiSummary updateSummary(UUID id, ApiUpdateSummaryRequest request) {
        var updatedSummary = repository.findOne(id)
            .map(summary -> mapper.applyAndGetUpdate(summary, request))
            .orElseThrow(() -> new NoSuchElementException("Summary [%s] not found".formatted(id)));

        OutputSummary savedSummary = repository.save(updatedSummary);

        return mapper.toApi(savedSummary);
    }

    public void deleteSummary(UUID id) {
        repository.delete(id);
    }

    public void deleteAllSummaries() {
        repository.deleteAll();
    }
}
