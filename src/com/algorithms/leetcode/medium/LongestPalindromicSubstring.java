package com.algorithms.leetcode.medium;

/**
 * Given a string s, find the longest palindromic substring in s. You may assume that the maximum length of s is 1000.
 * <p>
 * https://leetcode.com/problems/longest-palindromic-substring/
 */
public class LongestPalindromicSubstring {
    /**
     * See for more solutions:
     * https://leetcode.com/problems/longest-palindromic-substring/solutions/650496/all-approaches-code-in-java-including-manacher-s-algorithm-explanation/
     * <p>
     * Time Complexity : O(N)??? it doesn't look like so
     * Space Complexity : O(2N) => O(N)
     */
    public static String longestPalindromeDP(String s) {
        if (s == null || s.length() < 1) {
            return "";
        }
        final int sLength = s.length();
        final char[] sChars = s.toCharArray();
        // * dp[i][len + 1] means substring starting from i with length of len;
        final boolean[][] dp = new boolean[sLength][2];
        int currCol = 0;

        int maxLen = 0;
        int ans = 0; // record start index of s

        int count = 0;
        for (int len = 0; len < sLength; len++) {
            for (int start = 0; start + len < sLength; start++) {
                count++;
                final int end = start + len;
                if (len == 0) {
                    dp[start][currCol] = true;
                } else if (len == 1) {
                    dp[start][currCol] = (sChars[start] == sChars[end]);
                } else {
                    dp[start][currCol] = (sChars[start] == sChars[end] && dp[start + 1][currCol]);
                }

                if (dp[start][currCol] && len + 1 > maxLen) {
                    ans = start;
                    maxLen = len + 1;
                }
            }
            currCol = 1 - currCol;
        }
        System.out.println(count);
        return maxLen == 0 ? "" : s.substring(ans, ans + maxLen);
    }

    /**
     * O(N²) and O(1)
     */
    public static String longestPalindrome(String s) {
        if (s == null || s.length() < 1) {
            return "";
        }
        int start = 0;
        int maxLength = -1;
        for (int i = 0; i < s.length(); i++) {
            int length1 = expandFromMiddle(s, i, i); // this will cover the odd numbered palindromic substrings
            int length2 = expandFromMiddle(s, i, i + 1); // this will cover the even numbered palindromic substrings
            int length = Math.max(length1, length2);
            if (length > maxLength) {
                maxLength = length;
                start = i - ((length - 1) / 2);
            }
        }
        return s.substring(start, start + maxLength);
    }

    private static int expandFromMiddle(final String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1;
    }

    /**
     * Brute Force
     * <p>
     * Idea is to just get all the substrings of s and check if it is a palindrome or not. If it is, then update the the
     * left/right positions if it is a longer palindrome
     * <p>
     * O(N^3)
     */
    public static String longestPalindromeBruteForce(String s) {
        if (s == null || s.length() <= 1) {
            return s;
        }

        int start = -1;
        int end = -1;
        final int length = s.length();
        // could have used s directly and then do s.charAt(i) in isPalindrome(...) method, all tests do pass but that
        // results in TLE
        final char[] chars = s.toCharArray();
        for (int left = 0; left < length - 1; left++) { // O(N)
            for (int right = left; right < length; right++) { // O(N)
                if (isPalindrome(left, right, chars) && (right - left) >= (end - start)) { // O(N)
                    start = left;
                    end = right;
                }
            }
        }
        return start == -1 ? "" : s.substring(start, end + 1);
    }

    private static boolean isPalindrome(final int left, final int right, final char[] chars) {
        for (int i = left, j = right; i <= right && j >= left; i++, j--) {
            if (chars[i] != chars[j]) {
                return false;
            }
        }
        return true;
    }

    /*
     * See for more solutions:
     * https://leetcode.com/problems/longest-palindromic-substring/solutions/650496/all-approaches-code-in-java-including-manacher-s-algorithm-explanation/
     */

    public static void main(String[] args) {
        System.out.println(longestPalindrome("babad"));
//        System.out.println(longestPalindrome("ac"));
    }
}
