package com.LawrenceAwe.artifact;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class WordsAPIParser {
    private final ObjectMapper mapper;

    public WordsAPIParser() {
        this.mapper = new ObjectMapper();
    }

    public WordsAPIResponse parseResponse(String responseBody) throws Exception {
        return mapper.readValue(responseBody, WordsAPIResponse.class);
    }
}
