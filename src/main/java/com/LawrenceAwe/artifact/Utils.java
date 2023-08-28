package com.LawrenceAwe.artifact;

public class Utils {
    public static String toTitleCase(String string) {
        String[] arr = string.split(" ");
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : arr) {
            if (!s.isEmpty()) {
                stringBuilder.append(Character.toUpperCase(s.charAt(0)))
                        .append(s.substring(1).toLowerCase())
                        .append(" ");
            }
        }
        return stringBuilder.toString().trim();
    }
}
