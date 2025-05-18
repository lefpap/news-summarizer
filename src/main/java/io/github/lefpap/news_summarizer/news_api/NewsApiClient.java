package io.github.lefpap.news_summarizer.news_api;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.Map;

@HttpExchange
public interface NewsApiClient {

    @GetExchange("/everything")
    NewsApiResponse everything(@RequestParam(value = "q") String query);

    @GetExchange("/everything")
    NewsApiResponse everything(@RequestParam Map<String, String> queries);
}
