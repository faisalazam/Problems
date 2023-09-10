package com.algorithms.geeksforgeeks.medium;

public class LongestCommonPrefixInAnArray {
    /**
     * Given a array of N strings, find the longest common prefix among all strings present in the array.
     * <p>
     * https://practice.geeksforgeeks.org/problems/longest-common-prefix-in-an-array/0
     */
    private static String longestPrefix(String[] words, int size) {
        if (size == 0) {
            return "";
        }
        String prefix = words[0];
        for (int i = 1; i < size; i++) {
            while (words[i].indexOf(prefix) != 0) {
                prefix = prefix.substring(0, prefix.length() - 1);
            }
        }
        return prefix.equals("") ? "-1" : prefix;
    }

    private static String longestPrefixV1(String[] words, int size) {
        if (size == 0) {
            return "";
        }
        int smallestWordIndex = 0;
        int minLength = Integer.MAX_VALUE;
        for (int i = 0; i < size; i++) {
            if (words[i].length() < minLength) {
                smallestWordIndex = i;
                minLength = words[i].length();
            }
        }
        int index = 0;
        String smallestWord = words[smallestWordIndex];
        for (int i = 0; i < minLength; i++) {
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
}
