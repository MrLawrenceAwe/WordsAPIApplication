package com.LawrenceAwe.artifact;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

@Service
public class WordsAPIClient {
    private final OkHttpClient client;
    private static final String API_HOST = "wordsapiv1.p.rapidapi.com";

    public WordsAPIClient() {
        this.client = new OkHttpClient();
    }

    public String fetchWordDetails(String word, String apiKey) throws Exception {
        Request request = new Request.Builder()
                .url("https://wordsapiv1.p.rapidapi.com/words/" + word)
                .get()
                .addHeader("X-RapidAPI-Key", apiKey)
                .addHeader("X-RapidAPI-Host", API_HOST)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
