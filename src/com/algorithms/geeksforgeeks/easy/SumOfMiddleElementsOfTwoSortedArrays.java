package com.algorithms.geeksforgeeks.easy;

/**
 * Given 2 sorted arrays A and B of size N each. Print sum of middle elements of
 * the array obtained after merging the given arrays.
 * <p>
 * https://practice.geeksforgeeks.org/problems/sum-of-middle-elements-of-two-sorted-arrays/0
 */
public class SumOfMiddleElementsOfTwoSortedArrays {
    private static int findMidSum(int[] A, int[] B, int N) {
        int i = 0;
        int j = 0;
        int k = 0;
        int K = N;
        int K1 = K + 1;
        int kValue = -1;
        while (i < N && j < N) {
            int value;
            if (A[i] <= B[j]) {
                value = A[i++];
            } else {
                value = B[j++];
            }
            if (++k == K) {
                kValue = value;
            } else if (k == K1) {
                return kValue + value;
            }
        }
        while (i < N) {
            int value = A[i++];
            if (++k == K) {
                kValue = value;
            } else if (k == K1) {
                return kValue + value;
            }
        }
        while (j < N) {
            int value = B[j++];
            if (++k == K) {
                kValue = value;
            } else if (k == K1) {
                return kValue + value;
            }
        }
        return -1;
    }
}
