package io.github.lefpap.news_summarizer.summarizer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class NewsSummaryController {

    private final NewsSummaryService newsSummaryService;

    @GetMapping("/summarize")
    public OutputSummary summarize(@RequestParam(value = "topic") String topic) {
        return newsSummaryService.summarize(topic);
    }

    @ExceptionHandler(OutputSummaryParsingException.class)
    public ResponseEntity<Map<String, Object>> handleOutputSummaryParsingException(OutputSummaryParsingException ex) {

        return ResponseEntity.badRequest()
            .body(Map.of("error", ex.getMessage()));
    }
}
