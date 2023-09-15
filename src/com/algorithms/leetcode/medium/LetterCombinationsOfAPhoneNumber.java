package com.algorithms.leetcode.medium;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Given a string containing digits from 2-9 inclusive, return all possible letter combinations that the number could represent.
 * <p>
 * A mapping of digit to letters (just like on the telephone buttons) is given below. Note that 1 does not map to any letters.
 * <p>
 * https://leetcode.com/problems/letter-combinations-of-a-phone-number/
 * <p>
 * See this for more solutions:
 * https://leetcode.com/problems/letter-combinations-of-a-phone-number/solutions/2021106/4-approaches-bf-4-loops-backtracking-bfs-queue-with-image-explanation/
 */
public class LetterCombinationsOfAPhoneNumber {

    /**
     * Consider it as a tree where root starts with an empty string and each next level consists of char nodes which are
     * mapped by the digits.charAt(i). Then try to apply BFS.
     * <p>
     * Time Complexity: O(3^M×4^N) ???
     * Space Complexity: O(3^M×4^N) ???
     */
    public List<String> letterCombinationsUsingQueue(final String digits) {
        final Queue<String> letterCombinationsQueue = new LinkedList<>();
        if (digits == null || digits.isEmpty()) {
            return new ArrayList<>(letterCombinationsQueue);
        }
        final String[] charMap = new String[]{"0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        letterCombinationsQueue.offer("");

        for (int i = 0; i < digits.length(); i++) {
            final int index = Character.getNumericValue(digits.charAt(i));
            final char[] mappedChars = charMap[index].toCharArray();
            addPermutations(mappedChars, letterCombinationsQueue);
        }
        return new ArrayList<>(letterCombinationsQueue);
    }

    private static void addPermutations(final char[] mappedChars,
                                        final Queue<String> letterCombinationsQueue) {
        final int size = letterCombinationsQueue.size();
        for (int j = 0; j < size; j++) { // traverse one level of the tree
            final String permutation = letterCombinationsQueue.poll();
            for (char character : mappedChars) {
                letterCombinationsQueue.offer(permutation + character);
            }
        }
    }

    /**
     * Time Complexity: O(3^M×4^N) ???
     * Space Complexity: O(3^M×4^N) ???
     */
    public List<String> letterCombinationsV0(final String digits) {
        final LinkedList<String> letterCombinations = new LinkedList<>();
        if (digits == null || digits.isEmpty()) {
            return letterCombinations;
        }
        final String[] charMap = new String[]{"0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        letterCombinations.add("");

        for (int i = 0; i < digits.length(); i++) {
            final int index = Character.getNumericValue(digits.charAt(i));
            final char[] mappedChars = charMap[index].toCharArray();
            addPermutations(i, mappedChars, letterCombinations);
        }
        return letterCombinations;
    }

    private static void addPermutations(final int existingPermLength,
                                        final char[] mappedChars,
                                        final LinkedList<String> letterCombinations) {
        while (!letterCombinations.isEmpty() && letterCombinations.peek().length() == existingPermLength) {
            final String permutation = letterCombinations.remove();
            for (char character : mappedChars) {
                letterCombinations.add(permutation + character);
            }
        }
    }
}
