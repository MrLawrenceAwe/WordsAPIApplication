package com.LawrenceAwe.artifact;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WordsAPIResponse {
    private List<WordDefinitionData> results;

    public List<WordDefinitionData> getResults() {
        return results;
    }

    public void setResults(List<WordDefinitionData> results) {
        this.results = results;
    }
}

