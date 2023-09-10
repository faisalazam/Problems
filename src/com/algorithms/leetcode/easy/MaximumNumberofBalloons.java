package com.algorithms.leetcode.easy;

public class MaximumNumberofBalloons {

    /*
     * Given a string text, you want to use the characters of text to form as many instances of the word "balloon" as possible.
     *
     * You can use each character in text at most once. Return the maximum number of instances that can be formed.
     *
     * https://leetcode.com/problems/maximum-number-of-balloons/
     */
    public int maxNumberOfBalloons(String text) {
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
