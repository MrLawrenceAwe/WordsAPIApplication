package com.LawrenceAwe.artifact;

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
        assertThrows(WordAPIException.class, () -> controller.renderWordPage(sampleWord));
    }

    @Test
    void testSanitizeUserWordInput_WithInvalidCharacters() {
        // When
        String sanitizedWord = WordsAPIApplicationController.sanitizeUserWordInput("invalid<>word");
        // Then
        assertEquals("invalidword", sanitizedWord);
    }

    @Test
    void testSanitizeUserWordInput_WithNull() {
        // Given, When, Then
        assertThrows(WordAPIException.class, () -> WordsAPIApplicationController.sanitizeUserWordInput(null));
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
    void testSanitizeUserWordInput_ValidWord() {
        // Given
        String word = "example";
        // When
        String result = WordsAPIApplicationController.sanitizeUserWordInput(word);
        // Then
        assertEquals(word, result);
    }
}
