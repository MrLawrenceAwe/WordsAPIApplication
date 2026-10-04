# Words API Application

[![CI](https://github.com/MrLawrenceAwe/WordsAPIApplication/actions/workflows/build.yml/badge.svg)](https://github.com/MrLawrenceAwe/WordsAPIApplication/actions/workflows/build.yml)

A Spring Boot web application for looking up word definitions through the WordsAPI service on RapidAPI.

## Features

- Home page with word lookup flow.
- `/word-details/{word}` endpoint that fetches and renders word details.
- Input sanitisation for user-provided words.
- Template rendering with Jinjava.
- Unit and Cucumber tests for parsing, controller behaviour, and input sanitisation.
- GitHub Actions CI for Maven verification; optional SonarCloud analysis is available manually.

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

## Continuous integration

Every push and pull request runs `mvn -B verify` with Java 17. SonarCloud is an optional, manually dispatched workflow that requires a configured `SONAR_TOKEN`; external analysis is separate from build/test verification.
