package com.algorithms.geeksforgeeks.easy;

public class SubarrayWithGivenSum {
    /**
     * Given an unsorted array A of size N of non-negative integers, find a continuous sub-array
     * which adds to a given number S.
     * <p>
     * https://practice.geeksforgeeks.org/problems/subarray-with-given-sum/0
     * <p>
     * Sliding Window
     */
    void subArrayWithGivenSum(int[] A, int N, int S) {
        int end;
        int sum = 0;
        int start = 0;
        boolean found = false;
        for (int i = 0; i < N; i++) {
            sum += A[i];
            end = i;
            while (sum > S) {
                sum -= A[start];
                start++;
            }
            if (sum == S) {
                found = true;
                System.out.println((start + 1) + " " + (end + 1));
                break;
            }
        }
        if (!found) {
            System.out.println(-1);
        }
    }
}
