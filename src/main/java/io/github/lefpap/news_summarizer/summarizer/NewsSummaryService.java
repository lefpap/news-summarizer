package io.github.lefpap.news_summarizer.summarizer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.lefpap.news_summarizer.news_api.NewsApiClient;
import io.github.lefpap.news_summarizer.news_api.NewsApiQueryParams;
import io.github.lefpap.news_summarizer.news_api.NewsApiQueryParams.SearchIn;
import io.github.lefpap.news_summarizer.news_api.NewsApiQueryParams.SortBy;
import io.github.lefpap.news_summarizer.news_api.NewsApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
@Slf4j
public class NewsSummaryService {

    private final NewsApiClient newsApiClient;
    private final ChatClient chatClient;

    private final OutputSummaryParser outputSummaryParser;

    private final ObjectMapper objectMapper;

    public NewsSummaryService(NewsApiClient newsApiClient, ChatClient chatClient, OutputSummaryParser outputSummaryParser, Jackson2ObjectMapperBuilder objectMapperBuilder) {
        this.newsApiClient = newsApiClient;
        this.chatClient = chatClient;
        this.outputSummaryParser = outputSummaryParser;
        this.objectMapper = objectMapperBuilder.build();
    }

    public OutputSummary summarize(String topic) {

        NewsApiQueryParams queryParams = NewsApiQueryParams.builder()
            .q(topic)
            .from(LocalDate.now())
            .to(LocalDate.now().minusDays(3))
            .sortBy(SortBy.POPULARITY)
            .searchIn(SearchIn.TITLE, SearchIn.DESCRIPTION)
            .pageSize(10)
            .build();

        NewsApiResponse response = newsApiClient.getEverything(queryParams);

        log.info("Fetched {}/{} articles", response.articles().size(), response.totalResults());

        var content = chatClient.prompt()
            .user(createArticlesSummaryText(response))
            .call()
            .content();

        log.info("AI response: {}", content);

        return outputSummaryParser.parse(content);
    }

    private String createArticlesSummaryText(NewsApiResponse response) {
        try {
            StringBuilder builder = new StringBuilder();
            builder.append("Here are the articles to summarize:\n");
            String articles = objectMapper.writeValueAsString(response.articles());
            builder.append(articles);
            return builder.toString();
        } catch (JsonProcessingException ex) {
            throw new IllegalArgumentException("Error while converting articles to JSON", ex);
        }
    }

}
