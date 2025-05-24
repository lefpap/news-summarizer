package io.github.lefpap.news_summarizer.summary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.postgresql.util.PGobject;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component
public class OutputSummaryJdbcMapper {

    private final ObjectMapper objectMapper;

    public OutputSummaryJdbcMapper(Jackson2ObjectMapperBuilder objectMapperBuilder) {
        this.objectMapper = objectMapperBuilder
            .modules(new JavaTimeModule())
            .build();
    }

    /**
     * RowMapper that reads JSONB columns as strings and deserializes them
     * back into List<String> and List<Source>.
     */
    public RowMapper<OutputSummary> rowMapper() {
        return (rs, rowNum) -> {
            try {
                List<String> highlights = objectMapper.readValue(
                    rs.getString("highlights"),
                    new TypeReference<>() {
                    }
                );
                List<OutputSummary.Source> sources = objectMapper.readValue(
                    rs.getString("sources"),
                    new TypeReference<>() {
                    }
                );

                return new OutputSummary(
                    rs.getString("title"),
                    rs.getString("reading_time"),
                    highlights,
                    sources,
                    rs.getString("content"),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getTimestamp("updated_at").toLocalDateTime()
                );
            } catch (JsonProcessingException ex) {
                throw new DataRetrievalFailureException("Failed to parse JSON", ex);
            }
        };
    }

    /**
     * Build a MapSqlParameterSource for INSERT, converting JSON fields
     * into PGobject with type "jsonb" so Postgres stores them correctly.
     */
    public MapSqlParameterSource insertParameterSource(OutputSummary summary) {
        return new MapSqlParameterSource()
            .addValue("title", summary.title())
            .addValue("reading_time", summary.readingTime())
            .addValue("highlights", toJsonb(summary.highlights()))
            .addValue("sources", toJsonb(summary.sources()))
            .addValue("content", summary.content());
    }

    /**
     * Helper to wrap any serializable object into a PGobject("jsonb").
     */
    private PGobject toJsonb(Object value) {
        try {
            PGobject pg = new PGobject();
            pg.setType("jsonb");
            pg.setValue(objectMapper.writeValueAsString(value));
            return pg;
        } catch (SQLException | JsonProcessingException ex) {
            throw new DataRetrievalFailureException("Failed to convert to JSONB", ex);
        }
    }
}

