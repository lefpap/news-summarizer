package io.github.lefpap.news_summarizer.summary;

import io.github.lefpap.news_summarizer.summary.api.ApiCreateSummaryRequest;
import io.github.lefpap.news_summarizer.summary.api.ApiSummary;
import io.github.lefpap.news_summarizer.summary.api.ApiUpdateSummaryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * REST controller for managing summaries.
 * Provides endpoints for creating, retrieving, updating, and deleting summaries.
 */
@RestController()
@RequestMapping("/api/v1/summaries")
@RequiredArgsConstructor
public class OutputSummaryController {

    private final OutputSummaryService service;

    /**
     * Retrieves a list of summaries, optionally filtered by a query.
     *
     * @param query the optional search query
     * @return a list of summaries
     */
    @GetMapping
    public ResponseEntity<List<ApiSummary>> listSummaries(@RequestParam(value = "q", required = false) String query) {
        List<ApiSummary> summaries = service.listSummaries(query);
        return ResponseEntity.ok(summaries);
    }

    /**
     * Retrieves a specific summary by its ID.
     *
     * @param id the ID of the summary
     * @return the requested summary
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiSummary> getSummary(@PathVariable("id") UUID id) {
        ApiSummary summary = service.getSummary(id);
        return ResponseEntity.ok(summary);
    }

    /**
     * Creates a new summary.
     *
     * @param request the request containing summary details
     * @return the created summary
     */
    @PostMapping
    public ResponseEntity<ApiSummary> createSummary(@RequestBody ApiCreateSummaryRequest request) {
        ApiSummary summary = service.createSummary(request);
        return ResponseEntity.created(URI.create("")).body(summary);
    }

    /**
     * Updates an existing summary by its ID.
     *
     * @param id      the ID of the summary to update
     * @param request the request containing updated summary details
     * @return the updated summary
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiSummary> updateSummary(@PathVariable("id") UUID id, @RequestBody ApiUpdateSummaryRequest request) {
        ApiSummary summary = service.updateSummary(id, request);
        return ResponseEntity.accepted().body(summary);
    }

    /**
     * Deletes a summary by its ID.
     *
     * @param id the ID of the summary to delete
     * @return a response indicating the deletion was successful
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSummary(@PathVariable("id") UUID id) {
        service.deleteSummary(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Deletes all summaries.
     *
     * @return a response indicating the deletion was successful
     */
    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllSummaries() {
        service.deleteAllSummaries();
        return ResponseEntity.noContent().build();
    }

    /**
     * Handles NoSuchElementException by returning a 404 Not Found response.
     *
     * @param e the exception
     * @return a 404 Not Found response
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException e) {
        return ResponseEntity.notFound().build();
    }

}
