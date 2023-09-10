package com.algorithms.geeksforgeeks.basic.sorting;

public class BubbleSort {
    /**
     * https://practice.geeksforgeeks.org/problems/bubble-sort/1
     */
    void bubbleSort(int[] arr, int n) {
        for (int i = 0; i < n - 1; i++) {
            bubble(arr, i, n);
        }
    }

    private void bubble(int[] arr, int i, int n) {
        for (int j = 0; j < n - i - 1; j++) {
            if (arr[j] > arr[j + 1]) {
                int temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
            }
        }
    }
}
