package com.LawrenceAwe.artifact;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

class WordsAPIParserTest {

    private WordsAPIParser parser;
    private ObjectMapper mockMapper;

    @BeforeEach
    void setUp() {
        mockMapper = Mockito.mock(ObjectMapper.class);
        parser = new WordsAPIParser(mockMapper);
    }

    @Test
    void testParseResponse() throws Exception {
        // Given
        String responseBody = "some fake response body";
        WordsAPIResponse expectedResponse = new WordsAPIResponse();
        when(mockMapper.readValue(responseBody, WordsAPIResponse.class)).thenReturn(expectedResponse);

        // When
        WordsAPIResponse actualResponse = parser.parseResponse(responseBody);

        // Then
        assertEquals(expectedResponse, actualResponse);
    }
}
