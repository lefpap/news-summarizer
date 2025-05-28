package io.github.lefpap.news_summarizer.summary;

/**
 * Exception thrown when parsing an OutputSummary fails.
 */
public class OutputSummaryParsingException extends RuntimeException {

    /**
     * Constructs a new OutputSummaryParsingException with the specified message.
     *
     * @param message the detail message
     */
    public OutputSummaryParsingException(String message) {
        super(message);
    }
}
