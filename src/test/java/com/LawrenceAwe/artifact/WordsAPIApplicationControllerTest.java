package com.LawrenceAwe.artifact;

import okhttp3.OkHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WordsAPIApplicationControllerTest {

    @Mock
    private WordsAPIApplicationController controller;

    @Mock
    private TemplateService templateService;

    @Mock
    private WordsAPIClient wordsAPIClient;

    @Mock
    private WordsAPIParser wordsAPIParser;

    private final String apiKey = "sampleApiKey";

    @BeforeEach
    void setUp() {
        controller = new WordsAPIApplicationController(templateService, wordsAPIClient, wordsAPIParser, apiKey);
    }

    @Test
    void testHomepage() throws Exception {
        // Given
        when(templateService.renderTemplate("templates/index.html", new HashMap<>())).thenReturn("Homepage HTML");

        // When
        String result = controller.renderHomePage();

        // Then
        assertEquals("Homepage HTML", result);
    }

    @Test
    void testWordPage() throws Exception {
        // Given
        String sampleWord = "example";
        String sanitizedWord = WordsAPIApplicationController.sanitizeUserWordInput(sampleWord);
        String sampleResponse = "Sample API response";
        WordsAPIResponse sampleParsedResponse = new WordsAPIResponse();

        when(wordsAPIClient.fetchWordDetails(sanitizedWord, apiKey)).thenReturn(sampleResponse);
        when(wordsAPIParser.parseResponse(sampleResponse)).thenReturn(sampleParsedResponse);
        when(templateService.renderTemplate(anyString(), anyMap())).thenReturn("WordPage HTML");

        // When
        String result = controller.renderWordPage(sampleWord);

        // Then
        assertEquals("WordPage HTML", result);
    }

    @Test
    void testWordPageWhenExceptionThrown() throws Exception {
        // Given
        String sampleWord = "example";
        when(wordsAPIClient.fetchWordDetails(sampleWord, apiKey)).thenThrow(new RuntimeException("API Error"));

        // When, Then
        assertThrows(Exception.class, () -> controller.renderWordPage(sampleWord));
    }

    @Test
    void testSanitizeUserWordInput_WithInvalidCharacters() throws Exception {
        // When
        String sanitizedWord = WordsAPIApplicationController.sanitizeUserWordInput("invalid<>word");
        // Then
        assertEquals("invalidword", sanitizedWord);
    }

    @Test
    void testSanitizeUserWordInput_WithNull() {
        // Given, When, Then
        assertThrows(Exception.class, () -> WordsAPIApplicationController.sanitizeUserWordInput(null));
    }

    @Test
    void testSanitizeUserWordInput_WithOverLengthWord() throws Exception {
        // Given
        String longWord = "a".repeat(60);
        // When
        String result = WordsAPIApplicationController.sanitizeUserWordInput(longWord);
        // Then
        assertEquals("a".repeat(50), result);
    }

    @Test
    void testSanitizeUserWordInput_ValidWord() throws Exception {
        // Given
        String word = "example";
        // When
        String result = WordsAPIApplicationController.sanitizeUserWordInput(word);
        // Then
        assertEquals(word, result);
    }

    @ParameterizedTest
    @MethodSource("provideStringsForToTitleCase")
    void testToTitleCase(String input, String expected) {
        // When
        String actual = WordsAPIApplicationController.toTitleCase(input);
        // Then
        assertEquals(expected, actual);
    }

    private static Stream<Arguments> provideStringsForToTitleCase() {
        return Stream.of(
                Arguments.of("hello world", "Hello World"),
                Arguments.of("hELLo WOrLD", "Hello World"),
                Arguments.of("hello", "Hello"),
                Arguments.of("", ""),
                Arguments.of("HELLO", "Hello"),
                Arguments.of("hello", "Hello"),
                Arguments.of("123 hello! world", "123 Hello! World"),
                Arguments.of(" hello", "Hello"),
                Arguments.of("   ", "")
        );
    }
}
