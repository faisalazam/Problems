package com.algorithms.geeksforgeeks.easy;

import java.util.Arrays;

/**
 * Given an array A of size N containing 0s, 1s, and 2s; you need to sort the array in ascending order.
 * <p>
 * https://practice.geeksforgeeks.org/problems/sort-an-array-of-0s-1s-and-2s/0
 */
public class SortAnArrayOf0sAnd1sAnd2s {
    /**
     * We will use Three-way partitioning method. This approach will divide the array into four sections using
     * three-pointers, i.e., Low, Mid, and High and will sort the array in a single traversal.
     * <p>
     * Time Complexity: O(N), where N = size of the given array. We are using a single loop that can run at most N times.
     * Space Complexity: O(1) as we are not using any extra space.
     */
    public static void sort(int n, int[] a) {
        int lo = 0;
        int mid = 0;
        int high = n - 1;
        while (mid <= high) {
            switch (a[mid]) {
                case 0:
                    // Swap element at lo pointer with element at mid pointer
                    a[mid] = a[lo];
                    a[lo] = 0;
                    lo++;
                    mid++;
                    break;
                case 1:
                    // No need to do anything as we want all the 1's to be in the middle...
                    mid++;
                    break;
                case 2:
                    // Swap element at high pointer with element at mid pointer
                    a[mid] = a[high];
                    a[high] = 2;
                    high--;
                    break;
            }
        }
    }

    /**
     * Using the counting variables. Since in this case there are only 3 distinct values in the array so it’s easy to
     * maintain the count of all, Like the count of 0, 1, and 2. This can be followed by overwriting the array based
     * on the frequency(count) of the values.
     * <p>
     * Time Complexity: O(N), where N = size of the given array. We are using a single loop that can run at most N times.
     * Space Complexity: O(1) as we are not using any extra space.
     */
    public static void sortV1(int n, int[] a) {
        int zeros = 0;
        int ones = 0;
        // No need to count 2's explicitly as after 0's and 1's, the remaining indices can be filled with 2's.
        for (int i = 0; i < n; i++) {
            switch (a[i]) {
                case 0:
                    zeros++;
                    break;
                case 1:
                    ones++;
                    break;
            }
        }
        for (int i = 0; i < n; i++) {
            if (i < zeros) {
                a[i] = 0;
            } else if (i < zeros + ones) {
                a[i] = 1;
            } else {
                a[i] = 2;
            }
        }
    }

    /**
     * Brute Force Approach: just use the built-in sort function
     * <p>
     * Time Complexity: O(N*logN), As sorting takes NlogN time.
     * Space Complexity: O(1), As we are not using any extra space.
     */
    public static void sortV0(int n, int[] a) {
        Arrays.sort(a);
    }
}
