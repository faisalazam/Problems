package com.algorithms.geeksforgeeks.easy;

public class Anangram {
    /**
     * Given two strings a and b consisting of lowercase characters.
     * The task is to check whether two given strings are anagram of each other or not.
     * An anagram of a string is another string that contains same characters,
     * only the order of characters can be different. For example, “act” and “tac” are anagram of each other.
     * <p>
     * https://practice.geeksforgeeks.org/problems/anagram/0
     */
    private static String isAnangram(String str1, String str2) {
        if (str1 == null || str2 == null || str1.length() != str2.length()) {
            return "NO";
        }
        int[] str1Freq = buildCharFrequencies(str1);
        int[] str2Freq = buildCharFrequencies(str2);
        return delta(str1Freq, str2Freq) == 0 ? "YES" : "NO";
    }

    private static int[] buildCharFrequencies(String str) {
        int[] charFrequencies = new int[26];
        for (int i = 0; i < str.length(); i++) {
            charFrequencies[str.charAt(i) - 'a']++;
        }
        return charFrequencies;
    }

    private static int delta(int[] charFrequencies1, int[] charFrequencies2) {
        for (int i = 0; i < 26; i++) {
            if (charFrequencies1[i] != charFrequencies2[i]) {
                return -1;
            }
        }
        return 0;
    }
}
