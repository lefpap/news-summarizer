package io.github.lefpap.news_summarizer.summarizer;

import io.github.lefpap.news_summarizer.summary.OutputSummary;
import io.github.lefpap.news_summarizer.summary.OutputSummaryJdbcRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NewsSummarizerScheduler {

    private final NewsSummarizerService newsSummarizerService;
    private final OutputSummaryJdbcRepository summaryRepository;

    @Scheduled(cron = "${app.summarizer.cron}")
    public void automaticSummarization() {
        OutputSummary summary = newsSummarizerService.summarize("AI");
        OutputSummary saved = summaryRepository.save(summary);
        log.info("Saved summary with ID: {}", saved.id());
    }

}
