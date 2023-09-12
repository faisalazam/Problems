package com.algorithms.leetcode.medium;

import java.util.LinkedList;
import java.util.List;

public class LetterCombinationsOfAPhoneNumber {
    /**
     * Given a string containing digits from 2-9 inclusive, return all possible letter combinations that the number could represent.
     * <p>
     * A mapping of digit to letters (just like on the telephone buttons) is given below. Note that 1 does not map to any letters.
     * <p>
     * https://leetcode.com/problems/letter-combinations-of-a-phone-number/
     */
    // Time Complexity: O(3^M×4^N) ???
    // Space Complexity: O(3^M×4^N) ???
    public List<String> letterCombinations(final String digits) {
        final LinkedList<String> letterCombinations = new LinkedList<>();
        if (digits == null || digits.isEmpty()) {
            return letterCombinations;
        }
        final String[] charMap = new String[]{"0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        letterCombinations.add("");

        for (int i = 0; i < digits.length(); i++) {
            final int index = Character.getNumericValue(digits.charAt(i));
            final char[] mappedChars = charMap[index].toCharArray();

            while (!letterCombinations.isEmpty() && letterCombinations.peek().length() == i) {
                final String permutation = letterCombinations.remove();
                for (char character : mappedChars) {
                    letterCombinations.add(permutation + character);
                }
            }
        }
        return letterCombinations;
    }
}
