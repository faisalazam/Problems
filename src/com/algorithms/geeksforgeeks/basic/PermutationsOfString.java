package com.algorithms.geeksforgeeks.basic;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * Given a string S. The task is to print all permutations of a given string.
 * <p>
 * https://practice.geeksforgeeks.org/problems/permutations-of-a-given-string/0
 */
public class PermutationsOfString {
    public static List<String> permutations(final String str) { // WIP
        Queue<String> currentLevelQueue = new ArrayDeque<>();
        currentLevelQueue.add(""); // ABC => ABC ACB BAC BCA CAB CBA

        final char[] chars = str.toCharArray();
        for (char character : chars) {
            while (!currentLevelQueue.isEmpty() && currentLevelQueue.peek().length() < chars.length) {
                final Queue<String> permutationsQueue = currentLevelQueue;
                currentLevelQueue = new ArrayDeque<>();
                while (!permutationsQueue.isEmpty()) {
                    final String permutation = permutationsQueue.poll();
                    currentLevelQueue.add(permutation + character);
                }
            }
        }
        final Set<String> uniques = new HashSet<>(currentLevelQueue);
        return new ArrayList<>(uniques);
    }

    public static List<String> permutationsV1(final String str) { // WIP
        final LinkedList<String> perms = new LinkedList<>();
        if (str == null || str.isEmpty()) {
            return perms;
        }
        perms.add("");

        final char[] chars = str.toCharArray();
        for (int i = 0; i < str.length(); i++) {
            while (!perms.isEmpty() && perms.peek().length() == i) {
                final String permutation = perms.remove();
                for (char character : chars) {
                    perms.add(permutation + character);
                }
            }
        }
        return perms;
    }

    public static List<String> permutationsV0(String str) { // Working solution
        final char[] word = str.toCharArray();
        Arrays.sort(word);
        final int n = word.length;
        final boolean[] used = new boolean[n];
        final Set<String> uniquePermutations = new HashSet<>();
        permutations("", word, n, used, uniquePermutations);
        final List<String> result = new ArrayList<>(uniquePermutations);
        Collections.sort(result);
        return result;
    }

    private static void permutations(String curr,
                                     final char[] a,
                                     final int size,
                                     final boolean[] used,
                                     final Set<String> uniquePermutations) {
        if (curr.length() == size) {
            uniquePermutations.add(curr);
            return;
        }
        for (int i = 0; i < size; i++) {
            if (used[i]) {
                continue;
            }
            curr += a[i];
            used[i] = true;
            permutations(curr, a, size, used, uniquePermutations);

            curr = curr.substring(0, curr.length() - 1);
            used[i] = false;
        }
    }

    public static void main(String[] args) {
        System.out.println(permutationsV1("ABC"));
    }
}
