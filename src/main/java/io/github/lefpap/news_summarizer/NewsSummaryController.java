package io.github.lefpap.news_summarizer;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class NewsSummaryController {

    private final NewsSummaryService newsSummaryService;

    @GetMapping("/summaries")
    public OutputSummary getSummary(@RequestParam(value = "q") String query) {
        return newsSummaryService.summarizeNews(query);
    }
}
