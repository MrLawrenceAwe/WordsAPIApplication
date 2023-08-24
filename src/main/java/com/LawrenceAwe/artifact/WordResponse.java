package com.LawrenceAwe.artifact;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WordResponse {
    private String word;
    private List<WordDetails> results;

    public String getWord() {
        return word;
    }

    public List<WordDetails> getResults() {
        return results;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setResults(List<WordDetails> results) {
        this.results = results;
    }
}

