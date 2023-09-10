package com.algorithms.geeksforgeeks.basic;

import java.util.Arrays;

public class PermutationsOfString {
    /**
     * Given a string S. The task is to print all permutations of a given string.
     * <p>
     * https://practice.geeksforgeeks.org/problems/permutations-of-a-given-string/0
     */
    public static void permutations(String str) {
        char[] word = str.toCharArray();
        Arrays.sort(word);
        int n = word.length;
        boolean[] used = new boolean[n];
        permutations(word, n, used, "");
        System.out.println();
    }

    private static void permutations(char[] a, int size, boolean[] used, String curr) {
        if (curr.length() == size) {
            System.out.print(curr + " ");
            return;
        }
        for (int i = 0; i < size; i++) {
            if (used[i]) {
                continue;
            }
            curr += a[i];
            used[i] = true;
            permutations(a, size, used, curr);

            curr = curr.substring(0, curr.length() - 1);
            used[i] = false;
        }
    }
}
