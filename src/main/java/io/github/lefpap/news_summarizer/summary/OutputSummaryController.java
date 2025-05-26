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

@RestController()
@RequestMapping("/api/v1/summaries")
@RequiredArgsConstructor
public class OutputSummaryController {

    private final OutputSummaryService service;

    @GetMapping
    public ResponseEntity<List<ApiSummary>> listSummaries() {
        List<ApiSummary> summaries = service.listSummaries();
        return ResponseEntity.ok(summaries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiSummary> getSummary(@PathVariable("id") UUID id) {
        ApiSummary summary = service.getSummary(id);
        return ResponseEntity.ok(summary);
    }

    @PostMapping
    public ResponseEntity<ApiSummary> createSummary(@RequestBody ApiCreateSummaryRequest request) {
        ApiSummary summary = service.createSummary(request);
        return ResponseEntity.created(URI.create("")).body(summary);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiSummary> updateSummary(@PathVariable("id") UUID id, @RequestBody ApiUpdateSummaryRequest request) {
        ApiSummary summary = service.updateSummary(id, request);
        return ResponseEntity.accepted().body(summary);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSummary(@PathVariable("id") UUID id) {
        service.deleteSummary(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllSummaries() {
        service.deleteAllSummaries();
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException e) {
        return ResponseEntity.notFound().build();
    }

}
