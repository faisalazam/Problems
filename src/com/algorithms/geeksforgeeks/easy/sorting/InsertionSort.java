package com.algorithms.geeksforgeeks.easy.sorting;

public class InsertionSort {
    /**
     * The task is to complete insert() function which is used to implement Insertion Sort.
     * <p>
     * https://practice.geeksforgeeks.org/problems/insertion-sort/1
     */
    static void insertionSort(int[] arr, int n) {
        for (int i = 1; i < n; i++) {
            insert(arr, i);
        }
    }

    private static void insert(int[] arr, int i) {
        int value = arr[i];
        int hole = i;
        while (hole > 0 && arr[hole - 1] > value) {
            arr[hole] = arr[hole - 1];
            hole--;
        }
        arr[hole] = value;
    }
}
