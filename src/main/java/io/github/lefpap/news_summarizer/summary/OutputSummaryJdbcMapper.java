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
import java.util.UUID;

@Component
public class OutputSummaryJdbcMapper {

    private static final String ID_COLUMN = "id";
    private static final String TITLE_COLUMN = "title";
    private static final String DESCRIPTION_COLUMN = "description";
    private static final String READING_TIME_COLUMN = "reading_time";
    private static final String HIGHLIGHTS_COLUMN = "highlights";
    private static final String SOURCES_COLUMN = "sources";
    private static final String CONTENT_COLUMN = "content";
    private static final String CREATED_AT_COLUMN = "created_at";
    private static final String UPDATED_AT_COLUMN = "updated_at";

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
                    rs.getString(HIGHLIGHTS_COLUMN),
                    new TypeReference<>() {
                    }
                );
                List<OutputSummary.Source> sources = objectMapper.readValue(
                    rs.getString(SOURCES_COLUMN),
                    new TypeReference<>() {
                    }
                );

                return OutputSummary.builder()
                    .id(UUID.fromString(rs.getString(ID_COLUMN)))
                    .title(rs.getString(TITLE_COLUMN))
                    .description(rs.getString(DESCRIPTION_COLUMN))
                    .readingTime(rs.getString(READING_TIME_COLUMN))
                    .highlights(highlights)
                    .sources(sources)
                    .content(rs.getString(CONTENT_COLUMN))
                    .createdAt(rs.getTimestamp(CREATED_AT_COLUMN).toLocalDateTime())
                    .updatedAt(rs.getTimestamp(UPDATED_AT_COLUMN).toLocalDateTime())
                    .build();
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
            .addValue(ID_COLUMN, UUID.randomUUID())
            .addValue(TITLE_COLUMN, summary.title())
            .addValue(DESCRIPTION_COLUMN, summary.description())
            .addValue(READING_TIME_COLUMN, summary.readingTime())
            .addValue(HIGHLIGHTS_COLUMN, toJsonb(summary.highlights()))
            .addValue(SOURCES_COLUMN, toJsonb(summary.sources()))
            .addValue(CONTENT_COLUMN, summary.content());
    }

    /**
     * Build a MapSqlParameterSource for UPDATE, converting JSON fields
     * into PGobject with type "jsonb" so Postgres stores them correctly.
     */
    public MapSqlParameterSource updateParameterSource(OutputSummary summary) {
        return new MapSqlParameterSource()
            .addValue(ID_COLUMN, summary.id())
            .addValue(TITLE_COLUMN, summary.title())
            .addValue(DESCRIPTION_COLUMN, summary.description())
            .addValue(READING_TIME_COLUMN, summary.readingTime())
            .addValue(HIGHLIGHTS_COLUMN, toJsonb(summary.highlights()))
            .addValue(SOURCES_COLUMN, toJsonb(summary.sources()))
            .addValue(CONTENT_COLUMN, summary.content());
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

