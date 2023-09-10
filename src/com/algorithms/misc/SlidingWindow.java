package com.algorithms.misc;

public class SlidingWindow {
    public static void main(String[] args) {
        int[] arr = new int[]{1, 4, 2, 10, 2, 3, 1, 0, 20};
        int k = 4;
        int n = arr.length;
        System.out.println(maxSum(arr, n, k));
        System.out.println(maxSumV1(arr, n, k));
    }

    // Returns maximum sum in a subarray of size k.
    private static int maxSum(int[] arr, int n, int k) {
        // n must be greater
        if (n < k) {
            System.out.println("Invalid");
            return -1;
        }

        // Compute sum of first window of size k
        int maxSum = 0;
        for (int i = 0; i < k; i++)
            maxSum += arr[i];

        // Compute sums of remaining windows by removing first element of previous window
        // and adding last element of current window.
        int windowSum = maxSum;
        for (int i = k; i < n; i++) {
            windowSum += arr[i] - arr[i - k];
            maxSum = Math.max(maxSum, windowSum);
        }
        return maxSum;
    }

    //time complexity is O(k*n)
    private static int maxSumV1(int[] arr, int n, int k) {
        // Initialize result
        int maxSum = Integer.MIN_VALUE;

        // Consider all blocks starting with i.
        for (int i = 0; i < n - k + 1; i++) {
            int currentSum = 0;
            for (int j = 0; j < k; j++) {
                currentSum = currentSum + arr[i + j];
            }
            maxSum = Math.max(currentSum, maxSum);
        }
        return maxSum;
    }
}
