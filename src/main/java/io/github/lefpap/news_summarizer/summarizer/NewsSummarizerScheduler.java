package io.github.lefpap.news_summarizer.summarizer;

import io.github.lefpap.news_summarizer.summary.OutputSummary;
import io.github.lefpap.news_summarizer.summary.OutputSummaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewsSummarizerScheduler {

    private final NewsSummarizerService newsSummarizerService;
    private final OutputSummaryRepository outputSummaryRepository;

    @Scheduled(cron = "${summarizer.cron}")
    public void automaticSummarization() {
        OutputSummary summary = newsSummarizerService.summarize("AI");
        outputSummaryRepository.save(summary);
    }

}
