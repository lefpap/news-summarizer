package io.github.lefpap.news_summarizer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class NewsSummaryService {

    private static final String AI_PROMPT_INSTRUCTIONS = """
            You are a news summarizer. Summarize the following news articles:
        """;

    private final NewsApiClient newsApiClient;
    private final ChatClient chatClient;

    public OutputSummary summarizeNews(String query) {
        // Fetch top headlines from the News API
        NewsApiResponse response = newsApiClient.getEverything(query);

        log.info("Fetched {} articles {}", response.articles().size(), response);

        String user = createArticlesSummaryText(response);

        var result = chatClient
            .prompt(AI_PROMPT_INSTRUCTIONS)
            .user(user)
            .call();

        return new OutputSummary("Summary of news articles", result.content());
    }

    private static String createArticlesSummaryText(NewsApiResponse response) {
        return response.articles().stream()
            .map(article -> "Title: %s".formatted(article.title()) +
                "Description: %s".formatted(article.description()) +
                "URL: %s".formatted(article.url()) +
                "Published At: %s".formatted(article.publishedAt()) +
                "Content:\n %s".formatted(article.content()) +
                "\n\n"
            )
            .collect(Collectors.joining());
    }

}
