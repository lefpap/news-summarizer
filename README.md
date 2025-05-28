# News Summarizer

A Spring Boot application that fetches news articles from a news provider, summarizes them using AI, and provides
structured Markdown summaries with YAML front-matter. Includes API key-based authentication and a scheduled
summarization service.

## Features

- Fetches news from a configurable news provider
- Summarizes news articles into a single Markdown document
- YAML front-matter for easy integration with static site generators
- API key authentication with configurable roles
- Scheduled summarization (configurable via cron)

## Getting Started

### Prerequisites

- Java 17 or later
- Maven 3.6+
- Docker (for running with containers)
- An API key for your chosen news provider

### NewsAPI Setup (Current Default Provider)

This project currently uses [NewsAPI.org](https://newsapi.org/) as the default news provider. NewsAPI offers a simple
HTTP REST API for searching and retrieving live articles from all over the web.

#### How to set up NewsAPI:

1. Go to [https://newsapi.org/register](https://newsapi.org/register) and create a free account.
2. After registration, you will receive an API key.
3. Set the API key as an environment variable named `NEWS_API_KEY` or configure it in your `application.yml`.

Example (Windows Command Prompt):

```cmd
set NEWS_API_KEY=your_api_key_here
```

Example (Linux/macOS):

```sh
export NEWS_API_KEY=your_api_key_here
```

You can now use the app to fetch and summarize news from NewsAPI. For more details on available endpoints and
parameters, see the [NewsAPI documentation](https://newsapi.org/docs).

### Setup

1. **Clone the repository:**
   ```sh
   git clone <your-repo-url>
   cd news-summarizer
   ```
2. **Configure environment variables:**
    - `NEWS_API_KEY`: Your news provider API key
    - `ME_SECRET`, `BLOG_SECRET`: Secrets for API key authentication (optional, see `application.yml`)

   You can set these in your environment or in a `.env` file if using a tool like [direnv](https://direnv.net/).

3. **Build the project:**
   ```sh
   ./mvnw clean package
   ```
4. **Run the application:**
   ```sh
   ./mvnw spring-boot:run
   ```
   The app will start on [http://localhost:8080](http://localhost:8080).

### API Usage

- Endpoints are protected by API key authentication. Pass your API credentials as headers:
  ```http
  X-API-ID: <your-id>
  X-API-SECRET: <your-secret>
  ```
- See the `http/` folder for example requests (compatible with [Bruno](https://www.usebruno.com/)).

### Database

- Uses Flyway for schema migrations (see `src/main/resources/db/migration/`).

## Project Structure

- `src/main/java/io/github/lefpap/news_summarizer/` — Main Java source code
- `src/main/resources/application.yml` — Main configuration
- `http/` — Example API requests

## Development

- Run tests with:
  ```sh
  ./mvnw test
  ```
- To change the summarization schedule, edit the `cron` value under `summarizer` in `application.yml`.

## License

MIT License

## Credits

- [NewsAPI.org](https://newsapi.org/) for news data (default provider)
- [Spring Boot](https://spring.io/projects/spring-boot)

---
For questions or contributions, please open an issue or pull request.
