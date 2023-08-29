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
        when(templateService.renderTemplate("templates/index.html", new HashMap<>())).thenReturn("Homepage HTML");

        String result = controller.renderHomePage();

        assertEquals("Homepage HTML", result);
    }

    @Test
    public void testWordPage() throws Exception {
        String sampleWord = "example";
        String sanitizedWord = WordsAPIApplicationController.sanitizeUserWordInput(sampleWord);
        String sampleResponse = "Sample API response";
        WordsAPIResponse sampleParsedResponse = new WordsAPIResponse();

        when(wordsAPIClient.fetchWordDetails(sanitizedWord, apiKey)).thenReturn(sampleResponse);
        when(wordsAPIParser.parseResponse(sampleResponse)).thenReturn(sampleParsedResponse);

        when(templateService.renderTemplate(anyString(), anyMap())).thenReturn("WordPage HTML");

        String result = controller.renderWordPage(sampleWord);

        assertEquals("WordPage HTML", result);
    }

    @Test
    void testWordPageWhenExceptionThrown() throws Exception {
        String sampleWord = "example";
        when(wordsAPIClient.fetchWordDetails(sampleWord, apiKey)).thenThrow(new RuntimeException("API Error"));

        assertThrows(WordAPIException.class, () -> controller.renderWordPage(sampleWord));
    }

    @Test
    void testSanitizeUserWordInput_WithInvalidCharacters() {
        String sanitizedWord = WordsAPIApplicationController.sanitizeUserWordInput("invalid<>word");
        assertEquals("invalidword", sanitizedWord);
    }

    @Test
    void testSanitizeUserWordInput_WithNull() {
        assertThrows(WordAPIException.class, () -> WordsAPIApplicationController.sanitizeUserWordInput(null));
    }

    @Test
    void testSanitizeUserWordInput_WithOverLengthWord() {
        String longWord = "a".repeat(60);
        String result = WordsAPIApplicationController.sanitizeUserWordInput(longWord);
        assertEquals("a".repeat(50), result);
    }

    @Test
    void testSanitizeUserWordInput_ValidWord() {
        String word = "example";
        String result = WordsAPIApplicationController.sanitizeUserWordInput(word);
        assertEquals(word, result);
    }
}
