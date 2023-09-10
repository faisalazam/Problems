package com.algorithms.leetcode.easy;

public class MaximumNumberOfBalloons {

    /*
     * Given a string text, you want to use the characters of text to form as many instances of the word "balloon" as possible.
     *
     * You can use each character in text at most once. Return the maximum number of instances that can be formed.
     *
     * https://leetcode.com/problems/maximum-number-of-balloons/
     */
    public int maxNumberOfBalloons(String text) {
        // Any string made up of lowercase English letters.
        final String pattern = "balloon";
        return findMaxNumberOfPattern(text, pattern);
    }

    private int findMaxNumberOfPattern(String text, final String pattern) {
        // Next optimisation would be to use just 5 character array instead of 26 to store frequencies
        // of 'b', 'a', 'l', 'o' and 'n' only
        final int[] freqInText = getCharFrequencies(text);
        final int[] freqInPattern = getCharFrequencies(pattern);

        // Compare the maximum string that can be produced considering one character at a time.
        int answer = Integer.MAX_VALUE;
        for (int i = 0; i < 26; i++) {
            // Do not divide by zero.
            if (freqInPattern[i] > 0) {
                answer = Math.min(answer, freqInText[i] / freqInPattern[i]);
            }
        }

        return answer;
    }

    public int maxNumberOfBalloons1(String text) {
        int[] charFrequencies = getCharFrequencies(text);
        return maxNumberOfBalloons(charFrequencies);
    }

    private int[] getCharFrequencies(String text) {
        int[] charFrequencies = new int[26];
        for (int i = 0; i < text.length(); i++) {
            charFrequencies[text.charAt(i) - 'a']++;
        }
        return charFrequencies;
    }

    private int maxNumberOfBalloons(int[] charFrequencies) {
        int min = charFrequencies['b' - 'a'];
        min = Math.min(min, charFrequencies[0]);
        min = Math.min(min, charFrequencies['l' - 'a'] / 2);
        min = Math.min(min, charFrequencies['o' - 'a'] / 2);
        min = Math.min(min, charFrequencies['n' - 'a']);
        return min;
    }
}
