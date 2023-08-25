package com.LawrenceAwe.artifact;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ConfigurationService {
    public String getApiKey() {
        try (InputStream inputStream = WordsAPIApplicationController.class.getResourceAsStream("/API_KEYS");
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            return bufferedReader.readLine().trim();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load API key from resource file", e);
        }
    }
}