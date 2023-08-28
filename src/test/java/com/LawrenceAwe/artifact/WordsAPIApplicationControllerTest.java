package com.LawrenceAwe.artifact;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashMap;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class WordsAPIApplicationControllerTest {

    private WordsAPIApplicationController controller;

    // Mocking our dependencies
    @MockBean
    private TemplateService mockTemplateService;

    @MockBean
    private WordsAPIHandler mockWordsApiHandler;

    private static final String API_KEY = "test-api-key";

    @BeforeEach
    public void setup() {
        controller = new WordsAPIApplicationController(mockTemplateService, mockWordsApiHandler, API_KEY);
    }

    @Test
    public void testHomepage() throws Exception {
        // Given
        given(mockTemplateService.renderTemplate("templates/index.html", new HashMap<>())).willReturn("homepage");

        // When
        String response = controller.homepage();

        // Then
        assertThat(response).isEqualTo("homepage");
    }

    @Test
    public void testWordPage() throws Exception {
        String word = "example";
        WordsAPIResponse mockResponse = new WordsAPIResponse();
        // populate mockResponse with desired test data

        given(mockWordsApiHandler.fetchWordDetails(word, API_KEY)).willReturn(mockResponse);
        given(mockTemplateService.renderTemplate("templates/words_template.html", new HashMap<>())).willReturn("wordPage");

        // When
        String response = controller.wordPage(word);

        // Then
        assertThat(response).isEqualTo("wordPage");
    }

    @Test
    public void testWordPageException() throws Exception {
        String word = "example";

        given(mockWordsApiHandler.fetchWordDetails(word, API_KEY)).willThrow(new RuntimeException("Test Exception"));

        // When
        String response = controller.wordPage(word);

        // Then
        assertThat(response).contains("Failed to fetch data from WordsAPI: Test Exception");
    }
}