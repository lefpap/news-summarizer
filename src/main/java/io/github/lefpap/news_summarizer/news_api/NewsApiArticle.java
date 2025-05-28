package io.github.lefpap.news_summarizer.news_api;

public record NewsApiArticle(
    NewsApiSource source,
    String author,
    String title,
    String description,
    String url,
    String content,
    String publishedAt
) {
    public record NewsApiSource(
        String id,
        String name
    ) {
    }
}
