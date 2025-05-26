package io.github.lefpap.news_summarizer.summary;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.github.lefpap.news_summarizer.summary.OutputSummarySql.INSERT_SQL;
import static io.github.lefpap.news_summarizer.summary.OutputSummarySql.SELECT_ALL_SQL;
import static io.github.lefpap.news_summarizer.summary.OutputSummarySql.SELECT_BY_ID_SQL;

@Repository
@RequiredArgsConstructor
public class OutputSummaryJdbcRepository {

    private final JdbcClient jdbcClient;
    private final OutputSummaryJdbcMapper jdbcMapper;

    public List<OutputSummary> findAll() {
        return jdbcClient.sql(SELECT_ALL_SQL)
            .query(jdbcMapper.rowMapper())
            .list();
    }

    public Optional<OutputSummary> findOne(UUID id) {
        return jdbcClient.sql(SELECT_BY_ID_SQL)
            .param("id", id)
            .query(jdbcMapper.rowMapper())
            .optional();
    }

    public OutputSummary save(OutputSummary summary) {
        if (summary.id() == null) {
            return insert(summary);
        }

        return update(summary);
    }

    public void delete(UUID id) {
        jdbcClient.sql(OutputSummarySql.DELETE_SQL)
            .param("id", id)
            .update();
    }

    public void deleteAll() {
        jdbcClient.sql(OutputSummarySql.DELETE_ALL_SQL)
            .update();
    }

    private OutputSummary insert(OutputSummary summary) {
        return jdbcClient.sql(INSERT_SQL)
            .paramSource(jdbcMapper.insertParameterSource(summary))
            .query(jdbcMapper.rowMapper())
            .single();
    }

    public OutputSummary update(OutputSummary summary) {
        return jdbcClient.sql(OutputSummarySql.UPDATE_SQL)
            .paramSource(jdbcMapper.updateParameterSource(summary))
            .query(jdbcMapper.rowMapper())
            .single();
    }

}

