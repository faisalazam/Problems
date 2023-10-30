package com.algorithms.geeksforgeeks.easy;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Given 2 sorted arrays A and B of size N each. Print sum of middle elements of
 * the array obtained after merging the given arrays.
 * <p>
 * https://practice.geeksforgeeks.org/problems/sum-of-middle-elements-of-two-sorted-arrays/0
 * <p>
 * Expected Time Complexity: O(log N)
 * Expected Auxiliary Space: O(1)
 */
public class SumOfMiddleElementsOfTwoSortedArrays {

    // Time Complexity: O(n)
    // Auxiliary Space: O(1)
    private static int findMidSum(int[] ar1, int[] ar2, int n) {
        int i = 0;
        int j = 0;
        int k = 0;
        int K = n;
        int K1 = K + 1;
        int kValue = -1;
        while (i < n && j < n) {
            int value;
            if (ar1[i] <= ar2[j]) {
                value = ar1[i++];
            } else {
                value = ar2[j++];
            }
            if (++k == K) {
                kValue = value;
            } else if (k == K1) {
                return kValue + value;
            }
        }
        while (i < n) {
            int value = ar1[i++];
            if (++k == K) {
                kValue = value;
            } else if (k == K1) {
                return kValue + value;
            }
        }
        while (j < n) {
            int value = ar2[j++];
            if (++k == K) {
                kValue = value;
            } else if (k == K1) {
                return kValue + value;
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
     * Auxiliary Space: O(2n) => O(n)
     */
    private static int findMidSumV1(int[] ar1, int[] ar2, int n) { // Results in Time Limit Exceeded sometimes
        final Queue<Integer> priorityQueue = new PriorityQueue<>();
        for (int i = 0; i < n; i++) {
            priorityQueue.offer(ar1[i]);
            priorityQueue.offer(ar2[i]);
        }

        int value = 0;
        int x = 2 * n;
        while (!priorityQueue.isEmpty()) {
            if (x == n + 1) {
                value = priorityQueue.peek();
            } else if (x == n) {
                return value + priorityQueue.peek();
            }
            priorityQueue.poll();
            x--;
        }
        return -1;
    }

    /**
     * Time Complexity: O(n)
     * Auxiliary Space : O(2n) => O(n)
     */
    private static int findMidSumV0(int[] ar1, int[] ar2, int n) {
        int i = 0;
        int j = 0;
        int k = 0;
        final int[] merged = new int[2 * n];
        while (i < n && j < n) {
            if (ar1[i] < ar2[j]) {
                merged[k++] = ar1[i++];
            } else {
                merged[k++] = ar2[j++];
            }
        }
        while (i < n) {
            merged[k++] = ar1[i++];
        }
        while (j < n) {
            merged[k++] = ar2[j++];
        }
        return merged[n - 1] + merged[n];
    }
}
