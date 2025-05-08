package io.github.lefpap.news_summarizer;

public record NewsApiArticle(
    String title,
    String description,
    String content,
    String url,
    String publishedAt
) {}
