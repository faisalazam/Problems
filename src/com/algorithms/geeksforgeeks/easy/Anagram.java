package com.algorithms.geeksforgeeks.easy;

import java.util.Arrays;

public class Anagram {
    /**
     * Given two strings a and b consisting of lowercase characters.
     * The task is to check whether two given strings are anagram of each other or not.
     * An anagram of a string is another string that contains same characters,
     * only the order of characters can be different. For example, “act” and “tac” are anagram of each other.
     * <p>
     * https://practice.geeksforgeeks.org/problems/anagram/0
     * <p>
     * Time Complexity:O(|a|+|b|).
     * Auxiliary Space: O(Number of distinct characters). Here, it is O(1) for constant space.
     */
    private static boolean isAnagram(String str1, String str2) {
        if (str1 == null || str2 == null || str1.length() != str2.length()) {
            return false;
        }
        int[] adjustedCharFrequencies = buildAdjustedCharFrequencies(str1, str2);
        for (int frequency : adjustedCharFrequencies) {
            if (frequency != 0) {
                return false;
            }
        }
        return true;
    }

    private static boolean isAnagramV1(String str1, String str2) {
        if (str1 == null || str2 == null || str1.length() != str2.length()) {
            return false;
        }
        int[] str1Freq = buildCharFrequencies(str1);
        int[] str2Freq = buildCharFrequencies(str2);
        return delta(str1Freq, str2Freq);
    }

    // Time Complexity: O(N * logN), For sorting.
    // Auxiliary Space: O(1) as it is using constant extra space
    private static boolean isAnagramV0(String str1, String str2) {
        if (str1 == null || str2 == null || str1.length() != str2.length()) {
            return false;
        }
        final char[] charArray1 = str1.toCharArray();
        final char[] charArray2 = str2.toCharArray();

        Arrays.sort(charArray1);
        Arrays.sort(charArray2);

        for (int i = 0; i < str1.length(); i++) {
            if (charArray1[i] != charArray2[i]) {
                return false;
            }
        }
        return true;
    }

    private static int[] buildCharFrequencies(String str) {
        final int[] charFrequencies = new int[26];
        Arrays.fill(charFrequencies, 0);

        for (int i = 0; i < str.length(); i++) {
            charFrequencies[str.charAt(i) - 'a']++;
        }
        return charFrequencies;
    }

    private static int[] buildAdjustedCharFrequencies(String str1, String str2) {
        final int[] charFrequencies = new int[26];
        Arrays.fill(charFrequencies, 0);

        for (int i = 0; i < str1.length(); i++) {
            charFrequencies[str1.charAt(i) - 'a']++;
            charFrequencies[str2.charAt(i) - 'a']--;
        }
        return charFrequencies;
    }

    private static boolean delta(int[] charFrequencies1, int[] charFrequencies2) {
        for (int i = 0; i < 26; i++) {
            if (charFrequencies1[i] != charFrequencies2[i]) {
                return false;
            }
        }
        return true;
    }
}
