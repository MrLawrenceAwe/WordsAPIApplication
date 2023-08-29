package com.LawrenceAwe.artifact;

import okhttp3.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class WordsAPIClientTest {

    @Mock
    private OkHttpClient client;

    @Mock
    private Call call;

    @Mock
    private Response response;

    @Mock
    private ResponseBody responseBody;

    @InjectMocks
    private WordsAPIClient wordsAPIClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void fetchWordDetails_returnsCorrectWordDetails() throws Exception {
        // Given
        String word = "example";
        String apiKey = "testApiKey";
        String expectedResponse = "{\"word\":\"example\",\"definition\":\"a representative form or pattern\"}";
        when(client.newCall(any(Request.class))).thenReturn(call);
        when(call.execute()).thenReturn(response);
        when(response.body()).thenReturn(responseBody);
        when(responseBody.string()).thenReturn(expectedResponse);

        // When
        String result = wordsAPIClient.fetchWordDetails(word, apiKey);

        // Then
        assertEquals(expectedResponse, result);
        verify(client).newCall(any(Request.class));
    }
}