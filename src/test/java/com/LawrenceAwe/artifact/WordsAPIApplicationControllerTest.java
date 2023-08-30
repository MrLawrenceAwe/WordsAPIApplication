package com.LawrenceAwe.artifact;

import okhttp3.OkHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WordsAPIApplicationControllerTest {

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
    public void setUp() {
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
    public void testWordPage() throws Exception {
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

    @Test
    void testToTitleCase_basic() {
        // Given
        String input = "hello world";
        String expected = "Hello World";
        // When
        String actual = WordsAPIApplicationController.toTitleCase(input);
        // Then
        assertEquals(expected, actual);
    }

    @Test
    void testToTitleCase_mixedCase() {
        // Given
        String input = "hELLo WOrLD";
        String expected = "Hello World";
        // When
        String actual = WordsAPIApplicationController.toTitleCase(input);
        // Then
        assertEquals(expected, actual);
    }

    @Test
    void testToTitleCase_singleWord() {
        // Given
        String input = "hello";
        String expected = "Hello";
        // When
        String actual = WordsAPIApplicationController.toTitleCase(input);
        // Then
        assertEquals(expected, actual);
    }

    @Test
    void testToTitleCase_emptyString() {
        // Given
        String input = "";
        String expected = "";
        // When
        String actual = WordsAPIApplicationController.toTitleCase(input);
        // Then
        assertEquals(expected, actual);
    }

    @Test
    void testToTitleCase_allUppercase() {
        // Given
        String input = "HELLO";
        String expected = "Hello";
        // When
        String actual = WordsAPIApplicationController.toTitleCase(input);
        // Then
        assertEquals(expected, actual);
    }

    @Test
    void testToTitleCase_allLowercase() {
        // Given
        String input = "hello";
        String expected = "Hello";
        // When
        String actual = WordsAPIApplicationController.toTitleCase(input);
        // Then
        assertEquals(expected, actual);
    }

    @Test
    void testToTitleCase_numbersAndSymbols() {
        // Given
        String input = "123 hello! world";
        String expected = "123 Hello! World";
        // When
        String actual = WordsAPIApplicationController.toTitleCase(input);
        // Then
        assertEquals(expected, actual);
    }

    @Test
    void testToTitleCase_startsWithSpace() {
        // Given
        String input = " hello";
        String expected = "Hello";
        // When
        String actual = WordsAPIApplicationController.toTitleCase(input);
        // Then
        assertEquals(expected, actual);
    }

    @Test
    void testToTitleCase_onlySpaces() {
        // Given
        String input = "   ";
        String expected = "";
        // When
        String actual = WordsAPIApplicationController.toTitleCase(input);
        // Then
        assertEquals(expected, actual);
    }

    @Test
    void testToTitleCase_nullInput() {
        // Given, When, Then
        assertThrows(NullPointerException.class, () -> WordsAPIApplicationController.toTitleCase(null));
    }
}
