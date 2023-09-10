package com.algorithms.leetcode.medium;

import java.util.ArrayList;
import java.util.List;

public class FindAllAnagramsInAString {
    /**
     * Given a string s and a non-empty string p, find all the start indices of p's anagrams in s.
     * <p>
     * Strings consists of lowercase English letters only and the length of both strings s and p will not be larger than 20,100.
     * <p>
     * The order of output does not matter.
     * <p>
     * https://leetcode.com/problems/find-all-anagrams-in-a-string/
     */
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> results = new ArrayList<>();
        if (s == null || s.isEmpty()) {
            return results;
        }

        int left = 0;
        int right = 0;
        int count = p.length();
        int[] charCounts = getCharCounts(p);
        while (right < s.length()) {
            if (charCounts[s.charAt(right++) - 'a']-- >= 1) {
                count--;
            }
            if (count == 0) {
                results.add(left);
            }
            if (right - left == p.length() && charCounts[s.charAt(left++) - 'a']++ >= 0) {
                count++;
            }
        }
        return results;
    }

    private int[] getCharCounts(String p) {
        int[] charCounts = new int[26];
        for (char c : p.toCharArray()) {
            charCounts[c - 'a']++;
        }
        return charCounts;
    }
}
