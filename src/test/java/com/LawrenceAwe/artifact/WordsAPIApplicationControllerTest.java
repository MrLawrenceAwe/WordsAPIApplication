package com.LawrenceAwe.artifact;

import com.LawrenceAwe.artifact.Data.WordsAPIResponse;
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
        WordsAPIResponse sampleParsedResponse = new WordsAPIResponse();
        Map<String, Object> contextMap = new HashMap<>();
        contextMap.put("word", "Example");
        contextMap.put("results", sampleParsedResponse.getResults());

        when(wordsAPIClient.fetchWordDetails(sampleWord, apiKey)).thenReturn("Sample API response");
        when(wordsAPIParser.parseResponse("Sample API response")).thenReturn(sampleParsedResponse);
        when(templateService.renderTemplate("templates/words_template.html", contextMap)).thenReturn("WordPage HTML");

        // When
        String result = controller.renderWordPage(sampleWord);

        // Then
        assertEquals("WordPage HTML", result);
    }

    @Test
    void testWordPageWhenExceptionThrown() throws Exception {
        // Given
        String sampleWord = "example";
        when(wordsAPIClient.fetchWordDetails(sampleWord, apiKey)).thenThrow(new IllegalArgumentException("API Error"));

        // When, Then
        assertThrows(Exception.class, () -> controller.renderWordPage(sampleWord));
    }

    @Test
    void testSanitizeUserWordInput_WithInvalidCharacters()  {
        // When
        String sanitizedWord = WordsAPIApplicationController.sanitizeUserWordInput("invalid<>word");
        // Then
        assertEquals("invalidword", sanitizedWord);
    }

    @Test
    void testSanitizeUserWordInput_WithNull() {
        // Given, When, Then
        assertThrows(IllegalArgumentException.class, () -> WordsAPIApplicationController.sanitizeUserWordInput(null));
    }

    @Test
    void testSanitizeUserWordInput_WithOverLengthWord() {
        // Given
        String longWord = "a".repeat(60);
        // When
        String result = WordsAPIApplicationController.sanitizeUserWordInput(longWord);
        // Then
        assertEquals("a".repeat(50), result);
    }

    @Test
    void testSanitizeUserWordInput_ValidWord()  {
        // Given
        String word = "example";
        // When
        String result = WordsAPIApplicationController.sanitizeUserWordInput(word);
        // Then
        assertEquals(word, result);
    }

    @ParameterizedTest
    @MethodSource("provideStringsForToTitleCaseTest")
    void testToTitleCase(String input, String expected) {
        // When
        String actual = WordsAPIApplicationController.toTitleCase(input);
        // Then
        assertEquals(expected, actual);
    }

    private static Stream<Arguments> provideStringsForToTitleCaseTest() {
        return Stream.of(
                Arguments.of("hello world", "Hello World"),
                Arguments.of("hELLo WOrLD", "Hello World"),
                Arguments.of("hello", "Hello"),
                Arguments.of("", ""),
                Arguments.of("HELLO", "Hello"),
                Arguments.of("hello", "Hello"),
                Arguments.of("123 hello! world", "123 Hello! World"),
                Arguments.of(" hello", "Hello"),
                Arguments.of("   ", ""),
                Arguments.of("hello-world  ", "Hello-World"),
                Arguments.of(null, null)
        );
    }
}
