package io.github.lefpap.news_summarizer.summary;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.github.lefpap.news_summarizer.summary.OutputSummarySql.INSERT_SQL;
import static io.github.lefpap.news_summarizer.summary.OutputSummarySql.SELECT_ALL_MATCHED_SQL;
import static io.github.lefpap.news_summarizer.summary.OutputSummarySql.SELECT_ALL_SQL;
import static io.github.lefpap.news_summarizer.summary.OutputSummarySql.SELECT_BY_ID_SQL;

/**
 * Repository for accessing OutputSummary data in the database.
 * Provides methods for CRUD operations and custom queries.
 */
@Repository
@RequiredArgsConstructor
public class OutputSummaryJdbcRepository {

    private final JdbcClient jdbcClient;
    private final OutputSummaryJdbcMapper jdbcMapper;

    /**
     * Retrieves all summaries from the database.
     *
     * @return a list of all summaries
     */
    public List<OutputSummary> findAll() {
        return jdbcClient.sql(SELECT_ALL_SQL)
            .query(jdbcMapper.rowMapper())
            .list();
    }

    /**
     * Retrieves summaries matching the given query.
     *
     * @param query the search query
     * @return a list of matching summaries
     */
    public List<OutputSummary> findAllMatched(String query) {
        return jdbcClient.sql(SELECT_ALL_MATCHED_SQL)
            .param("title", query)
            .param("description", query)
            .query(jdbcMapper.rowMapper())
            .list();
    }

    /**
     * Retrieves a summary by its ID.
     *
     * @param id the ID of the summary
     * @return an Optional containing the summary if found
     */
    public Optional<OutputSummary> findOne(UUID id) {
        return jdbcClient.sql(SELECT_BY_ID_SQL)
            .param("id", id)
            .query(jdbcMapper.rowMapper())
            .optional();
    }

    /**
     * Saves a summary to the database.
     * Inserts a new summary if the ID is null, otherwise updates the existing summary.
     *
     * @param summary the summary to save
     * @return the saved summary
     */
    public OutputSummary save(OutputSummary summary) {
        if (summary.id() == null) {
            return insert(summary);
        }

        return update(summary);
    }


    /**
     * Deletes a summary by its ID.
     *
     * @param id the ID of the summary to delete
     */
    public void delete(UUID id) {
        jdbcClient.sql(OutputSummarySql.DELETE_SQL)
            .param("id", id)
            .update();
    }

    /**
     * Deletes all summaries from the database.
     */
    public void deleteAll() {
        jdbcClient.sql(OutputSummarySql.DELETE_ALL_SQL)
            .update();
    }

    /**
     * Inserts a new summary into the database.
     *
     * @param summary the summary to insert
     * @return the inserted summary
     */
    private OutputSummary insert(OutputSummary summary) {
        return jdbcClient.sql(INSERT_SQL)
            .paramSource(jdbcMapper.insertParameterSource(summary))
            .query(jdbcMapper.rowMapper())
            .single();
    }

    /**
     * Updates an existing summary in the database.
     *
     * @param summary the summary to update
     * @return the updated summary
     */
    public OutputSummary update(OutputSummary summary) {
        return jdbcClient.sql(OutputSummarySql.UPDATE_SQL)
            .paramSource(jdbcMapper.updateParameterSource(summary))
            .query(jdbcMapper.rowMapper())
            .single();
    }

}

