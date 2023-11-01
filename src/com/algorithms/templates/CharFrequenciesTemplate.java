package com.algorithms.templates;

import java.util.Arrays;

public class CharFrequenciesTemplate {
    private static final int NO_OF_DIGITS = 10;
    private static final int NO_OF_CHARS = 256;
    private static final int NO_OF_ALPHABETS = 26;
    private static final int NO_OF_ASCII_CHARS = 128;
    private static final int NO_OF_ALPHANUMERICS = 36;

    /**
     * 'a' => int value is 97
     * 'z' => int value is 122
     * 'a' -> 'z' => 122 - 97 = 26 characters array required to store lower case alphabets
     */
    private int[] lowercaseCharFrequencies(final String text) {
        final int[] charFrequencies = new int[NO_OF_ALPHABETS];
        Arrays.fill(charFrequencies, 0);

        for (char character : text.toCharArray()) {
            // Subtracting 'a' from the Character will make the character's int value zero based
            charFrequencies[character - 'a']++;
        }
        return charFrequencies;
    }

    /**
     * 'A' => int value is 65
     * 'Z' => int value is 90
     * 'A' -> 'Z' => 90 - 65 = 26 characters array required to store upper case alphabets
     */
    private int[] uppercaseCharFrequencies(final String text) {
        final int[] charFrequencies = new int[NO_OF_ALPHABETS];
        Arrays.fill(charFrequencies, 0);

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
    private int[] digitsFrequencies(final String text) {
        final int[] charFrequencies = new int[NO_OF_DIGITS];
        Arrays.fill(charFrequencies, 0);

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
    private int[] caseInsensitiveAlphanumericCharFrequencies(final String text) {
        final int[] charFrequencies = new int[NO_OF_ALPHANUMERICS];
        Arrays.fill(charFrequencies, 0);

        for (int i = 0; i < text.length(); i++) {
            charFrequencies[Character.getNumericValue(i)]++;
        }
        return charFrequencies;
    }

    /**
     * ASCII stands for the "American Standard Code for Information Interchange".
     * <p>
     * ASCII is a 7-bit character set containing 128 characters.
     * <p>
     * It contains the numbers from 0-9, the upper and lower case English letters from A to Z,
     * and some special characters.
     */
    private int[] asciiCharFrequencies(final String text) {
        final int[] charFrequencies = new int[NO_OF_ASCII_CHARS];
        Arrays.fill(charFrequencies, 0);

        for (int i = 0; i < text.length(); i++) {
            charFrequencies[text.charAt(i)]++;
        }
        return charFrequencies;
    }

    /**
     * 8-bit character set containing 256 possible characters.
     * <p>
     * The 8-bit characters are single-byte characters whose code points are 128 - 255.
     */
    private int[] allCharFrequencies(final String text) {
        final int[] charFrequencies = new int[NO_OF_CHARS];
        Arrays.fill(charFrequencies, 0);

        for (int i = 0; i < text.length(); i++) {
            charFrequencies[text.charAt(i)]++;
        }
        return charFrequencies;
    }

    private static int[] buildAdjustedCharFrequencies(final String str1, final String str2) {
        final int[] charFrequencies = new int[NO_OF_ALPHABETS];
        Arrays.fill(charFrequencies, 0);

        for (int i = 0; i < str1.length(); i++) {
            charFrequencies[str1.charAt(i) - 'a']++;
            charFrequencies[str2.charAt(i) - 'a']--;
        }
        return charFrequencies;
    }
}
