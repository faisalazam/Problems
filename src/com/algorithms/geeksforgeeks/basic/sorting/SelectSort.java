package com.algorithms.geeksforgeeks.basic.sorting;

/**
 * https://practice.geeksforgeeks.org/problems/selection-sort/1
 * <p>
 * The good thing about selection sort is it never makes more than O(n) swaps and can be useful when memory write
 * is a costly operation.
 * <p>
 * The default implementation is not stable. However it can be made stable. It is an in-place sorting algorithm.
 */
public class SelectSort {
    /**
     * Time Complexity: O(N2)
     * Auxiliary Space: O(1)
     */
    void selectionSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            int j = select(arr, i);
            swap(i, j, arr);
        }
    }

    private int select(int[] arr, int i) {
        int minIndex = i;
        for (int j = i + 1; j < arr.length; j++) {
            if (arr[j] < arr[minIndex]) {
                minIndex = j;
            }
        }
        return minIndex;
    }

    /**
     * Time Complexity: O(N2)
     * Auxiliary Space: O(1)
     */
    void selectionSortV0(int[] arr) {
        int n = arr.length;
        for (int i = n - 1; i >= 0; i--) {
            int j = selectV0(arr, i);
            swap(i, j, arr);
        }
    }

    private int selectV0(int[] arr, int i) {
        int maxIndex = i;
        for (int j = i - 1; j >= 0; j--) {
            if (arr[j] > arr[maxIndex]) {
                maxIndex = j;
            }
        }
        return maxIndex;
    }

    /**
     * Swapping without temp variable
     * a = a - b;
     * b = b + a;
     * a = b - a;
     */
    private void swap(int i, int j, int[] arr) { // swapping without temp variable
        if (i != j) {
            arr[i] = arr[i] - arr[j];
            arr[j] = arr[j] + arr[i];
            arr[i] = arr[j] - arr[i];
        }
    }
}
