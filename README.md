# Words API Application

A Spring Boot web application for looking up word definitions through the WordsAPI service on RapidAPI.

## Features

- Home page with word lookup flow.
- `/word-details/{word}` endpoint that fetches and renders word details.
- Input sanitisation for user-provided words.
- Template rendering with Jinjava.
- Unit and Cucumber tests for parsing, controller behaviour, and input sanitisation.
- GitHub Actions workflow for Maven verification and SonarCloud analysis.

## Tech Stack

- Java 17
- Spring Boot
- OkHttp
- Jackson
- Jinjava
- JUnit 5, Mockito, and Cucumber

## Running Locally

Set a RapidAPI key before starting the app:

```bash
export RAPID_API_WORDS_API_KEY="your-key"
mvn spring-boot:run
```

Then open:

```text
http://localhost:8080
```

## Tests

```bash
mvn test
```
