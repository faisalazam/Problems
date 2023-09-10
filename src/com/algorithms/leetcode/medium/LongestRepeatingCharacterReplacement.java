package com.algorithms.leetcode.medium;

public class LongestRepeatingCharacterReplacement {
    /**
     * Given a string s that consists of only uppercase English letters, you can perform at most k operations on that string.
     * <p>
     * In one operation, you can choose any character of the string and change it to any other uppercase English character.
     * <p>
     * Find the length of the longest sub-string containing all repeating letters you can get after performing the above operations.
     * <p>
     * Note:
     * Both the string's length and k will not exceed 10^4.
     * <p>
     * https://leetcode.com/problems/longest-repeating-character-replacement/
     */
    public int characterReplacement(String s, int k) {
        int maxCount = 0;
        int maxLength = 0;
        int windowStart = 0;
        int[] charCounts = new int[26];
        for (int windowEnd = 0; windowEnd < s.length(); windowEnd++) {
            int charIndex = s.charAt(windowEnd) - 'A';
            charCounts[charIndex]++;
            int currentCharCount = charCounts[charIndex];
            maxCount = Math.max(maxCount, currentCharCount);
            while (windowEnd - windowStart - maxCount + 1 > k) {
                charCounts[s.charAt(windowStart) - 'A']--;
                windowStart++;
            }
            maxLength = Math.max(maxLength, windowEnd - windowStart + 1);
        }
        return maxLength;
    }
}
