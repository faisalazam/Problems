package com.algorithms.geeksforgeeks.easy;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Given two sorted arrays A and B of size M and N respectively and an element k. The task is to find the element
 * that would be at the k’th position of the final sorted array.
 * <p>
 * https://practice.geeksforgeeks.org/problems/k-th-element-of-two-sorted-array/0
 * <p>
 * Expected Time Complexity: O(Log(N) + Log(M))
 * Expected Auxiliary Space: O(Log (N))
 * <p>
 * See for more optimised solutions:
 * https://www.geeksforgeeks.org/k-th-element-two-sorted-arrays/
 */
public class KthElementOfTwoSortedArrays {

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

    /**
     * Using Min Heap
     * <p>
     * Push the elements of both arrays to a priority queue (min-heap).
     * Pop-out k-1 elements from the front.
     * Element at the front of the priority queue is the required answer.
     * <p>
     * Time Complexity: O(NlogN)
     * Auxiliary Space: O(m+n) or O(k)?
     */
    private static long findKthElementV1(int[] arr1, int[] arr2, int n, int m, int k) {
        final Queue<Long> priorityQueue = new PriorityQueue<>(k);
        for (int i = 0; i < n; i++) {
            priorityQueue.offer((long) arr1[i]);
        }

        for (int i = 0; i < m; i++) {
            priorityQueue.offer((long) arr2[i]);
        }
        while (k-- > 1) {
            priorityQueue.remove();
        }
        return priorityQueue.isEmpty() ? -1 : priorityQueue.peek();
    }

    /**
     * Time Complexity: O(n)
     * Auxiliary Space : O(m + n)
     */
    private static long findKthElementV0(int[] arr1, int[] arr2, int n, int m, int k) {
        final int[] merged = new int[m + n];
        int i = 0;
        int j = 0;
        int kk = 0;
        while (i < m && j < n) {
            if (arr1[i] < arr2[j]) {
                merged[kk++] = arr1[i++];
            } else {
                merged[kk++] = arr2[j++];
            }
        }
        while (i < m) {
            merged[kk++] = arr1[i++];
        }
        while (j < n) {
            merged[kk++] = arr2[j++];
        }
        return merged[k - 1];
    }
}
