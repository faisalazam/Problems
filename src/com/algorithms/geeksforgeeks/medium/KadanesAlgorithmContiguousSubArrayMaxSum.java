package com.algorithms.geeksforgeeks.medium;

/**
 * Given an array arr of N integers. Find the contiguous sub-array with maximum sum.
 * <p>
 * Time Complexity: O(N)
 * Space Complexity: O(1)
 * <p>
 * https://practice.geeksforgeeks.org/problems/kadanes-algorithm/0
 *
 * Kadane’s algorithm: This algorithm works on the principle of continuously adding the array value to the current sum
 * so far and update the max sum every time and whenever the current sum becomes negative then initialize it to zero
 * and continue the process, by this approach only one loop is required to compute the maximum sum contiguous subarray
 *
 * Kadane’s Algorithm can be viewed both as greedy and DP. As we can see that we are keeping a running sum of integers
 * and when it becomes less than 0, we reset it to 0 (Greedy Part). This is because continuing with a negative sum is
 * way worse than restarting with a new range. Now it can also be viewed as a DP, at each stage we have 2 choices:
 * Either take the current element and continue with the previous sum OR restart a new range. Both choices are being
 * taken care of in the implementation.
 */
public class KadanesAlgorithmContiguousSubArrayMaxSum {
    /**
     * Time complexity : O(n)
     * Auxiliary space : O(1)
     */
    private static long contiguousSubArraySumImproved(int[] arr, int n) {
        long max = arr[0];
        long currentMax = arr[0];
        for (int i = 1; i < n; i++) {
            // if A[i] is greater, that means, our new subarray is starting at this index i...
            // To print the subarray with the maximum sum the idea is to maintain start index of maximum_sum_ending_here
            // at current index so that whenever maximum_sum_so_far is updated with maximum_sum_ending_here then start
            // index and end index of subarray can be updated with start and current index.
            currentMax = Math.max(arr[i], currentMax + arr[i]);
            max = Math.max(max, currentMax);
        }
        return max;
    }

    /**
     * Time Complexity: O(N)
     * Space Complexity: O(N)
     */
    private static long contiguousSubArraySum(int[] arr, int n) {
        final long[] dp = new long[n];
        dp[0] = arr[0];
        long max = dp[0];
        for (int i = 1; i < n; i++) {
            dp[i] = Math.max(dp[i - 1], 0) + arr[i];
            max = Math.max(max, dp[i]);
        }
        return max;
    }

    /**
     * Brute Force
     * <p>
     * Time Complexity: O(N²)
     * Space Complexity: O(1)
     */
    private static long contiguousSubArraySumV0(int[] arr, int n) { // Results in Time Limit Exceeded
        long max = arr[0];
        for (int i = 0; i < n; i++) {
            long sum = 0;
            for (int j = i; j < n; j++) {
                sum += arr[j];
                max = Math.max(max, sum);
            }
        }
        return max;
    }
}
