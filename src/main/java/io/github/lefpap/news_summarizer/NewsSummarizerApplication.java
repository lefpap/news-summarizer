package io.github.lefpap.news_summarizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the News Summarizer application.
 */
@SpringBootApplication
public class NewsSummarizerApplication {

    /**
     * Starts the Spring Boot application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(NewsSummarizerApplication.class, args);
    }

}
