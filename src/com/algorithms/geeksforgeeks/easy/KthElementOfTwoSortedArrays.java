package com.algorithms.geeksforgeeks.easy;

public class KthElementOfTwoSortedArrays {
    /**
     * Given two sorted arrays A and B of size M and N respectively and an element k. The task is to find the element
     * that would be at the k’th position of the final sorted array.
     * <p>
     * https://practice.geeksforgeeks.org/problems/k-th-element-of-two-sorted-array/0
     */
    private static int findKthElement(int[] A, int[] B, int N, int M, int K) {
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < N && j < M) {
            int value;
            if (A[i] <= B[j]) {
                value = A[i++];
            } else {
                value = B[j++];
            }
            if (++k == K) {
                return value;
            }
        }
        while (i < N) {
            int value = A[i++];
            if (++k == K) {
                return value;
            }
        }
        while (j < M) {
            int value = B[j++];
            if (++k == K) {
                return value;
            }
        }
        return -1;
    }
}
