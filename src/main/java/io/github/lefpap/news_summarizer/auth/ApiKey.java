package io.github.lefpap.news_summarizer.auth;

/**
 * Represents an API key with an ID, secret, and role.
 */
public record ApiKey(
    String id,
    String secret,
    AuthRole role
) {

    /**
     * Creates an ApiKey instance with the specified ID, secret, and role.
     *
     * @param id     the API key ID
     * @param secret the API key secret
     * @param role   the role associated with the API key
     * @return a new ApiKey instance
     */
    public static ApiKey of(String id, String secret, AuthRole role) {
        return new ApiKey(id, secret, role);
    }

    /**
     * Creates an ApiKey instance with the specified ID and secret.
     *
     * @param id     the API key ID
     * @param secret the API key secret
     * @return a new ApiKey instance
     */
    public static ApiKey of(String id, String secret) {
        return new ApiKey(id, secret, null);
    }

    /**
     * Enum representing the roles for API keys.
     */
    public enum AuthRole {
        /**
         * Role with read-only access.
         */
        READ_ONLY,

        /**
         * Role with full access.
         */
        FULL_ACCESS,
    }
}
