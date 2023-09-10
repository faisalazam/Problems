package com.algorithms.leetcode.easy;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class LongestWordInDictionary {
    /**
     * Given a list of strings words representing an English Dictionary,
     * find the longest word in words that can be built one character at a time by other words in words.
     * If there is more than one possible answer, return the longest word with the smallest lexicographical order.
     * <p>
     * If there is no answer, return the empty string.
     * <p>
     * https://leetcode.com/problems/longest-word-in-dictionary/
     */
    private String longestWord(String[] words) {
        Arrays.sort(words);
        String result = "";
        Set<String> builtWords = new HashSet<>();
        for (String word : words) {
            if (word.length() == 1 || builtWords.contains(word.substring(0, word.length() - 1))) { // remove the last char from word
                if (word.length() > result.length()) {
                    result = word;
                }
                builtWords.add(word);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String[] words = {"w", "wo", "wor", "worl", "world"};
        System.out.println(new LongestWordInDictionary().longestWord(words));
    }
}
