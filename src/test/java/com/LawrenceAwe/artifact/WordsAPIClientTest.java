package com.LawrenceAwe.artifact;

import okhttp3.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class WordsAPIClientTest {

    private WordsAPIClient client;

    @Mock
    private OkHttpClient mockHttpClient;

    @Mock
    private Call mockCall;

    @Mock
    private Response mockResponse;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        client = new WordsAPIClient(mockHttpClient);
    }

    @Test
    public void testFetchWordDetails_Successful() throws Exception {
        String expectedResponse = "{\"word\":\"example\"}";
        when(mockHttpClient.newCall((Request) any())).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);
        when(mockResponse.body()).thenReturn(ResponseBody.create(expectedResponse, MediaType.get("application/json")));

        String result = client.fetchWordDetails("example", "testApiKey");

        assertEquals(expectedResponse, result);
    }

    @Test
    public void testFetchWordDetails_ExceptionThrown() throws Exception {
        when(mockHttpClient.newCall((Request) any())).thenReturn(mockCall);
        when(mockCall.execute()).thenThrow(new IOException("Failed to fetch word"));

        assertThrows(Exception.class, () -> {
            client.fetchWordDetails("example", "testApiKey");
        });
    }

    @Test
    public void testGetWordDetailsURL() {
        String word = "example";
        String expectedURL = "https://wordsapiv1.p.rapidapi.com/words/example";

        assertEquals(expectedURL, WordsAPIClient.getWordDetailsURL(word));
    }

    @Test
    public void testOkHttpClient() {
        OkHttpClient okHttpClient = client.okHttpClient();
        assertNotNull(okHttpClient);
    }
}