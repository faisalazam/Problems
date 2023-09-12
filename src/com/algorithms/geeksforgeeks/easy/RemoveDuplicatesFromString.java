package com.algorithms.geeksforgeeks.easy;

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
    String removeDups(String S) {
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
        final Set<Character> chars = new LinkedHashSet<>();
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
