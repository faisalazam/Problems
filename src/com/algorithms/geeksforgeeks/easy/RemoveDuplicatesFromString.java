package com.algorithms.geeksforgeeks.easy;

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
     */
    void removeDuplicates(String str) {
        Set<Character> chars = new LinkedHashSet<>();
        for (int i = 0; i < str.length(); i++) {
            chars.add(str.charAt(i));
        }
        for (Character character : chars) {
            System.out.print(character);
        }
        System.out.println();
    }
}
