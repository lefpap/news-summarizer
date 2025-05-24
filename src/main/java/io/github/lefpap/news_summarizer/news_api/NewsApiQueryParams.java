package io.github.lefpap.news_summarizer.news_api;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

public record NewsApiQueryParams(

    @NotBlank(message = "`q` (the search query) is mandatory")
    @Size(max = 500)
    String q,

    @Size(max = 3)
    List<SearchIn> searchIn,

    @Size(max = 20)
    List<@NotBlank String> sources,

    List<@NotBlank String> domains,
    List<@NotBlank String> excludeDomains,

    @PastOrPresent LocalDate from,
    @PastOrPresent LocalDate to,

    @Pattern(regexp = "^[a-z]{2}(,[a-z]{2})*$",
        message = "Language must be 2-letter ISO codes separated by commas")
    String language,

    SortBy sortBy,

    @Min(1) @Max(100) Integer pageSize,
    @Min(1) Integer page
) {

    private static final String Q = "q";
    private static final String SEARCH_IN = "searchIn";
    private static final String SOURCES = "sources";
    private static final String DOMAINS = "domains";
    private static final String EXCLUDE_DOMAINS = "excludeDomains";
    private static final String FROM = "from";
    private static final String TO = "to";
    private static final String LANGUAGE = "language";
    private static final String SORT_BY = "sortBy";
    private static final String PAGE_SIZE = "pageSize";
    private static final String PAGE = "page";

    public static Builder builder() {   /* mandatory arg */
        return new Builder();
    }

    public Builder toBuilder() {
        Builder builder = new Builder();
        builder.q(q);
        builder.searchIn(searchIn);
        builder.sources(sources);
        builder.domains(domains);
        builder.excludeDomains(excludeDomains);
        builder.from(from);
        builder.to(to);
        builder.language(language);
        builder.sortBy(sortBy);
        builder.pageSize(pageSize);
        builder.page(page);
        return builder;
    }

    public Map<String, String> toMap() {
        var map = new HashMap<String, String>();

        map.put(Q, q);

        Optional.ofNullable(this.searchIn)
            .filter(not(List::isEmpty))
            .map(list -> list.stream()
                .map(SearchIn::value)
                .collect(Collectors.joining(",")))
            .ifPresent(csv -> map.put(SEARCH_IN, csv));

        Optional.ofNullable(this.sources)
            .filter(not(List::isEmpty))
            .map(list -> String.join(",", list))
            .ifPresent(csv -> map.put(SOURCES, csv));

        Optional.ofNullable(this.domains)
            .filter(not(List::isEmpty))
            .map(list -> String.join(",", list))
            .ifPresent(csv -> map.put(DOMAINS, csv));

        Optional.ofNullable(this.excludeDomains)
            .filter(not(List::isEmpty))
            .map(list -> String.join(",", list))
            .ifPresent(csv -> map.put(EXCLUDE_DOMAINS, csv));

        Optional.ofNullable(from)
            .map(d -> d.format(DateTimeFormatter.ISO_DATE))
            .ifPresent(fmtd -> map.put(FROM, fmtd));

        Optional.ofNullable(to)
            .map(t -> t.format(DateTimeFormatter.ISO_DATE))
            .ifPresent(s -> map.put(TO, s));

        Optional.ofNullable(language)
            .map(String::trim)
            .ifPresent(s -> map.put(LANGUAGE, s));

        Optional.ofNullable(sortBy)
            .map(SortBy::value)
            .ifPresent(s -> map.put(SORT_BY, s));

        Optional.ofNullable(pageSize)
            .map(String::valueOf)
            .ifPresent(s -> map.put(PAGE_SIZE, s));

        Optional.ofNullable(page)
            .map(String::valueOf)
            .ifPresent(s -> map.put(PAGE, s));

        return map;
    }

    public static NewsApiQueryParams of(Map<String, String> map) {
        Builder builder = new Builder();

        builder.q(map.get(Q));

        builder.searchIn(Optional.ofNullable(map.get(SEARCH_IN))
            .filter(not(String::isBlank))
            .map(String::trim)
            .map(s -> s.split(","))
            .stream()
            .flatMap(Arrays::stream)
            .map(SearchIn::of)
            .toList());

        builder.sources(Optional.ofNullable(map.get(SOURCES))
            .filter(not(String::isBlank))
            .map(String::trim)
            .map(s -> s.split(","))
            .stream()
            .flatMap(Arrays::stream)
            .toList());

        builder.domains(Optional.ofNullable(map.get(DOMAINS))
            .filter(not(String::isBlank))
            .map(String::trim)
            .map(s -> s.split(","))
            .stream()
            .flatMap(Arrays::stream)
            .toList());

        builder.excludeDomains(Optional.ofNullable(map.get(EXCLUDE_DOMAINS))
            .filter(not(String::isBlank))
            .map(String::trim)
            .map(s -> s.split(","))
            .stream()
            .flatMap(Arrays::stream)
            .toList());

        Optional.ofNullable(map.get(FROM))
            .map(LocalDate::parse)
            .ifPresent(builder::from);

        Optional.ofNullable(map.get(TO))
            .map(LocalDate::parse)
            .ifPresent(builder::to);

        Optional.ofNullable(map.get(LANGUAGE))
            .map(String::trim)
            .filter(not(String::isBlank))
            .ifPresent(builder::language);

        Optional.ofNullable(map.get(SORT_BY))
            .map(SortBy::of)
            .ifPresent(builder::sortBy);

        Optional.ofNullable(map.get(PAGE_SIZE))
            .filter(not(String::isBlank))
            .map(String::trim)
            .map(Integer::valueOf)
            .ifPresent(builder::pageSize);

        Optional.ofNullable(map.get(PAGE))
            .filter(not(String::isBlank))
            .map(String::trim)
            .map(Integer::valueOf)
            .ifPresent(builder::page);

        return builder.build();
    }

    public enum SearchIn {
        TITLE("title"), DESCRIPTION("description"), CONTENT("content");
        final String value;

        SearchIn(String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }

        public static SearchIn of(String value) {
            if (value == null || value.isBlank()) {
                throw new IllegalArgumentException("Search in value cannot be null or blank");
            }

            String trimmedValue = value.trim();
            return Arrays.stream(SearchIn.values())
                .filter(s -> s.value.equalsIgnoreCase(trimmedValue))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid search in value: " + trimmedValue));
        }

    }

    public enum SortBy {
        RELEVANCY("relevancy"), POPULARITY("popularity"), PUBLISHED_AT("publishedAt");
        final String value;

        SortBy(String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }

        public static SortBy of(String value) {
            if (value == null || value.isBlank()) {
                throw new IllegalArgumentException("Sort by value cannot be null or blank");
            }

            String trimmedValue = value.trim();
            return Arrays.stream(SortBy.values())
                .filter(s -> s.value.equalsIgnoreCase(trimmedValue))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid sort by value: " + trimmedValue));
        }
    }

    public static final class Builder {
        private String q;                     // mandatory
        private List<SearchIn> searchIn = List.of();
        private List<String> sources = List.of();
        private List<String> domains = List.of();
        private List<String> excludeDomains = List.of();
        private LocalDate from;
        private LocalDate to;
        private String language;
        private SortBy sortBy;
        private Integer pageSize;
        private Integer page;

        public Builder q(String q) {
            this.q = q;
            return this;
        }

        public Builder searchIn(List<SearchIn> fields) {
            this.searchIn = List.copyOf(fields);
            return this;
        }

        public Builder searchIn(SearchIn... fields) {
            this.searchIn = List.copyOf(Arrays.asList(fields));
            return this;
        }

        public Builder sources(List<String> s) {
            this.sources = List.copyOf(s);
            return this;
        }

        public Builder sources(String... s) {
            this.sources = List.copyOf(Arrays.asList(s));
            return this;
        }

        public Builder domains(List<String> d) {
            this.domains = List.copyOf(d);
            return this;
        }

        public Builder domains(String... d) {
            this.domains = List.copyOf(Arrays.asList(d));
            return this;
        }

        public Builder excludeDomains(List<String> d) {
            this.excludeDomains = List.copyOf(d);
            return this;
        }

        public Builder excludeDomains(String... d) {
            this.excludeDomains = List.copyOf(Arrays.asList(d));
            return this;
        }

        public Builder from(LocalDate date) {
            this.from = date;
            return this;
        }

        public Builder to(LocalDate date) {
            this.to = date;
            return this;
        }

        public Builder language(String lang) {
            this.language = lang;
            return this;
        }

        public Builder sortBy(SortBy s) {
            this.sortBy = s;
            return this;
        }

        public Builder pageSize(int ps) {
            this.pageSize = ps;
            return this;
        }

        public Builder page(int p) {
            this.page = p;
            return this;
        }

        public NewsApiQueryParams build() {
            return new NewsApiQueryParams(
                q,
                searchIn,
                sources,
                domains,
                excludeDomains,
                from,
                to,
                language,
                sortBy,
                pageSize,
                page
            );
        }
    }
}
