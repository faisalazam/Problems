package com.algorithms.geeksforgeeks.easy;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Given a string S, find length of the longest substring with all distinct characters.
 * For example, for input "abca", the output is 3 as "abc" is the longest substring with all distinct characters.
 * <p>
 * https://practice.geeksforgeeks.org/problems/longest-distinct-characters-in-string/0
 */
public class LongestDistinctCharactersInString {
    /**
     * Brute Force
     */
    private static int findMaxBruteForce(String str) { // Results in Time Limit Exceeded
        int max = 0;
        int start = 0;
        int currentMax = 0;
        final int length = str.length();
        final Set<Character> seenChars = new HashSet<>(length);
        for (int i = 0; i < length; ) { // i will be incremented or adjusted inside the loop
            if (!seenChars.add(str.charAt(i++))) {
                currentMax = 0;
                i = ++start;
                seenChars.clear();
                continue;
            }
            currentMax++;
            max = Integer.max(max, currentMax);
        }
        return max;
    }

    /**
     * Brute Force
     */
    private static int findMax(String str) {
        int max = 0;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char character = str.charAt(i);
            max = Math.max(max, result.length());
            int index = result.toString().indexOf(character);
            result.append(character);
            if (index >= 0) {
                result = new StringBuilder(result.substring(index + 1));
            }
        }
        return Math.max(max, result.length());
    }

    /**
     * Time Complexity: O(N)
     * Auxiliary Space: O(1)
     */
    private static int findMaxImproved(String str) {
        final int[] arr = new int[26]; // stores index of last occurrence of character
        Arrays.fill(arr, -1);
        int start = 0;
        int end = 0;
        int max = 1;
        while (end < str.length()) {
            final int charIndex = str.charAt(end) - 'a';
            if (arr[charIndex] >= start) { // that means the char has already been seen
                start = arr[charIndex] + 1;
            }
            arr[charIndex] = end++;
            max = Math.max(max, (end - start));
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println(findMax("abcadefg"));
        System.out.println(findMax("abcdeafgh"));
    }
}
