package io.github.lefpap.news_summarizer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NewsSummaryService {

    private final NewsApiClient newsApiClient;

    public OutputSummary summarizeNews(String query) {
        // Fetch top headlines from the News API
        NewsApiResponse response = newsApiClient.getEverything(query);

        log.info("Fetched {} articles {}", response.articles().size(), response);

        // TODO: Construct the text to feed to the chat client
        // TODO: Use the chat client to summarize the news articles
        return new OutputSummary("Summary of news articles", "This is a summary of the news articles.");
    }

}
