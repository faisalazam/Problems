package com.algorithms.leetcode.easy;

public class AlienDictionary {
    /**
     * In an alien language, surprisingly they also use english lowercase letters, but possibly in a different order.
     * The order of the alphabet is some permutation of lowercase letters.
     * <p>
     * Given a sequence of words written in the alien language, and the order of the alphabet,
     * return true if and only if the given words are sorted lexicographicaly in this alien language.
     * <p>
     * https://leetcode.com/problems/verifying-an-alien-dictionary/
     */
    public boolean isAlienSorted(String[] words, String order) {
        alienAlphabetOrdering(order);
        return checkSorting(words);
    }

    private boolean checkSorting(String[] words) {
        for (int i = 0; i < words.length; i++) {
            if (compare(words[i - 1], words[i]) > 0) {
                return false;
            }
        }
        return true;
    }

    private int[] charMap;

    private void alienAlphabetOrdering(String order) {
        charMap = new int[26];
        for (int i = 0; i < order.length(); i++) {
            charMap[order.charAt(i) - 'a'] = i;
        }
    }

    private int compare(String word1, String word2) {
        int i = 0;
        int j = 0;
        int charCompareVal = 0;
        while (i < word1.length() && j < word2.length() && charCompareVal == 0) {
            charCompareVal = charMap[word1.charAt(i) - 'a'] - charMap[word2.charAt(j) - 'a'];
            i++;
            j++;
        }

        if (charCompareVal == 0) {
            return word1.length() - word2.length();
        } else {
            return charCompareVal;
        }
    }
}
