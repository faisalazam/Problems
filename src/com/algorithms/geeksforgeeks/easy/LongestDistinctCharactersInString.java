package com.algorithms.geeksforgeeks.easy;

import java.util.Arrays;

public class LongestDistinctCharactersInString {
    /**
     * Given a string S, find length of the longest substring with all distinct characters.
     * For example, for input "abca", the output is 3 as "abc" is the longest substring with all distinct characters.
     * <p>
     * https://practice.geeksforgeeks.org/problems/longest-distinct-characters-in-string/0
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

    private static int findMaxImproved(String str) {
        int[] arr = new int[26]; //stores index of last occurrence of character
        Arrays.fill(arr, -1);
        int start = 0;
        int end = 0;
        int max = 1;
        while (end < str.length()) {
            int charIndex = str.charAt(end) - 'a';
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
