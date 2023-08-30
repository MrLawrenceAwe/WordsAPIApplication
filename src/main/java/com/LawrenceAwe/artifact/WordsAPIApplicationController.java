package com.LawrenceAwe.artifact;

import com.LawrenceAwe.artifact.Data.WordsAPIResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
public class WordsAPIApplicationController {

    private final TemplateService templateService;
    private final WordsAPIClient wordsAPIClient;
    private final WordsAPIParser wordsAPIParser;
    private final String apiKey;

    public WordsAPIApplicationController(TemplateService templateService, WordsAPIClient wordsAPIClient, WordsAPIParser wordsAPIParser ,@Value("${RAPID_API_WORDS_API_KEY}") String apiKey) {
        this.templateService = templateService;
        this.wordsAPIClient = wordsAPIClient;
        this.wordsAPIParser = wordsAPIParser;
        this.apiKey = apiKey;
    }

    @GetMapping("/")
    public String renderHomePage() throws Exception {
        return templateService.renderTemplate("templates/index.html", new HashMap<>());
    }

    @GetMapping("/word-details/{word}")
    public String renderWordPage(@PathVariable String word) throws Exception {
        word = sanitizeUserWordInput(word);
        try {
            String response = wordsAPIClient.fetchWordDetails(word, apiKey);
            WordsAPIResponse wordDetails = wordsAPIParser.parseResponse(response);
            Map<String, Object> contextMap = createContextMapForWordPage(word, wordDetails);
            return templateService.renderTemplate("templates/words_template.html", contextMap);
        } catch (IllegalArgumentException e) {
            throw new Exception("Failed to render Word Page: " + e.getMessage(), e);
        }
    }

    private Map<String, Object> createContextMapForWordPage(String word, WordsAPIResponse wordDetails) {
        Map<String, Object> contextMap = new HashMap<>();
        String wordInTitleCase = toTitleCase(word);
        contextMap.put("word", wordInTitleCase);
        contextMap.put("results", wordDetails.getResults());
        return contextMap;
    }

    public static String toTitleCase(String string) {
        if (string == null || string.isEmpty()) {
            return string;
        }

        String[] words = string.trim().split("\\s+");
        StringBuilder stringBuilder = new StringBuilder();

        for (String word : words) {
            String[] subWords = word.split("-");
            for (int i = 0; i < subWords.length; i++) {
                String subWord = subWords[i];
                if (!subWord.isEmpty()) {
                    stringBuilder.append(Character.toUpperCase(subWord.charAt(0)))
                            .append(subWord.substring(1).toLowerCase());
                    if (i < subWords.length - 1) {
                        stringBuilder.append("-");
                    }
                }
            }
            stringBuilder.append(" ");
        }

        return stringBuilder.toString().trim();
    }

    public static String sanitizeUserWordInput(String word) {
        if (word == null) {
            throw new IllegalArgumentException("Word input should not be null");
        }

        word = word.trim();

        if (word.length() > 50) {
            word = word.substring(0, 50);
        }

        word = word.replaceAll("[<>\";/]", "");

        return word;
    }
}
