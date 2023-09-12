package com.algorithms.geeksforgeeks.easy;

public class ReverseWordsInAGivenString {
    /**
     * Given a String of length S, reverse the whole string without reversing the individual words in it.
     * Words are separated by dots.
     * <p>
     * https://practice.geeksforgeeks.org/problems/reverse-words-in-a-given-string/0
     */
    // Time Complexity: O(N), since we are traversing the whole string once.
    // Auxiliary Space: O(N), as we've taken a string for result, where N is the length of the string
    public static String reverse(String wordsString) {
        StringBuilder currentWord = new StringBuilder();
        final StringBuilder result = new StringBuilder();

        for (int i = 0; i < wordsString.length(); i++) {
            final char character = wordsString.charAt(i);
            if (character == '.') {
                result.insert(0, currentWord);
                result.insert(0, character);
                currentWord = new StringBuilder();
            } else {
                currentWord.append(character);
            }
        }
        result.insert(0, currentWord);
        return result.toString();
    }

    public static String reverseV0(String wordsString) {
        if (wordsString == null || wordsString.trim().isEmpty()) {
            return "";
        }
        final String[] words = wordsString.split("\\.");
        final int size = words.length - 1;

        reverse(words, size);
        return joinReversedWords(words, size);
    }

    private static String joinReversedWords(final String[] words, final int size) {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i <= size; i++) {
            builder.append(words[i]);
            if (i != size) {
                builder.append(".");
            }
        }
        return builder.toString();
    }

    private static void reverse(final String[] words, final int size) {
        int start = 0;
        int end = size;
        while (start < end) {
            String temp = words[end];
            words[end] = words[start];
            words[start] = temp;
            start++;
            end--;
        }
    }
}
