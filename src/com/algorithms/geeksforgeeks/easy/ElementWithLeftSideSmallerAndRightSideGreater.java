package com.algorithms.geeksforgeeks.easy;

public class ElementWithLeftSideSmallerAndRightSideGreater {
    /**
     * Given an unsorted array of size N. Find the first element in array such that all of its left elements
     * are smaller and all right elements to it are greater than it.
     *
     * https://practice.geeksforgeeks.org/problems/unsorted-array/0
     */
    private static int findElement(int[] A, int N) {
        int element = -1;
        int maxSoFar = Integer.MIN_VALUE;
        boolean[] left = new boolean[N];
        left[0] = true;
        for (int i = 1; i < N; i++) {
            maxSoFar = Math.max(maxSoFar, A[i - 1]);
            left[i] = A[i] >= maxSoFar;
        }
        int minSoFar = Integer.MAX_VALUE;
        boolean[] right = new boolean[N];
        right[N - 1] = true;
        for (int i = N - 2; i > 0; i--) {
            minSoFar = Math.min(minSoFar, A[i + 1]);
            right[i] = A[i] <= minSoFar;
        }
        for (int i = 1; i < N - 1; i++) {
            if (left[i] && right[i]) {
                return A[i];
            }
        }
        return element;
    }
}
