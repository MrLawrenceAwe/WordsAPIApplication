package com.LawrenceAwe.artifact;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WordsAPIHandler {
    private final OkHttpClient client;
    private final ObjectMapper mapper;
    private static final String API_HOST = "wordsapiv1.p.rapidapi.com";

    public WordsAPIHandler() {
        this.client = new OkHttpClient();
        this.mapper = new ObjectMapper();
    }

    public WordsAPIResponse fetchWordDetails(String word, String apiKey) throws Exception {
        Request request = new Request.Builder()
                .url("https://wordsapiv1.p.rapidapi.com/words/" + word)
                .get()
                .addHeader("X-RapidAPI-Key", apiKey)
                .addHeader("X-RapidAPI-Host", API_HOST)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            return mapper.readValue(responseBody, WordsAPIResponse.class);
        }
    }
}

