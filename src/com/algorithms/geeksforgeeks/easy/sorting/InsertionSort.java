package com.algorithms.geeksforgeeks.easy.sorting;

public class InsertionSort {
    /**
     * Insertion sort is a simple sorting algorithm that works similar to the way you sort playing cards in your hands.
     * The array is virtually split into a sorted and an unsorted part. Values from the unsorted part are picked and
     * placed at the correct position in the sorted part.
     * <p>
     * The task is to complete insert() function which is used to implement Insertion Sort.
     * <p>
     * https://practice.geeksforgeeks.org/problems/insertion-sort/1
     * <p>
     * Time Complexity: O(N²) Two loops are required for the comparison and sorting.
     * Auxiliary Space: O(1) Since No extra Array or space is used.
     */
    static void insertionSort(int[] arr, int n) {
        for (int i = 1; i < n; i++) {
            insert(arr, i);
        }
    }

    private static void insert(int[] arr, int i) {
        final int value = arr[i];
        int hole = i;
        while (hole > 0 && arr[hole - 1] > value) {
            arr[hole] = arr[hole - 1];
            hole--;
        }
        arr[hole] = value;
    }
}
