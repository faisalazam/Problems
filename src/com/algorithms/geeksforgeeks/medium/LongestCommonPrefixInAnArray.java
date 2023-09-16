package com.algorithms.geeksforgeeks.medium;

import java.util.Arrays;

/**
 * Given a array of N strings, find the longest common prefix among all strings present in the array.
 * <p>
 * https://practice.geeksforgeeks.org/problems/longest-common-prefix-in-an-array/0
 * <p>
 * Expected Time Complexity: O(N*min(|arri|)).
 * Expected Auxiliary Space: O(min(|arri|)) for result.
 */
public class LongestCommonPrefixInAnArray {
    /**
     * Time Complexity : O(N M) - Since we are iterating through all the strings and for each string we are iterating
     * though each character, so we can say that the time complexity is O(N M) where,
     * N = Number of strings
     * M = Length of the largest string
     * Auxiliary Space : O(M) - To store the longest prefix string we are allocating space which is O(M).
     */
    static String longestPrefixV4(String[] words, int size) {
        if (words == null || words.length == 0) {
            return "";
        }
        String prefix = words[0];
        for (int i = 1; i < size; i++) {
            prefix = commonPrefix(prefix, words[i]);
        }
        return prefix.equals("") ? "-1" : prefix;
    }

    /**
     * Time complexity: O(NM logM), where N is the number of strings in the input list and M is the length of the
     * longest string in the input list. The sort() method used to sort the input list has a time complexity of O(NlogN),
     * and the loop through the characters in the first string has a time complexity of O(M). The all() function inside
     * the loop has a time complexity of O(N), where N is the number of strings in the input list. Therefore, the total
     * time complexity is O(NlogN + NM).
     * <p>
     * Auxiliary space: O(1). The function uses a constant amount of auxiliary space, since it only stores a prefix
     * string of length at most M.
     */
    static String longestPrefixV3(String[] words, int size) { // Results in Time Limit Exceeded
        if (words == null || words.length == 0) {
            return "";
        }
        Arrays.sort(words);
        final String prefix = commonPrefix(words[0], words[size - 1]);
        return prefix.equals("") ? "-1" : prefix;
    }

    private static String longestPrefixV2(String[] words, int size) {
        if (size == 0) {
            return "";
        }
        String prefix = words[0];
        for (int i = 1; i < size; i++) {
            // keep dropping last character until prefix becomes prefix of words[i] or the prefix becomes empty string
            while (words[i].indexOf(prefix) != 0) {
                prefix = prefix.substring(0, prefix.length() - 1); // drop last character
            }
            if (prefix.isEmpty()) { // no need to continue comparison with rest of the words as prefix has become empty
                break;
            }
        }
        return prefix.equals("") ? "-1" : prefix;
    }

    private static String longestPrefixV1(String[] words, int size) {
        if (size == 0) {
            return "";
        }
        int index = 0;
        final int smallestWordIndex = getSmallestWordIndex(words, size);
        final String smallestWord = words[smallestWordIndex];
        for (int i = 0; i < smallestWord.length(); i++) {
            char character = smallestWord.charAt(i);
            for (int j = 0; j < size; j++) {
                if (character != words[j].charAt(i)) {
                    return index == 0 ? "-1" : smallestWord.substring(0, index);
                }
            }
            index++;
        }
        return index == 0 ? "-1" : smallestWord.substring(0, index);
    }

    private static int getSmallestWordIndex(final String[] words, final int size) {
        int smallestWordIndex = 0;
        int minLength = Integer.MAX_VALUE;
        for (int i = 0; i < size; i++) {
            final int wordLength = words[i].length();
            if (wordLength < minLength) {
                smallestWordIndex = i;
                minLength = wordLength;
            }
        }
        return smallestWordIndex;
    }

    private static String commonPrefix(final String str1, final String str2) {
        int n1 = str1.length(), n2 = str2.length();
        final StringBuilder result = new StringBuilder();

        // Compare str1 and str2
        for (int i = 0, j = 0; i < n1 && j < n2; i++, j++) {
            if (str1.charAt(i) != str2.charAt(j)) {
                break;
            }
            result.append(str1.charAt(i));
        }
        return result.toString();
    }

    public static void main(String[] args) {
        final String[] words = new String[]{"geeksforgeeks", "geeks", "geek", "geezer"};
        System.out.println(longestPrefixV3(words, words.length));
    }
}
