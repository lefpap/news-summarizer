package io.github.lefpap.news_summarizer.summary;

public class OutputSummarySql {

    private OutputSummarySql() {
        // Prevent instantiation
    }

    public static final String SELECT_ALL_SQL = """
        SELECT * FROM summaries
        """;

    public static final String SELECT_BY_ID_SQL = """
        SELECT * FROM summaries
        WHERE id = :id
        """;

    public static final String INSERT_SQL = """
        INSERT INTO summaries
            (id, title, description, reading_time, highlights, sources, content)
        VALUES
            (:id, :title, :description, :reading_time, :highlights, :sources, :content)
        RETURNING *
        """;

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

    public static final String DELETE_SQL = """
        DELETE FROM summaries
        WHERE id = :id
        """;

    public static final String DELETE_ALL_SQL = """
        DELETE FROM summaries
        """;
}
