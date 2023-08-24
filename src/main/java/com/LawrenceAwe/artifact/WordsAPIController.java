package com.LawrenceAwe.artifact;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubspot.jinjava.Jinjava;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@RestController
public class WordsAPIController {

    private static final String API_KEY = getApiKey() ;
    private static final String API_HOST = "wordsapiv1.p.rapidapi.com";

    @GetMapping("/")
    public String homepage() throws Exception {
        Jinjava jinjava = new Jinjava();
        String template = new String(Files.readAllBytes(new ClassPathResource("templates/index.html").getFile().toPath()));
        return jinjava.render(template, new HashMap<>());
    }

    @GetMapping("/word-details/{word}")
    public String getWordDetails(@PathVariable String word) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://wordsapiv1.p.rapidapi.com/words/" + word)
                .get()
                .addHeader("X-RapidAPI-Key", API_KEY)
                .addHeader("X-RapidAPI-Host", API_HOST)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();


            ObjectMapper mapper = new ObjectMapper();
            WordResponse wordDetails = mapper.readValue(responseBody, WordResponse.class);

            Jinjava jinjava = new Jinjava();
            Map<String, Object> context = new HashMap<>();
            String wordInTitleCase = toTitleCase(wordDetails.getWord());
            context.put("word", wordInTitleCase);
            context.put("results", wordDetails.getResults());

            String template = new String(Files.readAllBytes(new ClassPathResource("templates/words_template.html").getFile().toPath()));
            return jinjava.render(template, context);

        } catch (Exception e) {
            return "Failed to fetch data from WordsAPI: " + e.getMessage();
        }
    }

    public static String toTitleCase(String string) {
        String[] arr = string.split(" ");
        StringBuffer stringBuffer = new StringBuffer();

        for (String s : arr) {
            stringBuffer.append(Character.toUpperCase(s.charAt(0)))
                    .append(s.substring(1).toLowerCase())
                    .append(" ");
        }
        return stringBuffer.toString().trim();
    }

    private static String getApiKey() {
        try (InputStream inputStream = WordsAPIController.class.getResourceAsStream("/API_KEYS");
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            return bufferedReader.readLine().trim();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load API key from resource file", e);
        }
    }
}



