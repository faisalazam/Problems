package com.algorithms.leetcode.medium;

public class GlobalAndLocalInversions {
    /**
     * We have some permutation A of [0, 1, ..., N - 1], where N is the length of A.
     * <p>
     * The number of (global) inversions is the number of i < j with 0 <= i < j < N and A[i] > A[j].
     * <p>
     * The number of local inversions is the number of i with 0 <= i < N and A[i] > A[i+1].
     * <p>
     * Return true if and only if the number of global inversions is equal to the number of local inversions.
     * <p>
     * https://leetcode.com/problems/global-and-local-inversions/
     */
    public boolean isIdealPermutation(int[] A) {
        int max = -1;
        for (int i = 0; i < A.length - 2; i++) {
            max = Math.max(max, A[i]);
            if (max > A[i + 2]) { // found global inversion which is not a local inversion
                return false;
            }
        }
        return true;
    }
}
