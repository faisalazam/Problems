package com.algorithms.geeksforgeeks.medium;

/**
 * Given an array arr of N integers. Find the contiguous sub-array with maximum sum.
 * <p>
 * Time Complexity: O(N)
 * Space Complexity: O(1)
 * <p>
 * https://practice.geeksforgeeks.org/problems/kadanes-algorithm/0
 * https://leetcode.com/problems/maximum-subarray/
 * <p>
 * Kadane’s algorithm: This algorithm works on the principle of continuously adding the array value to the current sum
 * so far and update the max sum every time and whenever the current sum becomes negative then initialize it to zero
 * and continue the process, by this approach only one loop is required to compute the maximum sum contiguous subarray
 * <p>
 * Kadane’s Algorithm can be viewed both as greedy and DP. As we can see that we are keeping a running sum of integers
 * and when it becomes less than 0, we reset it to 0 (Greedy Part). This is because continuing with a negative sum is
 * way worse than restarting with a new range. Now it can also be viewed as a DP, at each stage we have 2 choices:
 * Either take the current element and continue with the previous sum OR restart a new range. Both choices are being
 * taken care of in the implementation.
 */
public class KadanesAlgorithmContiguousSubArrayMaxSum {
    /**
     * Kadane Algorithm
     * Same as the below {@link #maxSubArrayImproved(int[])} method but just written little differently
     * <p>
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
            currentMax = Long.max(arr[i], currentMax + arr[i]);
            max = Long.max(max, currentMax);
        }
        return max;
    }

    /**
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public int maxSubArrayImproved(int[] nums) {
        int currentSum = 0;
        int maxSum = Integer.MIN_VALUE;
        for (int num : nums) {
            // if at any point sum becomes negative then no point keeping it because 0 is obviously greater than
            // negative, so just make your sum 0.
            if (currentSum < 0) {
                currentSum = 0;
            }
            currentSum += num;
            maxSum = Integer.max(currentSum, maxSum);
        }
        return maxSum;
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
            dp[i] = Long.max(dp[i - 1], 0) + arr[i];
            max = Long.max(max, dp[i]);
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
            long currentSum = 0;
            for (int j = i; j < n; j++) {
                currentSum += arr[j];
                max = Long.max(max, currentSum);
            }
        }
        return max;
    }

    /**
     * Brute Force
     * <p>
     * Time Complexity: O(n^3)
     * Space Complexity: O(1)
     */
    public int maxSubArrayBruteForce(int[] nums) { // Results in Time Limit Exceeded
        final int size = nums.length;
        int maxSum = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            for (int j = i; j < size; j++) {
                int currentSum = 0;
                for (int k = i; k <= j; k++) {
                    currentSum += nums[k];
                }
                maxSum = Integer.max(currentSum, maxSum);
            }
        }
        return maxSum;
    }
}
