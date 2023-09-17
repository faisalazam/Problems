package com.algorithms.geeksforgeeks.easy;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Given an array of distinct integers. The task is to count all the triplets such that sum of two elements equals the
 * third element.
 * <p>
 * https://practice.geeksforgeeks.org/problems/count-the-triplets/0
 */
public class CountTheTriplets {
    /**
     * Time Complexity: O(N²) - The sorting algorithm will take O(NlogN) time. Now there is also, a nested loop,
     * which will give time complexity of O(N²). Hence overall complexity will be O(NlogN + N²) ≈ O(N²)
     * Auxiliary Space: O(1)
     */
    private static int countTriplets(int n, int[] arr) {
        Arrays.sort(arr);

        int count = 0;
        for (int k = n - 1; k > 1; k--) {
            int i = 0;
            int j = k - 1;

            // This algo is working in a way by fixing the k and then traversing the array to find all pairs whose sum == arr[k]
            while (i < j) {
                int sum = arr[i] + arr[j];
                if (sum == arr[k]) {
                    count++;
                    i++;
                    j--;
                } else if (sum < arr[k]) {
                    i++;
                } else {
                    j--;
                }
            }
        }
        return count;
    }

    /**
     * Time Complexity: O(N²)
     * Auxiliary Space: O(N)
     */
    private static int countTripletsV2(int n, int[] arr) {
        int count = 0;
        final Set<Integer> nums = new HashSet<>();
        for (int i = 0; i < n; i++) {
            nums.add(arr[i]);
        }
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int sum = arr[i] + arr[j];
                if (nums.contains(sum)) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Time Complexity: O(N² * logN)
     * Auxiliary Space: O(1)
     */
    private static int countTripletsV1(int n, int[] arr) {
        Arrays.sort(arr);

        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int sum = arr[i] + arr[j];
                if (Arrays.binarySearch(arr, j, n, sum) > j) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Brute Force Approach
     * <p>
     * Time Complexity: O(N^3)
     * Auxiliary Space: O(1)
     */
    private static int countTripletsV0(int n, int[] arr) { // Results in Time Limit Exceeded
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    if (arr[i] + arr[j] == arr[k]
                            || arr[i] + arr[k] == arr[j]
                            || arr[j] + arr[k] == arr[i]) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
//        final int[] arr = {2}; // answer is 0
//        final int[] arr = {2, 3, 4}; // answer is 0
//        final int[] arr = {1, 5, 3, 2}; // answer is 2
//        final int[] arr = {12, 8, 2, 11, 5, 14, 10}; // answer is 3
        final int[] arr = {14, 3, 6, 8, 11, 16}; // answer is 3
        System.out.println(countTriplets(arr.length, arr));
    }
}
