package io.github.lefpap.news_summarizer.summary;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses text into an OutputSummary object.
 * Extracts YAML front matter and content from the input text.
 */
@Component
@Slf4j
public class OutputSummaryParser {

    private static final Pattern MARKDOWN_PATTERN = Pattern.compile("^---\\s*\\n([\\s\\S]*?)\\n---\\s*\\n([\\s\\S]*)$", Pattern.MULTILINE);
    private final ObjectMapper objectMapper;

    /**
     * Constructs an OutputSummaryParser with a YAML-enabled ObjectMapper.
     *
     * @param objectMapperBuilder the builder for creating the ObjectMapper
     */
    public OutputSummaryParser(Jackson2ObjectMapperBuilder objectMapperBuilder) {
        this.objectMapper = objectMapperBuilder
            .factory(new YAMLFactory())
            .build();
    }

    /**
     * Parses the given text into an OutputSummary object.
     *
     * @param text the input text containing YAML front matter and content
     * @return the parsed OutputSummary object
     * @throws OutputSummaryParsingException if parsing fails
     */
    public OutputSummary parse(String text) {
        try {
            Matcher m = MARKDOWN_PATTERN.matcher(text);
            if (!m.find())
                throw new OutputSummaryParsingException("No YAML frontmatter-matter on text:%n%s".formatted(text));

            String yamlText = m.group(1);
            String body = m.group(2).trim();

            Map<String, Object> frontMatter = objectMapper.readValue(
                yamlText, new TypeReference<>() {
                }
            );

            var summaryBuilder = OutputSummary.builder();
            summaryBuilder.title((String) frontMatter.get("title"));
            summaryBuilder.description((String) frontMatter.get("description"));
            summaryBuilder.readingTime((String) frontMatter.get("readingTime"));

            @SuppressWarnings("unchecked")
            List<String> hl = (List<String>) frontMatter.get("highlights");
            summaryBuilder.highlights(hl);

            @SuppressWarnings("unchecked")
            List<Map<String, String>> sources = (List<Map<String, String>>) frontMatter.get("sources");
            summaryBuilder.sources(sources.stream()
                .map(mo -> new OutputSummary.Source(mo.get("name"), mo.get("url")))
                .toList());

            summaryBuilder.content(body);
            return summaryBuilder.build();
        } catch (IOException ex) {
            throw new OutputSummaryParsingException("Error parsing YAML front-matter: %s".formatted(ex.getMessage()));
        } catch (Exception ex) {
            throw new OutputSummaryParsingException("Error parsing output summary: %s".formatted(ex.getMessage()));
        }
    }
}
