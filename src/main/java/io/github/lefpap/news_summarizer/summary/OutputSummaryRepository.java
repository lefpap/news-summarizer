package io.github.lefpap.news_summarizer.summary;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OutputSummaryRepository {

    private final JdbcClient jdbcClient;

    private final OutputSummaryJdbcMapper jdbcMapper;

    private static final String SELECT_ALL_SQL = """
        SELECT * FROM summaries
        """;

    private static final String INSERT_SQL = """
        INSERT INTO summaries
            (title, reading_time, highlights, sources, content)
        VALUES
            (:title, :reading_time, :highlights, :sources, :content)
        """;

    public List<OutputSummary> findAll() {
        return jdbcClient.sql(SELECT_ALL_SQL)
            .query(jdbcMapper.rowMapper())
            .list();
    }

    public void save(OutputSummary summary) {
        jdbcClient.sql(INSERT_SQL)
            .paramSource(jdbcMapper.insertParameterSource(summary))
            .update();
    }

}

