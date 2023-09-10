package com.algorithms.geeksforgeeks.easy.sorting;

public class QuickSort {
    /**
     * Given an array of integers. Complete the partition() function used for the implementation of Quick Sort.
     * <p>
     * Your Task:
     * You don't need to read input or print anything. Your task is to complete the function partition()
     * which takes the array arr[] and the range of indices to be considered [low, high] and partitions the array
     * in the given range considering the last element as the pivot such that, all the elements less than(or equal to)
     * the pivot lie before the elements greater than it.
     * <p>
     * Expected Time Complexity: O(n).
     * Expected Auxiliary Space: O(1).
     * <p>
     * https://practice.geeksforgeeks.org/problems/quick-sort/1
     * https://practice.geeksforgeeks.org/problems/sort-the-array/0
     */
    private static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int partitionIndex = partition(arr, low, high);
            quickSort(arr, low, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, high);
        }
    }

    /* This function takes last element as pivot, places  the pivot element
    at its correct position in sorted  array, and places all smaller (smaller
    than pivot) to left of pivot and all greater elements to right  of pivot */
    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int partitionIndex = low;
        for (int i = low; i < high; i++) {// 2 5 3 6 4
            if (arr[i] <= pivot) {
                swap(arr, i, partitionIndex);
                partitionIndex++;
            }
        }
        swap(arr, partitionIndex, high);
        return partitionIndex;
    }

    /**
     * a = a - b;
     * b = b + a;
     * a = b - a;
     */
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
