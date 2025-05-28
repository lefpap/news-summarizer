package io.github.lefpap.news_summarizer.auth;

public record ApiKey(
    String id,
    String secret,
    AuthRole role
) {

    public static ApiKey of(String id, String secret, AuthRole role) {
        return new ApiKey(id, secret, role);
    }

    public static ApiKey of(String id, String secret) {
        return new ApiKey(id, secret, null);
    }

    public enum AuthRole {
        READ_ONLY,
        FULL_ACCESS,
    }
}
