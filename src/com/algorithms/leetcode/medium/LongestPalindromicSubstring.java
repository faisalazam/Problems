package com.algorithms.leetcode.medium;

public class LongestPalindromicSubstring {
    /**
     * Given a string s, find the longest palindromic substring in s. You may assume that the maximum length of s is 1000.
     * <p>
     * https://leetcode.com/problems/longest-palindromic-substring/
     * <p>
     * O(N²) and O(1)
     */
    public String longestPalindrome(String s) {
        if (s == null || s.length() < 1) {
            return "";
        }
        int start = 0;
        int end = 0;
        for (int i = 0; i < s.length(); i++) {
            int length1 = expandFromMiddle(s, i, i); // this will cover the odd numbered palindromic substrings
            int length2 = expandFromMiddle(s, i, i + 1); // this will cover the even numbered palindromic substrings
            int length = Math.max(length1, length2);
            if (length > end - start) {
                start = i - ((length - 1) / 2);
                end = i + (length / 2);
            }
        }
        return s.substring(end - start + 1);
    }

    private int expandFromMiddle(String s, int left, int right) {
        if (s == null || left > right) {
            return 0;
        }
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1;
    }
}
