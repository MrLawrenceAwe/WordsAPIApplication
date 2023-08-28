package com.LawrenceAwe.artifact;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

public class WordsAPIParserTest {

    private WordsAPIParser parser;
    private ObjectMapper mockMapper;

    @BeforeEach
    public void setUp() {
        mockMapper = Mockito.mock(ObjectMapper.class);
        parser = new WordsAPIParser(mockMapper);
    }

    @Test
    public void testParseResponse() throws Exception {
        String responseBody = "some fake response body";
        WordsAPIResponse expectedResponse = new WordsAPIResponse();
        when(mockMapper.readValue(responseBody, WordsAPIResponse.class)).thenReturn(expectedResponse);

        WordsAPIResponse actualResponse = parser.parseResponse(responseBody);

        assertEquals(expectedResponse, actualResponse);
    }
}
