package com.LawrenceAwe.artifact;

import com.LawrenceAwe.artifact.Data.WordsAPIResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class WordsAPIParser {
    private final ObjectMapper mapper;

    public WordsAPIParser(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public WordsAPIResponse parseResponse(String rawResponseBody) throws Exception {
        return mapper.readValue(rawResponseBody, WordsAPIResponse.class);
    }
}
