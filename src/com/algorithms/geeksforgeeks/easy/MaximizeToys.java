package com.algorithms.geeksforgeeks.easy;

import java.util.Arrays;

public class MaximizeToys {
    /**
     * Given an array consisting cost of toys. Given an integer K depicting the amount with you.
     * Maximise the number of toys you can have with K amount.
     * <p>
     * https://practice.geeksforgeeks.org/problems/maximize-toys/0
     */
    private static int maxToys(int[] A, int N, int K) {
        int maxToys = 0;
        int spentAmount = 0;
        Arrays.sort(A);
        for (int i = 0; i < N; i++) {
            spentAmount += A[i];
            if (spentAmount <= K) {
                maxToys++;
            } else {
                break;
            }
        }
        return maxToys;
    }
}
