package com.algorithms.geeksforgeeks.easy;

public class KthElementOfTwoSortedArrays {
    /**
     * Given two sorted arrays A and B of size M and N respectively and an element k. The task is to find the element
     * that would be at the k’th position of the final sorted array.
     * <p>
     * https://practice.geeksforgeeks.org/problems/k-th-element-of-two-sorted-array/0
     */

    // Time Complexity: O(k)
    // Auxiliary Space: O(1)
    private static long findKthElement(int[] arr1, int[] arr2, int n, int m, int k) {
        int i = 0;
        int j = 0;
        int kk = 0;
        while (i < n && j < m) {
            final long value;
            if (arr1[i] <= arr2[j]) {
                value = arr1[i++];
            } else {
                value = arr2[j++];
            }
            if (++kk == k) {
                return value;
            }
        }
        while (i < n) {
            final long value = arr1[i++];
            if (++kk == k) {
                return value;
            }
        }
        while (j < m) {
            final long value = arr2[j++];
            if (++kk == k) {
                return value;
            }
        }
        return -1;
    }
}
