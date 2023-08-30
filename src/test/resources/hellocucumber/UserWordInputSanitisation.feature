Feature: User Word Input Sanitization

  Scenario: Null word input
    Given a null word input
    When the sanitizeUserWordInput method is called
    Then a IllegalArgumentException should be thrown with message "Word input should not be null"

  Scenario: Trimming whitespace from word input
    Given a word input "   sample   "
    When the sanitizeUserWordInput method is called
    Then the sanitized word should be "sample"

  Scenario: Word input is longer than 50 characters
    Given a word input "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxiiii"
    When the sanitizeUserWordInput method is called
    Then the sanitized word should be "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwx"

  Scenario: Word input contains harmful characters
    Given a word input "<script>alert('hello');</script>"
    When the sanitizeUserWordInput method is called
    Then the sanitized word should not contain any of the characters in "<>/\";"

  Scenario: Word input is valid
    Given a word input "validWord"
    When the sanitizeUserWordInput method is called
    Then the sanitized word should be "validWord"