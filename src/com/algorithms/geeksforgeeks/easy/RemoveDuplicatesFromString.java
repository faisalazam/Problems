package com.algorithms.geeksforgeeks.easy;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class RemoveDuplicatesFromString {
    /**
     * Given a string, the task is to remove duplicates from it.
     * Expected time complexity O(n) where n is length of input string and extra space O(1)
     * under the assumption that there are total 256 possible characters in a string.
     * <p>
     * Note: that original order of characters must be kept same.
     * <p>
     * https://practice.geeksforgeeks.org/problems/remove-duplicates/0
     * <p>
     * Time Complexity: O(|s|)
     * Auxiliary Space: O(constant)
     */
    // For constant space, declare a frequency array of size 255 (0-based indexing). For every character,
    // check if the frequency is zero or not. If 0, add the character to the resultant string & increase its frequency
    // by 1 in the array. If the frequency of a given character is greater than 0, then leave it as is.
    String removeDups(String S) {
        StringBuilder sb = new StringBuilder();
        int[] frequency = new int[255];
        Arrays.fill(frequency, 0);

        for (int i = 0; i < S.length(); i++) {
            char ch = S.charAt(i);
            if (frequency[ch] == 0) {
                sb.append(ch);
                frequency[ch]++;
            }
        }
        return sb.toString();
    }

    String removeDupsV1(String S) {
        final StringBuilder sb = new StringBuilder();
        // It works with LinkedHashSet too, but we don't really need to use LinkedHashSet as we are appending the
        // characters while traversing the actual string
        final Set<Character> chars = new HashSet<>();
        for (int i = 0; i < S.length(); i++) {
            final char character = S.charAt(i);
            if (chars.add(character)) {
                sb.append(character);
            }
        }
        return sb.toString();
    }

    // Time Complexity: O(n)
    // Auxiliary Space: O(n)
    String removeDupsV0(String S) {
        final Set<Character> chars = new LinkedHashSet<>(); // maintains insertion order
        for (int i = 0; i < S.length(); i++) {
            chars.add(S.charAt(i));
        }
        final StringBuilder sb = new StringBuilder();
        for (Character character : chars) {
            sb.append(character);
        }
        return sb.toString();
    }
}
