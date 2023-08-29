package com.LawrenceAwe.artifact;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Service
public class WordsAPIClient {
    private final OkHttpClient client;
    private static final String API_HOST = "wordsapiv1.p.rapidapi.com";

    @Autowired
    public WordsAPIClient(OkHttpClient client) {
        this.client = client;
    }

    public String fetchWordDetails(String word, String apiKey) throws Exception {
        Request request = createRequest(word, apiKey);

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    private static Request createRequest(String word, String apiKey) {
        return new Request.Builder()
                .url(getWordDetailsURL(word))
                .get()
                .addHeader("X-RapidAPI-Key", apiKey)
                .addHeader("X-RapidAPI-Host", API_HOST)
                .build();
    }

    private static String getWordDetailsURL(String word) {
        return "https://wordsapiv1.p.rapidapi.com/words/" + word;
    }

    @Configuration
    public static class OkHttpClientConfig {

        @Bean
        public OkHttpClient okHttpClient() {
            return new OkHttpClient();
        }
    }
}

