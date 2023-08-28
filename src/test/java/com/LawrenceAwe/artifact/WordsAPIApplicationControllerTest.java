package com.LawrenceAwe.artifact;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        when(templateService.renderTemplate(anyString(), any())).thenReturn("Homepage HTML");

        String result = controller.renderHomePage();

        assertEquals("Homepage HTML", result);
    }

    @Test
    public void testWordPage() throws Exception {
        String sampleWord = "example";
        String sampleResponse = "Sample API response";
        WordsAPIResponse sampleParsedResponse = new WordsAPIResponse(); // Assume you have a way to create a sample object

        when(wordsAPIClient.fetchWordDetails(sampleWord, apiKey)).thenReturn(sampleResponse);
        when(wordsAPIParser.parseResponse(sampleResponse)).thenReturn(sampleParsedResponse);
        when(templateService.renderTemplate(anyString(), any())).thenReturn("WordPage HTML");

        String result = controller.renderWordPage(sampleWord);

        assertEquals("WordPage HTML", result);
    }

    @Test
     void testWordPageWhenExceptionThrown() throws Exception {
        String sampleWord = "example";
        when(wordsAPIClient.fetchWordDetails(sampleWord, apiKey)).thenThrow(new RuntimeException("API Error"));

        assertThrows(WordAPIException.class, () -> controller.renderWordPage(sampleWord));
    }
}
