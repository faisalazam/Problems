package com.algorithms.geeksforgeeks.basic.sorting;

public class SelectSort {
    /**
     * https://practice.geeksforgeeks.org/problems/selection-sort/1
     */
    void selectionSort(int[] arr) {
        int n = arr.length;
        for (int i = n - 1; i >= 0; i--) {
            int j = select(arr, i);
            swap(i, j, arr);
        }
    }

    private int select(int[] arr, int i) {
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
    private void swap(int i, int j, int[] arr) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
