package com.algorithms.templates;

public class CharFrequenciesTemplate {
    /**
     * 'a' => int value is 97
     * 'z' => int value is 122
     * 'a' -> 'z' => 122 - 97 = 26 characters array required to store lower case alphabets
     */
    private int[] lowercaseCharFrequencies(String text) {
        int[] charFrequencies = new int[26];
        for (int i = 0; i < text.length(); i++) {
            // Subtracting 'a' from the Character will make the character's int value zero based
            charFrequencies[text.charAt(i) - 'a']++;
        }
        return charFrequencies;
    }

    /**
     * 'A' => int value is 65
     * 'Z' => int value is 90
     * 'A' -> 'Z' => 90 - 65 = 26 characters array required to store upper case alphabets
     */
    private int[] uppercaseCharFrequencies(String text) {
        int[] charFrequencies = new int[26];
        for (int i = 0; i < text.length(); i++) {
            // Subtracting 'A' from the Character will make the character's int value zero based
            charFrequencies[text.charAt(i) - 'A']++;
        }
        return charFrequencies;
    }

    /**
     * '0' => int value is 48
     * '9' => int value is 57
     * '0' -> '9' => 57 - 48 => 10 characters array required to store 0-9 digits
     */
    private int[] digitsFrequencies(String text) {
        int[] charFrequencies = new int[10];
        for (int i = 0; i < text.length(); i++) {
            // Subtracting '0' from the Character will make the character's int value zero based
            charFrequencies[text.charAt(i) - '0']++;
        }
        return charFrequencies;
    }

    /**
     * Character.getNumericValue('0') = 0
     * Character.getNumericValue('9') = 9
     * Character.getNumericValue('a') = Character.getNumericValue('A') = 10
     * Character.getNumericValue('z') = Character.getNumericValue('Z') = 35
     * i.e. 36 characters array required to store 0-9 digits + [a-z][A-Z] case insensitive alphabets
     */
    private int[] caseInsensitiveAlphanumericCharFrequencies(String text) {
        int[] charFrequencies = new int[36];
        for (int i = 0; i < text.length(); i++) {
            charFrequencies[Character.getNumericValue(i)]++;
        }
        return charFrequencies;
    }

    public static void main(String[] args) {
        System.out.println();
    }
}
