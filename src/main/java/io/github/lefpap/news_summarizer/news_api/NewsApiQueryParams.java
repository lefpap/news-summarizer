package io.github.lefpap.news_summarizer.news_api;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record NewsApiQueryParams(
    String q,
    SearchIn searchIn,
    List<String> sources,
    List<String> domains,
    List<String> excludeDomains,
    LocalDateTime from,
    LocalDateTime to,
    String language,
    SortBy sortBy,
    Integer pageSize,
    Integer page
) {

    public static final String Q = "q";
    public static final String SEARCH_IN = "searchIn";
    public static final String SOURCES = "sources";
    public static final String DOMAINS = "domains";
    public static final String EXCLUDE_DOMAINS = "excludeDomains";
    public static final String FROM = "from";
    public static final String TO = "to";
    public static final String LANGUAGE = "language";
    public static final String SORT_BY = "sortBy";
    public static final String PAGE_SIZE = "pageSize";
    public static final String PAGE = "page";

    public NewsApiQueryParams {
        if (q == null || q.isBlank()) {
            throw new IllegalArgumentException("Query parameter 'q' cannot be null or empty");
        }

        if (pageSize != null && pageSize < 1) {
            throw new IllegalArgumentException("Page size must be greater than 0");
        }

        if (page != null && page < 1) {
            throw new IllegalArgumentException("Page must be greater than 0");
        }
    }

    public Map<String, String> toMap() {
        Map<String, String> params = new HashMap<>();
        params.put(Q, q);

        if (searchIn != null) {
            params.put(SEARCH_IN, searchIn.value());
        }

        if (sources != null && !sources.isEmpty()) {
            params.put(SOURCES, String.join(",", sources));
        }

        if (domains != null && !domains.isEmpty()) {
            params.put(DOMAINS, String.join(",", domains));
        }

        if (excludeDomains != null && !excludeDomains.isEmpty()) {
            params.put(EXCLUDE_DOMAINS, String.join(",", excludeDomains));
        }

        if (from != null) {
            params.put(FROM, from.toString());
        }

        if (to != null) {
            params.put(TO, to.toString());
        }

        if (language != null) {
            params.put(LANGUAGE, language);
        }

        if (sortBy != null) {
            params.put(SORT_BY, sortBy.value());
        }

        if (pageSize != null) {
            params.put(PAGE_SIZE, String.valueOf(pageSize));
        }

        if (page != null) {
            params.put(PAGE, String.valueOf(page));
        }

        return params;
    }

    public static NewsApiQueryParams fromMap(Map<String, String> map) {
        String q = map.get(Q);
        SearchIn searchIn = map.containsKey(SEARCH_IN) ? SearchIn.valueOf(map.get(SEARCH_IN).toUpperCase()) : null;
        List<String> sources = map.containsKey(SOURCES) ? List.of(map.get(SOURCES).split(",")) : List.of();
        List<String> domains = map.containsKey(DOMAINS) ? List.of(map.get(DOMAINS).split(",")) : List.of();
        List<String> excludeDomains = map.containsKey(EXCLUDE_DOMAINS) ? List.of(map.get(EXCLUDE_DOMAINS).split(",")) : List.of();
        LocalDateTime from = map.containsKey(FROM) ? LocalDateTime.parse(map.get(FROM)) : null;
        LocalDateTime to = map.containsKey(TO) ? LocalDateTime.parse(map.get(TO)) : null;
        String language = map.get(LANGUAGE);
        SortBy sortBy = map.containsKey(SORT_BY) ? SortBy.valueOf(map.get(SORT_BY).toUpperCase()) : null;
        Integer pageSize = map.containsKey(PAGE_SIZE) ? Integer.valueOf(map.get(PAGE_SIZE)) : null;
        Integer page = map.containsKey(PAGE) ? Integer.valueOf(map.get(PAGE)) : null;
        return new NewsApiQueryParams(q, searchIn, sources, domains, excludeDomains, from, to, language, sortBy, pageSize, page);
    }

    public static NewsApiQueryParamsBuilder builder() {
        return new NewsApiQueryParamsBuilder();
    }

    public NewsApiQueryParamsBuilder toBuilder() {
        return new NewsApiQueryParamsBuilder()
            .q(q)
            .searchIn(searchIn)
            .sources(sources)
            .domains(domains)
            .excludeDomains(excludeDomains)
            .from(from)
            .to(to)
            .language(language)
            .sortBy(sortBy)
            .pageSize(pageSize)
            .page(page);
    }

    public enum SearchIn {
        TITLE("title"),
        DESCRIPTION("description"),
        CONTENT("content");

        private final String value;

        SearchIn(String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }
    }

    public enum SortBy {
        RELEVANCY("relevancy"),
        POPULARITY("popularity"),
        PUBLISHED_AT("publishedAt");

        private final String value;

        SortBy(String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }
    }

    public static class NewsApiQueryParamsBuilder {
        private String q;
        private SearchIn searchIn;
        private List<String> sources = new ArrayList<>();
        private List<String> domains = new ArrayList<>();
        private List<String> excludeDomains = new ArrayList<>();
        private LocalDateTime from;
        private LocalDateTime to;
        private String language;
        private SortBy sortBy;
        private Integer pageSize;
        private Integer page;

        public NewsApiQueryParamsBuilder q(String q) {
            this.q = q;
            return this;
        }

        public NewsApiQueryParamsBuilder searchIn(SearchIn searchIn) {
            this.searchIn = searchIn;
            return this;
        }

        public NewsApiQueryParamsBuilder sources(List<String> sources) {
            this.sources = List.copyOf(sources);
            return this;
        }

        public NewsApiQueryParamsBuilder sources(String... sources) {
            this.sources = List.of(sources);
            return this;
        }

        public NewsApiQueryParamsBuilder addSource(String source) {
            this.sources.add(source);
            return this;
        }

        public NewsApiQueryParamsBuilder domains(List<String> domains) {
            this.domains = List.copyOf(domains);
            return this;
        }

        public NewsApiQueryParamsBuilder domains(String... domains) {
            this.domains = List.of(domains);
            return this;
        }

        public NewsApiQueryParamsBuilder addDomain(String domain) {
            this.domains.add(domain);
            return this;
        }

        public NewsApiQueryParamsBuilder excludeDomains(List<String> excludeDomains) {
            this.excludeDomains = List.copyOf(excludeDomains);
            return this;
        }

        public NewsApiQueryParamsBuilder excludeDomains(String... excludeDomains) {
            this.excludeDomains = List.of(excludeDomains);
            return this;
        }

        public NewsApiQueryParamsBuilder addExcludeDomain(String excludeDomain) {
            this.excludeDomains.add(excludeDomain);
            return this;
        }

        public NewsApiQueryParamsBuilder from(LocalDateTime from) {
            this.from = from;
            return this;
        }

        public NewsApiQueryParamsBuilder to(LocalDateTime to) {
            this.to = to;
            return this;
        }

        public NewsApiQueryParamsBuilder language(String language) {
            this.language = language;
            return this;
        }

        public NewsApiQueryParamsBuilder sortBy(SortBy sortBy) {
            this.sortBy = sortBy;
            return this;
        }

        public NewsApiQueryParamsBuilder pageSize(Integer pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public NewsApiQueryParamsBuilder page(Integer page) {
            this.page = page;
            return this;
        }

        public NewsApiQueryParams build() {
            return new NewsApiQueryParams(q, searchIn, sources, domains, excludeDomains, from, to, language, sortBy, pageSize, page);
        }
    }
}
