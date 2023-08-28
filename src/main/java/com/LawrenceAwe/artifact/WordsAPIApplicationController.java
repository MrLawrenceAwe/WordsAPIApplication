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

    // Injecting the dependencies using the constructor
    public WordsAPIApplicationController(TemplateService templateService, WordsAPIClient wordsAPIClient, WordsAPIParser wordsAPIParser ,@Value("${RAPID_API_WORDS_API_KEY}") String apiKey) {
        this.templateService = templateService;
        this.wordsAPIClient = wordsAPIClient;
        this.wordsAPIParser = wordsAPIParser;
        this.apiKey = apiKey;
    }

    @GetMapping("/")
    public String homepage() throws Exception {
        return templateService.renderTemplate("templates/index.html", new HashMap<>());
    }

    @GetMapping("/word-details/{word}")
    public String wordPage(@PathVariable String word) {
        try {
            String response = wordsAPIClient.fetchWordDetails(word, apiKey);
            WordsAPIResponse wordDetails = wordsAPIParser.parseResponse(response);

            Map<String, Object> contextMap = new HashMap<>();
            String wordInTitleCase = Utils.toTitleCase(word);
            contextMap.put("word", wordInTitleCase);
            contextMap.put("results", wordDetails.getResults());

            return templateService.renderTemplate("templates/words_template.html", contextMap);
        } catch (Exception e) {
            return "Failed to fetch data from WordsAPI: " + e.getMessage();
        }
    }
}