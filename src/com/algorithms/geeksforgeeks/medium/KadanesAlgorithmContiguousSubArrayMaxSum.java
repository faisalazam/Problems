package com.algorithms.geeksforgeeks.medium;

public class KadanesAlgorithmContiguousSubArrayMaxSum {
    /**
     * Given an array arr of N integers. Find the contiguous sub-array with maximum sum.
     * <p>
     * Time Complexity: O(N)
     * Space Complexity: O(1)
     * <p>
     * https://practice.geeksforgeeks.org/problems/kadanes-algorithm/0
     */
    private static int contiguousSubArraySumImproved(int[] A, int N) {
        int currentMax = A[0];
        int max = Integer.MIN_VALUE;
        for (int i = 1; i < N; i++) {
            // if A[i] is greater, that means, our new subarray is starting at this index i...
            currentMax = Math.max(A[i], currentMax + A[i]);
            max = Math.max(max, currentMax);
        }
        return max;
    }

    /**
     * Time Complexity: O(N)
     * Space Complexity: O(N)
     */
    private static int contiguousSubArraySum(int[] array, int N) {
        int[] dp = new int[N];

        dp[0] = array[0];

        int max = Integer.MIN_VALUE;
        for (int i = 1; i < N; i++) {
            dp[i] = Math.max(dp[i - 1], 0) + array[i];
            max = Math.max(max, dp[i]);
        }
        return max;
    }
}
