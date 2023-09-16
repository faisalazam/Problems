package com.algorithms.geeksforgeeks.basic.sorting;

/**
 * Given an Integer N and a list arr. Sort the array using bubble sort algorithm.
 * <p>
 * https://practice.geeksforgeeks.org/problems/bubble-sort/1
 *
 * The idea is to compare every adjacent element and swap them if the next element is less than the current element.
 * and this process will continue till all the elements of the array come to their correct position.
 */
public class BubbleSort {
    /**
     * Time Complexity: O(N^2).
     * Auxiliary Space: O(1).
     */
    static void bubbleSort(int[] arr, int n) {
        for (int i = 0; i < n - 1; i++) {
            bubble(arr, i, n);
        }
    }

    private static void bubble(int[] arr, int i, int n) {
        for (int j = 0; j < n - i - 1; j++) {
            if (arr[j] > arr[j + 1]) {
                swap(arr, j);
            }
        }
    }

    private static void swap(int[] arr, int j) {
        int temp = arr[j];
        arr[j] = arr[j + 1];
        arr[j + 1] = temp;
    }
}
