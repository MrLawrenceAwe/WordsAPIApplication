package hellocucumber;

import com.LawrenceAwe.artifact.WordsAPIApplicationController;
import io.cucumber.java.en.*;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserWordInputSanitizationStepDefinitions {
    private String inputWord;
    private String sanitizedWord;
    private Exception exception;

    @Given("^a null word input$")
    public void aNullWordInput() {
        inputWord = null;
    }

    @Given("^a word input \"([^\"]*)\"$")
    public void aWordInput(String word) {
        inputWord = word;
    }

    @When("^the sanitizeUserWordInput method is called$")
    public void theSanitizeUserWordInputMethodIsCalled() {
        try {
            sanitizedWord = WordsAPIApplicationController.sanitizeUserWordInput(inputWord);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("^a WordAPIException should be thrown with message \"([^\"]*)\"$")
    public void aWordAPIExceptionShouldBeThrown(String message) {
        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
    }

    @Then("^the sanitized word should be \"([^\"]*)\"$")
    public void theSanitizedWordShouldBe(String expected) {
        assertEquals(expected, sanitizedWord);
    }

    @Then("the sanitized word should not contain any of the characters in {string}")
    public void the_sanitized_word_should_not_contain_any_of_the_characters(String chars) {
        for (char c : chars.toCharArray()) {
            assertFalse(sanitizedWord.contains(String.valueOf(c)));
        }
    }

}
