package com.LawrenceAwe.artifact;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
public class WordsAPIApplicationController {

    private final TemplateService templateService;
    private final WordsAPIHandler wordsApiHandler;
    private final ConfigurationService configurationService;

    public WordsAPIApplicationController() {
        this.templateService = new TemplateService();
        this.wordsApiHandler = new WordsAPIHandler();
        this.configurationService = new ConfigurationService();
    }

    @GetMapping("/")
    public String homepage() throws Exception {
        return templateService.renderTemplate("templates/index.html", new HashMap<>());
    }

    @GetMapping("/word-details/{word}")
    public String getWordDetails(@PathVariable String word) {
        try {
            WordsAPIResponse wordDetails = wordsApiHandler.fetchWordDetails(word, configurationService.getApiKey());

            Map<String, Object> context = new HashMap<>();
            String wordInTitleCase = Utils.toTitleCase(word);
            context.put("word", wordInTitleCase);
            context.put("results", wordDetails.getResults());

            return templateService.renderTemplate("templates/words_template.html", context);
        } catch (Exception e) {
            return "Failed to fetch data from WordsAPI: " + e.getMessage();
        }
    }
}