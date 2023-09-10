package com.algorithms.geeksforgeeks.medium;

public class LongestCommonSubstring {
    /**
     * Given two strings X and Y. The task is to find the length of the longest common substring.
     * <p>
     * https://practice.geeksforgeeks.org/problems/longest-common-substring/0
     */
    private static int longestCommonSubstring(String str1, String str2, int str1Length, int str2Length) {
        int commonSubStrLength = 0;
        if (str1Length == 0 || str2Length == 0) {
            return commonSubStrLength;
        }
        int[][] memoTable = new int[str1Length + 1][str2Length + 1];
        for (int i = 0; i <= str1Length; i++) {
            for (int j = 0; j <= str2Length; j++) {
                if (i == 0 || j == 0) {
                    memoTable[i][j] = 0;
                } else if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    memoTable[i][j] = memoTable[i - 1][j - 1] + 1;
                }
                commonSubStrLength = Math.max(memoTable[i][j], commonSubStrLength);
            }
        }
        return commonSubStrLength;
    }
}
