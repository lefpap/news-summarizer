package io.github.lefpap.news_summarizer.summary;

/**
 * Contains SQL queries for managing OutputSummary entities in the database.
 */
public class OutputSummarySql {

    private OutputSummarySql() {
        // Prevent instantiation
    }

    /**
     * SQL query to select all summaries.
     */
    public static final String SELECT_ALL_SQL = """
        SELECT * FROM summaries
        """;

    /**
     * SQL query to select summaries matching a search query.
     */
    public static final String SELECT_ALL_MATCHED_SQL = """
        SELECT * FROM summaries
        WHERE title ILIKE '%' || :title || '%'
        OR description ILIKE '%' || :description || '%'
        """;

    /**
     * SQL query to select a summary by its ID.
     */
    public static final String SELECT_BY_ID_SQL = """
        SELECT * FROM summaries
        WHERE id = :id
        """;

    /**
     * SQL query to insert a new summary.
     */
    public static final String INSERT_SQL = """
        INSERT INTO summaries
            (id, title, description, reading_time, highlights, sources, content)
        VALUES
            (:id, :title, :description, :reading_time, :highlights, :sources, :content)
        RETURNING *
        """;

    /**
     * SQL query to update an existing summary.
     */
    public static final String UPDATE_SQL = """
        UPDATE summaries SET
            title = :title,
            description = :description,
            reading_time = :reading_time,
            highlights = :highlights,
            sources = :sources,
            content = :content,
            updated_at = NOW()
        WHERE id = :id
        RETURNING *
        """;

    /**
     * SQL query to delete a summary by its ID.
     */
    public static final String DELETE_SQL = """
        DELETE FROM summaries
        WHERE id = :id
        """;

    /**
     * SQL query to delete all summaries.
     */
    public static final String DELETE_ALL_SQL = """
        DELETE FROM summaries
        """;
}
