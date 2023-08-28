package com.LawrenceAwe.artifact;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UtilsTest {

    @Test
    public void testToTitleCase_basic() {
        String input = "hello world";
        String expected = "Hello World";
        String actual = Utils.toTitleCase(input);
        assertEquals(expected, actual);
    }

    @Test
    public void testToTitleCase_mixedCase() {
        String input = "hELLo WOrLD";
        String expected = "Hello World";
        String actual = Utils.toTitleCase(input);
        assertEquals(expected, actual);
    }

    @Test
    public void testToTitleCase_singleWord() {
        String input = "hello";
        String expected = "Hello";
        String actual = Utils.toTitleCase(input);
        assertEquals(expected, actual);
    }

    @Test
    public void testToTitleCase_emptyString() {
        String input = "";
        String expected = "";
        String actual = Utils.toTitleCase(input);
        assertEquals(expected, actual);
    }

    @Test
    public void testToTitleCase_allUppercase() {
        String input = "HELLO";
        String expected = "Hello";
        String actual = Utils.toTitleCase(input);
        assertEquals(expected, actual);
    }

    @Test
    public void testToTitleCase_allLowercase() {
        String input = "hello";
        String expected = "Hello";
        String actual = Utils.toTitleCase(input);
        assertEquals(expected, actual);
    }

    @Test
    public void testToTitleCase_numbersAndSymbols() {
        String input = "123 hello! world";
        String expected = "123 Hello! World";
        String actual = Utils.toTitleCase(input);
        assertEquals(expected, actual);
    }

    @Test
    public void testToTitleCase_startsWithSpace() {
        String input = " hello";
        String expected = "Hello";
        String actual = Utils.toTitleCase(input);
        assertEquals(expected, actual);
    }


    @Test
    public void testToTitleCase_onlySpaces() {
        String input = "   ";
        String expected = "";
        String actual = Utils.toTitleCase(input);
        assertEquals(expected, actual);
    }

    @Test
    public void testToTitleCase_nullInput() {
        assertThrows(NullPointerException.class, () -> Utils.toTitleCase(null));
    }
}
