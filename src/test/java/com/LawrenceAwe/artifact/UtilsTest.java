package com.LawrenceAwe.artifact;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UtilsTest {

    @Test
    public void testToTitleCase_basic() {
        // Given
        String input = "hello world";
        String expected = "Hello World";
        // When
        String actual = Utils.toTitleCase(input);
        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testToTitleCase_mixedCase() {
        // Given
        String input = "hELLo WOrLD";
        String expected = "Hello World";
        // When
        String actual = Utils.toTitleCase(input);
        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testToTitleCase_singleWord() {
        // Given
        String input = "hello";
        String expected = "Hello";
        // When
        String actual = Utils.toTitleCase(input);
        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testToTitleCase_emptyString() {
        // Given
        String input = "";
        String expected = "";
        // When
        String actual = Utils.toTitleCase(input);
        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testToTitleCase_allUppercase() {
        // Given
        String input = "HELLO";
        String expected = "Hello";
        // When
        String actual = Utils.toTitleCase(input);
        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testToTitleCase_allLowercase() {
        // Given
        String input = "hello";
        String expected = "Hello";
        // When
        String actual = Utils.toTitleCase(input);
        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testToTitleCase_numbersAndSymbols() {
        // Given
        String input = "123 hello! world";
        String expected = "123 Hello! World";
        // When
        String actual = Utils.toTitleCase(input);
        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testToTitleCase_startsWithSpace() {
        // Given
        String input = " hello";
        String expected = "Hello";
        // When
        String actual = Utils.toTitleCase(input);
        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testToTitleCase_onlySpaces() {
        // Given
        String input = "   ";
        String expected = "";
        // When
        String actual = Utils.toTitleCase(input);
        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testToTitleCase_nullInput() {
        // Given, When, Then
        assertThrows(NullPointerException.class, () -> Utils.toTitleCase(null));
    }
}
