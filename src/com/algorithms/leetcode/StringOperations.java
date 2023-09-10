package com.algorithms.leetcode;

public class StringOperations {
    /**
     * Given two strings s and t , write a function to determine if t is an anagram of s.
     * <p>
     * https://leetcode.com/problems/valid-anagram/
     */
    private static boolean isAnangram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        int[] charFrequencies = new int[26];
        for (int i = 0; i < s.length(); i++) {
            charFrequencies[s.charAt(i) - 'a']++;
            charFrequencies[t.charAt(i) - 'a']--;
        }
        for (int charFrequency : charFrequencies) {
            if (charFrequency != 0) {
                return false;
            }
        }
        return true;
    }

    /*
     * Given a string, determine, if a permutation of a string can be a palindrome
     */
    private static boolean canPermutePalindrome(String s) {
        int[] charFrequencies = new int[128]; //ASCII char count -> considering both upper case as well as lower case chars
        for (int i = 0; i < s.length(); i++) {
            charFrequencies[s.charAt(i)]++;
        }
        int count = 0;
        for (int charFrequency : charFrequencies) {
            count += charFrequency % 2;
        }
        return count <= 1; //won't be palindrome if there are more than one odd numbered chars
    }

    public static void main(String[] args) {
        System.out.println(canPermutePalindrome("ab"));
    }
}
