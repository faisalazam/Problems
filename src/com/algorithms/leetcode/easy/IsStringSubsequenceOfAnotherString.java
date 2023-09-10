package com.algorithms.leetcode.easy;

public class IsStringSubsequenceOfAnotherString {
    /**
     * Given a string s and a string t, check if s is subsequence of t.
     * <p>
     * A subsequence of a string is a new string which is formed from the original string by deleting some (can be none)
     * of the characters without disturbing the relative positions of the remaining characters.
     * (ie, "ace" is a subsequence of "abcde" while "aec" is not).
     */
    public boolean isSubsequence(String s, String t) {
        if (s.length() == 0) {
            return true;
        }
        int sPointer = 0;
        int tPointer = 0;
        while (tPointer < t.length()) {
            if (t.charAt(tPointer) == s.charAt(sPointer)) {
                sPointer++;
                if (sPointer == s.length()) {
                    return true;
                }
            }
            tPointer++;
        }
        return false;
    }
}
