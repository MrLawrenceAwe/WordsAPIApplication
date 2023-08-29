package com.LawrenceAwe.artifact;

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
        } catch (Exception e) {
            throw new Exception("Failed to fetch data from WordsAPI: " + e.getMessage(), e);
        }
    }

    private Map<String, Object> createContextMapForWordPage(String word, WordsAPIResponse wordDetails) {
        Map<String, Object> contextMap = new HashMap<>();
        String wordInTitleCase = Utils.toTitleCase(word);
        contextMap.put("word", wordInTitleCase);
        contextMap.put("results", wordDetails.getResults());
        return contextMap;
    }

    public static String sanitizeUserWordInput(String word) throws Exception {
        if (word == null) throw new Exception("Word input is null", null);

        word = word.trim();

        if (word.length() > 50) word = word.substring(0, 50);

        word = word.replaceAll("[<>\";/]", "");

        return word;
    }
}
