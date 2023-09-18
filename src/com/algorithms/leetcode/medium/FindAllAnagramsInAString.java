package com.algorithms.leetcode.medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given two strings s and p, return an array of all the start indices of p's anagrams in s. You may return the answer
 * in any order.
 * <p>
 * An Anagram is a word or phrase formed by rearranging the letters of a different word or phrase, typically using all
 * the original letters exactly once.
 * <p>
 * Strings consists of lowercase English letters only.
 * <p>
 * The order of output does not matter.
 * <p>
 * https://leetcode.com/problems/find-all-anagrams-in-a-string/
 */
public class FindAllAnagramsInAString {
    /**
     * Brute Force
     * <p>
     * Get characters frequencies of p
     * Then traverse s from 0 to s.length() - p.length()
     * Substring s from i to i + p.length() and get characters frequencies of this substring
     * Check the delta and add i to results if no difference found in characters frequencies
     * The above algo will run for each index of s till s.length() - p.length()
     */
    public List<Integer> findAnagramsBruteForce(String s, String p) {
        final List<Integer> results = new ArrayList<>();
        if (s == null || s.isEmpty() || p.length() > s.length()) {
            return results;
        }
        final int pLength = p.length();
        final int loopLength = s.length() - pLength;
        final int[] charCountsOfP = charFrequencies(p);
        for (int i = 0; i <= loopLength; i++) {
            // Substring is a linear operation and so should be bounded by O(N), where N is the number of characters in
            // the source string. The required space boundary to hold the new substring is also O(N).
            final int[] charCountsOfSubStr = charFrequencies(s.substring(i, i + pLength));
            if (isAnagram(charCountsOfP, charCountsOfSubStr)) {
                results.add(i);
            }
        }
        return results;
    }

    /**
     * Improved upon the Brute Force approach - Sliding Window approach
     * <p>
     * Time Complexity: O(N), where N is length of the s string
     * Auxiliary Space: O(1), arrays used for frequencies are of O(1) as they don't depend on the length of the input strings
     */
    public List<Integer> findAnagramsImproved(String s, String p) {
        final List<Integer> results = new ArrayList<>();
        if (s == null || s.isEmpty() || p.length() > s.length()) {
            return results;
        }
        int left = 0;
        int right = p.length();
        final int[] charCountsOfP = charFrequencies(p, right); // O(1)
        final int[] charCountsOfSubStr = charFrequencies(s, right); // O(1)

        // This is to tackle the scenario when s.length() == p.length() as while loop won't execute in that case
        if (isAnagram(charCountsOfP, charCountsOfSubStr)) { // O(1)
            results.add(left);
        }

        while (right < s.length()) { // O(S) where S is length of the s string
            charCountsOfSubStr[s.charAt(left++) - 'a']--;
            charCountsOfSubStr[s.charAt(right++) - 'a']++;
            if (isAnagram(charCountsOfP, charCountsOfSubStr)) { // O(1)
                results.add(left);
            }
        }
        return results;
    }

    /**
     * Sliding Window approach with just one frequency array
     * <p>
     * Time Complexity: O(N), where N is length of the s string
     * Auxiliary Space: O(1), array used for frequencies is of O(1) as they don't depend on the length of the input strings
     */
    public List<Integer> findAnagrams(String s, String p) {
        final List<Integer> results = new ArrayList<>();
        if (s == null || s.isEmpty() || p.length() > s.length()) {
            return results;
        }

        int left = 0;
        int right = 0;
        final int pLength = p.length();
        int count = pLength;
        int[] charCounts = charFrequencies(p);
        while (right < s.length()) { // ababab => ab
            // Initially count = pLength, we remove the characters in s from charCounts, and then count gets decremented
            // if character from s matched with a character in p. count will be equal to 0 whenever all characters of p
            // are matched with characters in s.
            if (charCounts[s.charAt(right++) - 'a']-- >= 1) {
                count--;
            }
            if (count == 0) { // Found an Anagram starting at index specified by left
                results.add(left);
            }
            // After traversing pLength characters, (right - left) will be equal to pLength for every iteration of the
            // loop. characters in s was removed from charCounts in the first if condition, here we add the first
            // character of s of sliding window back in the charCounts
            if (right - left == pLength && charCounts[s.charAt(left++) - 'a']++ >= 0) {
                count++;
            }
        }
        return results;
    }

    private int[] charFrequencies(String p) {
        final int[] charCounts = new int[26];
        for (char c : p.toCharArray()) {
            charCounts[c - 'a']++;
        }
        return charCounts;
    }

    private int[] charFrequencies(final String str, final int length) {
        final int[] charCounts = new int[26];
        for (int i = 0; i < length; i++) {
            charCounts[str.charAt(i) - 'a']++;
        }
        return charCounts;
    }

    private static boolean isAnagram(final int[] charFrequencies1, final int[] charFrequencies2) {
        return Arrays.equals(charFrequencies1, charFrequencies2);
    }

    public static void main(final String[] args) {
        System.out.println(new FindAllAnagramsInAString().findAnagramsImproved("abab", "aba"));
    }
}
