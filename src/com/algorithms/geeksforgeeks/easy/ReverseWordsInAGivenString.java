package com.algorithms.geeksforgeeks.easy;

public class ReverseWordsInAGivenString {
    /**
     * Given a String of length S, reverse the whole string without reversing the individual words in it.
     * Words are separated by dots.
     * <p>
     * https://practice.geeksforgeeks.org/problems/reverse-words-in-a-given-string/0
     */
    public static String reverse(String wordsString) {
        if (wordsString == null || wordsString.trim().isEmpty()) {
            return "";
        }
        String[] words = wordsString.split("\\.");
        int size = words.length - 1;
        int start = 0;
        int end = size;
        while (start < end) {
            String temp = words[end];
            words[end] = words[start];
            words[start] = temp;
            start++;
            end--;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i <= size; i++) {
            if (i == size) {
                builder.append(words[i]);
            } else {
                builder.append(words[i]).append(".");
            }
        }
        return builder.toString();
    }
}
