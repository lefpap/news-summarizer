package io.github.lefpap.news_summarizer.news_api;

/**
 * Represents an article retrieved from the News API.
 * Contains details such as the source, author, title, and content.
 */
public record NewsApiArticle(
    NewsApiSource source,
    String author,
    String title,
    String description,
    String url,
    String content,
    String publishedAt
) {

    /**
     * Represents the source of a news article.
     * Contains the source ID and name.
     */
    public record NewsApiSource(
        String id,
        String name
    ) {
    }
}
